package service;

import java.util.ArrayList;
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
        this.usuarioDAO.setUsuario(usuario);
        this.usuarioDAO.Inserir();
        this.database.Desconectar();
    }
    
    public void Atualizar(Usuario usuario){
        this.database.Conectar();
        this.usuarioDAO.setUsuario(usuario);
        this.usuarioDAO.Atualizar();
        this.database.Desconectar();
        
    }
    
    public void Excluir(Usuario usuario){
        this.database.Conectar();
        this.usuarioDAO.setUsuario(usuario);
        this.usuarioDAO.Excluir();
        this.database.Desconectar();
        
    }
    
    public ArrayList<Usuario> Listar() {
        this.database.Conectar();
        ArrayList<Usuario> usuarios = this.usuarioDAO.Listar();
        this.database.Desconectar();
        
        return usuarios;
    }
}
