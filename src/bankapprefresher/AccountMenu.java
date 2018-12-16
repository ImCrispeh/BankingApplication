/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

import java.util.*;

/**
 * Provides a command line menu after logging into a customer account
 * @author chris
 */
public class AccountMenu {
    private Account account;
    private Scanner scan;
    private boolean isMenuEnd;
    
    //Constructor
    public AccountMenu(Account account) {
        scan = new Scanner(System.in);
        this.account = account;
        isMenuEnd = false;
    }
    
    /**
     * Provides initial message and displays menu options
     */
    public void showAccountMenu() {
        System.out.println("\nPerforming actions on " + account.getAccountName() + " (" + account.getAccountNumber() + ")");
        String input = "";
        
        while (!isMenuEnd) {
            System.out.println("------------------------------");
            System.out.println("OPTIONS");
            System.out.println("------------------------------");
            System.out.println("a. Withdraw");
            System.out.println("b. Deposit");
            System.out.println("c. Transfer");
            System.out.println("d. Rename account");
            System.out.println("e. Delete account");
            System.out.println("x. Go back");
            System.out.print("Please make a selection: ");
            input = scan.nextLine();
            accountMenuAction(input);
        }
    }
    
    /**
     * Calls specific handler based on user input
     * @param choice user input from menu
     */
    private void accountMenuAction(String choice) {
        System.out.println();
        choice = choice.toLowerCase();
        switch (choice) {
            case "a":
                withdraw();
                break;
            case "b":
                deposit();
                break;
            case "c":
                transfer();
                break;
            case "d":
                renameAccount();
                break;
            case "e":
                deleteAccount();
                break;
            case "x":
                goBack();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
        System.out.println();
    }
    
    private void withdraw() {
        System.out.println("Please enter the amount to withdraw");
        System.out.println("------------------------------");
        System.out.print("Amount: ");
        int amount = Integer.parseInt(scan.nextLine());
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            System.out.println("Withdraw sucessful");
        } else {
            System.out.println("Cannot withdraw that amount");
        }
    }
    
    private void deposit() {
        System.out.println("Please enter the amount to deposit");
        System.out.println("------------------------------");
        System.out.print("Amount: ");
        int amount = Integer.parseInt(scan.nextLine());
        account.deposit(amount);
        System.out.println("Deposit sucessful");
    }
    
    private void transfer() {
        
    }
    
    private void renameAccount() {
        
    }
    
    private void deleteAccount() {
        System.out.println("Deleting account and returning to main menu");
        Accounts.removeAccount(account.getAccountNumber());
        isMenuEnd = true;
    }
    
    private void goBack() {
        System.out.println("Returning to main menu");
        isMenuEnd = true;
    }
}
