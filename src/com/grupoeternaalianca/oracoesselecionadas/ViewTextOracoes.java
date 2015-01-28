package com.grupoeternaalianca.oracoesselecionadas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.util.TextViewEx;
import com.grupoeternaalianca.oracoesselecionadas.vo.OracaoVO;

import android.text.*;

public class ViewTextOracoes extends ActionBarActivity{
	private PersistenceDao persistenceDao = new PersistenceDao(this);
	private TextView tvTituloOracao=null;
	private TextViewEx textViewExs=null;
	private boolean favoritado;
	private int numeroOracao=0;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.vieworacoes);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		 
		 
		  Intent intent = getIntent();
		  Bundle extras = intent.getExtras();
		  OracaoVO oracao = new OracaoVO();
		  oracao = persistenceDao.buscaOracao(persistenceDao.openDB(),String.valueOf(extras.getInt("idOracao")));
		  numeroOracao = oracao.getIdNumero();
		  tvTituloOracao = (TextView) findViewById(R.id.tvTitulo);
		  textViewExs = (TextViewEx) findViewById(R.id.tvOracao);
		  tvTituloOracao.setText(Html.fromHtml(oracao.getTitulo()));
		  
		  String textoOracaoFormat1 =oracao.getTexto().replace("...", "[***]");
		  String textoOracaoFormat2 = textoOracaoFormat1.replace(".", ".<br/>");
		  String textoOracaoFormat3 = textoOracaoFormat2.replace("[***]", "...<br/>");
		  String textoOracaoFormat4 = textoOracaoFormat3.replace("!", "!<br/>");
		  
		  Spanned htmlTextFormt = Html.fromHtml(textoOracaoFormat4);
		  
		  textViewExs.setText(htmlTextFormt,true);	
	    
		  }

	    @Override
	    public boolean onPrepareOptionsMenu(Menu menu){
		return super.onPrepareOptionsMenu(menu);
	    }
	 
	 
		
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu){
	       getMenuInflater().inflate(R.menu.menuationbarfivoritos, menu);
		menu.removeItem(R.id.action_settings);
	       favoritado = persistenceDao.buscaFavoritoPorIdOracao(persistenceDao.openDB(), numeroOracao);
			 if(favoritado){
				 menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_action_important);
			 }else{
				 menu.findItem(R.id.action_favorite).setIcon(R.drawable.ic_action_not_important);
			 }
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item){
	        int id = item.getItemId();
 	        if (id == R.id.action_settings){
		    item.setVisible(false);
 	            return true;
 	        }
		if(id == android.R.id.home){
		    super.onBackPressed();
		    return true;
		}
		
	        if(id == R.id.action_favorite){
	        	if(favoritado){
	        		item.setIcon(R.drawable.ic_action_not_important);
	        		persistenceDao.deletaFavoritoPorIdOracao(persistenceDao.openDB(), numeroOracao);
	        		Toast.makeText(this,"Removido dos favoritos", Toast.LENGTH_LONG).show();
	        		favoritado=false;
	        	}else{
	        		item.setIcon(R.drawable.ic_action_important);
	        		persistenceDao.salvarFavorito(persistenceDao.openDB(), numeroOracao);
	        		Toast.makeText(this,"Adicionado aos favoritos", Toast.LENGTH_LONG).show();
	        		favoritado=true;
	        	}
	
	        }
	        
	        return super.onOptionsItemSelected(item);
	    }
	    
}
