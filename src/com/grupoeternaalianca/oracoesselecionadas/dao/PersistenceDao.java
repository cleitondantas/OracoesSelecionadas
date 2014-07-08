package com.grupoeternaalianca.oracoesselecionadas.dao;

import java.util.ArrayList;
import java.util.List;

import com.grupoeternaalianca.oracoesselecionadas.vo.TituloVO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersistenceDao{
	private static final String DATABASE_NAME = "ORACOES_SELECIONADAS_DB";
	private static final String TABLE_NOTES = "TITULOS";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_TITLE = "TITLE";
	private static final String COLUMN_NOTES_KEY = "KEY";
	private Cursor cursor;
	
	public void createDataBase(SQLiteDatabase bancoDados){
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_NOTES_KEY + " TEXT);";
			bancoDados.execSQL(sql);
	}
	
	public List<TituloVO> buscaTitulos(){
			ArrayList<TituloVO> tituloOracoes = new ArrayList<TituloVO>();
		return tituloOracoes;
	}
	
}
