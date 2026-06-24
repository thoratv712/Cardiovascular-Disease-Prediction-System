# Cardiovascular Disease Prediction System — Project Details

## Project Overview
**Title:** A Hybrid Machine Learning Framework for Cardiovascular Disease Prediction
**Type:** M.E. Dissertation Project
**Domain:** Healthcare + Machine Learning

---

## Technology Stack

| Technology | Purpose |
|------------|---------|
| **Java (JDK 1.7+)** | Core programming language for backend logic |
| **JSP (JavaServer Pages)** | Frontend pages — Login, Dashboard, Forms, Results |
| **Servlets** | Backend request handling (login, upload, prediction) |
| **Apache Tomcat 9.0** | Web server to run JSP + Servlets |
| **MySQL** | Database to store patient data and model metrics |
| **HeidiSQL** | GUI tool to manage MySQL database |
| **WEKA Library** | Machine learning — Random Forest, SVM, feature selection |
| **XGBoost4j** | Gradient boosting classifier |
| **Eclipse IDE** | Development environment |
| **Bootstrap + Highcharts** | UI styling and graph visualization |
| **HTML/CSS/JavaScript** | Frontend structure and interactivity |

---

## Core Modules

| Module | Function |
|--------|----------|
| Login | Admin authentication |
| Data Upload | Upload patient CSV dataset to MySQL |
| Preprocessing | Clean data, convert class to nominal, normalize |
| Feature Selection | PSO + FCBF + ReliefF to select important attributes |
| Classification | Train Random Forest, SVM, XGBoost models |
| Analysis | Display Accuracy, Precision, Recall, F1-score with charts |
| Prediction | Input new patient details → predict CVD risk |
| Export | Download performance metrics as CSV |

---

## Machine Learning Pipeline

```
Raw Dataset (CSV)
    ↓
Preprocessing (Clean + Normalize)
    ↓
Feature Selection (PSO + FCBF + ReliefF)
    ↓
Train Models (RF, SVM, XGBoost)
    ↓
Evaluate (Accuracy, Precision, Recall, F1)
    ↓
Best Model Selected
    ↓
Predict New Patient Risk
```

---

## Project Run Procedure

### Step 1 — Setup Database
```
1. Open HeidiSQL
2. Connect to MySQL server (localhost:3307)
3. Create database: 26_cardiovascular_disease_dataset
4. Create tables: heart_data, tblanalysis
```

### Step 2 — Setup Project in Eclipse
```
1. Open Eclipse IDE
2. File → Import → Existing Projects into Workspace
3. Browse to project folder
4. Click Finish
```

### Step 3 — Configure Tomcat Server
```
1. Window → Show View → Servers
2. Right click → New → Server
3. Select Apache Tomcat v9.0
4. Add project to server
```

### Step 4 — Verify Database Connection
```
Check Dbconn.java has correct:
- Database URL
- Username (root)
- Password (admin)
- Port (3307)
```

### Step 5 — Run Application
```
1. Right click project → Run As → Run on Server
2. Wait for Tomcat to start
3. Browser opens automatically
```

### Step 6 — Use Application
```
1. Login with: admin@gmail.com / admin
2. Upload CSV dataset
3. Click Pre-process
4. Click Feature Selection
5. Click Classification (trains RF, SVM, XGBoost)
6. View Analysis page (see accuracy charts)
7. Go to Prediction → enter patient details → get risk result
```

### Step 7 — Access URLs Directly
```
http://localhost:8080/Cardiovascular_Disease_Application/loginpage
```

---

## System Requirements

| Requirement | Specification |
|-------------|---------------|
| OS | Windows 7 or higher |
| RAM | Minimum 8 GB |
| Processor | Intel i5 or higher |
| Storage | 500 GB |
| JDK | 1.7 or higher |
| MySQL | 5.1 or higher |

---

## Output Results

| Model | Accuracy | Precision | Recall | F1-Score |
|-------|----------|-----------|--------|----------|
| Random Forest | 96.00% | 97.53% | 95.18% | 96.34% |
| SVM | 94.67% | 95.73% | 94.58% | 95.15% |
| XGBoost | 95.00% | 95.21% | 95.78% | 95.50% |

**Best Model:** Random Forest (96% accuracy)
