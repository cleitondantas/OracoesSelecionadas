package com.grupoeternaalianca.oracoesselecionadas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.util.Preferences;
import com.grupoeternaalianca.oracoesselecionadas.util.TextViewEx;
import com.grupoeternaalianca.oracoesselecionadas.vo.Constantes;
import com.grupoeternaalianca.oracoesselecionadas.vo.OracaoVO;

public class VisualizarOracoesActivity extends ActionBarActivity{
	private PersistenceDao persistenceDao = new PersistenceDao(this);
	private TextView tvTituloOracao=null;
	private TextViewEx textViewExs=null;
	private boolean favoritado;
	private int numeroOracao=0;
	private Preferences pref ;

	private OracaoVO oracao = new OracaoVO();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.vieworacoes);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		  pref = new Preferences(this);
		  oracao = persistenceDao.buscaOracao(persistenceDao.openDB(),String.valueOf(getIntent().getExtras().getInt(Constantes.IDORACAO)));
		  numeroOracao = oracao.getIdNumero();
		  tvTituloOracao = (TextView) findViewById(R.id.tvTitulo);
		  textViewExs = (TextViewEx) findViewById(R.id.tvOracao);
		  tvTituloOracao.setText(Html.fromHtml(oracao.getTitulo()));
		  
		  String textoOracaoFormat1 =oracao.getTexto().replace("...", "[***]");
		  String textoOracaoFormat2 = textoOracaoFormat1.replace(".", ".<br/>");
		  String textoOracaoFormat3 = textoOracaoFormat2.replace("[***]", "...<br/>");
		  String textoOracaoFormat4 = textoOracaoFormat3.replace("!", "!<br/>");
		  String textoOracaoFormat5 = textoOracaoFormat4.replace("\n", "<br/>");
		  Spanned htmlTextFormt = Html.fromHtml(textoOracaoFormat5);
		  textViewExs.setText(htmlTextFormt, true);
		 textViewExs.setTextSize((5*(pref.getFontSize(Constantes.fontSizeTexto,4))));
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
	        		Toast.makeText(this,R.string.removido_favoritos, Toast.LENGTH_LONG).show();
	        		favoritado=false;
	        	}else{
	        		item.setIcon(R.drawable.ic_action_important);
	        		persistenceDao.salvarFavorito(persistenceDao.openDB(), numeroOracao);
	        		Toast.makeText(this,R.string.adicionado_favoritos, Toast.LENGTH_LONG).show();
	        		favoritado=true;
	        	}
	
	        }
	        
	        return super.onOptionsItemSelected(item);
	    }
	    
}
