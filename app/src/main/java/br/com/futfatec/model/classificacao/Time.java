package br.com.futfatec.model.classificacao;

public class Time implements Comparable<Time> {
	private String nome;
	private int golsPro;
	private int golsContra;
	private int pontos;
	private int jogos;
	private int vitorias;

	public Time() {
	}

	public Time(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getGolsPro() {
		return golsPro;
	}

	public void setGolsPro(int golsPro) {
		this.golsPro = golsPro;
	}

	public int getGolsContra() {
		return golsContra;
	}

	public void setGolsContra(int golsContra) {
		this.golsContra = golsContra;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getJogos() {
		return jogos;
	}

	public void setJogos(int jogos) {
		this.jogos = jogos;
	}

	public int getVitorias() {
		return vitorias;
	}

	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return this.nome;
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(((Time) obj).toString());
	}

	public int getSaldoGols() {
		return this.getGolsPro() - this.getGolsContra();
	}

	@Override
	public int compareTo(Time t) {
		if (t.getPontos() == this.getPontos())
			if (t.getVitorias() == this.vitorias)
				if (t.getSaldoGols() == this.getSaldoGols())
					return this.nome.compareTo(t.getNome());
				else
					return t.getSaldoGols() - this.getSaldoGols();
			else
				return t.getVitorias() - this.vitorias;

		return t.getPontos() - this.pontos;
	}
}