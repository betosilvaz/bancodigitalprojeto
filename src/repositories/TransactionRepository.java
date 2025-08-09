package repositories;

import java.sql.*;
import java.util.ArrayList;

public class TransactionRepository {

    private static final String connectionString = "jdbc:sqlite:bank.db";

    public static void registerDeposit(int receiver_number, double value) {
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO transactions (type, receiver_id, value) VALUES (?, ?, ?);");
            stmt.setString(1, "deposit");
            stmt.setInt(2, receiver_number);
            stmt.setDouble(3, value);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void registerDraw(int sender_number, double value) {
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO transactions (type, sender_id, value) VALUES (?, ?, ?);");
            stmt.setString(1, "draw");
            stmt.setInt(2, sender_number);
            stmt.setDouble(3, value);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void registerTransfer(int sender_number, int receiver_number, double value) {
        try(Connection con = DriverManager.getConnection(connectionString)) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO transactions (type, sender_id, receiver_id, value) VALUES (?, ?, ?, ?);");
            stmt.setString(1, "transfer");
            stmt.setInt(2, sender_number);
            stmt.setInt(3, receiver_number);
            stmt.setDouble(4, value);
            stmt.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<String[]> getBankStatement(int account_number) {
        ResultSet result;
        ArrayList<String[]> array_to_return = new ArrayList<>();

        try(Connection con = DriverManager.getConnection(connectionString)) {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM transactions WHERE sender_id = ? OR receiver_id = ?;");
            stmt.setInt(1, account_number);
            stmt.setInt(2, account_number);
            result = stmt.executeQuery();

            while(result.next()) {
                array_to_return.add(new String[] {
                        String.valueOf(result.getInt("transaction_id")),
                        result.getString("type"),
                        String.valueOf(result.getInt("sender_id") == 0 ? "N/A" : result.getInt("sender_id")),
                        String.valueOf(result.getInt("receiver_id") == 0 ? "N/A" : result.getInt("receiver_id")),
                        String.valueOf(result.getDouble("value"))
                });
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return array_to_return;
    }

}
