package words;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class words {
    public static Connection myConn = null;
    public static Statement myStmt = null;
    public static ResultSet myRs = null;
    public static wordTree wordTree;

    static {
        loadData("jdbc:mysql://localhost:3306/dictionary2", "student", "student");
    }
    public static void loadData(String connectionUrl, String user, String password) {
        int count = 0;
        wordTree = new wordTree();
        try {
            myConn = DriverManager.getConnection(connectionUrl, user, password);
            System.out.println("Database connection successful!\n");
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT DISTINCT word FROM english");
            while (myRs.next()) {
                if (count == 0) {
                    count++;
                    continue;
                }
                String s = myRs.getString("word");
                wordTree.insert(s.toLowerCase());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    public static ArrayList<String> getWordList() {
        return wordTree.getWordList();
    }
    public static List<String> autoSuggest(String word) {
        return wordTree.autoSuggest(word);
    }

    public static void insert(String word) {
        wordTree.insert(word);
    }
    public static void remove(String word) {
        wordTree.removeWord(word);
    }

    public static String getDefinition(String word) {
        String definition = "";
        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT meaning FROM english WHERE word = " + "'" + word + "'");
            while (myRs.next()) {
                definition = myRs.getString("meaning");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return definition;
    }
}
