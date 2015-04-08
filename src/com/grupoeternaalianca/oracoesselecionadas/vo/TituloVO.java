package com.grupoeternaalianca.oracoesselecionadas.vo;

public class TituloVO {
	private int id;
	private String titulo;
	private String categoria;
	private int idOracao;
	private int idSubTitulo;
	
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

	@Override
	public String toString()
	{
		return this.idOracao+" ) "+ this.titulo;
	}
	public int getIdSubTitulo() {
		return idSubTitulo;
	}
	public void setIdSubTitulo(int idSubTitulo) {
		this.idSubTitulo = idSubTitulo;
	}
	

}
