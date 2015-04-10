package com.grupoeternaalianca.oracoesselecionadas;

import com.grupoeternaalianca.oracoesselecionadas.task.TaskDownloadDataBase;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConfiguracoesActivity extends ActionBarActivity {
	Button btAtualizaDados;
	private String urlDownload = "http://www.grupoeternaalianca.com/arquivos/basedb.sql";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.id.configuracoesActivity);
        btAtualizaDados = (Button) findViewById(R.id.btAtualizaOracoes);
        btAtualizaDados.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				realizaAtualizacao(v.getContext());
			}
		});

    }
    
    private void realizaAtualizacao(Context context){
		TaskDownloadDataBase taskDownload = new TaskDownloadDataBase(context);
		taskDownload.execute(urlDownload);
    }
    
    
}
