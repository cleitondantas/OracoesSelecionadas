package com.grupoeternaalianca.oracoesselecionadas;

import java.util.ArrayList;
import java.util.List;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.vo.TituloVO;

import android.app.Activity;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class SearchResultsActivity extends Activity {
 
    private ListView listViewSearch;
    private ArrayAdapter<TituloVO> ad;
    private List<TituloVO> itens = new ArrayList<TituloVO>();
    private PersistenceDao persistenceDao = new PersistenceDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        listViewSearch = (ListView) findViewById(R.id.listViewSearchResult);
        // get the action bar
        ActionBar actionBar = getActionBar();
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        handleIntent(getIntent());
        
        listViewSearch.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4){
				TituloVO titulo = (TituloVO)listViewSearch.getAdapter().getItem(p3);
				chamaTelaTextOracao(titulo.getIdOracao());
			}
		});  
    }
	public void chamaTelaTextOracao(int idOracao){
		Intent intent = new Intent(this, VisualizarOracoesActivity.class);
		 Bundle dados = new Bundle();
		 dados.putInt("idOracao", idOracao);
		 intent.putExtras(dados);
		 startActivity(intent);
	}
    
    
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
 
    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
    }
 
    private void doSearch(String query){
        for (TituloVO titulos : persistenceDao.buscaTitulosPorNome(persistenceDao.openDB(),query)){
        	itens.add(titulos);
        }
		ad = new ArrayAdapter<TituloVO>(this, R.layout.small, R.id.small, itens);
		listViewSearch.setAdapter(ad);
	  	Toast.makeText(this,itens.size() +" Resultados da busca", Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
        	Intent intent = new Intent(this, PrincipalActivity.class);
    		startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}