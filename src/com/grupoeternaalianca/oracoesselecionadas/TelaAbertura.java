package com.grupoeternaalianca.oracoesselecionadas;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class TelaAbertura extends Activity{
	private PersistenceDao persistenceDao = new PersistenceDao(this);
	public static SQLiteDatabase bancoDados = null;
	@Override
	public  void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	   
	    setContentView(R.layout.abertura_splashscreen);
	  
		final Context cont = getApplicationContext();
    	persistenceDao.onCreate(persistenceDao.openDB(cont));
    	if(! persistenceDao.verificaBancoExistente(persistenceDao.openDB(cont))){
  	       persistenceDao.criaConteudo(persistenceDao.openDB(cont),cont);
  	       persistenceDao.buscaGrupos(persistenceDao.openDB(cont));
    	}	

	    new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent minhaintent = new Intent(TelaAbertura.this,MainActivity.class);
				TelaAbertura.this.startActivity(minhaintent);
				TelaAbertura.this.finish();
			}
		}, 5000);

	}

}
