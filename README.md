# EduTrack Tutoring Center Management System

## Overview
EduTrack is a comprehensive management system for a tutoring center, built using **Java Enterprise Edition (JEE)** with **JSP, HTML, CSS, jQuery**, and **JavaScript** for the frontend. The backend is powered by **MySQL** and deployed on **Apache Tomcat 7**, following the **MVC architecture** with JEE servlets and controllers (without Spring or REST API). The project aims to streamline the management of courses, scheduling, attendance, and other administrative tasks.

---

## Table of Contents
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
---

## Technologies Used
- **Frontend**: JSP, HTML, CSS, jQuery, JavaScript
- **Backend**: JEE (Java Enterprise Edition) with servlets and controllers
- **Database**: MySQL
- **Server**: Apache Tomcat 7
- **Architecture**: MVC (Model-View-Controller)

---

## Project Structure
```plaintext
eduTrack/
├── build/
│   └── classes/
│       ├── Controllers/          # JEE servlets and controllers for handling requests
│       ├── dao/                  # Data access objects for database operations
│       ├── entities/             # Entity classes representing database tables
│       └── tools/                # Utility classes
├── src/
│   ├── main/
│   │   ├── java/                 # Java source files
│   │   │   ├── Controllers/      # Servlet controllers for handling requests
│   │   │   ├── dao/              # Data access objects
│   │   │   ├── entities/         # Entity classes
│   │   │   └── tools/            # Utility classes
│   │   └── webapp/               # Web application files
│   │       ├── META-INF/         # Meta-information files
│   │       ├── Views/            # JSP views for each user interface page
│   │       ├── WEB-INF/          # Web application configuration (web.xml, libraries)
│   │       └── assets/           # Static assets (CSS, JS, images, etc.)
├── script.sql                    # SQL script for database setup
└── scriptSql.sql                 # Additional SQL for sample data or configuration
