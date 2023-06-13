package model.dao;

import java.util.ArrayList;
import model.Midia;

public abstract class MidiaDAO {
    
    public abstract boolean Inserir(Midia midia);
    public abstract boolean Atualizar(Midia midia);
    public abstract boolean Excluir(int idMidia);
    public abstract ArrayList<Midia> Listar();
    public abstract ArrayList<Midia> ListarResumido();
    public abstract ArrayList<Midia> ListarPorGenero(int idGenero);
    public abstract Midia getMidiaById(int id);
    public abstract int QtdFavoritos(int idMidia);
}
