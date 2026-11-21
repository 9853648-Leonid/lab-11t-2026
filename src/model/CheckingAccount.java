
package model;

import exceptions.AccountException;
import exceptions.WithdrawalLimitException;

public class CheckingAccount extends BankAccount {

    private double dailyWithdrawalLimit = 300;

    public CheckingAccount(String customerName, String userId, String password) {
        super(customerName, userId, password);
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
        if (dailyWithdrawalLimit <= 0) {
            throw new AccountException(
                    "Денний ліміт зняття повинен бути більшим за 0."
            );
        }

        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > dailyWithdrawalLimit) {
            throw new WithdrawalLimitException(
                    "Сума зняття перевищує денний ліміт."
            );
        }

        super.withdraw(amount);
    }
}
