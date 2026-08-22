import java.util.*;

/**
 * Represents the holdings of a user - a mapping of stock symbol to quantity owned,
 * along with the full transaction history.
 */
public class Portfolio {
    private Map<String, Integer> holdings;      // symbol -> quantity
    private List<Transaction> transactionHistory;

    public Portfolio() {
        holdings = new HashMap<>();
        transactionHistory = new ArrayList<>();
    }

    public void addHolding(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public boolean removeHolding(String symbol, int quantity) {
        int owned = holdings.getOrDefault(symbol, 0);
        if (owned < quantity) return false;
        int remaining = owned - quantity;
        if (remaining == 0) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, remaining);
        }
        return true;
    }

    public int getQuantity(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public void recordTransaction(Transaction t) {
        transactionHistory.add(t);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    /** Calculates total current market value of all holdings using live prices from the market. */
    public double getPortfolioValue(Market market) {
        double total = 0;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            Stock stock = market.getStock(entry.getKey());
            if (stock != null) {
                total += stock.getPrice() * entry.getValue();
            }
        }
        return total;
    }

    public void displayHoldings(Market market) {
        if (holdings.isEmpty()) {
            System.out.println("You currently hold no stocks.");
            return;
        }
        System.out.println("\n----- YOUR PORTFOLIO -----");
        double total = 0;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            Stock stock = market.getStock(entry.getKey());
            double value = stock != null ? stock.getPrice() * entry.getValue() : 0;
            total += value;
            System.out.printf("%-6s | Qty: %-4d | Current Price: Rs.%-8.2f | Value: Rs.%.2f%n",
                    entry.getKey(), entry.getValue(), stock != null ? stock.getPrice() : 0, value);
        }
        System.out.printf("Total Portfolio Value: Rs.%.2f%n", total);
    }
}
