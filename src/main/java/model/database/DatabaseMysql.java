package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.dao.GeneroDAO;
import model.dao.GeneroDAOMysql;

public class DatabaseMysql implements Database {
    
    private static final String DATABASE = "db_javaflix";
    private static final String HOST = "localhost:3306";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://" + HOST + "/" + DATABASE;
    private static final String USR = "root";
    private static final String PWD = "";
    private Connection connection;
    
    @Override
    public void Conectar() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USR, PWD);
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @Override
    public void Desconectar() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public GeneroDAO getGeneroDAO() {
        return new GeneroDAOMysql(this);
    }
}
