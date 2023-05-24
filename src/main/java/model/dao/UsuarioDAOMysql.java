package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Usuario;
import model.database.DatabaseMysql;

public class UsuarioDAOMysql extends UsuarioDAO {
    
    private Connection connection;
    private PreparedStatement comando;
    private DatabaseMysql dbMysql;
    
    public UsuarioDAOMysql(DatabaseMysql dbMysql) {
        this.dbMysql = dbMysql;
    }

    @Override
    public boolean Inserir() {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "INSERT INTO usuario (email,senha,nome,dataExpiracao,admin) VALUES (?,?,?,?,?);";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, this.getUsuario().getEmail());
            this.comando.setString(2, this.getUsuario().getSenha());
            this.comando.setString(3, this.getUsuario().getNome());
            //this.comando.setString(4, this.getUsuario().getDataExpiracao()); Converter Calendar para Date
            this.comando.setBoolean(5, this.getUsuario().isAdmin());
            
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
    public boolean Atualizar() {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "UPDATE usuario SET email = ?,senha = ?,nome = ?,dataExpiracao = ?,admin = ? WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, this.getUsuario().getEmail());
            this.comando.setString(2, this.getUsuario().getSenha());
            this.comando.setString(3, this.getUsuario().getNome());
            //this.comando.setString(4, this.getUsuario().getDataExpiracao()); Converter Calendar para Date
            this.comando.setBoolean(5, this.getUsuario().isAdmin());
            this.comando.setInt(6, this.getUsuario().getId());
            
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
    public boolean Excluir() {
         try {
            this.connection = dbMysql.getConnection();
            String sql = "DELETE FROM usuario WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql);
            this.comando.setInt(1, this.getUsuario().getId());

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
    public ArrayList<Usuario> Listar() {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT id,email,senha,nome,dataExpiracao,admin FROM usuario ORDER BY id;";
            comando = connection.prepareStatement(sql);
            ResultSet rs = comando.executeQuery();

            ArrayList<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setNome(rs.getString("nome"));
                //usuario.setDataExpiracao(rs.getString("dataExpiracao")); TODO: Converter Date para Calendar
                usuario.setAdmin(rs.getBoolean("admin"));
                
                usuarios.add(usuario);
            }

            return usuarios;
        } catch (SQLException e) {
            return null;
        }
        
    }
    
}