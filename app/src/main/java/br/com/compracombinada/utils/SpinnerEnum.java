package br.com.compracombinada.utils;

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
