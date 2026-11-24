package com.banking.model;

/**
 * CheckingAccount
 * 
 * Subclass of Account allowing overdrafts up to an approved limit.
 * Demonstrates Inheritance and Polymorphic method overriding.
 */
public class CheckingAccount extends Account {

    private final double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolder, double initialBalance, double overdraftLimit) {
        super(accountNumber, accountHolder, initialBalance);
        if (overdraftLimit < 0.0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative. Provided: " + overdraftLimit);
        }
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0.0) {
            System.out.printf("[WITHDRAW FAILED] Withdrawal amount must be positive. Provided: $%.2f%n", amount);
            return false;
        }

        double maximumAvailable = balance + overdraftLimit;
        if (amount > maximumAvailable) {
            System.out.printf("[WITHDRAW REJECTED] Account %s: Requested $%.2f exceeds available funds ($%.2f) including overdraft limit of $%.2f.%n",
                    accountNumber, amount, balance, overdraftLimit);
            return false;
        }

        balance -= amount;
        System.out.printf("[WITHDRAW SUCCESS] Account %s debited with $%.2f. New Balance: $%.2f (Overdraft Limit: $%.2f)%n",
                accountNumber, amount, balance, overdraftLimit);
        return true;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public String toString() {
        return String.format("CheckingAccount [Number: %s, Holder: %s, Balance: $%.2f, OverdraftLimit: $%.2f]",
                accountNumber, accountHolder, balance, overdraftLimit);
    }
}
