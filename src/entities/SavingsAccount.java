package entities;

public class SavingsAccount extends Account {

    public SavingsAccount(int accountNumber, int agency, String password, double balance, Customer customer) {
        super(accountNumber, agency, password, balance, customer);
    }

    public SavingsAccount(Customer customer, String password) {
        super(customer, password);
    }

    @Override
    public void deposit(double value) {

    }

    @Override
    public void draw(double value) {

    }
}
