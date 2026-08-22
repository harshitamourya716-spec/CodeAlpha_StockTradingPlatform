import java.util.*;

/**
 * Simulates a basic stock market containing several tradable stocks.
 * Prices update ("tick") each time the user requests market data.
 */
public class Market {
    private Map<String, Stock> stocks;

    public Market() {
        stocks = new LinkedHashMap<>();
        stocks.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3800));
        stocks.put("INFY", new Stock("INFY", "Infosys Ltd", 1550));
        stocks.put("RELI", new Stock("RELI", "Reliance Industries", 2900));
        stocks.put("HDFC", new Stock("HDFC", "HDFC Bank", 1650));
        stocks.put("WIPRO", new Stock("WIPRO", "Wipro Ltd", 480));
        stocks.put("TATAM", new Stock("TATAM", "Tata Motors", 950));
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol.toUpperCase());
    }

    public Collection<Stock> getAllStocks() {
        return stocks.values();
    }

    /** Randomly updates the price of every stock to simulate live market movement. */
    public void tick() {
        for (Stock s : stocks.values()) {
            s.fluctuatePrice();
        }
    }

    public void displayMarket() {
        System.out.println("\n----- LIVE MARKET DATA -----");
        System.out.printf("%-6s | %-20s | %s%n", "SYMBOL", "COMPANY", "PRICE");
        for (Stock s : stocks.values()) {
            System.out.println(s);
        }
    }
}
