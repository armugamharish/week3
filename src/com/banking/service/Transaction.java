package com.banking.service;

/**
 * Transaction
 * 
 * Interface defining the standard financial transaction contract for bank accounts.
 * Demonstrates Abstraction by specifying operations without committing to implementation details.
 */
public interface Transaction {

    /**
     * Deposits money into the account.
     * 
     * @param amount the non-negative amount to deposit
     */
    void deposit(double amount);

    /**
     * Withdraws money from the account subject to subclass validation rules.
     * 
     * @param amount the positive amount to withdraw
     * @return true if withdrawal succeeded, false otherwise
     */
    boolean withdraw(double amount);

    /**
     * Retrieves the current balance.
     * 
     * @return current account balance
     */
    double getBalance();
}
