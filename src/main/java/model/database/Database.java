package model.database;

import model.dao.GeneroDAO;
import model.dao.MidiaDAO;
import model.dao.UsuarioDAO;

public interface Database {
    
    public void Conectar();
    
    public void Desconectar();
    
    public GeneroDAO getGeneroDAO();
    public MidiaDAO getMidiaDAO();
    public UsuarioDAO getUsuarioDAO();
}
