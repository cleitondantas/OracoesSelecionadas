package com.grupoeternaalianca.oracoesselecionadas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.util.TextViewEx;
import com.grupoeternaalianca.oracoesselecionadas.vo.OracaoVO;

import android.text.*;

public class ViewTextOracoes extends ActionBarActivity{
	private PersistenceDao persistenceDao = new PersistenceDao();
	private TextView tvTituloOracao=null;
	private TextViewEx textViewExs=null;
	
	public static SQLiteDatabase bancoDados = null;
	private static final String DATABASE_NAME = "ORACOES_SELECIONADAS_DB";
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.vieworacoes);
		  Intent intent = getIntent();
		  Bundle extras = intent.getExtras();
		  OracaoVO oracao = new OracaoVO();
		  oracao = persistenceDao.buscaOracao(openDB(),String.valueOf(extras.getInt("idOracao")));
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
		public SQLiteDatabase openDB(){
			try{
				bancoDados = openOrCreateDatabase(DATABASE_NAME, MODE_WORLD_READABLE, null);
			}catch (Exception e){
			}
			return bancoDados;
		}

	 
}
