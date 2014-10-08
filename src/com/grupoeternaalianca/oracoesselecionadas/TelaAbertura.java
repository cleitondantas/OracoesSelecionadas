package com.grupoeternaalianca.oracoesselecionadas;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.task.TaskProcess;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;


public class TelaAbertura extends Activity{
	private PersistenceDao persistenceDao = new PersistenceDao(this);
	public static SQLiteDatabase bancoDados = null;
	private Integer TIMESLEAP;
	@Override
	public  void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.abertura_splashscreen);
	    
    	persistenceDao.onCreate(persistenceDao.openDB());
    	if(!persistenceDao.verificaBancoExistente(persistenceDao.openDB())){
    		TIMESLEAP=12000;
    	    TaskProcess taskPross = new TaskProcess(this);
    	    taskPross.execute(persistenceDao);
	    }else{
	    	TIMESLEAP =3000;
	    }
	    new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent minhaintent = new Intent(TelaAbertura.this,MainActivity.class);
				TelaAbertura.this.startActivity(minhaintent);
				TelaAbertura.this.finish();
			}
		},TIMESLEAP);

	}

}
