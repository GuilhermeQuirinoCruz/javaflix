package service;

import java.util.ArrayList;
import java.util.Map;
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
        this.generoDAO.Inserir(genero);
        this.database.Desconectar();
    }
    
    public void Atualizar(Genero genero) {
        this.database.Conectar();
        this.generoDAO.Atualizar(genero);
        this.database.Desconectar();
    }
    
    public void Excluir(int idGenero) {
        this.database.Conectar();
        this.generoDAO.Excluir(idGenero);
        this.database.Desconectar();
    }
    
    public ArrayList<Genero> Listar() {
        this.database.Conectar();
        ArrayList<Genero> generos = this.generoDAO.Listar();
        this.database.Desconectar();
        
        return generos;
    }
    
    public int QtdMidiasCadastradas(int idGenero) {
        this.database.Conectar();
        int qtdMidias = this.generoDAO.QtdMidiasCadastradas(idGenero);
        this.database.Desconectar();
        
        return qtdMidias;
    }
    
    public Map<String, Number> MidiasPorGenero() {
        this.database.Conectar();
        Map<String, Number> midias = this.generoDAO.MidiasPorGenero();
        this.database.Desconectar();
        
        return midias;
    }
    
    public Map<String, Number> FavoritosPorGenero() {
        this.database.Conectar();
        Map<String, Number> favoritos = this.generoDAO.FavoritosPorGenero();
        this.database.Desconectar();
        
        return favoritos;
    }
    
    
}
