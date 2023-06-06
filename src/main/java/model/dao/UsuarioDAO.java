package model.dao;

import java.util.ArrayList;
import model.Usuario;
import model.Midia;

public abstract class UsuarioDAO {
    
    private Usuario usuario;

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public abstract boolean Inserir();
    public abstract boolean Atualizar();
    public abstract boolean Excluir();
    public abstract Usuario RetornaUsuarioByEmail(String email);
    public abstract Usuario RetornaUsuarioById(int id);
    public abstract boolean FavoritaMidia(int idUsuario, int idMidia);
    public abstract boolean DesfavoritaMidia(int idUsuario, int idMidia);
    public abstract ArrayList<Midia> ListaMidiaFavoritada(int idUsuario);
    
    public abstract ArrayList<Usuario> Listar();
    
    
}
