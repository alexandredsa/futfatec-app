package br.com.futfatec.model.rodada;


public enum Etapa {
	GRUPO("Grupo"), SEMI("Semi-Final"), FINAL("Final"), TERCEIRO("3º Lugar");

	String descricao;

	private Etapa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}