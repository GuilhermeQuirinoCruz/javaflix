package model.dao;

import java.util.ArrayList;
import model.Usuario;
import model.Midia;

public abstract class UsuarioDAO {
    
    public abstract boolean Inserir(Usuario usuario);
    public abstract boolean Atualizar(Usuario usuario);
    public abstract boolean Excluir(int idUsuario);
    public abstract Usuario RetornaUsuarioByEmail(String email);
    public abstract Usuario RetornaUsuarioById(int id);
    public abstract boolean FavoritaMidia(int idUsuario, int idMidia);
    public abstract boolean DesfavoritaMidia(int idUsuario, int idMidia);
    public abstract ArrayList<Midia> ListaMidiasFavoritadas(int idUsuario);
    public abstract ArrayList<Usuario> Listar();
    public abstract boolean IsMidiaFavoritada(int idUsuario, int idMidia);
}
