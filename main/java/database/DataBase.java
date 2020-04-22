package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * The Class DataBase.
 */
public class DataBase {



    /**
     * Connection.
     *
     * @return the connection
     * @throws SQLException
     *             the SQL exception
     */
    public static Connection connection() throws SQLException {

        ClassLoader cl = DataBase.class.getClassLoader();
        InputStream input = cl.getResourceAsStream("source.txt");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.getMessage();
        }
        ArrayList<String> cred = new ArrayList<>();
        BufferedReader in = null;
        try {

            in = new BufferedReader(new InputStreamReader(input,"UTF-8"));

            for (int i = 0; i < 3; i++)
                cred.add(in.readLine());

        } catch (IOException e) {
            e.getMessage();
        }

        return DriverManager.getConnection(cred.get(0), cred.get(1), cred.get(2)) ;

    }
}
