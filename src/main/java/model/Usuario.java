package model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private String nome;
    private LocalDate dataExpiracao;
    private boolean admin;
    
    public Usuario(){
    }

    public Usuario(int id, String email, String senha, String nome, LocalDate dataExpiracao, boolean admin){
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.dataExpiracao = dataExpiracao;
        this.admin = admin;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
    
    public Usuario(String email) {
        this.email = email;
    }
     
    public Usuario(String email, int id) {
        this.email = email;
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getSenha(){
        return this.senha;
    }
    
    public void setSenha(String senha){
        this.senha = senha;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public LocalDate getDataExpiracao(){
        return this.dataExpiracao;
    }
    
    public void setDataExpiracao(LocalDate dataExpiracao){
        this.dataExpiracao = dataExpiracao;
    }
    
    public boolean isAdmin(){
        return this.admin;
    }
    
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
}
