
// File: Transaction.java
import java.util.Date;

abstract class Transaction {
    protected String transactionId;
    protected Date date;
    protected double amount;
    protected Account account;

    public Transaction(String transactionId, double amount, Account account) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = new Date();
        this.account = account;
    }

    public abstract void execute();
}

// File: Withdrawal.java
class Withdrawal extends Transaction {
    public Withdrawal(String transactionId, double amount, Account account) {
        super(transactionId, amount, account);
    }

    @Override
    public void execute() {
        if (account.debit(amount)) {
            System.out.println("Withdrawal of $" + amount + " successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}

// File: Deposit.java
class Deposit extends Transaction {
    public Deposit(String transactionId, double amount, Account account) {
        super(transactionId, amount, account);
    }

    @Override
    public void execute() {
        account.credit(amount);
        System.out.println("Deposit of $" + amount + " successful.");
    }
}

// File: Account.java
class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public boolean debit(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void credit(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

// File: Card.java
class Card {
    private String cardNumber;
    private String expiryDate;
    private String pin;

    public Card(String cardNumber, String expiryDate, String pin) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.pin = pin;
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public String getCardDetails() {
        return "Card Number: " + cardNumber + ", Expiry: " + expiryDate;
    }
}

// File: Customer.java
import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private String address;
    private String customerId;
    private List<Account> accounts = new ArrayList<>();

    public Customer(String name, String address, String customerId) {
        this.name = name;
        this.address = address;
        this.customerId = customerId;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}

// File: Bank.java
class Bank {
    private String name;
    private String bankCode;

    public Bank(String name, String bankCode) {
        this.name = name;
        this.bankCode = bankCode;
    }

    public boolean verifyAccount(Account account) {
        return account != null;
    }

    public boolean transferFunds(Account from, Account to, double amount) {
        if (from.debit(amount)) {
            to.credit(amount);
            return true;
        }
        return false;
    }
}

// File: ATM.java
class ATM {
    private String location;
    private String id;

    public ATM(String location, String id) {
        this.location = location;
        this.id = id;
    }

    public boolean validateCard(Card card, String inputPin) {
        return card.validatePin(inputPin);
    }

    public void ejectCard() {
        System.out.println("Card ejected.");
    }

    public void processTransaction(Transaction transaction) {
        transaction.execute();
    }
}

// File: ATMSystem.java
public class ATMSystem {
    public static void main(String[] args) {
        Account acc = new Account("123456", 1000);
        Card card = new Card("987654321", "12/26", "1234");
        Customer customer = new Customer("Alice", "123 Main St", "C001");
        customer.addAccount(acc);
        Bank bank = new Bank("Example Bank", "EB123");
        ATM atm = new ATM("Downtown", "ATM001");

        if (atm.validateCard(card, "1234")) {
            System.out.println("Card validated.");
            Transaction withdrawal = new Withdrawal("TXN001", 200, acc);
            atm.processTransaction(withdrawal);
            System.out.println("Balance after: $" + acc.getBalance());
        } else {
            System.out.println("Invalid PIN.");
        }

        atm.ejectCard();
    }
}
