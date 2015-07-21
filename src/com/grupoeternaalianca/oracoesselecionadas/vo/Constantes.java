package com.grupoeternaalianca.oracoesselecionadas.vo;

import com.grupoeternaalianca.oracoesselecionadas.R;

public class Constantes {

	public static final String IDORACAO = "IDORACAO";
	public static final String IDLISTATITULOS = "IDLISTATITULOS";
	public static final String TITULO = "TITULO";

	public static int getLayoutFontSize(int size) {
		switch (size) {
		case 0:
			return R.layout.small;
		case 1:
			return R.layout.medium;
		case 2:
			return R.layout.large;
		default:
			return R.layout.small;
			
		}
	}

	public static int getIdFontSize(int size) {
		switch (size) {
		case 0:
			return R.id.small;
		case 1:
			return R.id.medium;
		case 2:
			return R.id.large;
		default:
			return R.id.small;
		}
	}
}