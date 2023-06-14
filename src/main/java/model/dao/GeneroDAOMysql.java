package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Genero;
import model.database.DatabaseMysql;

public class GeneroDAOMysql extends GeneroDAO {
    
    private Connection connection;
    private PreparedStatement comando;
    private DatabaseMysql dbMysql;
    
    public GeneroDAOMysql(DatabaseMysql dbMysql) {
        this.dbMysql = dbMysql;
    }
    
    @Override
    public boolean Inserir(Genero genero) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "INSERT INTO genero (nome) VALUES (?);";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, genero.getNome());

            if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        } catch (Exception e) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean Atualizar(Genero genero) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "UPDATE genero SET nome = ? WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql);
            this.comando.setString(1, genero.getNome());
            this.comando.setInt(2, genero.getId());

            if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        } catch (Exception e) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean Excluir(int idGenero) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "DELETE FROM genero WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql);
            this.comando.setInt(1, idGenero);

            if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        } catch (Exception e) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public ArrayList<Genero> Listar() {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT id, nome FROM genero ORDER BY id;";
            comando = connection.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();

            ArrayList<Genero> generos = new ArrayList<>();
            while (rs.next()) {
                Genero genero = new Genero();
                genero.setId(rs.getInt("id"));
                genero.setNome(rs.getString("nome"));

                generos.add(genero);
            }

            return generos;
        } catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public int QtdMidiasCadastradas(int idGenero) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT COUNT(*) qtd FROM midia WHERE idGenero = ?;";
            comando = connection.prepareStatement(sql);
            this.comando.setInt(1, idGenero);
            ResultSet rs = comando.executeQuery();
            
            
            int qtdMidias = 0;
            if (rs.next()) {
                qtdMidias = rs.getInt("qtd");
            }

            return qtdMidias;
        } catch (SQLException e) {
            return 0;
        }
    }
    
    @Override
    public Map<String, Number> MidiasPorGenero() {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT g.nome, COUNT(*) qtd FROM genero g JOIN midia m ON g.id = m.idGenero GROUP BY g.id;";
            comando = connection.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();

            Map<String, Number> midias = new HashMap<>();
            while (rs.next()) {
                midias.put(rs.getString("nome"), rs.getInt("qtd"));
            }
            
            return midias;
        } catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public Map<String, Number> FavoritosPorGenero() {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT g.nome, COUNT(*) qtd FROM genero g JOIN midia m ON g.id = m.idGenero JOIN favorita f ON m.id = f.idMidia GROUP BY g.id;";
            comando = connection.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();

            Map<String, Number> favoritos = new HashMap<>();
            while (rs.next()) {
                favoritos.put(rs.getString("nome"), rs.getInt("qtd"));
            }
            
            return favoritos;
        } catch (SQLException e) {
            return null;
        }
    }
}
