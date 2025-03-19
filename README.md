# Bank Transaction System

This is a simple bank transaction system that allows users to perform various banking operations such as deposits, withdrawals, and transfers. The system also includes user authentication and transaction history features.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven (for building the project)
- An IDE such as IntelliJ IDEA or Eclipse (optional)

## Project Structure

```
.classpath
.gitignore
.project
updated_system_uml.drawio
bin/
    class_diagram.puml
    bank_system/
        AppInitializer.class
        constants/
            CurrencyConstants.class
        controller/
            AuthController.class
            AuthController$SingletonHelper.class
            TransactionController.class
        libs/
            java-json.jar
        model/
            ...
        tests/
            ...
        view/
resources/
    logo.png
src/
    class_diagram.puml
    bank_system/
        AppInitializer.java
        constants/
        controller/
        libs/
        model/
        tests/
        view/
resources/
    logo.png
```

## How to Run

1. **Clone the repository:**

    ```sh
    git clone https://github.com/your-username/bank-transaction-system.git
    cd bank-transaction-system
    ```

2. **Build the project:**

    Use Maven to build the project. Run the following command in the project root directory:

    ```sh
    mvn clean install
    ```

3. **Run the application:**

    After building the project, you can run the application using the following command:

    ```sh
    java -cp target/bank-transaction-system-1.0-SNAPSHOT.jar bank_system.AppInitializer
    ```

    Alternatively, you can run the application from your IDE by running the `main` method in the `AppInitializer` class.

## Usage

- **Login:** Users can log in using their username and password.
- **Register:** New users can register by providing a username and password.
- **Deposit:** Users can deposit money into their account.
- **Withdraw:** Users can withdraw money from their account.
- **Transfer:** Users can transfer money to another user's account.
- **Transaction History:** Users can view their transaction history.

## UML Diagram

The UML diagram for the system is available in the `updated_system_uml.drawio` file. You can open this file using [draw.io](https://app.diagrams.net/) to view the diagram.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.