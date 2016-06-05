package br.com.futfatec.model.rodada;

import java.util.ArrayList;
import java.util.List;

public enum TipoEvento {
    GOL("Gol"), CARTAO_AMARELO("Cartão Amarelo"), CARTAO_VERMELHO("Cartão Vermelho");

    private String descricao;

    TipoEvento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static List<String> namesToArray() {
        ArrayList<String> names = new ArrayList<>();

        for (TipoEvento tipoEvento : TipoEvento.values()) {
            names.add(tipoEvento.getDescricao());
        }
        return names;
    }

    public static TipoEvento findByName(String name) {
        for (TipoEvento tipoEvento : TipoEvento.values()) {
            if (tipoEvento.getDescricao().equalsIgnoreCase(name))
                return tipoEvento;
        }

        return null;
    }
}
