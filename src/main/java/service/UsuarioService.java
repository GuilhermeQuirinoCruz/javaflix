package service;

import java.util.ArrayList;
import model.Midia;
import model.Usuario;
import model.dao.UsuarioDAO;
import model.database.Database;
import model.database.DatabaseFactory;

public class UsuarioService {
    private Database database;
    private UsuarioDAO usuarioDAO;
    
    public UsuarioService(){
        this.database = DatabaseFactory.getDatabase();
        this.usuarioDAO = this.database.getUsuarioDAO();
    }
    
    public void Inserir(Usuario usuario) {
        this.database.Conectar();
        this.usuarioDAO.Inserir(usuario);
        this.database.Desconectar();
    }
    
    public void Atualizar(Usuario usuario){
        this.database.Conectar();
        this.usuarioDAO.Atualizar(usuario);
        this.database.Desconectar();
    }
    
    public void Excluir(int idUsuario){
        this.database.Conectar();
        this.usuarioDAO.Excluir(idUsuario);
        this.database.Desconectar();
    }
    
    public ArrayList<Usuario> Listar() {
        this.database.Conectar();
        ArrayList<Usuario> usuarios = this.usuarioDAO.Listar();
        this.database.Desconectar();
        
        return usuarios;
    }
    
    public Usuario RetornaUsuarioByEmail(String email){
        this.database.Conectar();
        Usuario usuario = this.usuarioDAO.RetornaUsuarioByEmail(email);
        this.database.Desconectar();
        
        return usuario;
    }
    
    public Usuario RetornaUsuarioById(int id){
        this.database.Conectar();
        Usuario usuario = this.usuarioDAO.RetornaUsuarioById(id);
        this.database.Desconectar();
        
        return usuario;
    }
    
    public boolean FavoritaMidia(int idUsuario, int idMidia){
        this.database.Conectar();
        this.usuarioDAO.FavoritaMidia(idUsuario, idMidia);
        this.database.Desconectar();
        
        return true;
    }
    
    public boolean DesfavoritaMidia(int idUsuario, int idMidia){
        this.database.Conectar();
        this.usuarioDAO.DesfavoritaMidia(idUsuario, idMidia);
        this.database.Desconectar();
        
        return false;
    }
    
    public ArrayList<Midia> ListaMidiasFavoritadas(int idUsuario){
        this.database.Conectar();
        ArrayList<Midia> midias = this.usuarioDAO.ListaMidiasFavoritadas(idUsuario);
        this.database.Desconectar();
        
        return midias;
    }
    
    public boolean IsMidiaFavoritada(int idUsuario, int idMidia) {
        this.database.Conectar();
        boolean favoritada = this.usuarioDAO.IsMidiaFavoritada(idUsuario, idMidia);
        this.database.Desconectar();
        
        return favoritada;
    }
}
