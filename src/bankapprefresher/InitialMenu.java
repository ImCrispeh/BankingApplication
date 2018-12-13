/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

import java.util.Scanner;

/**
 *
 * @author chris
 */
public class InitialMenu {
    Scanner scan;

    public InitialMenu() {
        scan = new Scanner(System.in);
    }

    public void showInitialMenu() {
        System.out.println("Welcome to BANKING APPLICATION");
        System.out.println("------------------------------");
        String input = "";

        while (!input.equals("x")) {
            System.out.println("OPTIONS");
            System.out.println("------------------------------");
            System.out.println("a. Login");
            System.out.println("b. Create account");
            System.out.println("x. Exit");
            System.out.print("Please make a selection: ");
            input = scan.nextLine();
            initialMenuSelection(input);
        }
    }

    public void initialMenuSelection(String choice) {
        choice = choice.toLowerCase();
        switch (choice) {
            case "a":
                login();
                break;
            case "b":
                register();
                break;
            case "x":
                System.out.println("Exiting application\n");
                break;
            default:
                System.out.println("Invalid input\n");
                break;
        }
    }

    public void login() {
        System.out.println("\nPlease enter your credentials");
        System.out.println("------------------------------");
        System.out.print("User ID: ");
        int accountId = Integer.parseInt(scan.nextLine());
        System.out.print("Enter your password: ");
        String password = scan.nextLine();

        Customer customer = Customers.login(accountId, password);
        if (customer != null) {
            CustomerMenu custMenu = new CustomerMenu(customer);
            custMenu.showCustomerMenu();
        } else {
            System.out.println("Error: invalid account credentials\n");
        }
    }
    
    public void register() {
        System.out.println("\nPlease enter your preferred credentials");
        System.out.println("------------------------------");
        System.out.print("Name: ");
        String name = scan.nextLine();
        System.out.print("Password: ");
        String password = scan.nextLine();
        
        int id = Util.generateCustomerId();
        Customer customer = new Customer(id, name, password);
        Customers.addCustomer(customer);
        CustomerMenu custMenu = new CustomerMenu(customer);
        custMenu.showCustomerMenu();
    }
}
