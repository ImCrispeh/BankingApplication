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
public class CustomerMenu {
    private Customer customer;
    private Scanner scan;
    private ArrayList<Account> initialCustomerAccounts;
    private ArrayList<Account> customerAccounts;
    
    //Constructor
    public CustomerMenu(Customer customer) {
        scan = new Scanner(System.in);
        this.customer = customer;
        initialCustomerAccounts = Accounts.getCustomerAccounts(customer.getId());
        customerAccounts = initialCustomerAccounts;
    }
    
    /**
     * Provides initial message and displays menu options
     */
    public void showCustomerMenu() {
        System.out.println("\nWelcome " + customer.getName());
        System.out.println("Customer ID: " + customer.getId());
        String input = "";
        
        while (!input.equals("x")) {
            System.out.println("------------------------------");
            System.out.println("OPTIONS");
            System.out.println("------------------------------");
            System.out.println("a. View accounts");
            System.out.println("b. Create account");
            System.out.println("c. Select account");
            System.out.println("d. Edit customer details");
            System.out.println("x. Logout");
            System.out.print("Please make a selection: ");
            input = scan.nextLine();
            customerMenuSelection(input);
        }
    }
    
    /**
     * Calls specific handler based on user input
     * @param choice user input from menu
     */
    private void customerMenuSelection(String choice) {
        System.out.println();
        choice = choice.toLowerCase();
        switch (choice) {
            case "a":
                viewAccounts();
                break;
            case "b":
                createAccount();
                break;
            case "c":
                selectAccount();
                break;
            case "d":
                editDetails();
                break;
            case "x":
                logout();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
        System.out.println();
    }
    
    private void viewAccounts() {
        System.out.println("Viewing accounts");
        System.out.println("------------------------------");
        for (Account account : customerAccounts) {
            System.out.println("Account name: " + account.getAccountName());
            System.out.println("Account number: " + account.getAccountNumber());
            System.out.println("Account balance: " + account.getBalance());
            System.out.println("------------------------------");
        }
    }
    
    private void createAccount() {
        System.out.println("Please enter the account name");
        System.out.println("------------------------------");
        System.out.print("Account name: ");
        String name = scan.nextLine();
        
        int number = Util.generateAccountNumber();
        Account newAccount = new Account(number, customer.getId(), name);
        customerAccounts.add(newAccount);
    }
    
    private void selectAccount() {
        System.out.println("Please enter the account number");
        System.out.println("------------------------------");
        System.out.print("Account number: ");
        int number = Integer.parseInt(scan.nextLine());
        
        Optional<Account> account = customerAccounts.stream()
                .filter(acc -> acc.getAccountNumber() == number)
                .findFirst();
        
        if (account.isPresent()) {
            AccountMenu accountMenu = new AccountMenu(account.get());
            accountMenu.showAccountMenu();
        } else {
            System.out.println("Invalid account number");
        }
    }
    
    private void editDetails() {
        
    }
    
    private void logout() {
        System.out.println("Logging out\n");
        Accounts.updateAccounts(initialCustomerAccounts, customerAccounts);
    }
    
    private void accountAction() {
        
    }
}
