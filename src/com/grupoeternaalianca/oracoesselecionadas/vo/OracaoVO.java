package com.grupoeternaalianca.oracoesselecionadas.vo;

public class OracaoVO {
	private static String titulo;
	private static  String texto;
	private static int idNumero;
	
	
	public int getIdNumero() {
		return idNumero;
	}
	public String getTexto() {
		return texto;
	}
	public void setIdNumero(int idNumero) {
		OracaoVO.idNumero = idNumero;
	}
	public void setTexto(String texto) {
		OracaoVO.texto = texto;
	}
	public static String getTitulo() {
		return titulo;
	}
	public static void setTitulo(String titulo) {
		OracaoVO.titulo = titulo;
	}
}
