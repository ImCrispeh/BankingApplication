/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

import java.util.*;

/**
 * Utility class for generating IDs for customers and their bank accounts
 * @author chris
 */
public class Util {
    private static ArrayList<Integer> usedAccountNumbers = new ArrayList<>();
    private static int nextCustomerId = 0;
    
    /**
     * Generates a new random account number
     * @return 
     */
    public static int generateAccountNumber() {
        int accountNum;
        Random rand = new Random();
        do {
            accountNum = rand.nextInt(999999) + 1;
        } while (usedAccountNumbers.contains(accountNum));
        usedAccountNumbers.add(accountNum);
        return accountNum;
    }
    
    /**
     * Generates a new customer ID
     * @return 
     */
    public static int generateCustomerId() {
        nextCustomerId++;
        return nextCustomerId;
    }
}
