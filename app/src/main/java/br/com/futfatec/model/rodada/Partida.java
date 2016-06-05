package br.com.futfatec.model.rodada;

import java.util.List;

public class Partida implements Comparable<Partida> {
    private Time timeA;
    private Time timeB;
    private String horaInicio;
    private List<Evento> eventos;

    private Status status;

    public Time getTimeA() {
        return timeA;
    }

    public void setTimeA(Time timeA) {
        this.timeA = timeA;
    }

    public Time getTimeB() {
        return timeB;
    }

    public void setTimeB(Time timeB) {
        this.timeB = timeB;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Partida))
            return false;

        Partida p = (Partida) obj;

        if (!(p.getHoraInicio().equals(this.horaInicio)))
            return false;

        if (!(p.getTimeA().equals(this.timeA)))
            return false;

        if (!(p.getTimeB().equals(this.timeB)))
            return false;

        return true;
    }

    @Override
    public int compareTo(Partida p) {
        return Integer.parseInt(this.horaInicio.replace(":", "")) - Integer.parseInt(p.getHoraInicio().replace(":", ""));
    }

}
