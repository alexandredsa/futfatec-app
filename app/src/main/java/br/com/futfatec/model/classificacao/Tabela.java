package br.com.futfatec.model.classificacao;

import java.util.List;

public class Tabela {
	private String id;
	private String nomeCampeonato;
	private String leagueId;
	private List<Grupo> grupos;

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeCampeonato() {
		return nomeCampeonato;
	}

	public void setNomeCampeonato(String nomeCampeonato) {
		this.nomeCampeonato = nomeCampeonato;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public Grupo getBySigla(String sigla) {
		for (Grupo grupo : this.grupos) {
			if (grupo.getSigla().equals(sigla))
				return grupo;
		}

		return null;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
