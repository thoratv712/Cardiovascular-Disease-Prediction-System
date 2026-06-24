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

<img width="1366" height="730" alt="CVD SS 1 0" src="https://github.com/user-attachments/assets/3e5e740f-7f05-4626-9f60-14ac3c0a06dd" />
<img width="1366" height="728" alt="CVD SS 2 0" src="https://github.com/user-attachments/assets/25134044-1eab-4543-ac66-5bed35f00d87" />
<img width="1366" height="728" alt="CVD SS 2 1" src="https://github.com/user-attachments/assets/3726e336-e5ea-403e-9c36-214a21bea748" />
<img width="1366" height="728" alt="CVD SS 2 2" src="https://github.com/user-attachments/assets/abd89a21-5810-4b98-9943-02534d68b027" />
<img width="1366" height="728" alt="CVD SS 3 0" src="https://github.com/user-attachments/assets/98e1b951-1eea-4f1c-8a1d-3d88bc86c184" />
<img width="1366" height="728" alt="CVD SS 3 1" src="https://github.com/user-attachments/assets/1bf9c682-622a-40da-b2b7-6ed21f6abfb3" />
<img width="1366" height="728" alt="CVD SS 4 0" src="https://github.com/user-attachments/assets/b1503399-4533-456c-8905-64f0369bbabf" />
<img width="1366" height="728" alt="CVD SS 4 1" src="https://github.com/user-attachments/assets/1abff9fa-0429-4cd0-80e9-4c6230945105" />
<img width="1366" height="728" alt="CVD SS 4 2" src="https://github.com/user-attachments/assets/1f00fa21-d97f-4f9a-88dc-f256c3c917bd" />
<img width="1366" height="728" alt="CVD SS 4 3" src="https://github.com/user-attachments/assets/60b5e726-fc20-420a-b8be-7d4018862550" />
<img width="1366" height="728" alt="CVD SS 4 4" src="https://github.com/user-attachments/assets/8009e809-26c8-45c4-bc3b-014c02cfafc5" />
<img width="1366" height="728" alt="CVD SS 4 5" src="https://github.com/user-attachments/assets/ec4c8d3e-ba3f-43b0-ab8a-25e3fa570f39" />
<img width="1366" height="728" alt="CVD SS 5 0" src="https://github.com/user-attachments/assets/d8dd8167-fd43-4e67-b6f0-776d62021a6f" />
<img width="1366" height="728" alt="CVD SS 5 1" src="https://github.com/user-attachments/assets/996dd5d4-b7a3-4886-b284-39fcf8acf1a8" />
<img width="1366" height="728" alt="CVD SS 5 2" src="https://github.com/user-attachments/assets/4283fd13-5226-4daa-9917-e596890724fb" />
<img width="1366" height="728" alt="CVD SS 6 0" src="https://github.com/user-attachments/assets/237843f6-7adf-4328-9495-af1bf60b78fc" />


