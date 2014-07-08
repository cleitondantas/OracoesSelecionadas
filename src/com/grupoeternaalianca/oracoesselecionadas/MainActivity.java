package com.grupoeternaalianca.oracoesselecionadas;

import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.grupoeternaalianca.oracoesselecionadas.vo.*;
import java.util.*;

public class MainActivity extends ActionBarActivity{
	
	public static SQLiteDatabase bancoDados = null;
	private static final String DATABASE_NAME = "ORACOES_SELECIONADAS_DB";
	private static final String TABLE_NOTES = "TITULOS";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_TITLE = "TITLE";
	private static final String COLUMN_NOTES_KEY = "KEY";
	
	
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
		openOrCreateDB();
        listView = (ListView)findViewById(R.id.listView1);
		ad = new ArrayAdapter<String>(this, R.layout.small, R.id.small, itens);

		for (int i=0;i < 400;i++){
			itens.add("Oração " + i);

		}
		listView.setAdapter(ad);
		listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4){
					String posicao =	listView.getAdapter().getItem(p3).toString();

					OracaoVO vo= new OracaoVO();
					vo.setIdNumero(p3);
					vo.setTexto(posicao);

					Toast.makeText(MainActivity.this, posicao, Toast.LENGTH_LONG).show();

				}
			});
    }

	public void chamaTelaTextOracao()
	{
		Intent intent = new Intent(MainActivity.this, ViewTextOracoes.class);
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{

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
			registraNovoDado();
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
	public void openOrCreateDB(){
		try{
			bancoDados = openOrCreateDatabase(DATABASE_NAME, MODE_WORLD_READABLE, null);
			
			Toast.makeText(MainActivity.this, "Banco Criado", Toast.LENGTH_SHORT).show();
		}
		catch (Exception e){
			Toast.makeText(MainActivity.this, "Erro ao Criar Banco " + e,Toast.LENGTH_SHORT).show();
		}

	}
	public void registraNovoDado(){
		try{
			bancoDados = openOrCreateDatabase(DATABASE_NAME, MODE_WORLD_READABLE, null);
			String sql2 = "INSERT or replace INTO " + TABLE_NOTES + " ("+COLUMN_TITLE+","+COLUMN_NOTES_KEY+") VALUES ('Ave Maria','um');";
			bancoDados.execSQL(sql2);
			Toast.makeText(MainActivity.this, "Dados Salvos", Toast.LENGTH_LONG).show();
		}
		catch (Exception e){
			Toast.makeText(MainActivity.this, "Erro ao Salvar  "+e, Toast.LENGTH_LONG).show();
		}
	}

}
