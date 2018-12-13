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
    private ArrayList<Account> accounts;
    
    public Accounts() {
        accounts = new ArrayList<>();
    }
    
    public void createAccount(int accountNumber, String accountName) {
        Account newAccount = new Account(accountNumber, accountName);
        accounts.add(newAccount);
    }
    
    public boolean removeAccount(int accountNumber) {
        Optional<Account> account = accounts.stream()
                .filter(acc -> acc.getAccountNumber() == accountNumber)
                .findFirst();
        
        if (account.isPresent()) {
            accounts.remove(account.get());
            return true;
        } else {
            return false;
        }
    }
}
