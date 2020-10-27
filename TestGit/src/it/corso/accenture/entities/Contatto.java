package it.corso.accenture.entities;

import java.io.Serializable;

public class Contatto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String numero;
	
	public Contatto(String nome, String numero) {
		super();
		this.nome = nome;
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Contatto [nome=" + nome + ", numero=" + numero + "]";
	}
	
}
