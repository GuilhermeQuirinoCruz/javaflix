package service;

import java.util.ArrayList;
import java.util.Map;
import model.Midia;
import model.dao.MidiaDAO;
import model.database.Database;
import model.database.DatabaseFactory;

public class MidiaService {
    private Database database;
    private MidiaDAO midiaDAO;
    
    public MidiaService(){
        this.database = DatabaseFactory.getDatabase();
        this.midiaDAO = this.database.getMidiaDAO();
    }
    
    public void Inserir(Midia midia) {
        this.database.Conectar();
        this.midiaDAO.Inserir(midia);
        this.database.Desconectar();
    }
    
    public void Atualizar(Midia midia){
        this.database.Conectar();
        this.midiaDAO.Atualizar(midia);
        this.database.Desconectar();
    }
    
    public void Excluir(int idMidia){
        this.database.Conectar();
        this.midiaDAO.Excluir(idMidia);
        this.database.Desconectar();
    }
    
    public ArrayList<Midia> Listar() {
        this.database.Conectar();
        ArrayList<Midia> midias = this.midiaDAO.Listar();
        this.database.Desconectar();
        
        return midias;
    }
    
    public ArrayList<Midia> ListarResumido() {
        this.database.Conectar();
        ArrayList<Midia> midias = this.midiaDAO.ListarResumido();
        this.database.Desconectar();
        
        return midias;
    }
    
    public Midia GetMidiaById(int id){
        this.database.Conectar();
        Midia midia = this.midiaDAO.getMidiaById(id);
        this.database.Desconectar();
        
        return midia;
    }
    
    public ArrayList<Midia> ListarPorGenero(int idGenero) {
        this.database.Conectar();
        ArrayList<Midia> midias = this.midiaDAO.ListarPorGenero(idGenero);
        this.database.Desconectar();
        
        return midias;
    }
    
    public int QtdFavoritos(int idMidia) {
        this.database.Conectar();
        int qtdFavoritos = this.midiaDAO.QtdFavoritos(idMidia);
        this.database.Desconectar();
        
        return qtdFavoritos;
    }
    
    public Map<String, Number> FavoritosPorMidia() {
        this.database.Conectar();
        Map<String, Number> favoritos = this.midiaDAO.FavoritosPorMidia();
        this.database.Desconectar();
        
        return favoritos;
    }
}
