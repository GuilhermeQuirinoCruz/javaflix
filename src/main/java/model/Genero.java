package model;

public class Genero {
    
    private int id;
    private String nome;

    public Genero() {
    }

    public Genero(int id) {
        this.id = id;
    }
    
    public Genero(String nome) {
        this.nome = nome;
    }

    public Genero(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
