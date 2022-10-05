package com.reactor.springbootreactor.app.models;


public class Usuario {
	
	private String nome;
	private String sobreNome;
	
	
	public Usuario(String nome, String sobreNome) {
		super();
		this.nome = nome;
		this.sobreNome = sobreNome;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", sobreNome=" + sobreNome + "]";
	}
	
	
	
	
}
