package com.grupoeternaalianca.oracoesselecionadas.task;
import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
public class TaskDownloadDataBase extends AsyncTask<String, Object, String> {
private ProgressDialog progress;
private Context context;
private PersistenceDao persistenceDao;

	public TaskDownloadDataBase(Context context) {
	this.context = context;
	persistenceDao = new PersistenceDao(context);
	}
	
	@Override
	protected void onPreExecute(){
		progress = new ProgressDialog(context);
		progress.setMessage("Garregando ...");
		progress.show();
	}
	
	@Override
	protected String doInBackground(String... urls) {
		
		try {
			Log.i("DEBUG","Iniciado");
			URL url = new URL("http://www.grupoeternaalianca.com/arquivos/basedb.sql");
			publishProgress("Abrindo Connecxao");
			URLConnection conec = url.openConnection();
			InputStream input  = conec.getInputStream();
			Log.i("DEBUG","Iniciado Gravacao");
			persistenceDao.onUpgrade(persistenceDao.openDB(), 1,2);
			persistenceDao.criaConteudo(input,persistenceDao.openDB());	
			Log.i("DEBUG","Persistidos");
		} catch (IOException e) {
			Log.i("DEBUG","Erro"+e);
			publishProgress("Erro ao Gravar dados"+e);
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	protected void onProgressUpdate(Object... params){

	}
	@Override
	protected void onPostExecute(String params){
		progress.setMessage("Base atualizada !");
		progress.dismiss();
	}

}
