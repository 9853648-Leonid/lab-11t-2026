import model.BankAccount;
import model.CheckingAccount;
import exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInterface {

    private final List<BankAccount> accounts = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() {
        while (true) {
            try {
                printMainMenu();

                String option = scanner.next();

                switch (Integer.parseInt(option)) {
                    case 1:
                        addNewAccount();
                        break;
                    case 2:
                        deleteAccount();
                        break;
                    case 3:
                        deposit();
                        break;
                    case 4:
                        withdraw();
                        break;
                    case 5:
                        printInfo();
                        break;
                    case 6:
                        System.out.println("Дякуємо за використання банківської системи!");
                        return;
                    default:
                        System.out.println("Виберіть пункт від 1 до 6.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть номер пункту меню.");
            } catch (AccountException e) {
                System.err.println("Помилка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Непередбачена помилка: " + e.getMessage());
            }
        }
    }

    private void printMainMenu() throws IOException {
        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }

    private void addNewAccount() {

        System.out.println("\n****************");
        System.out.println("Creating new account:");
        System.out.println("****************");

        System.out.print(">>> Enter customer name: ");
        String name = scanner.next();

        System.out.print(">>> Enter user id: ");
        String userId = scanner.next();

        System.out.print(">>> Enter user password: ");
        String password = scanner.next();

        System.out.print(">>> Confirm password: ");
        String confirmPassword = scanner.next();

        // Проверка уникальности userId
        for (BankAccount account : accounts) {
            if (account.getUserId().equals(userId)) {
                throw new DuplicateUserIdException(
                        "Такий ідентифікатор користувача вже існує."
                );
            }
        }

        // Проверка совпадения паролей
        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException(
                    "Паролі не співпадають."
            );
        }

        // Создание нового счета
        CheckingAccount account = new CheckingAccount(
                name, userId, password
        );

        accounts.add(account);

        System.out.println("\nРахунок успішно створено!");
    }

    private void deleteAccount() {

        System.out.println("\n****************");
        System.out.println("Deleting account:");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        String userId = scanner.next();

        System.out.print(">>> Enter user password: ");
        String password = scanner.next();

        BankAccount account = null;

        // Поиск счета
        for (BankAccount current : accounts) {
            if (current.getUserId().equals(userId)) {
                account = current;
                break;
            }
        }

        // Проверка существования счета
        if (account == null) {
            throw new AccountNotFoundException(
                    "Рахунок з таким ідентифікатором не знайдено."
            );
        }

        // Проверка пароля
        if (!account.getPassword().equals(password)) {
            throw new InvalidPasswordException(
                    "Неправильний пароль."
            );
        }

        // Проверка баланса
        if (account.getBalance() != 0) {
            throw new AccountException(
                    "Неможливо видалити рахунок з ненульовим балансом."
            );
        }

        accounts.remove(account);

        System.out.println("\nРахунок успішно видалено!");
    }

    private void deposit() {

        System.out.println("\n****************");
        System.out.println("Making a deposit:");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        String userId = scanner.next();

        System.out.print(">>> Enter user password: ");
        String password = scanner.next();

        System.out.print(">>> Enter amount: ");

        double amount;

        try {
            amount = Double.parseDouble(scanner.next());
        } catch (NumberFormatException e) {
            throw new AccountException(
                    "Некоректна сума поповнення."
            );
        }

        // Поиск счета
        BankAccount account = null;

        for (BankAccount current : accounts) {
            if (current.getUserId().equals(userId)) {
                account = current;
                break;
            }
        }

        if (account == null) {
            throw new AccountNotFoundException(
                    "Рахунок не знайдено."
            );
        }

        // Проверка пароля
        if (!account.getPassword().equals(password)) {
            throw new InvalidPasswordException(
                    "Неправильний пароль."
            );
        }

        // Пополнение счета
        account.deposit(amount);

        System.out.println("\nРахунок успішно поповнено!");
        System.out.println("Новий баланс: " + account.getBalance());
    }

    private void withdraw() {
        System.out.println("\n****************");
        System.out.println("Making a withdrawal:");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        String userId = scanner.next();

        System.out.print(">>> Enter user password: ");
        String password = scanner.next();

        System.out.print(">>> Enter amount: ");
        double amount;

        try {
            amount = Double.parseDouble(scanner.next());
        } catch (NumberFormatException e) {
            throw new AccountException("Некоректна сума зняття.");
        }

        BankAccount account = null;

        for (BankAccount current : accounts) {
            if (current.getUserId().equals(userId)) {
                account = current;
                break;
            }
        }

        if (account == null) {
            throw new AccountNotFoundException("Рахунок не знайдено.");
        }

        if (!account.getPassword().equals(password)) {
            throw new InvalidPasswordException("Неправильний пароль.");
        }

        account.withdraw(amount);

        System.out.println("\nКошти успішно знято!");
        System.out.println("Новий баланс: " + account.getBalance());
    }

    private void printInfo() {
        System.out.println("\n****************");
        System.out.println("Printing account info:");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        String userId = scanner.next();

        System.out.print(">>> Enter user password: ");
        String password = scanner.next();

        BankAccount account = null;

        for (BankAccount current : accounts) {
            if (current.getUserId().equals(userId)) {
                account = current;
                break;
            }
        }

        if (account == null) {
            throw new AccountNotFoundException("Рахунок не знайдено.");
        }

        if (!account.getPassword().equals(password)) {
            throw new InvalidPasswordException("Неправильний пароль.");
        }

        CheckingAccount checkingAccount = (CheckingAccount) account;

        System.out.println("\nAccount info:");
        System.out.println(" - customer name: " + account.getCustomerName());
        System.out.println(" - account balance: " + account.getBalance());
        System.out.println(" - account type: Checking");
        System.out.println(" - account daily withdrawal limit: "
                + checkingAccount.getDailyWithdrawalLimit());

        System.out.print("\nPress Enter to return to main menu...");

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}