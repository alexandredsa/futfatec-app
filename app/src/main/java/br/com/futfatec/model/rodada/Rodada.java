package br.com.futfatec.model.rodada;

import java.util.TreeSet;


public class Rodada {
	private String id;
	private Etapa etapa;
	private TreeSet<Partida> partidas;
	/**
	 * Timestamp - Dia do jogo
	 */
	private long dia;

	private String idTabela;

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public TreeSet<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(TreeSet<Partida> partidas) {
		this.partidas = partidas;
	}

	public long getDia() {
		return dia;
	}

	public void setDia(long dia) {
		this.dia = dia;
	}

	public String getIdTabela() {
		return idTabela;
	}

	public void setIdTabela(String idTabela) {
		this.idTabela = idTabela;
	}

	public void setPartida(Partida partida) {
		if (partidas == null)
			return;

		partidas.add(partida);
	}

}
