package model.dao;

import java.util.ArrayList;
import model.Usuario;

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
    public abstract ArrayList<Usuario> Listar();
    
}
