package com.grupoeternaalianca.oracoesselecionadas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.vo.*;

import java.util.*;

 @SuppressLint("ShowToast")
public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
	
	private PersistenceDao persistenceDao = new PersistenceDao();
	public static SQLiteDatabase bancoDados = null;
	private static final String DATABASE_NAME = "ORACOES_SELECIONADAS_DB";
	
	private ListView listView;
	private ArrayAdapter<String> ad;
	private List<String> itens = new ArrayList<String>();
	public List<GrupoVO> grupovoList =null;
	public static String listaTitulo[];
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowTitleEnabled(true);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);   
        mNavigationDrawerFragment.setUp(  R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        
    	persistenceDao.openOrCreateDB(openDB());
    	if(! persistenceDao.verificaBancoExistente(openDB())){
    		persistenceDao.criaConteudo(openDB(),this);
    		persistenceDao.buscaGrupos(openDB());
    	}
    	int i=1;
		for (TituloVO titulos : persistenceDao.buscaTitulos(openDB())){
			itens.add(	i++ +") " + titulos.getTitulo());
		}
		
        listView = (ListView)findViewById(R.id.listView1);
		ad = new ArrayAdapter<String>(this, R.layout.small, R.id.small, itens);
		listView.setAdapter(ad);
		
		listView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4){
					String texto = listView.getAdapter().getItem(p3).toString();
					Toast.makeText(MainActivity.this, texto, Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
	public SQLiteDatabase openDB(){
		try{
			bancoDados = openOrCreateDatabase(DATABASE_NAME, MODE_WORLD_READABLE, null);
		}catch (Exception e){
			Toast.makeText(MainActivity.this, (CharSequence) e, Toast.LENGTH_LONG).show();
		}
		return bancoDados;
	}

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	  Toast.makeText(this, "Posicao "+String.valueOf(position), Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction() .replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
    
//-----------------------------------------------------Classe     PlaceholderFragment
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached( getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
