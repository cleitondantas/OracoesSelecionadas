package com.grupoeternaalianca.oracoesselecionadas;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.vo.OracaoVO;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class ViewTextOracoes extends ActionBarActivity{
	private TextView tvTextOracao=null;
	private TextView tvTituloOracao=null;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.vieworacoes);
		  Intent intent = getIntent();
		  Bundle extras = intent.getExtras();
		  
		 PersistenceDao persistenceDao = new PersistenceDao();
		 MainActivity main = new MainActivity();
		 
		OracaoVO oracao = persistenceDao.buscaOracao(main.openDB(),String.valueOf(extras.getInt("idOracao")));
		 
		  tvTituloOracao = (TextView) findViewById(R.id.tvTitulo);
		  tvTextOracao = (TextView) findViewById(R.id.tvOracao);
		  
		  tvTituloOracao.setText(oracao.getTitulo());
		  tvTextOracao.setText(oracao.getTexto());
	 }
	 

	 
}
