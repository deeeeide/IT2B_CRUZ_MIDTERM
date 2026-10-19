package com.canteen.ui;

import com.canteen.dao.ScenarioDao;
import com.canteen.model.ScenarioEntity;
import java.util.List;
import java.util.Scanner;

public class CanteenInventoryMenu {
    private final ScenarioDao dao;
    private final Scanner scanner;

    public CanteenInventoryMenu() {
        this.dao = new ScenarioDao();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n------------------------------------------------");
            System.out.println("          CANTEEN INVENTORY MANAGEMENT");
            System.out.println("------------------------------------------------");
            System.out.println("[1] Add New Food Item");
            System.out.println("[2] View Canteen Inventory");
            System.out.println("[3] Exit System");
            System.out.print("\nEnter choice: ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // buffer clear
            } else {
                scanner.nextLine();
            }

            switch (choice) {
                case 1 -> addNewItem();
                case 2 -> viewInventory();
                case 3 -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nInvalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private void addNewItem() {
        System.out.println("\n--- ADD NEW FOOD ITEM ---");
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Category (MEAL / SNACK / BEVERAGE): ");
        String category = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Stock Quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        ScenarioEntity newItem = new ScenarioEntity(name, category, price, stock);
        if (dao.addFoodItem(newItem)) {
            System.out.println("\nSUCCESS: Food item added to inventory!");
        } else {
            System.out.println("\nFAILED to add item.");
        }
    }

    private void viewInventory() {
        List<ScenarioEntity> inventory = dao.getAllFoodItems();
        System.out.println("\n---------------------------------------------------------------");
        System.out.println("                    CANTEEN FOOD INVENTORY");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("| %-3s | %-20s | %-10s | %-7s | %-5s |%n", "ID", "ITEM NAME", "CATEGORY", "PRICE", "STOCK");
        System.out.println("---------------------------------------------------------------");

        for (ScenarioEntity item : inventory) {
            System.out.printf("| %-3d | %-20s | %-10s | %-7.2f | %-5d |%n",
                    item.id(), item.itemName(), item.category(), item.price(), item.stock());
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("Total Records Displayed: " + inventory.size());
    }
}