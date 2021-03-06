package com.grupoeternaalianca.oracoesselecionadas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoeternaalianca.oracoesselecionadas.dao.PersistenceDao;
import com.grupoeternaalianca.oracoesselecionadas.util.Preferences;
import com.grupoeternaalianca.oracoesselecionadas.vo.Constantes;
import com.grupoeternaalianca.oracoesselecionadas.vo.TituloVO;

public class PrincipalActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchView.OnQueryTextListener {
    private static final String LOGS = "logs";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private SearchView mSearchView;
    private PersistenceDao persistenceDao = new PersistenceDao(this);
    private boolean controlaList = false;
    private ListView listView;
    private int sizeTextoTitulos;
    private ArrayAdapter<TituloVO> ad;
    private List<TituloVO> itens = new ArrayList<TituloVO>();
    private MenuItem searchItem;
    private int qtdOracoes;
    private Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayShowTitleEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pref = new Preferences(this);
        sizeTextoTitulos = pref.getFontSize(Constantes.fontSizeTitulo, 1);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        criaListView(null);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                TituloVO titulo = (TituloVO) listView.getAdapter().getItem(p3);
                if (titulo.getIdSubTitulo() > 0) {
                    redirectSubListaOracoes(titulo.getIdSubTitulo(), titulo.getTitulo());
                } else {
                    Toast.makeText(PrincipalActivity.this, titulo.getIdOracao() + " " + titulo.getTitulo(), Toast.LENGTH_LONG).show();
                    chamaTelaTextOracao(titulo.getIdOracao());
                }
            }
        });
    }

    private void criaListView(String numeroGrupo) {
        itens.clear();
        for (TituloVO titulos : persistenceDao.buscaTitulos(persistenceDao.openDB(this))) {
            if (numeroGrupo != null && numeroGrupo.equalsIgnoreCase("-1")) {
                List<Integer> numerosOracaoesFavoritas = persistenceDao.buscaTodosFavoritos(persistenceDao.openDB(this));
                if (numerosOracaoesFavoritas.contains(titulos.getIdOracao())) {
                    itens.add(titulos);
                }
            } else {
                if (numeroGrupo == null || numeroGrupo.equalsIgnoreCase("0")) {
                    itens.add(titulos);
                    qtdOracoes = itens.size();
                } else {
                    if (titulos.getCategoria().equalsIgnoreCase(numeroGrupo)) {
                        itens.add(titulos);
                    }
                }
            }
        }
        listView = (ListView) findViewById(R.id.oracoesListView);
        ad = new ArrayAdapter<TituloVO>(this, Constantes.getLayoutFontSize(sizeTextoTitulos), Constantes.getIdFontSize(sizeTextoTitulos), itens);
        listView.setAdapter(ad);

    }

    public void chamaTelaTextOracao(int idOracao) {
        Intent intent = new Intent(this, VisualizarOracoesActivity.class);
        Bundle dados = new Bundle();
        dados.putInt(Constantes.IDORACAO, idOracao);
        intent.putExtras(dados);
        startActivity(intent);
    }

    private void redirectSubListaOracoes(int idSubListTitle, String titulo) {
        Intent intent = new Intent(this, SubListaActivity.class);
        Bundle dados = new Bundle();
        dados.putInt(Constantes.IDLISTATITULOS, idSubListTitle);
        dados.putString(Constantes.TITULO, titulo);
        intent.putExtras(dados);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            redirectConfiguracoes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (controlaList) {
            criaListView(String.valueOf(position - 1));
        } else {
            controlaList = true;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
    }

    public void onSectionAttached(int number) {
        //Numero Selecionado
    }

    public void restoreActionBar(String mTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

    }

    private void redirectConfiguracoes() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextChange(String text) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String text) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(SearchManager.QUERY, text);
        startActivity(intent);

        return false;
    }

    @Override
    public void onBackPressed() {
        if (qtdOracoes == itens.size()) {
            super.onBackPressed();
        }

        criaListView("0");
        mSearchView.onActionViewCollapsed();
        mSearchView.setQuery("", false);
        mSearchView.clearFocus();

    }

    public void exitOrMenu(String texto) {

        AlertDialog.Builder mensagem = new AlertDialog.Builder(PrincipalActivity.this);
        mensagem.setMessage(texto);
        mensagem.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mensagem.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        mensagem.setIcon(R.drawable.ic_launcher);
        mensagem.show();
    }

    // -----------------------------------------------------Classe
    // PlaceholderFragment

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
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
            ((PrincipalActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
