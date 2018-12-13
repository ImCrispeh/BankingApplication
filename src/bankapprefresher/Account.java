/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

/**
 *
 * @author chris
 */
public class Account {
    private int accountNumber;
    private int customerId;
    private String accountName;
    private int balance;

    public Account(int accountNumber, int customerId, String accountName) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountName = accountName;
    }

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
    
    public void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }
    
    public void deposit(int amount) {
        balance += amount;
    }
}
