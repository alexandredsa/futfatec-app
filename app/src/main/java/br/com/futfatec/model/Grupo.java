package br.com.futfatec.model;

import java.util.TreeSet;

public class Grupo {
	private String sigla;
	private TreeSet<Time> times;
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public TreeSet<Time> getTimes() {
		return times;
	}
	public void setTimes(TreeSet<Time> times) {
		this.times = times;
	}
	public void addTime(Time time) {
		this.times.add(time);
	}
}
