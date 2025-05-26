import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class EncryptedATMSimulator extends JFrame {
    private double balance;
    private static final String SECRET_KEY = "1234567890123456"; // 16-char AES key
    private String encryptedPIN;
    private int attemptsLeft = 5;

    private JTextField pinField = new JPasswordField(10);
    private JButton loginButton = new JButton("Login");

    private JPanel menuPanel = new JPanel();
    private JTextArea displayArea = new JTextArea(10, 30);

    private java.util.List<String> transactionHistory = new ArrayList<>();

    public EncryptedATMSimulator() {
        // Encrypt the actual PIN (e.g., "1234")
        encryptedPIN = loadPIN(); // Load the encrypted PIN from file

        loadBalance(); // Load balance from file

        setTitle("ATM Simulator");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.add(new JLabel("Enter PIN:"));
        loginPanel.add(pinField);
        loginPanel.add(loginButton);
        add(loginPanel, BorderLayout.NORTH);

        // Display Area
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // ATM Menu
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10));
        String[] actions = { "Check Balance", "Deposit", "Withdraw", "Mini Statement", "Change PIN", "Exit" };
        for (String action : actions) {
            JButton btn = new JButton(action);
            menuPanel.add(btn);
            btn.addActionListener(e -> handleAction(action));
        }

        // Login Button Action
        loginButton.addActionListener(e -> {
            String enteredPin = pinField.getText();
            if (verifyPIN(enteredPin)) {
                remove(loginPanel);
                add(menuPanel, BorderLayout.SOUTH);
                revalidate();
                repaint();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                displayArea.setText("Welcome!\nLogin time: " + dtf.format(LocalDateTime.now()) + "\n\n");
            } else {
                attemptsLeft--;
                if (attemptsLeft == 0) {
                    JOptionPane.showMessageDialog(this, "Too many failed attempts. Exiting.", "Blocked", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect PIN. Attempts left: " + attemptsLeft, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean verifyPIN(String enteredPIN) {
        return encryptedPIN.equals(encrypt(enteredPIN));
    }

    private void handleAction(String action) {
        switch (action) {
            case "Check Balance":
                displayArea.append("Balance: ₹" + String.format("%.2f", balance) + "\n");
                break;
            case "Deposit":
                String depStr = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
                try {
                    double dep = Double.parseDouble(depStr);
                    if (dep > 0) {
                        balance += dep;
                        saveBalance();
                        String log = "Deposited ₹" + dep + " on " + getCurrentTime();
                        transactionHistory.add(log);
                        displayArea.append(log + "\n");
                    }
                } catch (Exception e) {
                    displayArea.append("Invalid deposit.\n");
                }
                break;
            case "Withdraw":
                String withStr = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
                try {
                    double with = Double.parseDouble(withStr);
                    if (with > 0 && with <= balance) {
                        balance -= with;
                        saveBalance();
                        String log = "Withdrawn ₹" + with + " on " + getCurrentTime();
                        transactionHistory.add(log);
                        displayArea.append(log + "\n");
                    } else {
                        displayArea.append("Insufficient balance or invalid amount.\n");
                    }
                } catch (Exception e) {
                    displayArea.append("Invalid withdrawal.\n");
                }
                break;
            case "Mini Statement":
                displayArea.append("\n--- Mini Statement ---\n");
                if (transactionHistory.isEmpty()) {
                    displayArea.append("No transactions yet.\n");
                } else {
                    for (String tx : transactionHistory) {
                        displayArea.append(tx + "\n");
                    }
                }
                displayArea.append("----------------------\n");
                break;
            case "Change PIN":
                String oldPin = JOptionPane.showInputDialog(this, "Enter current PIN:");
                if (!verifyPIN(oldPin)) {
                    displayArea.append("Incorrect current PIN.\n");
                    return;
                }
                String newPin = JOptionPane.showInputDialog(this, "Enter new PIN (4 digits):");
                if (newPin != null && newPin.matches("\\d{4}")) {
                    encryptedPIN = encrypt(newPin);
                    savePIN(); // Save the new PIN to file
                    displayArea.append("PIN successfully changed.\n");
                } else {
                    displayArea.append("Invalid new PIN. Must be 4 digits.\n");
                }
                break;
            case "Exit":
                saveBalance();
                System.exit(0);
        }
    }

    // AES Encryption
    private String encrypt(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encVal = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed");
        }
    }

    // Load encrypted PIN from file
    private String loadPIN() {
        try (BufferedReader reader = new BufferedReader(new FileReader("pin.txt"))) {
            return reader.readLine();
        } catch (IOException e) {
            return encrypt("1234"); // Default PIN if not set
        }
    }

    // Save encrypted PIN to file
    private void savePIN() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pin.txt"))) {
            writer.write(encryptedPIN);
        } catch (IOException e) {
            displayArea.append("Error saving PIN.\n");
        }
    }

    // Load balance from file
    private void loadBalance() {
        try (BufferedReader reader = new BufferedReader(new FileReader("balance.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                balance = Double.parseDouble(line.trim());
            } else {
                balance = 5000;
            }
        } catch (IOException | NumberFormatException e) {
            balance = 5000; // Default balance
        }
    }

    // Save balance to file
    private void saveBalance() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("balance.txt"))) {
            writer.write(String.valueOf(balance));
        } catch (IOException e) {
            displayArea.append("Error saving balance.\n");
        }
    }

    private String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EncryptedATMSimulator().setVisible(true);
        });
    }
}