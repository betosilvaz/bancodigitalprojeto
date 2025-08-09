import entities.Account;
import services.AuthenticatorService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;
import static utils.TerminalCleaner.clean;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static boolean authenticated = false;
    public static Account account = null;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        createDatabaseIfNotExists();

        begin:
        while(true) {

            int option = askOption();
            switch (option) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> { break begin; }
            }

            if(!authenticated) continue; // happened some error in authentication

            int action = askAction();
            switch(action) {
                case 1 -> {
                    System.out.print("Value: ");
                    double value = sc.nextDouble();
                    sc.nextLine();

                    // TODO

                }
                case 2 -> {
                    System.out.println("Value: ");
                    double value = sc.nextDouble();
                    sc.nextLine();

                    // TODO

                }
                case 3 -> {
                    System.out.println("Insert the receivers data..");
                    System.out.print("Account number: ");
                    int account_number = sc.nextInt();
                    System.out.print("\nValue: ");
                    double value = sc.nextDouble();
                    sc.nextLine();

                    // TODO

                }
                case 4 -> {
                    System.out.println("===== BANK STATEMENT =====");

                    // TODO

                }
                case 5 -> {
                    continue begin;
                }
            }
            System.out.print("Type \"continue\" to go foward: ");
            sc.nextLine();
        }

        sc.close();
    }

    public static void login() {
        clean();
        System.out.println("===== LOGIN =====");
        System.out.println("1. Savings account");
        System.out.println("2. Checking account");
        System.out.print("Enter the type of account: ");
        int type = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your CPF: "); // CPF is like a social security number in Brazil
        String cpf = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        account = AuthenticatorService.authenticate(type, cpf, password);
        if(account != null) authenticated = true;

    }

    public static void register() {
        clean();
        System.out.println("===== REGISTER =====");
        System.out.println("Account types");
        System.out.println("1. Savings account");
        System.out.println("2. Checking account");
        System.out.print("Enter the type of your account: ");
        int type = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        account = AuthenticatorService.registerNewAccount(type, email, name, cpf, password);
        if(account != null) authenticated = true;

    }

    public static void createDatabaseIfNotExists() {
        String conn_string = "jdbc:sqlite:bank.db";

        try(Connection con = DriverManager.getConnection(conn_string)) {

            String create_customers_table = "CREATE TABLE IF NOT EXISTS customers (" +
                    "customer_id INTEGER PRIMARY KEY," +
                    "cpf TEXT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL" +
                    ");";

            String create_accounts_table = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "number INTEGER PRIMARY KEY," +
                    "agency INTEGER NOT NULL," +
                    "type TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "customer_id INTEGER NOT NULL," +
                    "FOREIGN KEY (customer_id) REFERENCES clientes (customer_id)" +
                    ");";

            String create_transactions_table = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "transaction_id INTEGER PRIMARY KEY," +
                    "type TEXT NOT NULL," +
                    "sender_id INTEGER," +
                    "receiver_id INTEGER," +
                    "value DECIMAL NOT NULL," +
                    "FOREIGN KEY (sender_id) REFERENCES accounts (number)," +
                    "FOREIGN KEY (receiver_id) REFERENCES accounts (number)" +
                    ");";


            Statement stmt = con.createStatement();

            stmt.executeUpdate(create_customers_table);
            stmt.executeUpdate(create_accounts_table);
            stmt.executeUpdate(create_transactions_table);


        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int askOption() {
        int option;
        do {
            clean();
            System.out.println("===== DIGITAL BANK =====");
            System.out.println("Menu: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Option: ");
            option = sc.nextInt();
            sc.nextLine();
        } while (option < 1 || option > 3);
        return option;
    }

    public static int askAction() {
        int action;
        do {
            clean();
            System.out.printf("===== WELCOME - %s =====\n", account.getCustomer().getName().toUpperCase());
            System.out.println("Menu: ");
            System.out.println("1. Draw");
            System.out.println("2. Deposit");
            System.out.println("3. Transfer");
            System.out.println("4. Bank statement");
            System.out.println("5. Exit");
            System.out.println("Action: ");
            action = sc.nextInt();
            sc.nextLine();
            System.out.println();
        } while(action < 1 || action > 5);
        return action;
    }

}