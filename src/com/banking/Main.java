package com.banking;

import com.banking.model.CheckingAccount;
import com.banking.model.Customer;
import com.banking.model.SavingsAccount;
import com.banking.service.BankingService;

/**
 * Main
 * 
 * Entry point testing and demonstrating all core OOP principles in the Banking System:
 * 1. Abstraction: Contract defined by Transaction interface & Account abstract class.
 * 2. Encapsulation: State is protected/private, accessed and modified only via validated methods.
 * 3. Inheritance: SavingsAccount and CheckingAccount inherit identity, balance, and logic from Account.
 * 4. Polymorphism: Operations like withdraw() and transfer() work polymorphically across subclasses.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("            BANKING SYSTEM OOP ARCHITECTURE DEMO                 ");
        System.out.println("=================================================================");

        BankingService bankingService = new BankingService();

        // 1. Create a Customer
        Customer customer = new Customer("CUST-1001", "Dr. Alexander Wright");
        System.out.println("Created Customer: " + customer);

        // 2. Instantiate different account types (Inheritance & Polymorphism)
        // Savings Account: Initial $5,000, 4.5% APY, $500 minimum balance
        SavingsAccount savings = new SavingsAccount(
                "SA-9011",
                customer.getFullName(),
                5000.0,
                0.045,
                500.0
        );

        // Checking Account: Initial $1,500, $1,000 overdraft limit
        CheckingAccount checking = new CheckingAccount(
                "CA-4022",
                customer.getFullName(),
                1500.0,
                1000.0
        );

        // 3. Link accounts to customer (Aggregation)
        customer.addAccount(savings);
        customer.addAccount(checking);

        // 4. Print initial portfolio
        bankingService.printCustomerPortfolio(customer);

        // 5. Demonstrate Deposits
        System.out.println(">>> SCENARIO 1: Standard Deposits");
        savings.deposit(1200.0);
        checking.deposit(500.0);

        // 6. Demonstrate Savings Account Withdrawal & Minimum Balance Constraint
        System.out.println("\n>>> SCENARIO 2: Savings Account Withdrawal & Rule Enforcement");
        System.out.println("-- Attempting valid withdrawal of $2,000.00:");
        savings.withdraw(2000.0);

        System.out.println("\n-- Attempting invalid withdrawal that breaches minimum balance ($500.00):");
        // Balance is currently $4,200. Attempting to withdraw $4,000 would leave $200, violating $500 minimum
        savings.withdraw(4000.0);

        // Apply monthly interest
        System.out.println("\n-- Applying monthly interest to Savings Account:");
        savings.applyInterest();

        // 7. Demonstrate Checking Account Overdraft
        System.out.println("\n>>> SCENARIO 3: Checking Account Overdraft Support");
        System.out.println("Current Checking Balance: $" + checking.getBalance());
        System.out.println("-- Withdrawing $2,200.00 (Exceeds $2,000 balance, enters overdraft):");
        checking.withdraw(2200.0);

        System.out.println("\n-- Attempting withdrawal exceeding total overdraft limit:");
        // Balance is now -$200. Withdrawing another $900 would make balance -$1,100, exceeding $1,000 limit
        checking.withdraw(900.0);

        // 8. Demonstrate Inter-Account Transfer (Polymorphic Transfer Service)
        System.out.println("\n>>> SCENARIO 4: Inter-Account Transfer (Polymorphic Transaction)");
        bankingService.transfer(savings, checking, 800.0);

        // 9. Display Final Portfolio
        System.out.println("\n>>> FINAL AUDIT: Updated Customer Portfolio");
        bankingService.printCustomerPortfolio(customer);

        System.out.println("=================================================================");
        System.out.println("         ALL OOP DEMONSTRATION SCENARIOS COMPLETED               ");
        System.out.println("=================================================================");
    }
}
