package model.dao;

import java.util.ArrayList;
import model.Midia;

public abstract class MidiaDAO {
    
    private Midia midia;

    public Midia getMidia() {
        return this.midia;
    }

    public void setMidia(Midia midia) {
        this.midia = midia;
    }
    
    public abstract boolean Inserir();
    public abstract boolean Atualizar();
    public abstract boolean Excluir();
    public abstract ArrayList<Midia> Listar();
    public abstract ArrayList<Midia> ListarResumido();
    public abstract ArrayList<Midia> ListarPorGenero(int idGenero);
    public abstract Midia getMidiaById(int id);
}
