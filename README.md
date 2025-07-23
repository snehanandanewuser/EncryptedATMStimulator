# 🔐 Encrypted ATM Simulator

🎓 **Capstone Project – 2nd Year B.Tech (CSE)**  
🗓️ **Developed in April–May 2025**

This is a secure **Java-based desktop ATM simulator** built using Java Swing GUI. It is designed to simulate key ATM functionalities with an added layer of **encryption and brute-force protection**.

---

## 🧩 Features

- 🔐 **AES-encrypted PIN** login system  
- 💰 **Balance inquiry**  
- 💸 **Deposit and withdrawal** functionality  
- 🧾 **Mini statement** (transaction history)  
- 🔁 **Change PIN** securely  
- 🚫 **Brute-force prevention**: Automatically blocks access after 5 failed login attempts  
- 💾 Data stored securely using **Java File I/O**

---

## 🛡️ Security Focus

- PINs are stored using **AES encryption**  
- Brute-force attacks are mitigated by locking the account temporarily after repeated failed login attempts

---

## 💻 Tech Stack

- Java (JDK 17+)  
- Java Swing for GUI  
- Java File I/O for local data handling  
- AES Encryption using `javax.crypto`

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/sneha1807-web/EncryptedATMSimulator.git
   ```

2. Open the project in your Java IDE (e.g., IntelliJ IDEA, Eclipse)

3. Run the `Main.java` file to launch the application
