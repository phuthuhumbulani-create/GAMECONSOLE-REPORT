/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.electronicfrenchise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Student
 */




enum OrderStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    RETURNED
}

// Custom Checked Exception
class InvalidOrderException extends Exception {
    public InvalidOrderException(String message) {
        super(message);
    }
}

//  Abstract Base Class Order
abstract class Order {
    private String orderId;
    private String customerName;
    private double orderTotal;
    private OrderStatus status;

    public Order(String orderId, String customerName, double orderTotal, OrderStatus status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderTotal = orderTotal;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public double getOrderTotal() { return orderTotal; }
    public OrderStatus getStatus() { return status; }

    public abstract double calculateLoyaltyPoints();
}

// Concrete Subclass 
class RetailOrder extends Order {
    
    public RetailOrder(String orderId, String customerName, double orderTotal, OrderStatus status) 
            throws InvalidOrderException {
        super(orderId, customerName, orderTotal, status);
        
        // Input validations
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidOrderException("Customer name cannot be empty.");
        }
        if (orderTotal <= 0) {
            throw new InvalidOrderException("Order total must be greater than 0.");
        }
    }

    @Override
    public double calculateLoyaltyPoints() {
        // Awarding 1 loyalty point for every R100 spent (rounded down)
        return Math.floor(getOrderTotal() / 100.0);
    }

    public void printOrderDetails() {
        System.out.printf("ORDER ID: %s   CUSTOMER: %s   TOTAL: R%.2Fi   STATUS: %s   PTS: %.0f\n",
                getOrderId(), getCustomerName(), getOrderTotal(), getStatus(), calculateLoyaltyPoints());
    }

// MAIN RUN APPLICATION 

public class  ElectronicFrenchise  {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ------------------------------------------
        //  SALES ANALYTICS
        // ------------------------------------------
        System.out.println("===  SALES REPORT ===");
        runQuestion1();
        System.out.println("\n");

        // ------------------------------------------
       
        // ------------------------------------------
        System.out.println("===  CAPTURE ORDERS ===");
        ArrayList<RetailOrder> orders = new ArrayList<>();
        
        while (true) {
            System.out.println("--- Capture Order ---");
            System.out.print("Enter order ID: ");
            String id = scanner.nextLine();
            
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter order total: ");
            double total = 0;
            try {
                total = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                total = -1; // Trigger exception logic downstream
            }

            System.out.print("Enter order status (PENDING, SHIPPED, DELIVERED, RETURNED): ");
            String statusInput = scanner.nextLine().toUpperCase();
            OrderStatus status = OrderStatus.PENDING;
            try {
                status = OrderStatus.valueOf(statusInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status entered. Defaulting to PENDING.");
            }

            try {
                // Instantiating valid order throws check criteria if invalid
                RetailOrder validOrder = new RetailOrder(id, name, total, status);
                orders.add(validOrder);
            } catch (InvalidOrderException e) {
                System.out.println("ERROR: Invalid order. Order total must be greater than 0 and customer name cannot be empty. Please try again.");
                continue; // Re-prompt loops back without asking for another entry confirmation
            }

            System.out.print("Add another order? (y/n): ");
            String choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("y")) {
                break;
            }
        }

        // sort descending using Collections.sort & Custom Comparator
        Collections.sort(orders, new Comparator<RetailOrder>() {
            @Override
            public int compare(RetailOrder o1, RetailOrder o2) {
                return Double.compare(o2.getOrderTotal(), o1.getOrderTotal());
            }
        });

        //  Summary Report
        System.out.println("\n===================================================");
        System.out.println("GAMING CONSOLE REPORT  TOTALS");
        System.out.println("===================================================");
        double totalPoints = 0;
        for (RetailOrder o : orders) {
            o.printOrderDetails();
            totalPoints += o.calculateLoyaltyPoints();
        }
        System.out.println("---------------------------------------------------");
        System.out.printf("TOTAL LOYALTY POINTS EARNED ACROSS ALL ORDERS: %.0f\n", totalPoints);
        System.out.println("===================================================\n");


        System.out.println("=== RUNNING QUESTION 3: REPEAT CUSTOMER REPORT ===");
        
       // Count order quantities using a HashMap
        HashMap<String, Integer> customerOrderCounts = new HashMap<>();
        for (RetailOrder o : orders) {
            String cName = o.getCustomerName();
            customerOrderCounts.put(cName, customerOrderCounts.getOrDefault(cName, 0) + 1);
        }

        // Print Repeat Customer Bonus Report
        System.out.println("===================================================");
        System.out.println("REPEAT CUSTOMER BONUS REPORT");
        System.out.println("===================================================");
        
        for (Map.Entry<String, Integer> entry : customerOrderCounts.entrySet()) {
            if (entry.getValue() > 1) {
                double bonusPoints = calculateCompoundedBonus(entry.getValue());
                System.out.printf("CUSTOMER: %s   ORDERS PLACED: %d   COMPOUNDED BONUS: %.0f points\n",
                        entry.getKey(), entry.getValue(), bonusPoints);
            }
        }
        System.out.println("===================================================");
    }

    // Implementation 
    private static void runQuestion1() {
        String[] stores = {"Cape Town", "Port Elizabeth", "Pretoria"};
        String[] categories = {"PS5", "XBOX", "SWITCH"};

        //  2D array
        double[][] sales = {
            {1000, 92000, 3000},   // Cape Town
            {2000, 3000, 4000},   // Port Elizabeth
            {1500, 1100, 1200},  // Pretoria
          
        };

        // 2. Compute Store Row Totals via 1D array
        double[] storeTotals = new double[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                storeTotals[i] += sales[i][j];
            }
        }

        // 3. Compute Category Column Totals via 1D array
        double[] catTotals = new double[3];
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                catTotals[j] += sales[i][j];
            }
        }

        // 4. Grand Total
        double grandTotal = 0;
        for (double val : storeTotals) grandTotal += val;

        // 5. Highest Store Index Finder
        int highStoreIdx = 0;
        for (int i = 1; i < storeTotals.length; i++) {
            if (storeTotals[i] > storeTotals[highStoreIdx]) highStoreIdx = i;
        }

        // 6. Lowest Category Index Finder
        int lowCatIdx = 0;
        for (int j = 1; j < catTotals.length; j++) {
            if (catTotals[j] < catTotals[lowCatIdx]) lowCatIdx = j;
        }

        // 7. Manual Bubble Sort Logic (Descending by Sales Weight)
        String[] sortedStores = stores.clone();
        double[] sortedTotals = storeTotals.clone();
        for (int i = 0; i < sortedTotals.length - 1; i++) {
            for (int j = 0; j < sortedTotals.length - i - 1; j++) {
                if (sortedTotals[j] < sortedTotals[j + 1]) {
                    double tVal = sortedTotals[j];
                    sortedTotals[j] = sortedTotals[j + 1];
                    sortedTotals[j + 1] = tVal;

                    String tStr = sortedStores[j];
                    sortedStores[j] = sortedStores[j + 1];
                    sortedStores[j + 1] = tStr;
                }
            }
        }

        // Print Out Sample Output Data Block 
        System.out.println("===================================================");
        System.out.println("GAMING CONCOLE REPORT");
        System.out.println("===================================================");
    }

    private static double calculateCompoundedBonus(Integer value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
}
