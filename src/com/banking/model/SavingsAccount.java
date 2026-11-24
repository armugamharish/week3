package com.banking.model;

/**
 * SavingsAccount
 * 
 * Subclass of Account that enforces a minimum balance requirement and provides
 * interest accrual capability. Demonstrates Inheritance and Polymorphism.
 */
public class SavingsAccount extends Account {

    private final double annualInterestRate; // e.g. 0.04 for 4.0%
    private final double minimumBalance;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance,
                          double annualInterestRate, double minimumBalance) {
        super(accountNumber, accountHolder, initialBalance);
        if (annualInterestRate < 0.0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        if (minimumBalance < 0.0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }
        if (initialBalance < minimumBalance) {
            throw new IllegalArgumentException(String.format(
                    "Initial balance ($%.2f) must meet or exceed minimum balance requirement ($%.2f).",
                    initialBalance, minimumBalance));
        }
        this.annualInterestRate = annualInterestRate;
        this.minimumBalance = minimumBalance;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0.0) {
            System.out.printf("[WITHDRAW FAILED] Withdrawal amount must be positive. Provided: $%.2f%n", amount);
            return false;
        }

        if (balance - amount < minimumBalance) {
            System.out.printf("[WITHDRAW REJECTED] Account %s: Withdrawing $%.2f would violate minimum balance of $%.2f. Current Balance: $%.2f%n",
                    accountNumber, amount, minimumBalance, balance);
            return false;
        }

        balance -= amount;
        System.out.printf("[WITHDRAW SUCCESS] Account %s debited with $%.2f. New Balance: $%.2f%n",
                accountNumber, amount, balance);
        return true;
    }

    /**
     * Calculates and applies monthly interest based on the annual interest rate.
     */
    public void applyInterest() {
        double monthlyInterest = balance * (annualInterestRate / 12.0);
        balance += monthlyInterest;
        System.out.printf("[INTEREST APPLIED] Account %s earned $%.2f monthly interest (%.2f%% annual). New Balance: $%.2f%n",
                accountNumber, monthlyInterest, annualInterestRate * 100.0, balance);
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    @Override
    public String toString() {
        return String.format("SavingsAccount [Number: %s, Holder: %s, Balance: $%.2f, MinBalance: $%.2f, APY: %.2f%%]",
                accountNumber, accountHolder, balance, minimumBalance, annualInterestRate * 100.0);
    }
}
