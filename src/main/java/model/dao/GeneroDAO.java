package model.dao;

import java.util.ArrayList;
import model.Genero;

public abstract class GeneroDAO {
    
    private Genero genero;

    public Genero getGenero() {
        return this.genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    public abstract boolean Inserir();
    public abstract boolean Atualizar();
    public abstract boolean Excluir();
    public abstract ArrayList<Genero> Listar();
}
