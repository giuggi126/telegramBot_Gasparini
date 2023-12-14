import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DB {

    private Connection conn;


    public DB() {
        connetti();
    }

    private Connection connetti() {
        String db = "jdbc:mysql://localhost:3306/videogiochi";
        String username = "root";
        String password = "";

        try {
            conn = DriverManager.getConnection(db, username, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<String> raccogliDati(String chatID) {
        List<String> listaGiochi = new ArrayList<>();
        String query = "SELECT Nome FROM giochi WHERE ChatId = ?";
        try {
            if(!conn.isValid(5))
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, chatID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nomeGioco = resultSet.getString("Nome");
                    listaGiochi.add(" " + nomeGioco);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (listaGiochi.isEmpty()) {
            listaGiochi.add("Nessun gioco nel carrello");
        }

        return listaGiochi;
    }


    public String aggiungiGioco(String ChatID, String Nome) {
        try {
            if (!conn.isValid(5)) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String query = "INSERT INTO giochi(ChatId, Nome) VALUES(?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, ChatID);
            statement.setString(2, Nome);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Inserimento non avvenuto";
        }

        return "Inserimento avvenuto con successo";
    }

    public String EliminaGioco(String ChatID, String Nome) {
        try {
            if (!conn.isValid(5)) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String query = "DELETE FROM giochi WHERE ChatId LIKE ? AND Nome LIKE ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, ChatID);
            statement.setString(2, Nome);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "il gioco non è stato rimosso";
        }
        return "il gioco è stato rimosso con successo";
    }
}