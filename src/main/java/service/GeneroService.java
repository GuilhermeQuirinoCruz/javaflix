package service;

import java.util.ArrayList;
import model.Genero;
import model.dao.GeneroDAO;
import model.database.Database;
import model.database.DatabaseFactory;

public class GeneroService {
    private Database database;
    private GeneroDAO generoDAO;

    public GeneroService() {
        this.database = DatabaseFactory.getDatabase();
        this.generoDAO = this.database.getGeneroDAO();
    }
    
    public void Inserir(Genero genero) {
        this.database.Conectar();
        this.generoDAO.setGenero(genero);
        this.generoDAO.Inserir();
        this.database.Desconectar();
    }
    
    public void Atualizar(Genero genero) {
        this.database.Conectar();
        this.generoDAO.setGenero(genero);
        this.generoDAO.Atualizar();
        this.database.Desconectar();
    }
    
    public void Excluir(Genero genero) {
        this.database.Conectar();
        this.generoDAO.setGenero(genero);
        this.generoDAO.Excluir();
        this.database.Desconectar();
    }
    
    public ArrayList<Genero> Listar() {
        this.database.Conectar();
        ArrayList<Genero> generos = this.generoDAO.Listar();
        this.database.Desconectar();
        
        return generos;
    }
}
