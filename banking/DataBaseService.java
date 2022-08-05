package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DataBaseService {

    private static DataBaseService dbs;
    private SQLiteDataSource dataSource;

    private DataBaseService() {}

    public static DataBaseService getInstance() {
        if (dbs == null) {
            dbs = new DataBaseService();
        }
        return dbs;
    }

    public void setDataBase(String url) {
        dataSource = new SQLiteDataSource();
        String URL = String.format("jdbc:sqlite:%s", url);
        dataSource.setUrl(URL);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String query = "CREATE TABLE IF NOT EXISTS card(" +
                "id INTEGER PRIMARY KEY," +
                "number TEXT UNIQUE," +
                "pin TEXT," +
                "balance INTEGER DEFAULT 0)";

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAccount(Account account) {
        String query = "INSERT INTO card VALUES (?, ?, ?, ?)";
        int id = Integer.parseInt(account.getIdNumber());
        String number = account.getCreditCard().getNumber();
        String PIN = account.getCreditCard().getPIN();
        int balance = account.getBalance();

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.setString(2, number);
                statement.setString(3, PIN);
                statement.setInt(4, balance);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAccount(Account account) {
        String query = "DELETE FROM card WHERE id = ?";
        int idNumber = Integer.parseInt(account.getIdNumber());

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, idNumber);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean containsID(int idToCheck) {
        String query = "SELECT * FROM card WHERE id = ?";

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, idToCheck);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account getAccount(int idNumber) {
        String query = "SELECT * FROM card WHERE id = ?";

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, idNumber);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    // Retrieve column values
                    String id = String.format("%09d", result.getInt("id"));
                    String number = result.getString("number");
                    String pin = String.format("%04d", result.getInt("pin"));
                    int balance = result.getInt("balance");
                    CreditCard creditCard = new CreditCard(number, pin);
                    return new Account(id, balance, creditCard);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBalance(Account account) {
        String query = "UPDATE card SET balance = ? WHERE id = ?";
        int balance = account.getBalance();
        int idNumber = Integer.parseInt(account.getIdNumber());

        try (Connection con = dataSource.getConnection()) {
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, balance);
                statement.setInt(2, idNumber);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
