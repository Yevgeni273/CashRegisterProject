import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class CashRegister {
    static Scanner scan = new Scanner(System.in);

    static String[] mainChoices = {
        "Lauriat", "Chef Special", "Chao Fan"
    };

    static String[] lauriat = {
        "Chinese-Style Fried Chicken Lauriat", "Pork Chop Lauriat", 
        "Sweet & Sour Pork Lauriat", "Shanghai Lauriat"
    };
    static float[] lauriatPrices = {178, 178, 165, 165};

    static String[] chefSpecial = {
        "Chinese-Style Fried Chicken Rice Meal", "Sweet & Sour Fish", 
        "Chinese-Style Pork Chop", "Sweet & Sour Pork"
    };
    static float[] chefSprices = {88, 109, 105, 109};

    static String[] chaoFan = {
        "Pork Siomai Chaofan", "Pork Chaofan", 
        "Beef Siomai Chaofan", "Yang Chow Siomai Chaofan"
    };
    static float[] chaoFprices = {90, 55, 105, 110};

    static String currentUser;

    static List<String> orders = new ArrayList<>();
    static List<Integer> quantities = new ArrayList<>();
    static List<Float> price = new ArrayList<>();

    static List<String> usernames = new ArrayList<>();
    static List<String> passwords = new ArrayList<>();

    static {
        usernames.add("admin");
        passwords.add("Admin123");
    }

    public static void signup() {
        System.out.println("\n======= User Signup =======");
        String username, password;

        while (true) {
            System.out.print("Enter username (5-15 alphanumeric characters): ");
            username = scan.nextLine();
            if (username.matches("^[a-zA-Z0-9]{5,15}$")) {
                break;
            } else {
                System.out.println("Invalid username format. Try again.");
            }
        }

        while (true) {
            System.out.print("Enter password (8-20 characters, 1 uppercase, 1 number): ");
            password = scan.nextLine();
            if (password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
                break;
            } else {
                System.out.println("Invalid password format. Try again.");
            }
        }

        usernames.add(username);
        passwords.add(password);
        System.out.println("Signup successful!");
    }

    public static boolean login() {
        System.out.println("\n======= User Login =======");
        while (true) {
            System.out.print("Enter username: ");
            String user = scan.nextLine();
            System.out.print("Enter password: ");
            String pass = scan.nextLine();
            
            for (int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equals(user) && passwords.get(i).equals(pass)) {
                    System.out.println("Login successful!");
                    currentUser = user;
                    return true;
                }
            }
            System.out.println("Incorrect username or password. Try again.");
        }
    }

    public static void displayMainChoices() {
        System.out.println("\n======= Chowking Menu =======");
        for (int i = 0; i < mainChoices.length; i++) {
            System.out.println((i + 1) + ". " + mainChoices[i]);
        }
        System.out.println("0. Go back to Options");
    }

    public static void displayActualMenus(int menuOption) {
        if (menuOption == 1) {
            System.out.println("\n======= Lauriat =======");
            for (int j = 0; j < lauriat.length; j++) {
                System.out.println((j + 1) + ". " + lauriat[j] + " --- ?" + lauriatPrices[j]);
            }
        } else if (menuOption == 2) {
            System.out.println("\n======= Chef Special =======");
            for (int j = 0; j < chefSpecial.length; j++) {
                System.out.println((j + 1) + ". " + chefSpecial[j] + " --- ?" + chefSprices[j]);
            }
        } else if (menuOption == 3) {
            System.out.println("\n======= Chao Fan =======");
            for (int j = 0; j < chaoFan.length; j++) {
                System.out.println((j + 1) + ". " + chaoFan[j] + " --- ?" + chaoFprices[j]);
            }
        }
        System.out.println("0. Go Back to Main Menu");
    }

    public static void addOrder(int menuOption) {
        while (true) {
            displayActualMenus(menuOption);
            int choice = -1;
            try {
                System.out.print("\nEnter item number to order (0 to go back to Main Menu): ");
                choice = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine(); 
                continue;
            }

            if (choice == 0) {
                return;
            }

            int quantity = 0;
            try {
                System.out.print("Enter quantity: ");
                quantity = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid quantity. Please enter a number.");
                scan.nextLine();
                continue;
            }

            if (menuOption == 1 && choice <= lauriat.length && choice > 0) {
                orders.add(lauriat[choice - 1]);
                quantities.add(quantity);
                price.add(lauriatPrices[choice - 1]);
            } else if (menuOption == 2 && choice <= chefSpecial.length && choice > 0) {
                orders.add(chefSpecial[choice - 1]);
                quantities.add(quantity);
                price.add(chefSprices[choice - 1]);
            } else if (menuOption == 3 && choice <= chaoFan.length && choice > 0) {
                orders.add(chaoFan[choice - 1]);
                quantities.add(quantity);
                price.add(chaoFprices[choice - 1]);
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void editOrder() {
        System.out.println("\n======= Edit Order =======");
        for (int j = 0; j < orders.size(); j++) {
            System.out.println((j + 1) + ". " + orders.get(j) + " | qty: " + quantities.get(j) + " | prc: " + (quantities.get(j) * price.get(j)));
        }

        int orderIndex = -1;
        try {
            System.out.print("Enter order number to edit: ");
            orderIndex = scan.nextInt();
            scan.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input.");
            scan.nextLine();
            return;
        }

        if (orderIndex > orders.size() || orderIndex <= 0) {
            System.out.println("Invalid order number. Please try again.");
            return;
        }
        int actualIndex = orderIndex - 1;

        System.out.print("Do you want to change the item? (yes/no): ");
        String pilian = scan.nextLine().toLowerCase();

        if (pilian.equals("yes")) {
            displayMainChoices();
            int menuOption = -1;
            try {
                System.out.print("Choose a new category: ");
                menuOption = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                scan.nextLine();
                return;
            }

            displayActualMenus(menuOption);

            int newItemIndex = -1;
            try {
                System.out.print("\nEnter new item number: ");
                newItemIndex = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                scan.nextLine();
                return;
            }

            if (menuOption == 1 && newItemIndex <= lauriat.length && newItemIndex > 0) {
                orders.set(actualIndex, lauriat[newItemIndex - 1]);
                price.set(actualIndex, lauriatPrices[newItemIndex - 1]);
            } else if (menuOption == 2 && newItemIndex <= chefSpecial.length && newItemIndex > 0) {
                orders.set(actualIndex, chefSpecial[newItemIndex - 1]);
                price.set(actualIndex, chefSprices[newItemIndex - 1]);
            } else if (menuOption == 3 && newItemIndex <= chaoFan.length && newItemIndex > 0) {
                orders.set(actualIndex, chaoFan[newItemIndex - 1]);
                price.set(actualIndex, chaoFprices[newItemIndex - 1]);
            } else {
                System.out.println("Invalid item selected.");
                return;
            }

            System.out.print("Enter new quantity: ");
            try {
                int newQuantity = scan.nextInt();
                scan.nextLine();
                if (newQuantity > 0) {
                    quantities.set(actualIndex, newQuantity);
                }
            } catch (Exception e) {
                System.out.println("Invalid quantity.");
                scan.nextLine();
            }
        }
        System.out.println("Item Successfully Edited");
    }

    public static void removeOrder() {
        System.out.println("\n======= Remove Order =======");
        for (int j = 0; j < orders.size(); j++) {
            System.out.println((j + 1) + ". " + orders.get(j) + " | qty: " + quantities.get(j) + " | prc: " + (quantities.get(j) * price.get(j)));
        }
        System.out.println("0. Go back to Options");

        int indexRemove = -1;
        try {
            System.out.print("Enter order number to remove: ");
            indexRemove = scan.nextInt();
            scan.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input.");
            scan.nextLine();
            return;
        }

        if (indexRemove > orders.size() || indexRemove <= 0) {
            System.out.println("Invalid order number. Please try again.");
            return;
        }

        int actualIndexRemove = indexRemove - 1;

        System.out.print("Do you want to remove this item? (yes/no): ");
        String removeConfirmation = scan.nextLine();

        if (removeConfirmation.equalsIgnoreCase("yes")) {
            orders.remove(actualIndexRemove);
            quantities.remove(actualIndexRemove);
            price.remove(actualIndexRemove);
            System.out.println("Item Removed");
        }
    }

    public static void viewCart() {
        System.out.println("\n======= Your Cart =======");
        float total = 0;
        for (int j = 0; j < orders.size(); j++) {
            System.out.println((j + 1) + ". " + orders.get(j) + " | qty: " + quantities.get(j) + " | prc: " + (quantities.get(j) * price.get(j)));
            total += price.get(j) * quantities.get(j);
        }
        System.out.println("Total Price: ?" + total);
    }

    public static void clearCart() {
        orders.clear();
        quantities.clear();
        price.clear();
        System.out.println("Cart cleared!");
    }

      public static void logTransaction(float totalAmount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write("Transaction Date: " + LocalDateTime.now());
            writer.newLine();
            writer.write("Customer: " + currentUser);
            writer.newLine();
            writer.write("Items:");
            writer.newLine();
            for (int i = 0; i < orders.size(); i++) {
                writer.write(String.format("- %s | Quantity: %d | Price: %.2f", orders.get(i), quantities.get(i), price.get(i) * quantities.get(i)));
                writer.newLine();
            }
            writer.write(String.format("Total Amount: %.2f", totalAmount));
            writer.newLine();
            writer.write("--------------------------------------------");
            writer.newLine();
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing transaction to file.");
        }
    }

    public static void checkOut() {
        if (orders.isEmpty()) {
            System.out.println("Your cart is empty. Please add items to your cart before checking out.");
            return;
        }

        System.out.println("\n======= Checkout =======");
        System.out.print("Confirm checkout? (yes/no): ");
        String confirmation = scan.nextLine().toLowerCase();

        if (confirmation.equals("no")) {
            System.out.println("Checkout cancelled.");
            return;
        }

        float total = 0;

        System.out.println("\n======= Discount ======= \n1. Person with Disability \n2. Senior Citizen \n3. Coupon \n4. No Discount");
        System.out.print("Enter discount: ");
        int discount = scan.nextInt();

        int coupPercentage = 0;
        if (discount == 3) {
            System.out.print("Enter coupon percentage: ");
            coupPercentage = scan.nextInt();
        }
        scan.nextLine(); 

        System.out.println("\n=========== OFFICIAL RECEIPT ============");
        System.out.println("Store: Chowking Branch");
        System.out.println("Customer: " + currentUser);
        System.out.println("Date: " + LocalDateTime.now());
        System.out.println("-----------------------------------------");

        for (int j = 0; j < orders.size(); j++) {
            float prices = price.get(j) * quantities.get(j);
            System.out.printf("%-30s x%-2d ?%.2f\n", orders.get(j), quantities.get(j), prices);
            total += prices;
        }

        System.out.println("-----------------------------------------");

        if (discount == 1 || discount == 2) {
            total *= 0.75;
        } else if (discount == 3) {
            total *= (1 - (coupPercentage / 100.0));
        }

        System.out.printf("TOTAL:                          ?%.2f\n", total);
        System.out.println("==========================================");

        float remainingBalance = total;
        float payment = 0;

        while (remainingBalance > 0) {
            System.out.printf("Remaining Balance: ?%.2f\n", remainingBalance);
            System.out.print("Enter payment amount: ?");
            float currentPayment = scan.nextFloat();
            scan.nextLine();

            if (currentPayment <= 0) {
                System.out.println("Invalid amount. Please enter a valid payment.");
                continue;
            }

            payment += currentPayment;
            remainingBalance -= currentPayment;
        }

        float change = Math.abs(remainingBalance);
        System.out.printf("\nCHANGE:                         ?%.2f\n", change);
        System.out.println("==========================================");
        System.out.println("Thank you for your purchase!");

        logTransaction(total);

        orders.clear();
        quantities.clear();
        price.clear();
    }

    public static void options() {
        System.out.println("\n======= Cash Register ======= \n1. Add Order \n2. Edit Order \n3. Remove Order \n4. View Cart \n5. Clear Cart \n6. Check Out \n7. Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {
        System.out.println("======= Welcome to Chowking Cashier System =======");

        while (true) {
            System.out.println("\n1. Signup\n2. Login");
            System.out.print("Choose an option: ");
            int choice = -1;
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
                continue;
            }

            if (choice == 1) {
                signup();
            } else if (choice == 2) {
                if (usernames.isEmpty()) {
                    System.out.println("No users found. Please signup first.");
                } else if (login()) {
                    break;
                }
            } else {
                System.out.println("Invalid option.");
            }
        }

        while (true) {
            options();
            int option = -1;
            try {
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input.");
                scan.nextLine();
                continue;
            }

            switch (option) {
                case 1: 
                    while (true) {
                        displayMainChoices();
                        int menuOption = -1;
                        try {
                            System.out.print("Choose a category: ");
                            menuOption = scan.nextInt();
                            scan.nextLine();
                        } catch (Exception e) {
                            System.out.println("Invalid input.");
                            scan.nextLine();
                            continue;
                        }
                        if (menuOption == 0) break;
                        if (menuOption >= 1 && menuOption <= 3) {
                            addOrder(menuOption);
                        } else {
                            System.out.println("Invalid category. Try again.");
                        }
                    }
                    break;
                case 2: editOrder(); break;
                case 3: removeOrder(); break;
                case 4: viewCart(); break;
                case 5: clearCart(); break;
                case 6: checkOut(); return;
                case 7: 
                    System.out.println("Exiting... Thank you!");
                    return;
                default: 
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
