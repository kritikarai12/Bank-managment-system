import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

public class Main extends JFrame {
    private HashMap<String, BankAccount> accounts = new HashMap<>();
    private JTextArea outputArea;
    private JTextField accountNumberField;
    private JTextField accountHolderField;
    private JTextField amountField;

    public Main() {
        setTitle("Bank Management System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        accountNumberField = new JTextField(15);
        accountHolderField = new JTextField(15);
        amountField = new JTextField(15);
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");

        createAccountButton.addActionListener(new CreateAccountAction());
        depositButton.addActionListener(new DepositAction());
        withdrawButton.addActionListener(new WithdrawAction());
        checkBalanceButton.addActionListener(new CheckBalanceAction());

        add(new JLabel("Account Number:"));
        add(accountNumberField);
        add(new JLabel("Account Holder:"));
        add(accountHolderField);
        add(new JLabel("Amount:"));
        add(amountField);
        add(createAccountButton);
        add(depositButton);
        add(withdrawButton);
        add(checkBalanceButton);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    private class CreateAccountAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String accountNumber = accountNumberField.getText();
            String accountHolder = accountHolderField.getText();

            if (accounts.containsKey(accountNumber)) {
                outputArea.setText("Account already exists!");
            } else {
                BankAccount account = new BankAccount(accountNumber, accountHolder);
                accounts.put(accountNumber, account);
                outputArea.setText("Account created successfully!");
            }
        }
    }

    private class DepositAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String accountNumber = accountNumberField.getText();
            double amount = Double.parseDouble(amountField.getText());

            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                account.deposit(amount);
                outputArea.setText("Deposited: " + amount);
            } else {
                outputArea.setText("Account not found!");
            }
        }
    }

    private class WithdrawAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String accountNumber = accountNumberField.getText();
            double amount = Double.parseDouble(amountField.getText());

            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                if (amount <= account.getBalance()) {
                    account.withdraw(amount);
                    outputArea.setText("Withdrew: " + amount);
                } else {
                    outputArea.setText("Insufficient funds!");
                }
            } else {
                outputArea.setText("Account not found!");
            }
        }
    }

    private class CheckBalanceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String accountNumber = accountNumberField.getText();

            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                outputArea.setText("Current Balance: " + account.getBalance());
            } else {
                outputArea.setText("Account not found!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
