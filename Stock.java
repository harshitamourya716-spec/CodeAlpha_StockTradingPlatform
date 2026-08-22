/**
 * Represents a single stock with a ticker symbol, company name, and current market price.
 * Price fluctuates randomly each "market tick" to simulate a live market.
 */
public class Stock {
    private String symbol;
    private String companyName;
    private double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    /** Simulates market movement by randomly changing the price by up to +/-5%. */
    public void fluctuatePrice() {
        double changePercent = (Math.random() * 10 - 5) / 100.0; // -5% to +5%
        price = price * (1 + changePercent);
        if (price < 1) price = 1; // price floor
        price = Math.round(price * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-20s | Rs.%.2f", symbol, companyName, price);
    }
}
