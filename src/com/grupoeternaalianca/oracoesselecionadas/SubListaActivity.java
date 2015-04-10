package com.grupoeternaalianca.oracoesselecionadas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.vo.Constantes;
import com.grupoeternaalianca.oracoesselecionadas.vo.TituloVO;

public class SubListaActivity extends Activity {
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
		getActionBar().setTitle(extras.getString(Constantes.TITULO));
		List<TituloVO> subTitlesList = persistenceDao.buscaSubTitulosPorSubId(persistenceDao.openDB(this), extras.getInt(Constantes.IDLISTATITULOS));
		criaListView(subTitlesList);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
					TituloVO titulo = (TituloVO) listView.getAdapter().getItem(p3);
					chamaTelaTextOracao(titulo.getIdOracao());
			}
		});
	}
	
	public void chamaTelaTextOracao(int idOracao) {
		Intent intent = new Intent(this, VisualizarOracoesActivity.class);
		Bundle dados = new Bundle();
		dados.putInt(Constantes.IDORACAO, idOracao);
		intent.putExtras(dados);
		startActivity(intent);
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
		if(id == android.R.id.home){
		    super.onBackPressed();
		    return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void criaListView(List<TituloVO>  subTitlesList) {
		listView = (ListView) findViewById(R.id.listViewSubListaOracoes);
		List<TituloVO> tituloList = new ArrayList<TituloVO>();
		
		for (final TituloVO titulo : subTitlesList) {
			TituloVO tituloVO = new TituloVO(titulo){
				@Override 
				public String toString(){
					return titulo.getTitulo();
				};
			};
			tituloList.add(tituloVO);
		}

		arrayAdapter = new ArrayAdapter<TituloVO>(this, R.layout.small, R.id.small, tituloList);
		listView.setAdapter(arrayAdapter);
	}
}
