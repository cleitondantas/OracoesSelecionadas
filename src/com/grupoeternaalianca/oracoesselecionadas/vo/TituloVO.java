package com.grupoeternaalianca.oracoesselecionadas.vo;

public class TituloVO {
	private int id;
	private String titulo;
	private String categoria;
	private int idOracao;
	private int idSubOracao;
	
	public int getIdOracao() {
		return idOracao;
	}
	public void setIdOracao(int idOracao) {
		this.idOracao = idOracao;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getIdSubOracao() {
		return idSubOracao;
	}
	public void setIdSubOracao(int idSubOracao) {
		this.idSubOracao = idSubOracao;
	}
	@Override
	public String toString()
	{
		return this.idOracao+" ) "+ this.titulo;
	}
	

}
