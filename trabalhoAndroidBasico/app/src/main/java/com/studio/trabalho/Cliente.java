package com.studio.trabalho;

public class Cliente {

    private int id;
    private String nome;
    private String telefone;
    private String email;
    private byte[] img;

    public Cliente(){}

    public Cliente(int id, String nome, String telefone, String email, byte[] img) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.img = img;
    }

    public Cliente(String nome, String telefone, String email, byte[] img) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
