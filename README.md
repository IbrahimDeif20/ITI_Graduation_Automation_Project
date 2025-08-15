# 🛒 Web Automation Graduation Project

An end-to-end **automated testing framework** for an e-commerce platform, built with **Java**, **Selenium WebDriver**, **TestNG**, and **Page Object Model (POM)**.  
The project automates core user journeys such as **registration**, **login**, **shopping cart operations**, **checkout**, and **product search**, with advanced reporting using **Allure** and CI/CD execution via **Maven** and **Jenkins**.

---

## 📋 Features

### 🔍 Automated Test Coverage
- **User Registration** (valid and invalid scenarios)
- **Login** with valid and invalid credentials
- **Home Page Navigation** and search functionality
- **Shopping Cart** add, update, clear, and price validation
- **Product Sorting** and view mode changes
- **Checkout Process** with address entry and order completion
- **Validation of UI elements** like footer links and social media redirections

### 🏗️ Framework Design
- **Page Object Model (POM)** for maintainable locators and methods
- **Data-Driven Testing** using JSON and properties files
- **Listeners** for logging and screenshot capture on failures
- **Reusable Utilities** for waits, dropdown selection, scrolling, screenshots, and logging

### 📊 Reporting & Logging
- **Allure Reports** for detailed, interactive test results
- **Log4j2 Logging** for debugging and execution tracking
- Automatic **screenshot capture** on test failures or validations

### ⚙️ Configuration
- **Cross-browser execution** (Chrome, Edge, Firefox)
- Environment URLs and credentials stored in properties and JSON files
- Test execution configured via **TestNG XML suites**

---

## 🛠️ Tech Stack
- **Language:** Java
- **Automation Tool:** Selenium WebDriver
- **Test Framework:** TestNG
- **Design Pattern:** Page Object Model (POM)
- **Build Tool:** Maven
- **Reporting:** Allure
- **Logging:** Log4j2
- **Data Management:** JSON & Properties files
- **Continuous Integration:** Jenkins

---

## 🚀 How to Run

1. **Clone the Repository**
   ```bash
   git clone <repo-url>
   cd Web_automation_graduation_Project
