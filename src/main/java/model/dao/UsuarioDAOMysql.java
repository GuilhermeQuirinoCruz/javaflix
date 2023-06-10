package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.database.DatabaseMysql;
import model.Usuario;
import java.sql.Date;
import model.Genero;
import model.Midia;

public class UsuarioDAOMysql extends UsuarioDAO {
    
    private Connection connection;
    private PreparedStatement comando;
    private DatabaseMysql dbMysql;
    
    public UsuarioDAOMysql(DatabaseMysql dbMysql) {
        this.dbMysql = dbMysql;
    }

    @Override
    public boolean Inserir(Usuario usuario) {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "INSERT INTO usuario (email,senha,nome,admin,dataExpiracao) VALUES (?,?,?,?,?);";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, usuario.getEmail());
            this.comando.setString(2, usuario.getSenha());
            this.comando.setString(3, usuario.getNome());
            this.comando.setBoolean(4, usuario.isAdmin());
            this.comando.setDate(5,Date.valueOf(usuario.getDataExpiracao()));
            
           if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        } catch(Exception e) {
             try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean Atualizar(Usuario usuario) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "UPDATE usuario SET email = ?,senha = ?,nome = ?, dataExpiracao = ?,admin = ? WHERE id = ?;";
            //
            //
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, usuario.getEmail());
            this.comando.setString(2, usuario.getSenha());
            this.comando.setString(3, usuario.getNome());
            this.comando.setDate(4,Date.valueOf(usuario.getDataExpiracao()));
            this.comando.setBoolean(5, usuario.isAdmin());
            this.comando.setInt(6, usuario.getId());
            
            if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        } catch(Exception e) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            return false;
        }
    }

    @Override
    public boolean Excluir(int idUsuario) {
         try {
            this.connection = dbMysql.getConnection();
            String sql = "DELETE FROM usuario WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql);
            this.comando.setInt(1, idUsuario);

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

    @Override
    public Usuario RetornaUsuarioByEmail(String email) {
        try{
            this.connection = dbMysql.getConnection();
           // String sql = "SELECT id,email,senha,nome,dataExpiracao,admin FROM usuario WHERE email = ?;";
            String sql = "SELECT id,email FROM usuario WHERE email = ?;";
            comando = connection.prepareStatement(sql);
            this.comando.setString(1,email);
            
            ResultSet rs = comando.executeQuery();
            rs.next();
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setEmail(rs.getString("email"));
            
            return usuario;
        } catch(SQLException e) {
            return null;
        }
    }

    @Override
    public Usuario RetornaUsuarioById(int id) {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "SELECT id,email,senha,nome,admin FROM usuario WHERE id = ?;";
          
            comando = connection.prepareStatement(sql);
            this.comando.setInt(1,id);
            
            ResultSet rs = comando.executeQuery();
            rs.next();
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setNome(rs.getString("nome"));
            usuario.setAdmin(rs.getBoolean("admin"));
                
            return usuario;
        } catch(SQLException e) {
            return null;
        }
    }

    @Override
    public boolean FavoritaMidia(int idUsuario, int idMidia) {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "INSERT INTO favorita (idUsuario,idMidia) VALUES (?,?);";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setInt(1, idUsuario);
            this.comando.setInt(2, idMidia);
            
           if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                
                return true;
            } else {
                this.connection.rollback();
                return false;
            }
        } catch(Exception e) {
             try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public ArrayList<Midia> ListaMidiasFavoritadas(int idUsuario) {
        try {
            this.connection = dbMysql.getConnection();
            String sql = "SELECT m.id,m.titulo,m.descricao,m.capa,m.trailer,m.video,g.id AS idGenero,g.nome AS nomeGenero FROM midia m JOIN favorita f ON m.id = f.idMidia JOIN genero g ON m.idGenero = g.id WHERE f.idUsuario = ?;";
            comando = connection.prepareStatement(sql);
            this.comando.setInt(1, idUsuario);
            ResultSet rs = comando.executeQuery();

            ArrayList<Midia> midias = new ArrayList<>();
            while (rs.next()) {
                Midia midia = new Midia();
                midia.setId(rs.getInt("id"));
                midia.setTitulo(rs.getString("titulo"));
                midia.setDescricao(rs.getString("descricao"));
                midia.setCapa(rs.getString("capa"));
                midia.setTrailer(rs.getString("trailer"));
                midia.setVideo(rs.getString("video"));
                midia.setGenero(new Genero(rs.getInt("idGenero"), rs.getString("nomeGenero")));
              
                midias.add(midia);
            }
            
            return midias;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean DesfavoritaMidia(int idUsuario, int idMidia) {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "DELETE FROM favorita WHERE idUsuario = ? AND idMidia = ?;";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setInt(1, idUsuario);
            this.comando.setInt(2, idMidia);
            
            if (this.comando.executeUpdate() > 0) {
                this.connection.commit();
                
                return true;
            } else {
                this.connection.rollback();
                
                return false;
            }
        } catch(Exception e) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
             
            return false;
        }
    }

    @Override
    public boolean IsMidiaFavoritada(int idUsuario, int idMidia) {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "SELECT * FROM favorita WHERE idUsuario = ? AND idMidia = ?;";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setInt(1, idUsuario);
            this.comando.setInt(2, idMidia);
            
            return this.comando.executeQuery().next();
        } catch(Exception e) {
            e.printStackTrace();
            
            return false;
        }
    }
}
