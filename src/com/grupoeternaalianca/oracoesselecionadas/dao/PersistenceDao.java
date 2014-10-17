package com.grupoeternaalianca.oracoesselecionadas.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.grupoeternaalianca.oracoesselecionadas.R;
import com.grupoeternaalianca.oracoesselecionadas.vo.GrupoVO;
import com.grupoeternaalianca.oracoesselecionadas.vo.OracaoVO;
import com.grupoeternaalianca.oracoesselecionadas.vo.TituloVO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class PersistenceDao extends SQLiteOpenHelper{
	public static final String DATABASE_NAME = "ORACOES_SELECIONADAS_DB";
	private static final String TABLE_NOTES = "TITULOS";
	private static final String TABLE_ORACAO = "ORACAO";
	private static final String TABLE_GRUPO = "GRUPO";
	private static final String TABLE_FAVORITOS = "FAVORITOS";
	
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_IDTITULO = "IDTITULO";
	private static final String COLUMN_IDGRUPO = "IDGRUPO";
	private static final String COLUMN_TITULO = "TITULO";
	private static final String COLUMN_TITLE = "TITULO";
	private static final String COLUMN_GRUPO = "GRUPO";
	private static final String COLUMN_ORACAO = "ORACAO";
	private static final String COLUMN_IDORACAO = "IDORACAO";
	
	public static final String SCRIPT_DELECAO_TABELA_ORACAO =  "DROP TABLE IF EXISTS " + TABLE_ORACAO;
	public static final String SCRIPT_DELECAO_TABELA_TITULO =  "DROP TABLE IF EXISTS " + TABLE_NOTES;
	public static final String SCRIPT_DELECAO_TABELA_GRUPO =  "DROP TABLE IF EXISTS " + TABLE_GRUPO;
	
	private Cursor cursor;
	private static PersistenceDao instance;
	private static final int VERSAO =1;
	public static SQLiteDatabase bancoDados = null;
	private static Context contextStatic;
	public PersistenceDao(Context context) {
		super(context, DATABASE_NAME, null, VERSAO);
		openDB(context);
		contextStatic =context;
	}
	public static PersistenceDao getInstance(Context context) {
		if(instance == null)
			instance = new PersistenceDao(context);
		return instance;
	}
	/**
	 * Verifica a existencia do banco de dados
	 * 
	 * @param bancoDados
	 * @return
	 */
	public boolean verificaBancoExistente(SQLiteDatabase bancoDados){
		cursor = bancoDados.query(TABLE_NOTES, new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_GRUPO}, null,null,null,null,null);
		if(cursor.getCount()>2){
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo Busca os titulos e Lista para ler na view
	 */
	public List<TituloVO> buscaTitulos(SQLiteDatabase bancoDados){
			ArrayList<TituloVO> tituloOracoes = new ArrayList<TituloVO>();
		cursor = bancoDados.query(TABLE_NOTES, new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_GRUPO,COLUMN_IDORACAO}, null,null,null,null,null);
			TituloVO titulo =null;
			while(cursor.moveToNext()){
				titulo = new TituloVO();
				titulo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
				titulo.setCategoria(cursor.getString(cursor.getColumnIndex(COLUMN_GRUPO)));
				titulo.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
				titulo.setIdOracao(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_IDORACAO))));
				
				tituloOracoes.add(titulo);
				
			}
			bancoDados.close();
		return tituloOracoes;
	}
	
	/**
	 * Metodo Busca os titulos e Lista para ler na view
	 */
	public List<TituloVO> buscaTitulosPorNome(SQLiteDatabase bancoDados,String query){
			ArrayList<TituloVO> tituloOracoes = new ArrayList<TituloVO>();
	
		cursor = bancoDados.query(TABLE_NOTES, new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_GRUPO,COLUMN_IDORACAO},COLUMN_TITLE+" LIKE ?",new String[]{"%" +query+"%"},null,null,null,null);
			TituloVO titulo =null;
			while(cursor.moveToNext()){
				titulo = new TituloVO();
				titulo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
				titulo.setCategoria(cursor.getString(cursor.getColumnIndex(COLUMN_GRUPO)));
				titulo.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
				titulo.setIdOracao(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_IDORACAO))));
				
				tituloOracoes.add(titulo);
				
			}
			bancoDados.close();
		return tituloOracoes;
	}
	
	/**
	 * Metodo Busca os Grupos
	 */
	public List<GrupoVO> buscaGrupos(SQLiteDatabase bancoDados){
			ArrayList<GrupoVO> grupoVOs = new ArrayList<GrupoVO>();
			cursor = bancoDados.query(TABLE_NOTES, new String[]{COLUMN_ID,COLUMN_TITULO,COLUMN_IDGRUPO}, null,null,null,null,null);
			GrupoVO grupovo =null;
			while(cursor.moveToNext()){
				grupovo = new GrupoVO();
				grupovo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
				grupovo.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITULO)));
				grupovo.setIdgrupo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_IDGRUPO))));
				grupoVOs.add(grupovo);
				
			}
			bancoDados.close();
		return grupoVOs;
	}
	
	/**
	 * Metodo Busca os titulos e Lista para ler na view
	 */
	public OracaoVO buscaOracao(SQLiteDatabase bancoDados,String idOracao){
			cursor = bancoDados.query(TABLE_ORACAO, new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_ORACAO,COLUMN_IDORACAO},"IDORACAO = "+idOracao ,null,null,null,null);
			OracaoVO oracao = new OracaoVO();
			while(cursor.moveToNext()){
				oracao = new OracaoVO();
				oracao.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
				oracao.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));	
				oracao.setTexto(cursor.getString(cursor.getColumnIndex(COLUMN_ORACAO)));			
				oracao.setIdNumero(cursor.getInt(cursor.getColumnIndex(COLUMN_IDORACAO)));
				
				}
			bancoDados.close();
		return oracao;
	}
	
	/**
	 * Método que persiste o numero da oracao que deseja que seja favoritado
	 * @param bancoDados
	 * @param numero
	 */
	public void salvarFavorito(SQLiteDatabase bancoDados,int numero){
		String query = "INSERT or replace INTO "+TABLE_FAVORITOS+" ("+COLUMN_IDORACAO+") VALUES ("+numero+");";
		bancoDados.execSQL(query);
	}
	/**
	 * Busca o favorito por id de oracao retornando se existe eu nao
	 * @param numero
	 * @return
	 */
	public boolean buscaFavoritoPorIdOracao(SQLiteDatabase bancoDados,int numero){
		cursor = bancoDados.query(TABLE_FAVORITOS, new String[]{COLUMN_IDORACAO},"IDORACAO = "+numero  ,null,null,null,null);
		return cursor.getCount()>0;
	}
	
	/**
	 * Busca o favorito por id de oracao retornando se existe eu nao
	 * @param numero
	 * @return
	 */
	public List<Integer> buscaTodosFavoritos(SQLiteDatabase bancoDados){
		List<Integer> registrosFavoritos = new ArrayList<Integer>();
		cursor = bancoDados.query(TABLE_FAVORITOS, new String[]{COLUMN_IDORACAO},null,null,null,null,null);
		while(cursor.moveToNext()){
			registrosFavoritos.add(cursor.getInt(cursor.getColumnIndex(COLUMN_IDORACAO)));
		}
		return registrosFavoritos;
	}
	
	
	public boolean deletaFavoritoPorIdOracao(SQLiteDatabase bancoDados,int numero){
		return bancoDados.delete(TABLE_FAVORITOS, "IDORACAO = "+numero ,null)>0;
	}
	
	/**
	 * Metodo Cria o conteudo do banco de dados apartir das linas do arquivo de SQL 	
	 * @param bd
	 * @param context
	 */
	public void criaConteudo(final SQLiteDatabase openDB) {
		try {
			byFile(R.raw.basedb,openDB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Metodo Cria o conteudo do banco de dados apartir das linas do arquivo de SQL com InputStrem	
	 * @param InputStream
	 * @param SQLiteDatabase
	 */
	public void criaConteudo(InputStream input,final SQLiteDatabase openDB) {
		try {
			byFile(input,openDB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    protected void byFile(int fileID, SQLiteDatabase bd) throws IOException {
        StringBuilder sql = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(contextStatic.getResources().openRawResource(fileID)));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0) {
                sql.append(line);
                if (line.endsWith(";")) {
                    bd.execSQL(sql.toString());
                    sql.delete(0, sql.length());
                }
            }
        }
    }
    protected void byFile(InputStream input, SQLiteDatabase bd) throws IOException {
        StringBuilder sql = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0) {
                sql.append(line);
                if (line.endsWith(";")) {
                    bd.execSQL(sql.toString());
                    sql.delete(0, sql.length());
                }
            }
        }
    }
	/**
	 * Cria o banco de dados se não existe
	 * @param bancoDados
	 */
	@Override
	public void onCreate(SQLiteDatabase bancoDados) {
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_GRUPO + " TEXT, " + COLUMN_IDORACAO + " INTEGER);";
		bancoDados.execSQL(sql);
		String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_ORACAO + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_ORACAO + " TEXT, " + COLUMN_IDORACAO + " INTEGER);";
		bancoDados.execSQL(sql2);
		String sql3 = "CREATE TABLE IF NOT EXISTS " + TABLE_GRUPO + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITULO + " TEXT, "+COLUMN_IDGRUPO + " INTEGER);";
		bancoDados.execSQL(sql3);
		String sql4 = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITOS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_IDORACAO + " INTEGER);";
		bancoDados.execSQL(sql4);
		bancoDados.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase bancoDados, int oldVersion, int newVersion) {
		bancoDados.execSQL(SCRIPT_DELECAO_TABELA_GRUPO);
		bancoDados.execSQL(SCRIPT_DELECAO_TABELA_ORACAO);
		bancoDados.execSQL(SCRIPT_DELECAO_TABELA_TITULO);
		onCreate(bancoDados);
	}
	
	public SQLiteDatabase openDB(){
		try{
			bancoDados = contextStatic.openOrCreateDatabase(PersistenceDao.DATABASE_NAME, Context.MODE_WORLD_READABLE, null);
		}catch (Exception e){
		}
		return bancoDados;
	}	
    
	public SQLiteDatabase openDB(Context context){
		try{
			bancoDados = context.openOrCreateDatabase(PersistenceDao.DATABASE_NAME, Context.MODE_WORLD_READABLE, null);
		}catch (Exception e){
		}
		return bancoDados;
	}
}
