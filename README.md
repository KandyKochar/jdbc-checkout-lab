# JDBC Checkout System Lab

A Java JDBC checkout system connecting to Oracle database for managing product inventory and purchase transactions.

## Overview
Extends JDBC concepts into a checkout system scenario, performing transactional operations including inventory queries, purchase recording, and total calculation.

## Features
- Product inventory lookup via JDBC
- Checkout transaction processing
- Total price calculation
- Oracle database integration

## Technologies Used
- **Language:** Java (JDK 8+)
- **Database:** Oracle SQL
- **API:** JDBC

## Project Structure
```
src/
+-- OracleInfo.java        # Database connection constants
+-- CheckoutSystem.java    # Checkout and inventory JDBC operations
```

## Prerequisites
- Oracle JDBC driver (ojdbc8.jar) on classpath
- Oracle database with product/checkout schema

## Usage
```bash
javac -cp ojdbc8.jar src/*.java -d out/
java -cp out/:ojdbc8.jar CheckoutSystem
```

## Author
Kandy Kochar
