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
public class CustomerMenu {
    Customer customer;
    Scanner scan;
    
    public CustomerMenu(Customer customer) {
        scan = new Scanner(System.in);
        this.customer = customer;
    }
    
    public void showCustomerMenu() {
        System.out.println("\nWelcome " + customer.getName());
        System.out.println("Customer ID: " + customer.getId());
        System.out.println("------------------------------");
        String input = "";
        
        while (!input.equals("x")) {
            System.out.println("OPTIONS");
            System.out.println("------------------------------");
            System.out.println("a. View accounts");
            System.out.println("b. Create account");
            System.out.println("c. Select account");
            System.out.println("d. Edit customer details");
            System.out.println("x. Exit");
            System.out.print("Please make a selection: ");
            input = scan.nextLine();
            customerMenuSelection(input);
        }
    }
    
    public void customerMenuSelection(String choice) {
        choice = choice.toLowerCase();
    }
}
