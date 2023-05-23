package model.database;

import model.dao.GeneroDAO;
import model.dao.MidiaDAO;

public interface Database {
    
    public void Conectar();
    
    public void Desconectar();
    
    public GeneroDAO getGeneroDAO();
    public MidiaDAO getMidiaDAO();
}
