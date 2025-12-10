# **Monthly Budget Tracker — Android**

Monthly Budget Tracker is an Android app that helps users manage their monthly expenses.  
It provides clear reports, graphs, and transaction tracking by categories to give a full picture of the user’s spending.

The app follows **MVVM architecture** and uses **Room** for local database storage. Coroutines are used for async operations. No dependency injection or external APIs are used; all data is stored locally. UI is built using **XML layouts**.

---

## **Quick Start**

Install the app on your Android device or emulator.  
All data is stored locally and no backend or network connection is required.

---

## **Key Features**

📊 **Expenditure Reports** – View monthly spending summaries  
📈 **Graphs** – Visualize spending trends with charts  
💰 **Transaction Management** – Add, edit, or delete transactions  
🗂️ **Category Tracking** – Organize expenses by categories  
📝 **Monthly Overview** – Track income and expenses to stay within budget  

---

## **How the System Works**

🧠 **MVVM Architecture**  
ViewModels handle UI logic, Room repositories manage data, and XML layouts render the UI.  

💾 **Room Database**  
Stores all transactions, categories, and budget data locally.  

🔄 **Coroutines**  
Handles database operations asynchronously to keep the UI smooth.  

📊 **Reports and Graphs**  
Aggregates transactions by category and month to generate summaries and charts.  

---

## **Getting Started**

Download and install the app on your device.  
Start adding transactions and categories to begin tracking your monthly budget.  

---

## **Best Practices**

📐 **MVVM** – Clear separation of UI and business logic  
🔄 **Offline-first Design** – Works entirely without internet  
💾 **Room Caching** – Reliable local storage for all transactions  
📊 **Reactive Updates** – UI updates automatically based on data changes  

---

## **Future Improvements**

💳 Support for recurring transactions  
📈 Advanced charts and reports  
🔔 Expense notifications and reminders  

---

## **Author**

Developed using Kotlin, XML layouts, MVVM, Room, and Coroutines.  
All data is stored locally and no external APIs are required.
