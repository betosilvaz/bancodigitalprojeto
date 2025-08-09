package entities;

import utils.RandomNumber;

public abstract class Account {

    private int accountNumber;
    private int agency;
    private String password;
    private double balance;
    private Customer customer;

    /*
        user when you recover an account that already exists in database
     */
    public Account(int accountNumber,  int agency, String password, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.password = password;
        this.balance = balance;
        this.customer = customer;
    }

    /*
        Used when creating a new Account in database
     */
    public Account(Customer customer, String password) {
        this.accountNumber = 0;
        this.agency = RandomNumber.generate(1, 10);
        this.password = password;
        this.balance = 0;
        this.customer = customer;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int account_number) {
        this.accountNumber = account_number;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public abstract void deposit(double value);
    public abstract void draw(double value);

}
