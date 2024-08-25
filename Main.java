import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if (expenseTracker.registerUser(username, password)) {
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("Username already exists!");
                    }
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    User user = expenseTracker.loginUser(username, password);
                    if (user != null) {
                        System.out.println("Login successful!");
                        handleUserSession(expenseTracker, user, scanner);
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static void handleUserSession(ExpenseTracker expenseTracker, User user, Scanner scanner) {
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. Show Category-wise Summation");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    expenseTracker.addExpense(user, date, category, amount);
                    System.out.println("Expense added successfully!");
                    break;

                case 2:
                    System.out.println("Listing all expenses:");
                    expenseTracker.listExpenses(user);
                    break;

                case 3:
                    System.out.println("Category-wise Summation:");
                    expenseTracker.showCategoryWiseSummation(user);
                    break;

                case 4:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }
}
