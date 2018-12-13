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
public class Customers {
    private ArrayList<Customer> customers = new ArrayList<>();
    
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }
    
    public Customer login(int id, String password) {
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
