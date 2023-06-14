package model.dao;

import java.util.ArrayList;
import java.util.Map;
import model.Genero;

public abstract class GeneroDAO {
    
    public abstract boolean Inserir(Genero genero);
    public abstract boolean Atualizar(Genero genero);
    public abstract boolean Excluir(int idGenero);
    public abstract ArrayList<Genero> Listar();
    public abstract int QtdMidiasCadastradas(int idGenero);
    public abstract Map<String, Number> MidiasPorGenero();
    public abstract Map<String, Number> FavoritosPorGenero();
}
