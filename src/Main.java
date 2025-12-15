import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();


        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.println("=== WARSAW STUDENT BUDGET TRACKER ===");

        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add New Expense");
            System.out.println("2. List All Expenses");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Description (e.g., Coffee): ");
                        String desc = scanner.nextLine();

                        System.out.print("Enter Amount (PLN) (e.g., 25.50): ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Enter Category (e.g., Food): ");
                        String cat = scanner.nextLine();

                        Expense newExpense = new Expense(desc, amount, cat, LocalDate.now());
                        manager.addExpense(newExpense);
                        System.out.println("✅ Saved successfully!");
                        break;

                    case 2:
                        manager.displayAllExpenses();
                        break;

                    case 3:
                        System.out.println("Goodbye! Keep saving. 👋");
                        return;

                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}