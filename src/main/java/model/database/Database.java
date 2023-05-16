package model.database;

import model.dao.GeneroDAO;

public interface Database {
    
    public void Conectar();
    
    public void Desconectar();
    
    public GeneroDAO getGeneroDAO();
}
