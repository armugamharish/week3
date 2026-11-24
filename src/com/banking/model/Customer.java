package com.banking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Customer
 * 
 * Represents a banking customer who can own multiple bank accounts (both Savings and Checking).
 * Demonstrates Aggregation/Composition (one Customer has-a collection of Accounts) and Encapsulation.
 */
public class Customer {

    private final String customerId;
    private final String fullName;
    private final List<Account> accounts;

    public Customer(String customerId, String fullName) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty.");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Customer full name cannot be null or empty.");
        }
        this.customerId = customerId;
        this.fullName = fullName;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Cannot add a null account.");
        }
        // Check for duplicate account number
        for (Account existing : accounts) {
            if (existing.getAccountNumber().equalsIgnoreCase(account.getAccountNumber())) {
                throw new IllegalArgumentException("Account number already exists for this customer: " + account.getAccountNumber());
            }
        }
        accounts.add(account);
        System.out.printf("[ACCOUNT LINKED] Account %s successfully linked to customer %s (%s).%n",
                account.getAccountNumber(), fullName, customerId);
    }

    public Account getAccount(String accountNumber) {
        if (accountNumber == null) return null;
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public double getTotalNetWorth() {
        double total = 0.0;
        for (Account acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return String.format("Customer [ID: %s, Name: %s, Total Accounts: %d, Net Worth: $%.2f]",
                customerId, fullName, accounts.size(), getTotalNetWorth());
    }
}
