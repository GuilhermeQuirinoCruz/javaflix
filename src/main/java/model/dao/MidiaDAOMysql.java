
package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Midia;
import model.database.DatabaseMysql;

/**
 *
 * @author bryan
 */
public class MidiaDAOMysql extends MidiaDAO {
    private Connection connection;
    private PreparedStatement comando;
    private DatabaseMysql dbMysql;
    
    public MidiaDAOMysql(DatabaseMysql dbMysql) {
        this.dbMysql = dbMysql;
    }

    @Override
    public boolean Inserir() {
        try{
            this.connection = dbMysql.getConnection();
            String sql = "INSERT INTO midia (titulo,descricao,video,capa,idGenero) VALUES (?,?,?,?,?);";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, this.getMidia().getTitulo());
            this.comando.setString(2, this.getMidia().getDescricao());
            this.comando.setString(3, this.getMidia().getVideo());
            this.comando.setString(4, this.getMidia().getCapa());
            this.comando.setInt(5, this.getMidia().getIdGenero());
            
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
            String sql = "UPDATE  midia SET titulo = ?, descricao = ?, video = ?, capa = ?, idGenero = ? WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.comando.setString(1, this.getMidia().getTitulo());
            this.comando.setString(2, this.getMidia().getDescricao());
            this.comando.setString(3, this.getMidia().getVideo());
            this.comando.setString(4, this.getMidia().getCapa());
            this.comando.setInt(5, this.getMidia().getIdGenero());
            this.comando.setInt(6, this.getMidia().getId());
            
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
            String sql = "DELETE FROM midia WHERE id = ?;";
            this.comando = this.connection.prepareStatement(sql);
            this.comando.setInt(1, this.getMidia().getId());

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
            String sql = "SELECT id,titulo,descricao,video,capa,idGenero FROM midia ORDER BY id;";
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
                midia.setIdGenero(rs.getInt("idGenero"));
                
                midias.add(midia);
            }

            return midias;
        } catch (SQLException e) {
            return null;
        }
        
    }
    
    
    
    
    
    
}
