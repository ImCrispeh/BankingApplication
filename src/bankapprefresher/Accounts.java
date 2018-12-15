/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Overall collection of accounts, provides the ability to add and remove
 * @author chris
 */
public class Accounts {
    private static ArrayList<Account> accounts = new ArrayList<>();;
    
    public static ArrayList<Account> getAllAccounts() {
        return accounts;
    }
    
    public static ArrayList<Account> getCustomerAccounts(int customerId) {
        ArrayList<Account> customerAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getCustomerId() == customerId) {
                customerAccounts.add(account);
            }
        }
        return customerAccounts;
    }
    
    public static void updateAccounts(ArrayList<Account> oldAccounts, ArrayList<Account> newAccounts) {
        accounts.removeAll(oldAccounts);
        accounts.addAll(newAccounts);
    }
    
    /**
     * Creates an account with specified parameters and adds it to the collection
     * @param accountNumber
     * @param customerId
     * @param accountName 
     */
    public static void createAccount(int accountNumber, int customerId, String accountName) {
        Account newAccount = new Account(accountNumber, customerId, accountName);
        accounts.add(newAccount);
    }
    
    /**
     * Removes an account from the collection after checking that it exists
     * @param accountNumber
     * @param customerId
     * @return if the account was found/removed or not
     */
    public static boolean removeAccount(int accountNumber, int customerId) {
        Optional<Account> account = accounts.stream()
                .filter(acc -> acc.getAccountNumber() == accountNumber && acc.getCustomerId() == customerId)
                .findFirst();
        
        if (account.isPresent()) {
            accounts.remove(account.get());
            return true;
        } else {
            return false;
        }
    }
}
