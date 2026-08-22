import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single buy or sell transaction made by the user.
 */
public class Transaction {
    private String type;       // BUY or SELL
    private String symbol;
    private int quantity;
    private double pricePerShare;
    private LocalDateTime timestamp;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Transaction(String type, String symbol, int quantity, double pricePerShare) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.timestamp = LocalDateTime.now();
    }

    public double getTotalValue() {
        return quantity * pricePerShare;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-4s %4d x %-6s @ Rs.%.2f = Rs.%.2f",
                timestamp.format(FORMATTER), type, quantity, symbol, pricePerShare, getTotalValue());
    }

    /** Serializes the transaction to a single CSV line for file storage. */
    public String toCsv() {
        return type + "," + symbol + "," + quantity + "," + pricePerShare + "," + timestamp;
    }
}
