# Smart Gate Pass Approval System — NIET Hostel

A Java-based web application designed to digitize and simplify the **gate pass approval process for students living in NIET Hostels**.

The system allows students to apply for gate passes online, enables Wardens and the Chief Warden to approve requests, and allows Security Guards to verify approved passes at the hostel gate.

---

## 🚀 Project Overview

The Smart Gate Pass Approval System provides a centralized platform for managing:

- Student registration
- Student account approval
- Hostel information
- Gate pass applications
- Warden approval
- Chief Warden approval
- Security Guard verification
- Student exit and return records
- Role-based access control

The main objective is to replace the traditional paper-based gate pass process with a **faster, secure, and digitally managed system**.

---

# 🏗️ System Architecture

The application follows a layered architecture using Advanced Java technologies.

```text
                    ┌─────────────────────┐
                    │      Browser        │
                    │  Student / Warden   │
                    │ Chief Warden / Guard │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      JSP Pages      │
                    │   Presentation UI   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     Servlets        │
                    │   Controllers       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Services       │
                    │ Business Logic      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        DAO          │
                    │ Database Operations │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │        MySQL        │
                    │      Database       │
                    └─────────────────────┘
```

---

# 👥 User Roles

The system contains four major roles.

## 1. Student

Students living in NIET Hostel can:

- Register an account
- Login
- View profile
- View hostel information
- Apply for a gate pass
- View previous gate pass requests
- Track approval status
- View their generated pass code

---

## 2. Warden

Wardens are responsible for:

- Login
- Viewing pending student registrations
- Approving or rejecting student registrations
- Viewing gate pass requests
- Approving or rejecting gate pass requests
- Adding approval remarks
- Managing students belonging to their hostel

---

## 3. Chief Warden

The Chief Warden provides the final approval.

The Chief Warden can:

- Login
- View gate pass requests approved by Wardens
- Give final approval
- Reject gate pass requests
- View request information

---

## 4. Security Guard

The Security Guard verifies approved gate passes at the hostel gate.

The Guard can:

- Login using the Guard Access Code
- Enter the Gate Pass Code
- View student and pass details
- Allow student exit
- Record student return

---

# 🔄 Complete System Flow

```text
                     START
                       │
                       ▼
                  Home Page
                       │
             ┌─────────┴─────────┐
             │                   │
             ▼                   ▼
       Hostel / Pricing       Login
             │                   │
             │           ┌───────┴────────┐
             │           │                │
             │           ▼                ▼
             │        Student           Admin
             │                            │
             │                     ┌──────┴──────┐
             │                     │             │
             │                  Warden      Chief Warden
             │
             ▼
       Student Registration
             │
             ▼
        Registration
             │
             ▼
          PENDING
             │
             ▼
        Warden Review
             │
       ┌─────┴─────┐
       │           │
     Reject      Approve
       │           │
       ▼           ▼
      END        ACTIVE
                   │
                   ▼
             Student Login
                   │
                   ▼
           Apply Gate Pass
                   │
                   ▼
             Generate Pass
                   │
                   ▼
                PENDING
                   │
                   ▼
             Warden Review
                   │
            ┌──────┴──────┐
            │             │
          Reject        Approve
            │             │
            ▼             ▼
           END       WARDEN_APPROVED
                          │
                          ▼
                   Chief Warden
                       Review
                          │
                   ┌──────┴──────┐
                   │             │
                 Reject        Approve
                   │             │
                   ▼             ▼
                  END          ACTIVE
                                 │
                                 ▼
                         Security Guard
                           Verification
                                 │
                                 ▼
                           Allow Exit
                                 │
                                 ▼
                          EXIT_RECORDED
                                 │
                                 ▼
                         Record Return
                                 │
                                 ▼
                            COMPLETED
```

---

# 📁 Project Structure

```text
GatePassSystem/
│
├── database/
│   └── schema.sql
│
├── src/
│   └── com/
│       └── niet/
│           └── gatepass/
│
│               ├── model/
│               │   ├── Student.java
│               │   ├── Warden.java
│               │   ├── ChiefWarden.java
│               │   ├── Hostel.java
│               │   ├── GatePass.java
│               │   └── GatePassLog.java
│               │
│               ├── dao/
│               │   ├── StudentDAO.java
│               │   ├── WardenDAO.java
│               │   ├── ChiefWardenDAO.java
│               │   ├── HostelDAO.java
│               │   ├── GatePassDAO.java
│               │   └── GatePassLogDAO.java
│               │
│               ├── service/
│               │   ├── LoginService.java
│               │   ├── StudentService.java
│               │   ├── GatePassService.java
│               │   ├── ApprovalService.java
│               │   └── VerificationService.java
│               │
│               ├── controller/
│               │   ├── RegisterServlet.java
│               │   ├── LoginServlet.java
│               │   ├── LogoutServlet.java
│               │   ├── StudentServlet.java
│               │   ├── GatePassServlet.java
│               │   ├── WardenServlet.java
│               │   ├── ChiefWardenServlet.java
│               │   └── VerifyPassServlet.java
│               │
│               ├── filter/
│               │   ├── AuthenticationFilter.java
│               │   ├── AuthorizationFilter.java
│               │   └── LoggingFilter.java
│               │
│               └── util/
│                   ├── DBConnection.java
│                   ├── PasswordUtil.java
│                   └── PassCodeGenerator.java
│
├── WebContent/
│   │
│   ├── index.jsp
│   ├── hostel.jsp
│   ├── pricing.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── error.jsp
│   │
│   ├── css/
│   │   └── style.css
│   │
│   ├── student/
│   │   ├── dashboard.jsp
│   │   ├── apply_gatepass.jsp
│   │   ├── my_requests.jsp
│   │   └── profile.jsp
│   │
│   ├── warden/
│   │   └── dashboard.jsp
│   │
│   ├── chiefwarden/
│   │   └── dashboard.jsp
│   │
│   ├── guard/
│   │   └── verify.jsp
│   │
│   └── WEB-INF/
│       ├── web.xml
│       └── lib/
│           └── mysql-connector-j.jar
│
├── .gitignore
├── .classpath
├── .project
└── README.md
```

---

# 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| JSP | Frontend / presentation |
| Servlets | Request handling |
| JDBC | Database connectivity |
| MySQL | Database |
| HTML | Page structure |
| CSS | User interface styling |
| Apache Tomcat | Application server |
| Eclipse | Development environment |
| Git & GitHub | Version control |

---

# 🗄️ Database

The application uses **MySQL**.

The database schema is available in:

```text
database/schema.sql
```

The database contains tables for:

- Students
- Wardens
- Chief Warden
- Hostels
- Gate Passes
- Gate Pass Logs

---

# 🔐 Authentication

The application supports login for:

```text
Student
   │
   ├── Student Dashboard
   │
   └── Gate Pass Application

Warden
   │
   └── Warden Dashboard

Chief Warden
   │
   └── Chief Warden Dashboard

Security Guard
   │
   └── Gate Pass Verification
```

Role-based filters are used to prevent unauthorized access.

---

# 📋 Gate Pass Status Lifecycle

The gate pass follows this lifecycle:

```text
PENDING
   │
   ▼
WARDEN_APPROVED
   │
   ▼
ACTIVE
   │
   ▼
EXIT_RECORDED
   │
   ▼
COMPLETED
```

Rejected requests can follow:

```text
PENDING
   │
   └──► WARDEN_REJECTED


WARDEN_APPROVED
   │
   └──► CHIEF_WARDEN_REJECTED
```

---

# 🚪 Gate Verification Flow

At the hostel gate:

```text
Student
   │
   ▼
Shows Gate Pass / Pass Code
   │
   ▼
Security Guard Login
   │
   ▼
Enter Pass Code
   │
   ▼
System verifies Pass
   │
   ├───────────────┐
   │               │
 Invalid          Valid
   │               │
   ▼               ▼
 Reject        Show Student
 Verification     Details
                     │
                     ▼
                 Allow Exit
                     │
                     ▼
              EXIT_RECORDED
                     │
                     ▼
                Student Returns
                     │
                     ▼
               Record Return
                     │
                     ▼
                 COMPLETED
```

---

# ⚙️ Prerequisites

Before running the project, install:

### 1. JDK

JDK 17 or higher.

Check installation:

```bash
java -version
```

and:

```bash
javac -version
```

### 2. Apache Tomcat

Tomcat 10 or higher is recommended.

The project uses the:

```text
jakarta.servlet.*
```

namespace.

### 3. MySQL

Install MySQL Server and MySQL Workbench.

### 4. Eclipse

Use:

```text
Eclipse IDE for Enterprise Java and Web Developers
```

---

# 🗃️ Database Setup

## Step 1

Open MySQL Workbench.

## Step 2

Open:

```text
database/schema.sql
```

## Step 3

Run the complete SQL script.

This creates the required database and tables.

---

# 🔧 Database Configuration

Open:

```text
src/com/niet/gatepass/util/DBConnection.java
```

Configure:

```java
private static final String DB_URL =
    "jdbc:mysql://localhost:3306/gatepass_db?useSSL=false&serverTimezone=UTC";

private static final String DB_USER = "root";

private static final String DB_PASSWORD = "YOUR_MYSQL_PASSWORD";
```

Replace:

```text
YOUR_MYSQL_PASSWORD
```

with your local MySQL password.

**Do not upload real passwords to a public GitHub repository.**

---

# ▶️ Running the Project

## Step 1

Open Eclipse.

## Step 2

Import the project as a Dynamic Web Project.

## Step 3

Configure Apache Tomcat.

## Step 4

Make sure MySQL is running.

## Step 5

Make sure the MySQL Connector/J JAR is available:

```text
WebContent/WEB-INF/lib/
```

## Step 6

Right-click the project.

Select:

```text
Run As
   ↓
Run on Server
```

## Step 7

Select your Tomcat server.

## Step 8

Open:

```text
http://localhost:8080/GatePassSystem/
```

---

# 🧪 Complete Testing Procedure

### Step 1 — Student Registration

Open:

```text
Register
```

Enter:

```text
Name
Student ID
Email
Password
Hostel
Room Number
Phone
```

Submit the registration.

The account initially becomes:

```text
PENDING
```

---

### Step 2 — Warden Approval

Login as the Warden.

Open the pending student registrations.

Approve the student.

The student status becomes:

```text
ACTIVE
```

---

### Step 3 — Student Login

Login using the student's credentials.

Open:

```text
Apply Gate Pass
```

Fill in the required information.

Submit the request.

The gate pass becomes:

```text
PENDING
```

---

### Step 4 — Warden Approval

Login as the Warden.

Open pending gate pass requests.

Approve the request.

Status:

```text
WARDEN_APPROVED
```

---

### Step 5 — Chief Warden Approval

Login as the Chief Warden.

Review the request.

Approve the gate pass.

Status:

```text
ACTIVE
```

The pass is now ready for gate verification.

---

### Step 6 — Security Guard Verification

Login as Security Guard.

Enter the generated Pass Code.

The system displays the student's information.

Click:

```text
Allow Exit
```

Status becomes:

```text
EXIT_RECORDED
```

When the student returns, verify the same pass again and select:

```text
Record Return
```

Status becomes:

```text
COMPLETED
```

---

# 🔑 Demo Credentials

The database seed file can contain demo accounts for testing.

Example:

| Role | Email |
|---|---|
| Chief Warden | chiefwarden@niet.co.in |
| Warden 1 | warden1@niet.co.in |
| Warden 2 | warden2@niet.co.in |
| Warden 3 | warden3@niet.co.in |

Use the password configured in your `schema.sql`.

For security, do not publish real passwords in a public repository.

---

# 🔒 Security Features

The application includes:

- Authentication
- Authorization
- Role-based access control
- Password hashing
- Session management
- Authentication filters
- Authorization filters
- Logging filters
- Gate pass verification
- Unique pass code generation

---

# 📊 Main Modules

```text
1. Public Website
      │
      ├── Home
      ├── Hostel Information
      └── Pricing
       
2. Authentication
      │
      ├── Registration
      └── Login

3. Student Module
      │
      ├── Dashboard
      ├── Profile
      ├── Apply Gate Pass
      └── My Requests

4. Warden Module
      │
      ├── Student Approval
      └── Gate Pass Approval

5. Chief Warden Module
      │
      └── Final Gate Pass Approval

6. Security Guard Module
      │
      └── Gate Pass Verification
```

---

# 🌟 Future Enhancements

The following features can be added in future versions:

- QR Code based gate pass
- Email notifications
- SMS notifications
- Real-time approval notifications
- Guard-specific accounts
- Admin analytics dashboard
- Gate pass history reports
- PDF gate pass generation
- Mobile application
- Improved password security using BCrypt or Argon2
- HTTPS deployment
- Maven/Gradle dependency management

---

# 👨‍💻 Project Purpose

This project was developed as an **Advanced Java / Java Web Technology project** to demonstrate the practical implementation of:

- JSP
- Servlets
- JDBC
- MySQL
- MVC architecture
- Session management
- Filters
- Authentication
- Authorization
- CRUD operations
- Role-based access control

---

# 📌 Project Status

```text
Project: Smart Gate Pass Approval System
Organization: NIET Hostel
Technology: Advanced Java
Backend: Java Servlet + JDBC
Frontend: JSP + HTML + CSS
Database: MySQL
Server: Apache Tomcat
Status: Working MVP
```

---

# 👥 User Flow Summary

```text
STUDENT
   │
   ▼
Register
   │
   ▼
Warden Approves Registration
   │
   ▼
Student Login
   │
   ▼
Apply Gate Pass
   │
   ▼
Warden Approval
   │
   ▼
Chief Warden Approval
   │
   ▼
Gate Pass Active
   │
   ▼
Security Guard Verification
   │
   ▼
Exit
   │
   ▼
Return
   │
   ▼
Completed
```

---

## 📄 License

This project is developed for educational and academic purposes.