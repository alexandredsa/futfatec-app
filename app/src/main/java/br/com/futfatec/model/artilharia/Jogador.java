package br.com.futfatec.model.artilharia;


public class Jogador implements Comparable<Jogador> {
    private String id;
    private String nome;
    private int cartaoAmarelo;
    private int cartaoVermelho;
    private int gols;
    private String time;

    private String idTabela;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Jogador(String nome) {
        this.nome = nome;
    }

    public Jogador() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCartaoAmarelo() {
        return cartaoAmarelo;
    }

    public void setCartaoAmarelo(int cartaoAmarelo) {
        this.cartaoAmarelo = cartaoAmarelo;
    }

    public int getCartaoVermelho() {
        return cartaoVermelho;
    }

    public void setCartaoVermelho(int cartaoVermelho) {
        this.cartaoVermelho = cartaoVermelho;
    }

    public int getGols() {
        return gols;
    }

    public void setGols(int gols) {
        this.gols = gols;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdTabela() {
        return idTabela;
    }

    public void setIdTabela(String idTabela) {
        this.idTabela = idTabela;
    }

    @Override
    public int compareTo(Jogador j) {
        if (j.getGols() == this.gols)
            return this.nome.compareTo(j.getNome());

        return j.getGols() - this.gols;
    }
}
