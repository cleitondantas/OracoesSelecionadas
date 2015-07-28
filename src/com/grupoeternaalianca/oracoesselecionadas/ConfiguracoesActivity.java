package com.grupoeternaalianca.oracoesselecionadas;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.grupoeternaalianca.oracoesselecionadas.task.TaskDownloadDataBase;


public class ConfiguracoesActivity extends ActionBarActivity {
//	Button btAtualizaDados;
	private String urlDownload = "http://www.grupoeternaalianca.com/arquivos/basedb.sql";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.id.configuracoesActivity);

    }
    
    private void realizaAtualizacao(Context context){
		TaskDownloadDataBase taskDownload = new TaskDownloadDataBase(context);
		taskDownload.execute(urlDownload);
    }
    
    
}
