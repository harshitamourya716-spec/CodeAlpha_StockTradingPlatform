import java.util.Scanner;

/**
 * CodeAlpha Java Internship - Task 2: Stock Trading Platform
 * Simulates a basic stock trading environment with buy/sell operations
 * and portfolio performance tracking.
 */
public class Main {
    public static void main(String[] args) {
        Market market = new Market();
        Scanner sc = new Scanner(System.in);

        System.out.println("===================================================");
        System.out.println("     WELCOME TO CODEALPHA STOCK TRADING PLATFORM");
        System.out.println("===================================================");
        System.out.print("Enter your trader name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = "Trader";

        User user = new User(name, 100000); // starting virtual balance: Rs.1,00,000
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    market.tick(); // simulate live price movement
                    market.displayMarket();
                    break;

                case "2":
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.nextLine().trim();
                    System.out.print("Enter quantity: ");
                    int buyQty = readInt(sc);
                    if (buyQty > 0) user.buyStock(market, buySymbol, buyQty);
                    else System.out.println("Quantity must be positive.");
                    break;

                case "3":
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.nextLine().trim();
                    System.out.print("Enter quantity: ");
                    int sellQty = readInt(sc);
                    if (sellQty > 0) user.sellStock(market, sellSymbol, sellQty);
                    else System.out.println("Quantity must be positive.");
                    break;

                case "4":
                    user.displaySummary(market);
                    break;

                case "5":
                    user.displayTransactionHistory();
                    break;

                case "6":
                    running = false;
                    System.out.println("Thank you for trading with CodeAlpha. Goodbye, " + name + "!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. View Live Market Data");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio Summary");
        System.out.println("5. View Transaction History");
        System.out.println("6. Exit");
        System.out.println("----------------------------------------");
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
