package com.grupoeternaalianca.oracoesselecionadas.dao;

import java.util.ArrayList;
import java.util.List;
import com.grupoeternaalianca.oracoesselecionadas.vo.TituloVO;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class PersistenceDao{
	private static final String COLUMN_ID = "ID";
	private static final String TABLE_NOTES = "TITULOS";
	private static final String COLUMN_TITLE = "TITULO";
	private static final String COLUMN_GRUPO = "GRUPO";
	private Cursor cursor;
	
	public void openOrCreateDB(SQLiteDatabase bancoDados){
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_GRUPO + " TEXT);";
			bancoDados.execSQL(sql);
			bancoDados.close();
	}
	
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
	
	public void registraNovoDado(SQLiteDatabase bancoDados){
			String sql2 = "INSERT or replace INTO " + TABLE_NOTES + " ("+COLUMN_TITLE+","+COLUMN_GRUPO+") VALUES ('Ave Maria','Intercessão');";
			bancoDados.execSQL(sql2);
			
			String sql3 = "INSERT or replace INTO " + TABLE_NOTES + " ("+COLUMN_TITLE+","+COLUMN_GRUPO+") VALUES ('Pai Nosso','Intercessão');";
			bancoDados.execSQL(sql3);
			
			bancoDados.close();
	}
	
}
