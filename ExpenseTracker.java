import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ExpenseTracker {
    private Map<String, User> users;
    private Map<String, List<Expense>> userExpenses;
    private static final String USERS_FILE = "users.ser";
    private static final String EXPENSES_FILE = "expenses.ser";

    public ExpenseTracker() {
        users = new HashMap<>();
        userExpenses = new HashMap<>();
        loadUsers();
        loadExpenses();
    }

    // User Registration
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        User newUser = new User(username, password);
        users.put(username, newUser);
        userExpenses.put(username, new ArrayList<>());
        saveUsers();
        return true;
    }

    // User Login
    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }

    // Add an Expense
    public void addExpense(User user, LocalDate date, String category, double amount) {
        Expense expense = new Expense(date, category, amount);
        userExpenses.get(user.getUsername()).add(expense);
        saveExpenses();
    }

    // List All Expenses
    public void listExpenses(User user) {
        List<Expense> expenses = userExpenses.get(user.getUsername());
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    // Category-wise Summation
    public void showCategoryWiseSummation(User user) {
        List<Expense> expenses = userExpenses.get(user.getUsername());
        Map<String, Double> categorySum = new HashMap<>();
        for (Expense expense : expenses) {
            categorySum.put(expense.getCategory(), 
                categorySum.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }

        for (Map.Entry<String, Double> entry : categorySum.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Total: " + entry.getValue());
        }
    }

    // Save users to a file
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load users from a file
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // No saved users yet
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Save expenses to a file
    private void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EXPENSES_FILE))) {
            oos.writeObject(userExpenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load expenses from a file
    @SuppressWarnings("unchecked")
    private void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EXPENSES_FILE))) {
            userExpenses = (Map<String, List<Expense>>) ois.readObject();
        } catch (FileNotFoundException e) {
            // No saved expenses yet
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

