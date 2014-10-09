package com.grupoeternaalianca.oracoesselecionadas;

import com.grupoeternaalianca.oracoesselecionadas.task.TaskDownloadDataBase;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends ActionBarActivity {
	Button btAtualizaDados;
	private String urlDownload = "http://192.168.1.100:8080/oracoesselecionadas/basedb.sql";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
	    btAtualizaDados = (Button) findViewById(R.id.button1);
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
		Toast.makeText(this,"Operação Realizada", Toast.LENGTH_LONG).show();
    }

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_settings,
					container, false);
			return rootView;
		}
	}

}
