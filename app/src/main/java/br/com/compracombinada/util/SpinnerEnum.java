package br.com.compracombinada.util;

public enum SpinnerEnum {

	MARCA("Marca"),FAMILIA("Familia"),DIVISAO("Divisao"), PREFERENCIA("Preferencia");

	private String classe;

	private SpinnerEnum(String classe) {
		this.classe = classe;
	}

	public String getClasse() {
		return this.classe;
	}

}
