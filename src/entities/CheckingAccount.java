package entities;

import entities.*;

public class CheckingAccount extends Account {

    public CheckingAccount(int accountNumber, int agency, String password, double balance, Customer customer) {
        super(accountNumber, agency, password, balance, customer);
    }

    public CheckingAccount(Customer customer, String password) {
        super(customer, password);
    }

    @Override
    public void deposit(double value) {
        if(value <= 0) throw new RuntimeException("Negative deposits are not possible");
        this.balance += value;
    }

    @Override
    public void draw(double value) {
        if(value > this.balance) throw new RuntimeException("You cant draw more than what you have in your balance");
        this.balance -= value;
    }
}
