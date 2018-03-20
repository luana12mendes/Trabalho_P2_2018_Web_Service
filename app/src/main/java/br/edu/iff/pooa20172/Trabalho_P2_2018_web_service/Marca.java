package br.edu.iff.pooa20172.Trabalho_P2_2018_web_service;


public class Marca {
    private String nome;
    private String fipe_name;
    private int order;
    private String key;
    private int id;

    public Marca(String nome, String fipe_name, int order, String key, int id){
        this.nome = nome;
        this.fipe_name = fipe_name;
        this.order = order;
        this.key = key;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFipe_name() {
        return fipe_name;
    }

    public void setFipe_name(String fipe_name) {
        this.fipe_name = fipe_name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
