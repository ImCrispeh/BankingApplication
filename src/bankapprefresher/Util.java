/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

import java.util.*;

/**
 *
 * @author chris
 */
public class Util {
    private static ArrayList<Integer> usedAccountNumbers = new ArrayList<>();
    private static int nextCustomerId = 0;
    
    public static int generateAccountNumber() {
        int accountNum;
        Random rand = new Random();
        do {
            accountNum = rand.nextInt(999999) + 1;
        } while (!usedAccountNumbers.contains(accountNum));
        usedAccountNumbers.add(accountNum);
        return accountNum;
    }
    
    public static int generateCustomerId() {
        nextCustomerId++;
        return nextCustomerId;
    }
}
