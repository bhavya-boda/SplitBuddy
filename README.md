# 🌱 SplitBuddy

**SplitBuddy** is a simple console-based expense manager app built in Java. It helps groups of friends, roommates, or colleagues easily split shared expenses equally, by exact amounts, or by percentages, and settle balances automatically.

---

## ✨ Features

* ➕ Add expenses with different split strategies (Equal / Exact / Percentage)
* 📊 Show your personal balances (who you owe and who owes you)
* 💸 Settle up directly with another user (automatically settles the full amount you owe)
* 📃 View all outstanding balances in the group
* 🧹 Clear all data and start fresh anytime

---

## ⚙️ Technologies Used

* Java (Core Java, Collections, OOP)
* Console-based UI (using `Scanner`)

---

## 🚀 Getting Started

Follow these steps to run the project locally:

1. **Clone the repository:**

```bash
git clone https://github.com/yourusername/SplitBuddy.git
```

2. **Navigate into the project directory:**

```bash
cd SplitBuddy
```

3. **Compile the project:**

```bash
javac Main.java
```

4. **Run the application:**

```bash
java Main
```

---

## 📝 Usage Guide

When you run the app, you'll see:

```
=== SplitBuddy ===
1. Add expense
2. Show my balance
3. Settle expense with person
4. Show all balances
5. Clear all data
0. Exit
Choose option:
```

**Options explained:**

* `1`: Add a new expense and choose how to split it among users.
* `2`: View your personalized balance sheet (see who you owe and who owes you).
* `3`: Settle the full amount you owe to another user.
* `4`: Show all current non-zero balances in the group.
* `5`: Clear all data (users, balances, and expenses).
* `0`: Exit the application.

---

## 📌 Example

```
=== SplitBuddy ===
Choose option: 1
Enter payer userId: Alice
Enter total amount: 300
Enter number of users to split among: 2
Enter userId #1: Bob
Enter userId #2: Charlie
Choose split type (enter number):
1) Equal
2) Exact
3) Percentage
1
```

Then later:

```
Choose option: 3
Enter your userId: Bob
Enter other userId to settle with: Alice
You paid Rs 150.0 to Alice.
You are all settled with Alice.
```

---

## ✏️ Author

Created by **\[Bhavya Boda]** – Happy splitting with SplitBuddy! 💰

> ⭐ Don't forget to star the repository if you like it!
