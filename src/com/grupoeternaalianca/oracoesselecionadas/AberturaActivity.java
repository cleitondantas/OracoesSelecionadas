package com.grupoeternaalianca.oracoesselecionadas;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.task.TaskProcess;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;


public class AberturaActivity extends Activity{
	private PersistenceDao persistenceDao = new PersistenceDao(this);
	public static SQLiteDatabase bancoDados = null;
	private Integer TIMESLEAP;
	@Override
	public  void onCreate(Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.abertura_splashscreen);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    
    	persistenceDao.onCreate(persistenceDao.openDB());
    	
    	if(!persistenceDao.verificaBancoExistente(persistenceDao.openDB())){
    		TIMESLEAP=2000;
    	    TaskProcess taskPross = new TaskProcess(this);
    	    taskPross.execute(persistenceDao);
	    }else{
	    	TIMESLEAP=2000;
	    }
	    new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent minhaintent = new Intent(AberturaActivity.this,PrincipalActivity.class);
				AberturaActivity.this.startActivity(minhaintent);
				AberturaActivity.this.finish();
			}
		},TIMESLEAP);

	}

}
