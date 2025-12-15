import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseManager {
    private List<Expense> expenseList;
    private static final String FILE_NAME = "expenses.csv"; // Changed to English filename

    public ExpenseManager() {
        this.expenseList = new ArrayList<>();
        loadFromFile();
    }

    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
        saveToFile(expense);
    }


    private void saveToFile(Expense expense) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             PrintWriter out = new PrintWriter(fw)) {


            out.println(expense.getDate() + "," + expense.getDescription() + "," +
                    expense.getAmount() + "," + expense.getCategory());

        } catch (IOException e) {
            System.out.println("❌ Error saving to file: " + e.getMessage());
        }
    }


    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                String[] parts = line.split(",");

                if (parts.length == 4) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    String desc = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    String category = parts[3];

                    Expense loadedExpense = new Expense(desc, amount, category, date);
                    this.expenseList.add(loadedExpense);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ File could not be read.");
        } catch (Exception e) {
            System.out.println("⚠️ Corrupted data found, skipping line.");
        }
    }

    public void displayAllExpenses() {
        System.out.println("\n--- EXPENSE LIST ---");
        if (this.expenseList.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            for (Expense item : this.expenseList) {
                System.out.println(item);
            }
        }
    }
}