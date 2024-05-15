# XSS Scanner

XSS Scanner is a web application built with Spring Boot that allows you to scan URLs for potential Cross-Site Scripting (XSS) vulnerabilities using the Dalfox tool.

## Key Features

- **Effortless Scanning**: Easily scan URLs for XSS vulnerabilities with just a few clicks.
- **Save Scan Results**: Save scan results for future reference with custom scan names.
- **Filter Results**: Filter scan results by severity to focus on critical vulnerabilities.

## How to Use

1. Enter a name for your scan and the URL you want to scan.
2. Click the "Scan" button to start the process.
3. Once the scan is complete, view the results.
4. Save the scan results with a custom name using the "Save" button.
5. Filter scan results by severity to prioritize fixing critical issues.

## Technologies Used

- **Spring Boot**: Backend framework for building robust Java applications.
- **Thymeleaf**: Server-side Java template engine for the frontend.
- **Dalfox**: Powerful XSS scanning tool for vulnerability detection.
- **WebClient**: Spring's reactive client for making HTTP requests.

## Getting Started

To run the application locally:


1. Ensure you have Java and Maven installed on your machine.
2. Run the application using `mvn spring-boot:run`.
3. Access the application at `http://localhost:8080`.

## Note

This project is for educational purposes and should only be used with appropriate permissions.
