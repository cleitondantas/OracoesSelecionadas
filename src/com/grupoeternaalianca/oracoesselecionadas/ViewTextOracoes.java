package com.grupoeternaalianca.oracoesselecionadas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class ViewTextOracoes extends ActionBarActivity{
	private int idOracao;
	private TextView tvTextOracao=null;
	private TextView tvTituloOracao=null;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.vieworacoes);
		  Intent intent = getIntent();
		  Bundle extras = intent.getExtras();
		  idOracao = extras.getInt("idOracao");
		 
		  
		  tvTituloOracao = (TextView) findViewById(R.id.tvTitulo);
		  tvTextOracao = (TextView) findViewById(R.id.tvOracao);
		  
		  tvTituloOracao.setText("Ave Maria");
		  tvTextOracao.setText("Ave Maria Cheia de graças o Senhor é convosco ");
	 }
	 

	 
}
