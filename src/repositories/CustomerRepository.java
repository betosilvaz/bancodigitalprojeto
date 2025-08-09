package repositories;

import entities.Customer;

import java.sql.*;

public class CustomerRepository {

    private static final String connectionString = "jdbc:sqlite:bank.db";

    public static void create(String email, String name, String cpf) {
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO customers (cpf, name, email) VALUES (?, ?, ?)");
            stmt.setString(1, cpf);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean exists(String cpf) {
        boolean exist = false;
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("SELECT 1 FROM customers WHERE cpf = ?");
            stmt.setString(1, cpf);
            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                exist = true;
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    public static Customer search(String cpf) {
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM customers WHERE cpf = ?;");
            stmt.setString(1, cpf);
            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return new Customer(result.getInt("customer_id"), result.getString("name"), result.getString("email"), result.getString("cpf"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
