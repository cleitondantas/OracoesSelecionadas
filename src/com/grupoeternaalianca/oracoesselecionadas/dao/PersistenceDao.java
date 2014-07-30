package com.grupoeternaalianca.oracoesselecionadas.dao;

import java.io.BufferedReader;
import java.io.IOException;
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
import android.support.v7.app.ActionBarActivity;


public class PersistenceDao extends ActionBarActivity{
	private static final String COLUMN_ID = "ID";
	private static final String TABLE_NOTES = "TITULOS";
	private static final String TABLE_ORACAO = "ORACAO";
	private static final String TABLE_GRUPO = "GRUPO";
	
	private static final String COLUMN_IDGRUPO = "IDGRUPO";
	private static final String COLUMN_TITULO = "TITULO";
	private static final String COLUMN_TITLE = "TITULO";
	private static final String COLUMN_GRUPO = "GRUPO";
	private static final String COLUMN_ORACAO = "ORACAO";
	private static final String COLUMN_IDORACAO = "IDORACAO";
	private Cursor cursor;
	
	public void openOrCreateDB(SQLiteDatabase bancoDados){
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_GRUPO + " TEXT, " + COLUMN_IDORACAO + " INTEGER);";
			bancoDados.execSQL(sql);
			String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_ORACAO + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_ORACAO + " TEXT, " + COLUMN_IDORACAO + " INTEGER);";
			bancoDados.execSQL(sql2);
			String sql3 = "CREATE TABLE IF NOT EXISTS " + TABLE_GRUPO + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITULO + " TEXT, "+COLUMN_IDGRUPO + " INTEGER);";
			bancoDados.execSQL(sql3);
			bancoDados.close();
	}
	
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
			cursor = bancoDados.query(TABLE_NOTES, new String[]{COLUMN_ID,COLUMN_TITLE,COLUMN_GRUPO}, null,null,null,null,null);
			TituloVO titulo =null;
			while(cursor.moveToNext()){
				titulo = new TituloVO();
				titulo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
				titulo.setCategoria(cursor.getString(cursor.getColumnIndex(COLUMN_GRUPO)));
				titulo.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
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
			}
			bancoDados.close();
		return oracao;
	}
	/**
	 * Metodo Cria o conteudo do banco de dados apartir das linas do arquivo de SQL 	
	 * @param bd
	 * @param context
	 */
	public void criaConteudo(SQLiteDatabase bd,Context context){
		try {
			byFile(R.raw.basedb,bd,context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    protected void byFile(int fileID, SQLiteDatabase bd, Context context) throws IOException {
        StringBuilder sql = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(fileID)));
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
}
