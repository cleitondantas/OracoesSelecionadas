package com.grupoeternaalianca.oracoesselecionadas;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.vo.TituloVO;

public class SubListaOracoes extends Activity {
	private ListView listView;
	private PersistenceDao persistenceDao = new PersistenceDao(this);
	private ArrayAdapter<TituloVO> arrayAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_lista_oracoes);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		List<TituloVO> subTitlesList = persistenceDao.buscaSubTitulosPorSubId(persistenceDao.openDB(this), extras.getInt("idListTitile"));
		criaListView(subTitlesList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub_lista_oracoes, menu);
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
	
	private void criaListView(List<TituloVO>  subTitlesList) {
		listView = (ListView) findViewById(R.id.listViewSubListaOracoes);
		arrayAdapter = new ArrayAdapter<TituloVO>(this, R.layout.small, R.id.small, subTitlesList);
		listView.setAdapter(arrayAdapter);
	}
}
