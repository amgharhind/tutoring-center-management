# EduTrack: Tutoring Center Management System

## Overview
EduTrack is a comprehensive web-based management system designed to streamline the administrative and operational tasks of a tutoring center. Built using **Java Enterprise Edition (JEE)**, it provides functionalities for managing students, teachers, courses, schedules, and payments. The system adheres to the **MVC architecture** with JEE servlets and controllers, ensuring a clear separation of concerns and a robust backend. The frontend is developed with **JSP, HTML, CSS, jQuery, and JavaScript**, offering an intuitive user experience. It is designed to be deployed on **Apache Tomcat 7** and utilizes **MySQL** for data persistence.

## The "Why": Motivation and Importance

Managing a tutoring center manually can be complex, time-consuming, and prone to errors, especially as the number of students, teachers, and courses grows. This system addresses these challenges by:

*   **Automating Administrative Tasks:** Significantly reducing manual effort in areas such as course registration, scheduling, attendance tracking, and record-keeping.
*   **Improving Data Accessibility and Centralization:** Providing a single, authoritative platform for all relevant information, making it easily accessible to authorized personnel.
*   **Enhancing Communication:** Facilitating seamless interaction and information exchange between students, teachers, and administrators.
*   **Optimizing Resource Utilization:** Efficiently managing tutoring sessions, including the allocation of classrooms, teacher availability, and course assignments.
*   **Ensuring Data Integrity:** Maintaining consistent, accurate, and up-to-date records of all operations and user data.

## Key Features

*   **User Management**: Comprehensive registration and management of student, teacher, and administrator accounts.
*   **Course Management**: Efficient creation, assignment, and tracking of courses and subjects offered by the center.
*   **Scheduling**: Advanced management of tutoring sessions, including dynamic allocation of rooms and teachers.
*   **Attendance Tracking**: Robust system for recording and monitoring student attendance for each session.
*   **Payment Management**: Streamlined handling of student payments, invoices, and financial records.
*   **Reporting**: Generation of insightful reports on student performance, teacher workload, and financial summaries to aid decision-making.
*   **User-Friendly Interface**: Intuitive frontend built with JSP, HTML, CSS, jQuery, and JavaScript for an optimal user experience.
*   **Robust Backend**: Developed with JEE servlets and controllers, ensuring a stable and scalable system.
*   **MVC Architecture**: Adherence to the Model-View-Controller design pattern for clear separation of concerns and maintainable code.

## Technology Stack

*   **Frontend**: JSP, HTML, CSS, jQuery, JavaScript
*   **Backend**: JEE (Java Enterprise Edition) with Servlets and Controllers
*   **Database**: MySQL
*   **Server**: Apache Tomcat 7
*   **Architecture**: MVC (Model-View-Controller)

## Project Structure

The project follows a standard Java web application structure, organized to separate concerns and facilitate development:

```
tutoring-center-management/
├── README.md                                       # This file
├── tutoring-center-management/
│   ├── eduTrack2/
│   │   ├── .settings/                              # Eclipse project settings
│   │   ├── build/classes/                          # Compiled Java classes
│   │   ├── src/main/
│   │   │   ├── java/                               # Java source code
│   │   │   │   ├── Controllers/                    # Servlets handling requests and responses
│   │   │   │   │   ├── addDocumentController.java
│   │   │   │   │   ├── addMatiereToStudentController.java
│   │   │   │   │   ├── addSalleController.java
│   │   │   │   │   ├── chartController.java
│   │   │   │   │   ├── deleteStudentController.java
│   │   │   │   │   ├── groupesController.java
│   │   │   │   │   ├── loginController.java
│   │   │   │   │   ├── logoutCotroller.java
│   │   │   │   │   ├── matiereStudentController.java
│   │   │   │   │   ├── matieresController.java
│   │   │   │   │   ├── niveauController.java
│   │   │   │   │   ├── noAvailableSalleController.java
│   │   │   │   │   ├── payStudentController.java
│   │   │   │   │   ├── registerAbsenceController.java
│   │   │   │   │   ├── resetPasswordController.java
│   │   │   │   │   ├── sallesController.java
│   │   │   │   │   ├── sessionsController.java
│   │   │   │   │   ├── signUpController.java
│   │   │   │   │   ├── updateProfileController.java
│   │   │   │   │   ├── validerProfServlet.java
│   │   │   │   │   └── validerStudent.java
│   │   │   │   ├── dao/                            # Data Access Objects for database interaction
│   │   │   │   │   ├── DBUtil.java
│   │   │   │   │   ├── Dao.java
│   │   │   │   │   └── IDao.java
│   │   │   │   ├── entities/                       # Java Beans/POJOs representing database entities
│   │   │   │   └── tools/                            # Utility classes
│   │   │   └── webapp/                             # Web content (JSP, HTML, CSS, JS)
│   │   │       ├── META-INF/         # Meta-information files
│   │   │       ├── Views/            # JSP views for each user interface page
│   │   │       ├── WEB-INF/          # Web application configuration (web.xml, libraries)
│   │   │       └── assets/           # Static assets (CSS, JS, images, etc.)
│   │   ├── .classpath                              # Eclipse classpath configuration
│   │   └── .project                                # Eclipse project configuration
│   ├── script.sql                                  # Database schema and initial data script
│   └── scriptSql.sql                               # Additional SQL script (possibly for updates or specific functions)
```

## Getting Started

To set up and run this project locally, follow these steps:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/amgharhind/tutoring-center-management.git
    cd tutoring-center-management
    ```
2.  **Database Setup:**
    *   Create a new database (e.g., `eduTrack2`) in your preferred SQL database system (e.g., MySQL).
    *   Execute the `script.sql` and `scriptSql.sql` files to create the necessary tables and populate initial data.
    *   Update the database connection details in `DBUtil.java` (located in `src/main/java/dao/`) to match your database configuration.
3.  **Build and Deploy:**
    *   Import the `eduTrack2` project into an IDE like Eclipse.
    *   Ensure you have a Java Development Kit (JDK) and Apache Tomcat 7 (or similar servlet container) configured.
    *   Build the project to compile the Java source files.
    *   Deploy the `eduTrack2` web application to your servlet container.
4.  **Access the Application:**
    *   Once deployed, access the application through your web browser, typically at `http://localhost:8080/eduTrack2` (or your configured context path).

## Usage

Upon successful deployment, users (administrators, teachers, students) can log in to the system to perform various tasks:

*   **Administrators:** Manage users, courses, rooms, and view reports.
*   **Teachers:** Manage their assigned courses, record attendance, and update student progress.
*   **Students:** View their schedules, course materials, and payment status.

## Limitations and Future Work

*   **Security:** Implement more robust security measures, including input validation, protection against SQL injection, and proper session management to safeguard against common web vulnerabilities.
*   **User Interface/Experience:** Enhance the frontend with a more modern, responsive design and improved user experience (UX) to increase usability and engagement.
*   **Scalability:** Optimize the application for handling a larger number of concurrent users and extensive data, potentially by implementing caching strategies or load balancing.
*   **Reporting Features:** Expand the reporting capabilities with more detailed analytics, customizable report generation, and data visualization tools.
*   **Error Handling:** Implement comprehensive error handling and logging mechanisms to improve system stability and facilitate debugging.
*   **Framework Adoption:** Consider migrating to a more modern Java web framework (e.g., Spring Boot) for easier development, maintenance, and enhanced scalability, leveraging its ecosystem for rapid application development.




## License
This project is open-source and available under the [MIT License](LICENSE).
