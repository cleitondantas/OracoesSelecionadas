package com.grupoeternaalianca.oracoesselecionadas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.grupoeternaalianca.oracoesselecionadas.task.TaskDownloadDataBase;
import com.grupoeternaalianca.oracoesselecionadas.vo.Constantes;

public class SettingsActivity extends ActionBarActivity {
	
	private int fontSizeTexto=0;
	private int fontSizeTitulo=0;

	private String urlDownload = "http://192.168.1.100:8080/oracoesselecionadas/basedb.sql";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		final  SeekBar sbSizeFont = (SeekBar)findViewById(R.id.seekBar);
		final  SeekBar sbSizeFontTitle = (SeekBar)findViewById(R.id.seekBarTitle);
		sbSizeFontTitle.setMax(2);
		sbSizeFont.setMax(6);

		fontSizeTitulo = getSettingPrefFontText(Constantes.fontSizeTitulo);
		fontSizeTexto = getSettingPrefFontText(Constantes.fontSizeTexto);
		sbSizeFont.setProgress(fontSizeTexto);
		sbSizeFontTitle.setProgress(fontSizeTitulo);
		
		sbSizeFontTitle.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
				fontSizeTitulo = progress;
				salvarPref();
			}
		});
		
		sbSizeFont.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
				fontSizeTexto = progress;
				salvarPref();
			}
		});
	}

    private void realizaAtualizacao(Context context){
		TaskDownloadDataBase taskDownload = new TaskDownloadDataBase(context);
		taskDownload.execute(urlDownload);
		Toast.makeText(this,"Operação Realizada", Toast.LENGTH_LONG).show();
    }

    public void salvarPref(){
    	SharedPreferences settings = getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Constantes.fontSizeTexto, fontSizeTexto);
        editor.putInt(Constantes.fontSizeTitulo, fontSizeTitulo);
        editor.commit();
 	}
    
    public int getSettingPrefFontText(String key){
    SharedPreferences settings = getSharedPreferences("Preferences", 0);
    	return settings.getInt(key, 1);
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_settings,	container, false);
			return rootView;
		}
	}

}
