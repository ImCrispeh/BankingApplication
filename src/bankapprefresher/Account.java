/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

/**
 * Provides basic functionality of a bank account such as withdrawing and
 * depositing money
 * TODO: add ability to transfer (might not even need a new method, combine withdraw/deposit)
 * @author chris
 */
public class Account {
    private int accountNumber;
    private int customerId;
    private String accountName;
    private int balance;

    //Constructor
    public Account(int accountNumber, int customerId, String accountName) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountName = accountName;
    }

    //Getters and setters
    public int getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getBalance() {
        return balance;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    /**
     * Withdraws the specified amount from the account balance if possible
     * @param amount amount to withdraw
     */
    public void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }
    
    /**
     * Deposits the specified amount into the account balance
     * @param amount amount to deposit 
     */
    public void deposit(int amount) {
        balance += amount;
    }
}
