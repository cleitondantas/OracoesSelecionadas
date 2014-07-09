package com.grupoeternaalianca.oracoesselecionadas;

import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.vo.*;

import java.util.*;

public class MainActivity extends ActionBarActivity{
	private PersistenceDao persistenceDao = new PersistenceDao();
	public static SQLiteDatabase bancoDados = null;
	private static final String DATABASE_NAME = "ORACOES_SELECIONADAS_DB";
	
	private ListView listView;
	private ArrayAdapter<String> ad;
	private List<String> itens = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    	persistenceDao.openOrCreateDB(openDB());
    	persistenceDao.registraNovoDado( openDB());

		for (TituloVO titulos : persistenceDao.buscaTitulos(openDB())){
			itens.add("Oração " + titulos.getTitulo());
		}
		
        listView = (ListView)findViewById(R.id.listView1);
		ad = new ArrayAdapter<String>(this, R.layout.small, R.id.small, itens);

		listView.setAdapter(ad);
		listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4){
					String posicao = listView.getAdapter().getItem(p3).toString();
					Toast.makeText(MainActivity.this, posicao, Toast.LENGTH_LONG).show();
					chamaTelaTextOracao(p3);	
				}
			});
    }

	public void chamaTelaTextOracao(int idOracao){
		Intent intent = new Intent(this, ViewTextOracoes.class);
		 Bundle dados = new Bundle();
		 dados.putInt("idOracao", idOracao);
		 intent.putExtras(dados);
		 startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings){
		
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{

        public PlaceholderFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState)
		{
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
	public SQLiteDatabase openDB(){
		try{
			bancoDados = openOrCreateDatabase(DATABASE_NAME, MODE_WORLD_READABLE, null);
			Toast.makeText(MainActivity.this, "OpenDB", Toast.LENGTH_SHORT).show();
		}catch (Exception e){
			Toast.makeText(MainActivity.this, "Erro ao Criar Banco " + e,Toast.LENGTH_SHORT).show();
		}
		return bancoDados;
	}
}
