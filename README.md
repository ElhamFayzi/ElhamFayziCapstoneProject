# FundBridge  
*A Seamless Banking and Lending Service for Small Businesses*

---

## Overview  
FundBridge is a standalone Java command-line application built as a class project.  
It lets:

- **Individual users** register, log in, view balances, deposit/withdraw, and apply for business loans  
- **Lenders** register, log in, browse pending loan requests, filter by business type, and approve by index or unique ID  

---

## Features    
- **User Flows** 
    - **User Registration & Login**  
    - **Account Management**: view balance, deposit, withdraw  
    - **Business Loan Application**: submit with financial details  
    - **Loan Tracking**: view status and history of past applications  
- **Lender Flows**  
    - **Lender Portal**: register/login as lender, browse & filter loan requests  
    - **Loan Approval**: approve by list index or unique application ID  
    - **Custom Data Structures**: LinkedList & Queue for workflows  
- **Data Storage**
    - **File-Based Persistence**: .CSV and .TXT files under `Directories/` and `Users/`

---

## Prerequisites  
- JDK 11 or newer  

---

## Project Structure 
- **Core Classes**
- **S\_**: Service classes  
  Handle registration, login, account operations, loan submission, and approvals (e.g. `S_UserService.java`, `S_LenderService.java`)

- **U\_**: Utility & Data Structure classes  
  Custom LinkedList, Queue implementations and search/sort utilities (e.g. `U_LinkedList.java`, `U_Queue.java`, `U_SearchAndSort.java`)

- **C\_**: Comparator classes  
  Provide `Comparator` implementations for sorting lenders and loan requests (e.g. `C_LenderNameComparator.java`, `C_LoanReqNameComparator.java`)

- **No prefix**: Core components  
  The CLI entry point (`BankingApp.java`), other main classes (`User.java`, `Lender.java`, `Loan.java`), and their inherited classes
  
- **Directories/**
  - PersonalBankingUsers.txt  --> List of Users
  -  LenderDirectory.csv       --> List of Lenders
  -  loanApplications.csv      --> Loan Applications

- **Users/**
  - <First Last>.csv          --> Loan Applications of individual users

---

## Quick Start 
- **Personal Banking**
``` _src/ java BankingApp.java 1_```

- **Businesses and Lenders**
```_src/ java BankingApp.java 2_```
