/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.electronicfrenchise;

import java.util.Scanner;

public class SaleAnalytics {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] stores = {"Cape Town", "Port Elizabeth", "Pretoria"};
        String[] categories = {"PS5", "XBOX", "SWITCH"};

        int rows = stores.length;
        int cols = categories.length;
        int[][] sales = new int[rows][cols];
        int[] storeTotals = new int[rows];
        int[] categoryTotals = new int[cols];

        // 1. Capture into 2D array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Enter " + categories[j] + " sales for " + stores[i] + ": ");
                sales[i][j] = sc.nextInt();
            }
        }

        // 2. Row totals, 3. Column totals, 4. Grand total
        int grandTotal = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                storeTotals[i] += sales[i][j];
                categoryTotals[j] += sales[i][j];
            }
            grandTotal += storeTotals[i];
        }

        // 5. Highest store
        int maxStoreIndex = 0;
        for (int i = 1; i < rows; i++) {
            if (storeTotals[i] > storeTotals[maxStoreIndex]) maxStoreIndex = i;
        }

        // 6. Lowest category
        int minCatIndex = 0;
        for (int j = 1; j < cols; j++) {
            if (categoryTotals[j] < categoryTotals[minCatIndex]) minCatIndex = j;
        }

 // Report
        System.out.println("===================================================");
        System.out.println("GAME CONSOLE ANALYTICS REPORT");
        System.out.println("===================================================");
        System.out.printf("%-15s %-11s %-9s %-9s %s\n","","PS5","XBOX","SWITCH","TOTAL");
        for (int i = 0; i < rows; i++) {
            System.out.printf("%-15s %-11d %-9d %-9d %d\n", stores[i], sales[i][0], sales[i][1], sales[i][2], storeTotals[i]);
        }
        System.out.println("---------------------------------------------------");
        System.out.printf("CATEGORY TOTALS %d\t %d\t %d\n", categoryTotals[0], categoryTotals[1], categoryTotals[2]);
        System.out.println("GRAND TOTAL SALES: " + grandTotal);
        System.out.println("\nCity WITH THE HIGHEST TOTAL SALES: " + stores[maxStoreIndex] + " (" + storeTotals[maxStoreIndex] + ")");
        System.out.println("CATEGORY WITH THE LOWEST TOTAL SALES: " + categories[minCatIndex] + " (" + categoryTotals[minCatIndex] + ")");

        // 7. Self-implemented Bubble Sort for ranking - NOT Arrays.sort()
        String[] sortedStores = stores.clone();
        int[] sortedTotals = storeTotals.clone();
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < rows - 1 - i; j++) {
                if (sortedTotals[j] < sortedTotals[j+1]) {
                    int tempTotal = sortedTotals[j];
                    sortedTotals[j] = sortedTotals[j+1];
                    sortedTotals[j+1] = tempTotal;
                    String tempStore = sortedStores[j];
                    sortedStores[j] = sortedStores[j+1];
                    sortedStores[j+1] = tempStore;
                }
            }
        }
        System.out.println("---------------------------------------------------");
        System.out.println("City RANKING (HIGHEST TO LOWEST SALES)");
        System.out.println("---------------------------------------------------");
        for (int i = 0; i < rows; i++) {
            System.out.println((i+1) + ". " + sortedStores[i] + "\t " + sortedTotals[i]);
        }
        System.out.println("===================================================");
    }
}