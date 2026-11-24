package com.banking.model;

import com.banking.service.Transaction;

/**
 * Account
 * 
 * Abstract base class representing a generic bank account.
 * Implements the Transaction interface and demonstrates:
 * - Encapsulation (protected/private fields, public getters, validation)
 * - Abstraction (declares abstract withdraw method for subclasses to implement)
 */
public abstract class Account implements Transaction {

    protected final String accountNumber;
    protected final String accountHolder;
    protected double balance;

    public Account(String accountNumber, String accountHolder, double initialBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or empty.");
        }
        if (accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException("Account holder name cannot be null or empty.");
        }
        if (initialBalance < 0.0) {
            throw new IllegalArgumentException("Initial balance cannot be negative. Provided: " + initialBalance);
        }
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Deposit amount must be strictly positive. Provided: " + amount);
        }
        this.balance += amount;
        System.out.printf("[DEPOSIT SUCCESS] Account %s credited with $%.2f. New Balance: $%.2f%n",
                accountNumber, amount, balance);
    }

    /**
     * Abstract withdrawal method to be customized by specific account types
     * (e.g. checking overdraft limit or savings minimum balance).
     */
    @Override
    public abstract boolean withdraw(double amount);

    @Override
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    @Override
    public String toString() {
        return String.format("%s [Number: %s, Holder: %s, Balance: $%.2f]",
                getClass().getSimpleName(), accountNumber, accountHolder, balance);
    }
}
