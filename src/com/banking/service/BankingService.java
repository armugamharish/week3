package com.banking.service;

import com.banking.model.Account;
import com.banking.model.Customer;

/**
 * BankingService
 * 
 * Provides domain business operations across accounts, including inter-account
 * money transfers with transactional integrity and portfolio summary reports.
 */
public class BankingService {

    /**
     * Transfers funds from a source account to a destination account atomically.
     * Demonstrates Polymorphism: works transparently across any combination of Account subclasses.
     * 
     * @param fromAccount source account to debit
     * @param toAccount   destination account to credit
     * @param amount      amount to transfer
     * @return true if transfer was completely successful; false otherwise
     */
    public boolean transfer(Account fromAccount, Account toAccount, double amount) {
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("[TRANSFER INITIATED] Transferring $%.2f from %s to %s...%n",
                amount,
                fromAccount != null ? fromAccount.getAccountNumber() : "null",
                toAccount != null ? toAccount.getAccountNumber() : "null");

        if (fromAccount == null || toAccount == null) {
            System.out.println("[TRANSFER FAILED] Both source and destination accounts must be specified.");
            return false;
        }

        if (fromAccount.getAccountNumber().equalsIgnoreCase(toAccount.getAccountNumber())) {
            System.out.println("[TRANSFER FAILED] Source and destination account cannot be the same.");
            return false;
        }

        if (amount <= 0.0) {
            System.out.printf("[TRANSFER FAILED] Transfer amount must be positive. Provided: $%.2f%n", amount);
            return false;
        }

        // Attempt withdrawal from source account
        boolean debitSuccess = fromAccount.withdraw(amount);
        if (!debitSuccess) {
            System.out.println("[TRANSFER ABORTED] Debit failed. Transfer operation rolled back.");
            return false;
        }

        // Complete deposit into destination account
        try {
            toAccount.deposit(amount);
            System.out.printf("[TRANSFER COMPLETED] Successfully transferred $%.2f from %s to %s.%n",
                    amount, fromAccount.getAccountNumber(), toAccount.getAccountNumber());
            return true;
        } catch (Exception ex) {
            // Rollback in case of unexpected deposit error
            System.out.println("[CRITICAL ERROR] Deposit failed, executing compensatory rollback...");
            fromAccount.deposit(amount);
            return false;
        }
    }

    /**
     * Prints a comprehensive, formatted financial portfolio overview for a customer.
     */
    public void printCustomerPortfolio(Customer customer) {
        if (customer == null) {
            System.out.println("Cannot print portfolio for a null customer.");
            return;
        }

        System.out.println("=================================================================");
        System.out.printf(" CUSTOMER FINANCIAL PORTFOLIO: %s (ID: %s)%n",
                customer.getFullName(), customer.getCustomerId());
        System.out.println("=================================================================");
        System.out.printf("%-15s %-18s %-14s %-20s%n", "Account No", "Type", "Balance", "Details");
        System.out.println("-".repeat(65));

        if (customer.getAccounts().isEmpty()) {
            System.out.println("No accounts currently registered under this customer.");
        } else {
            for (Account acc : customer.getAccounts()) {
                String type = acc.getClass().getSimpleName();
                String details = acc.toString();
                System.out.printf("%-15s %-18s $%-13.2f %s%n",
                        acc.getAccountNumber(), type, acc.getBalance(), details);
            }
        }

        System.out.println("-".repeat(65));
        System.out.printf("Total Net Worth Across All Accounts: $%.2f%n", customer.getTotalNetWorth());
        System.out.println("=================================================================\n");
    }
}
