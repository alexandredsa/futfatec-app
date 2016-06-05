package br.com.futfatec.model.rodada;

import java.util.List;

import br.com.futfatec.model.artilharia.Jogador;


public class Time {
    private String nome;
    private int gols;
    private List<Jogador> jogadores;

    public Time(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getGols() {
        return gols;
    }

    public void setGols(int gols) {
        this.gols = gols;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Time))
            return false;

        Time t = (Time) obj;

        if (!(t.getNome().equals(this.nome)))
            return false;

        return true;
    }

}
