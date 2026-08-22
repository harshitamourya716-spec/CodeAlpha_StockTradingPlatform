import java.io.*;
import java.util.*;

/**
 * Represents a trading account: cash balance + portfolio.
 * Handles buy/sell operations and persists state to a file (File I/O).
 */
public class User {
    private String name;
    private double cashBalance;
    private Portfolio portfolio;

    private static final String DATA_FILE = "portfolio_data.txt";

    public User(String name, double startingBalance) {
        this.name = name;
        this.cashBalance = startingBalance;
        this.portfolio = new Portfolio();
        loadData();
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public boolean buyStock(Market market, String symbol, int quantity) {
        Stock stock = market.getStock(symbol);
        if (stock == null) {
            System.out.println("Stock symbol '" + symbol + "' not found.");
            return false;
        }
        double cost = stock.getPrice() * quantity;
        if (cost > cashBalance) {
            System.out.printf("Insufficient balance. Need Rs.%.2f but you have Rs.%.2f%n", cost, cashBalance);
            return false;
        }
        cashBalance -= cost;
        portfolio.addHolding(stock.getSymbol(), quantity);
        Transaction t = new Transaction("BUY", stock.getSymbol(), quantity, stock.getPrice());
        portfolio.recordTransaction(t);
        saveData();
        System.out.printf("Bought %d share(s) of %s at Rs.%.2f each. Total: Rs.%.2f%n",
                quantity, stock.getSymbol(), stock.getPrice(), cost);
        return true;
    }

    public boolean sellStock(Market market, String symbol, int quantity) {
        Stock stock = market.getStock(symbol);
        if (stock == null) {
            System.out.println("Stock symbol '" + symbol + "' not found.");
            return false;
        }
        if (portfolio.getQuantity(stock.getSymbol()) < quantity) {
            System.out.println("You don't own enough shares of " + stock.getSymbol() + " to sell.");
            return false;
        }
        boolean removed = portfolio.removeHolding(stock.getSymbol(), quantity);
        if (!removed) return false;

        double proceeds = stock.getPrice() * quantity;
        cashBalance += proceeds;
        Transaction t = new Transaction("SELL", stock.getSymbol(), quantity, stock.getPrice());
        portfolio.recordTransaction(t);
        saveData();
        System.out.printf("Sold %d share(s) of %s at Rs.%.2f each. Total: Rs.%.2f%n",
                quantity, stock.getSymbol(), stock.getPrice(), proceeds);
        return true;
    }

    public void displaySummary(Market market) {
        System.out.println("\n===== ACCOUNT SUMMARY =====");
        System.out.println("Trader: " + name);
        System.out.printf("Cash Balance: Rs.%.2f%n", cashBalance);
        portfolio.displayHoldings(market);
        double netWorth = cashBalance + portfolio.getPortfolioValue(market);
        System.out.printf("Net Worth (Cash + Holdings): Rs.%.2f%n", netWorth);
    }

    public void displayTransactionHistory() {
        List<Transaction> history = portfolio.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("\n----- TRANSACTION HISTORY -----");
        for (Transaction t : history) {
            System.out.println(t);
        }
    }

    // ---------- File I/O: persistence ----------

    private void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            pw.println("CASH," + cashBalance);
            for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
                pw.println("HOLD," + entry.getKey() + "," + entry.getValue());
            }
            for (Transaction t : portfolio.getTransactionHistory()) {
                pw.println("TXN," + t.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Error saving portfolio: " + e.getMessage());
        }
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                switch (parts[0]) {
                    case "CASH":
                        cashBalance = Double.parseDouble(parts[1]);
                        break;
                    case "HOLD":
                        portfolio.addHolding(parts[1], Integer.parseInt(parts[2]));
                        break;
                    case "TXN":
                        // Historical transactions are informational only; skipped on reload
                        // to keep loading simple, since holdings/cash already reflect them.
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading portfolio: " + e.getMessage());
        }
    }
}
