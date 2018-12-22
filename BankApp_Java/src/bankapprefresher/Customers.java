/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapprefresher;

import java.util.*;

/**
 * Overall collection of customers, provides the ability to add, remove and login
 * @author chris
 */
public class Customers {
    private static ArrayList<Customer> customers = new ArrayList<>();
    
    /**
     * Get overall list of customers
     * @return customers
     */
    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    /**
     * Adds specified customer to collection
     * @param customer 
     */
    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    /**
     * Removes customer from collection
     * @param customer 
     */
    public static void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }
    
    /**
     * Checks if user matching specific credentials exists before allowing login
     * @param id
     * @param password
     * @return 
     */
    public static Customer login(int id, String password) {
        for (Customer customer : customers) {
            if (id == customer.getId() && password.equals(customer.getPassword())) {
                return customer;
            }
        }
        return null;
        
        //STREAM WAY TO DO IT
//        Optional<Customer> customer = customers.stream()
//                .filter(Customer -> Customer.getId() == id && Customer.getPassword().equals(password))
//                .findFirst();
//        
//        if (customer.isPresent()) {
//            return customer.get();
//        } else {
//            return null;
//        }
    }
}
