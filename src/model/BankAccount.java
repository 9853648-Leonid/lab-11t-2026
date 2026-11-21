
package model;

import exceptions.AccountException;
import exceptions.InsufficientBalanceException;

public class BankAccount {

    private String customerName;
    private String userId;
    private String password;
    private double balance;

    public BankAccount(String customerName, String userId, String password) {
        if (customerName == null || customerName.isEmpty()) {
            throw new AccountException(
                    "Ім'я користувача не може бути порожнім."
            );
        }

        if (userId == null || userId.isEmpty()) {
            throw new AccountException(
                    "Ідентифікатор користувача не може бути порожнім."
            );
        }

        if (password == null || password.length() < 8 ||
                !password.matches(".*\\d.*")) {
            throw new AccountException(
                    "Пароль повинен містити щонайменше 8 символів та одну цифру."
            );
        }

        this.customerName = customerName;
        this.userId = userId;
        this.password = password;
        this.balance = 0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new AccountException(
                    "Баланс не може бути від'ємним."
            );
        }

        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new AccountException(
                    "Сума поповнення повинна бути більшою за 0."
            );
        }

        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new AccountException(
                    "Сума зняття повинна бути більшою за 0."
            );
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Недостатньо коштів на рахунку."
            );
        }

        balance -= amount;
    }
}
