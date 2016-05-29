package br.com.futfatec.model.rodada;


import br.com.futfatec.model.artilharia.Jogador;

public class Evento {
	private TipoEvento tipo;
	private Jogador jogador;

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

}
