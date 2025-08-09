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
        if(value <= 0) throw new RuntimeException("Negative deposits are not possible");
        this.balance += value;
    }

    @Override
    public void draw(double value) {
        if(value > this.balance) throw new RuntimeException("You cant draw more than what you have in your balance");
        this.balance -= value;
    }
}
