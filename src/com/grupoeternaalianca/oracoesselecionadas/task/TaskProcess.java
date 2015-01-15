package com.grupoeternaalianca.oracoesselecionadas.task;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class TaskProcess extends AsyncTask<PersistenceDao,String,Object>{
private Context context;
private ProgressDialog progress;
	public TaskProcess(Context context) {
		this.context = context;
	}
	@Override
	protected void onPreExecute(){
		progress = new ProgressDialog(this.context);
		progress.setMessage("Carregando...");
		progress.show();
	}
	@Override
	protected Object doInBackground(PersistenceDao... params) {
		PersistenceDao persistenceDao  = params[0];
//		persistenceDao.criaConteudo(persistenceDao.openDB());
		persistenceDao.copiaBanco(PersistenceDao.DATABASE_NAME);
		return null;
	}
	@Override
	protected void onProgressUpdate(String... params){
		progress.setMessage(params[0]);
	}
	@Override
	protected void onPostExecute(Object params){
		progress.setMessage("Carregado !");
		progress.dismiss();
	}
}
