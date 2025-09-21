/*
    Garrett's Popcorn Vending Machine
    Author: Harvey Pierson
    Course: COMP 170, Fall 1 2025
    System: Visual Studio Code, Windows 10
*/

import java.util.Scanner;

public class GarrettsPopcornMachine {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Constants for rows and columns
        final int ROWS = 3;
        final int COLS = 3;
        final String SENTINEL = "Q"; // Sentinel to quit

        // Column headers
        final char[] COLUMNS = {'P', 'N', 'R'};

        // Product names in 2D array
        String[][] products = {
            {"Garrett Mix", "Pecan Caramel Crisp", "Plain"},
            {"Caramel Crisp", "Cashew Caramel Crisp", "Buttery"},
            {"Cheese Corn", "Almond Caramel Crisp", "Sweet Corn"}
        };

        // Prices in 2D array
        double[][] prices = {
            {14.99, 10.99, 6.99},
            {16.99, 9.99, 8.99},
            {12.99, 11.99, 7.99}
        };

        // Stock levels (extra credit)
        int[][] stock = {
            {5, 5, 5},
            {5, 5, 5},
            {5, 5, 5}
        };

        // Track purchases
        int totalItems = 0;
        double totalCost = 0.0;

        System.out.println("Welcome to Garrett's Popcorn Vending Machine!");
        System.out.println("Enter a row number (0-2) and a column letter (P, N, R) to select an item.");
        System.out.println("Enter Q at any time to finish.\n");

        while (true) {
            System.out.print("Enter product row (0-2) or Q to quit: ");
            String rowInput = input.nextLine().trim().toUpperCase();

            if (rowInput.equals(SENTINEL)) {
                break;
            }

            // Validate row input
            int row;
            try {
                row = Integer.parseInt(rowInput);
                if (row < 0 || row >= ROWS) {
                    System.out.println("Invalid row. Try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number 0-2 or Q to quit.");
                continue;
            }

            System.out.print("Enter product column (P, N, R): ");
            String colInput = input.nextLine().trim().toUpperCase();

            // Validate column input
            if (colInput.length() != 1 || "PNR".indexOf(colInput.charAt(0)) == -1) {
                System.out.println("Invalid column. Try again.");
                continue;
            }

            int col = "PNR".indexOf(colInput.charAt(0));

            // Check stock (extra credit)
            if (stock[row][col] <= 0) {
                System.out.println("Sorry, " + products[row][col] + " is out of stock!");
                continue;
            }

            // Process purchase
            totalItems++;
            totalCost += prices[row][col];
            stock[row][col]--; // reduce stock

            System.out.printf("Added %s ($%.2f) to your cart.%n", products[row][col], prices[row][col]);
        }

        // Summary
        System.out.println("\n--- Purchase Summary ---");
        System.out.println("Total items purchased: " + totalItems);
        System.out.printf("Total cost: $%.2f%n", totalCost);

        // Extra credit: restock notification
        int restockCount = 0;
        double vendorCost = 0.0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (stock[r][c] < 3) {
                    int needed = 5 - stock[r][c];
                    restockCount += needed;
                    vendorCost += (prices[r][c] * 0.5) * needed;
                    System.out.printf("NOTICE: %s stock is low (%d left). Restock needed: %d%n", 
                        products[r][c], stock[r][c], needed);
                }
            }
        }

        if (restockCount > 0) {
            System.out.printf("Vendor must order %d items. Estimated vendor cost: $%.2f%n", restockCount, vendorCost);
        }

        System.out.println("Thank you for shopping with Garrett's Popcorn!");
        input.close();
    }
}
