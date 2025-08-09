package repositories;

import entities.*;

import java.sql.*;

public class AccountRepository {

    private static final String connectionString = "jdbc:sqlite:bank.db";

    public static void create(Account account) {
        String type = (account instanceof SavingsAccount) ? "savings" : "checking";
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO accounts (agency, type, password, customer_id) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, account.getAgency());
            stmt.setString(2, type);
            stmt.setString(3, account.getPassword());
            stmt.setInt(4, account.getCustomer().getId());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Account search(int account_number) {
        Account account = null;
        int accountNumber = 0, agency = 0;
        String password = null, type = null;
        double balance = 0;
        Customer customer = null;
        ResultSet positiveBalance = null, negativeBalance = null;
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM accounts a JOIN customers c USING (customer_id) WHERE number = ?;");
            stmt.setInt(1, account_number);
            ResultSet result = stmt.executeQuery();

            if(result.next()) {
                type = result.getString("type");
                accountNumber = result.getInt("number");
                agency = result.getInt("agency");
                password = result.getString("password");
                customer = new Customer(result.getInt("customer_id"), result.getString("name"), result.getString("email"), result.getString("cpf"));
            }

            stmt = con.prepareStatement("SELECT SUM(value) as \"positive\" FROM transactions WHERE receiver_id = ?;");
            stmt.setInt(1, account_number);
            positiveBalance = stmt.executeQuery();
            if(positiveBalance.next()) {
                balance += positiveBalance.getDouble("positive");
            }

            stmt = con.prepareStatement("SELECT SUM(value) as \"negative\" FROM transactions WHERE sender_id = ?;");
            stmt.setInt(1, account_number);
            negativeBalance = stmt.executeQuery();
            if(negativeBalance.next()) {
                balance -= negativeBalance.getDouble("negative");
            }

            if(type == null)
                return null;

            if(type.equals("savings")) {
                account = new SavingsAccount(accountNumber, agency, password, balance, customer);
            } else if(type.equals("checking")) {
                account = new CheckingAccount(accountNumber, agency, password, balance, customer);
            } else {
                throw new SQLException("invalid account type");
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return account;
    }

    public static Account search(int _type, String cpf) {
        Account account = null;
        int accountNumber = 0, agency = 0;
        String password = null;
        double balance = 0;
        Customer customer = null;

        String type = (_type == 1) ? "savings" : "checking";

        ResultSet positiveBalance = null, negativeBalance = null;
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM accounts a JOIN customers c USING (customer_id) WHERE c.cpf = ? AND type = ?;");
            stmt.setString(1, cpf);
            stmt.setString(2, type);
            ResultSet result = stmt.executeQuery();

            if(result.next()) {
                accountNumber = result.getInt("number");
                agency = result.getInt("agency");
                password = result.getString("password");
                customer = new Customer(result.getInt("customer_id"), result.getString("name"), result.getString("email"), result.getString("cpf"));
            } else {
                return null;
            }

            stmt = con.prepareStatement("SELECT SUM(value) AS \"positive\" FROM transactions WHERE receiver_id = (SELECT number FROM accounts JOIN customers USING (customer_id) WHERE customers.cpf = ?) LIMIT 1;");
            stmt.setString(1, cpf);
            positiveBalance = stmt.executeQuery();
            if(positiveBalance.next()) {
                balance += positiveBalance.getDouble("positive");
            }

            stmt = con.prepareStatement("SELECT SUM(value) AS \"negative\" FROM transactions WHERE sender_id = (SELECT number FROM accounts JOIN customers USING (customer_id) WHERE customers.cpf = ?) LIMIT 1;");
            stmt.setString(1, cpf);
            negativeBalance = stmt.executeQuery();
            if(negativeBalance.next()) {
                balance -= negativeBalance.getDouble("negative");
            }

            if(type.equals("savings")) {
                account = new SavingsAccount(accountNumber, agency, password, balance, customer);
            } else if(type.equals("checking")) {
                account = new CheckingAccount(accountNumber, agency, password, balance, customer);
            } else {
                throw new SQLException("invalid account type");
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return account;
    }

    public static boolean exists(int type, String cpf) {
        boolean exist = false;
        try(Connection con = DriverManager.getConnection(connectionString)) {
            String _type = (type == AccountType.SAVINGS_ACCOUNT.getType()) ? "savings" : "checking";

            PreparedStatement stmt = con.prepareStatement("SELECT 1 FROM accounts a JOIN customers c USING (customer_id) WHERE a.type = ? AND c.cpf = ?;");
            stmt.setString(1, _type);
            stmt.setString(2, cpf);
            ResultSet result = stmt.executeQuery();

            if(result.next()) {
                exist = true;
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    public static void save(Account account) {

    }

}
