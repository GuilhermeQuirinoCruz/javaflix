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
import model.Midia;
import model.database.DatabaseMysql;

public class MidiaDAOMysql extends MidiaDAO {
    private Connection connection;
    private PreparedStatement comando;
    private DatabaseMysql dbMysql;
    
    public MidiaDAOMysql(DatabaseMysql dbMysql) {
        this.dbMysql = dbMysql;
    }

    @Override
    public boolean Inserir(Midia midia) {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "INSERT INTO midia (titulo,descricao,video,capa,idGenero,trailer) VALUES (?,?,?,?,?,?);";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, midia.getTitulo());
            this.comando.setString(2, midia.getDescricao());
            this.comando.setString(3, midia.getVideo());
            this.comando.setString(4, midia.getCapa());
            this.comando.setInt(5, midia.getGenero().getId());
            this.comando.setString(6, midia.getTrailer());
           if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        }
        catch(Exception e){
             try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean Atualizar(Midia midia) {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "UPDATE midia SET titulo = ?, descricao = ?, video = ?, capa = ?, idGenero = ?, trailer = ? WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, midia.getTitulo());
            this.comando.setString(2, midia.getDescricao());
            this.comando.setString(3, midia.getVideo());
            this.comando.setString(4, midia.getCapa());
            this.comando.setInt(5, midia.getGenero().getId());
            this.comando.setString(6, midia.getTrailer());
            this.comando.setInt(7, midia.getId());
            
            if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        }
        catch(Exception e){
             try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean Excluir(int idMidia) {
         try {
            this.connection = dbMysql.getConnection();
            String sql = "DELETE FROM midia WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql);
            this.comando.setInt(1, idMidia);

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
    public ArrayList<Midia> Listar() {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT m.id,m.titulo,m.descricao,m.video,m.capa,g.id idGenero,g.nome nomeGenero,m.trailer FROM midia m JOIN genero g ON m.idGenero = g.id ORDER BY m.id;";
            comando = connection.prepareStatement(sql);
            
            ResultSet rs = comando.executeQuery();

            ArrayList<Midia> midias = new ArrayList<>();
            while (rs.next()) {
                Midia midia = new Midia();
                midia.setId(rs.getInt("id"));
                midia.setTitulo(rs.getString("titulo"));
                midia.setDescricao(rs.getString("descricao"));
                midia.setVideo(rs.getString("video"));
                midia.setCapa(rs.getString("capa"));
                midia.setGenero(new Genero(rs.getInt("idGenero"), rs.getString("nomeGenero")));
                midia.setTrailer(rs.getString("trailer"));
                
                midias.add(midia);
            }

            return midias;
        } catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public ArrayList<Midia> ListarResumido() {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT m.id,m.titulo,m.descricao,g.nome AS nomeGenero FROM midia m JOIN genero g ON m.idGenero = g.id ORDER BY m.id;";
            comando = connection.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();

            ArrayList<Midia> midias = new ArrayList<>();
            while (rs.next()) {
                Midia midia = new Midia();
                midia.setId(rs.getInt("id"));
                midia.setTitulo(rs.getString("titulo")); 
                midia.setGenero(new Genero(rs.getString("nomeGenero")));
                
                midias.add(midia);
            }
            
            return midias;
        } catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public Midia getMidiaById(int id){
       try{
           this.connection = dbMysql.getConnection();
           String sql = "SELECT m.id, m.titulo,m.descricao,m.video,m.capa,m.trailer,g.id AS idGenero, g.nome AS nomeGenero FROM midia m JOIN genero g ON m.idGenero = g.id WHERE m.id = ?;";
           comando = connection.prepareStatement(sql);
           this.comando.setInt(1, id);
           ResultSet rs = comando.executeQuery();
           rs.next();
           Midia midia = new Midia();
           midia.setId(rs.getInt("id"));
           midia.setTitulo(rs.getString( "titulo"));
           midia.setDescricao(rs.getString("descricao"));
           midia.setVideo(rs.getString("video"));
           midia.setCapa(rs.getString("capa"));
           midia.setTrailer(rs.getString("trailer"));
           midia.setGenero(new Genero(rs.getInt("idGenero"), rs.getString("nomeGenero")));
           
           return midia;
       } catch(SQLException e) {
           return null;
       }
    }

    @Override
    public ArrayList<Midia> ListarPorGenero(int idGenero) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT m.id,m.descricao,m.titulo,m.capa,m.trailer,m.video FROM midia m JOIN genero g ON m.idGenero = g.id WHERE g.id = ? ORDER BY m.id;";
            comando = connection.prepareStatement(sql);
            comando.setInt(1, idGenero);
            ResultSet rs = comando.executeQuery();

            ArrayList<Midia> midias = new ArrayList<>();
            while (rs.next()) {
                Midia midia = new Midia();
                midia.setId(rs.getInt("id"));
                midia.setTitulo(rs.getString("titulo"));
                midia.setDescricao(rs.getString("descricao"));
                midia.setCapa(rs.getString("capa"));
                midia.setVideo(rs.getString("video"));
                midia.setTrailer(rs.getString("trailer"));
                
                midias.add(midia);
            }
            
            return midias;
        } catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public int QtdFavoritos(int idMidia) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT COUNT(*) qtd FROM favorita WHERE idMidia = ?;";
            comando = connection.prepareStatement(sql);
            comando.setInt(1, idMidia);
            ResultSet rs = comando.executeQuery();

            int qtdFavoritos = 0;
            if (rs.next()) {
                qtdFavoritos = rs.getInt("qtd");
            }
            
            return qtdFavoritos;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public Map<String, Number> FavoritosPorMidia() {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT m.titulo, COUNT(*) qtd FROM midia m JOIN favorita f ON m.id = f.idMidia GROUP BY m.id;";
            comando = connection.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();

            Map<String, Number> favoritos = new HashMap<>();
            while (rs.next()) {
                favoritos.put(rs.getString("titulo"), rs.getInt("qtd"));
            }
            
            return favoritos;
        } catch (SQLException e) {
            return null;
        }
    }
}
