import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        CurrencyService currencyService = new CurrencyService(); // <-- YENİ HİZMETİMİZ

        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.println("=== WARSAW STUDENT BUDGET TRACKER (v2.0) ===");

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

                        System.out.print("Enter Amount (PLN): ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Enter Category: ");
                        String cat = scanner.nextLine();


                        System.out.println("Checking live exchange rates from NBP...");
                        double rate = currencyService.getTryRate();



                        if (rate > 0) {
                            double tryAmount = amount / rate;
                            System.out.printf("💡 Info: %.2f PLN is approx %.2f TRY (Rate: %.4f)\n", amount, tryAmount, rate);
                        } else {
                            System.out.println("⚠️ Currency info unavailable (Offline mode).");
                        }


                        Expense newExpense = new Expense(desc, amount, cat, LocalDate.now());
                        manager.addExpense(newExpense);
                        System.out.println("✅ Saved successfully!");
                        break;

                    case 2:
                        manager.displayAllExpenses();
                        break;

                    case 3:
                        System.out.println("Goodbye! 👋");
                        return;

                    default:
                        System.out.println("❌ Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input!");
                scanner.nextLine();
            }
        }
    }
}