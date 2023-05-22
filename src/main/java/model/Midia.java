

package model;

/**
 *
 * @author bryan
 */
public class Midia {
    
    private int id;
    private int idGenero;
    private String titulo;
    private String descricao;
    private String video;
    private String capa;

    public Midia(int id, int idGenero, String titulo, String descricao, String video, String capa) {
        this.id = id;
        this.idGenero = idGenero;
        this.titulo = titulo;
        this.descricao = descricao;
        this.video = video;
        this.capa = capa;
    }
    
    public Midia() {
        
    }
    
    public Midia(int idGenero, String titulo, String descricao, String video, String capa) {
        this.idGenero = idGenero;
        this.titulo = titulo;
        this.descricao = descricao;
        this.video = video;
        this.capa = capa;
    }
    
    public Midia(int id) {
        this.id = id;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }
    
    
    
    
    
    
    
    
    
    
    
}
