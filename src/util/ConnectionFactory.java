package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Notrelb on 20.03.2016.
 */
public class ConnectionFactory {

    //Private instance
    private static ConnectionFactory instance = new ConnectionFactory();

    public static final String URL = "jdbc:h2:~/test";
    public static final String USER = "sa";
    public static final String PASSWORD = "";
    public static final String DRIVER_CLASS = "org.h2.Driver";

    //private Constructor
    private ConnectionFactory(){
        try{
            Class.forName(DRIVER_CLASS);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }




    //Instanz erzeugen
    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR: Es war nicht möglich sich mit der Datenbank zu Verbinden. Du brauchst vielecht H2 JAR");
        }

        return connection;
    }


    //Public Methode um die instanz zu bekommen da der Konstruktor private is, ist diese methode static
    public static Connection getConnection(){
        return instance.createConnection();
    }




}
