

package model;


/**
 *
 * @author bryan
 */
public class Midia {
    
    private int id;
    private Genero genero;
    private String titulo;
    private String descricao;
    private String video;
    private String capa;
    private String trailer;

    public Midia(int id, Genero genero, String titulo, String descricao, String video, String capa,String trailer) {
        this.id = id;
        this.genero = genero;
        this.titulo = titulo;
        this.descricao = descricao;
        this.video = video;
        this.capa = capa;
        this.trailer = trailer;
    }
    
    public Midia() {
        
    }
    
    public Midia(Genero genero, String titulo, String descricao, String video, String capa, String trailer) {
        this.genero = genero;
        this.titulo = titulo;
        this.descricao = descricao;
        this.video = video;
        this.capa = capa;
        this.trailer = trailer;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
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

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
