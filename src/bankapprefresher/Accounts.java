/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author chris
 */
public class Accounts {
    private static ArrayList<Account> accounts = new ArrayList<>();;
    
    public static void createAccount(int accountNumber, int customerId, String accountName) {
        Account newAccount = new Account(accountNumber, customerId, accountName);
        accounts.add(newAccount);
    }
    
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
