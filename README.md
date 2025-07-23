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

- PINs are stored using **AES encryption**.
- Brute-force attacks are mitigated by locking the account temporarily after repeated failed login attempts.

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
   git clone https://github.com/snehanandanewuser/EncryptedATMSimulator.git
   ```
2. Open the project in any Java IDE (e.g., IntelliJ IDEA or Eclipse).
3. Compile and run the main class (`ATM.java` or equivalent).

---

## 📚 Learnings

Throughout the development of this project, I gained hands-on experience in:

- 🔐 Implementing AES encryption for secure PIN storage and verification  
- 🧠 Preventing brute-force attacks with login attempt limits  
- 💾 Using Java File I/O for persistent data handling  
- 🖥️ Designing desktop GUIs with Java Swing  
- 🧪 Testing features like deposit, withdrawal, and mini statement  
- 🛠️ Writing clean, modular code using object-oriented principles  
- 🧩 Simulating real-world ATM logic securely and effectively  

This project deepened my understanding of **Java programming** and strengthened my knowledge of **secure software design**.
