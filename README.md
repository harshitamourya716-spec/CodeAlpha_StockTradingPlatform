# CodeAlpha Stock Trading Platform

**Author:** Harshita Mourya | B.Tech CSE, Technocrats Institute of Technology, Bhopal (2nd Year)
**Internship:** CodeAlpha Java Programming Internship — Task 2

A console-based Java simulation of a basic stock trading environment.
I picked this task first because it needed the most OOP practice — modeling
stocks, a portfolio, transactions, and a market as separate classes felt
like a good way to actually apply what I've studied in my DSA/OOP coursework
instead of just theory.

## Features
- Live (simulated) market data for 6 stocks, with prices fluctuating randomly each tick
- Buy and sell stocks with real-time price checks
- Portfolio tracking: holdings, current value, and net worth
- Full transaction history log
- **Persistent storage**: cash balance and holdings are saved to
  `portfolio_data.txt` using Java File I/O, so your portfolio survives across runs

## Concepts Used
- Object-Oriented Programming (Stock, User, Portfolio, Transaction, Market classes)
- Collections (HashMap, ArrayList)
- File I/O for persisting portfolio data
- Java Time API (LocalDateTime) for transaction timestamps

## How to Run
```bash
javac *.java
java Main
```

## Project Structure
```
CodeAlpha_StockTradingPlatform/
├── Stock.java         # Represents a tradable stock
├── Market.java         # Simulates live market data
├── Transaction.java   # Represents a buy/sell transaction
├── Portfolio.java      # Tracks holdings + transaction history
├── User.java           # Trading account: cash + portfolio + file persistence
├── Main.java           # Console menu / entry point
└── README.md
```

## Sample Menu
```
1. View Live Market Data
2. Buy Stock
3. Sell Stock
4. View Portfolio Summary
5. View Transaction History
6. Exit
```

Starting virtual balance: **Rs. 1,00,000**

## What I Learned
This was my first time using a HashMap to model something real (holdings:
symbol -> quantity owned) instead of just for DSA practice problems. Also
had to think through edge cases like "what if I try to sell more shares
than I own" or "what if price goes below a rupee" — small things but they
made the buy/sell logic more solid.

---
Built for the **CodeAlpha Java Programming Internship**.
Harshita Mourya, B.Tech CSE, Technocrats Institute of Technology, Bhopal
