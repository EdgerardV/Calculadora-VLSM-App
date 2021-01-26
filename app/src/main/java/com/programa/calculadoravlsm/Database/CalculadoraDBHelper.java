package com.programa.calculadoravlsm.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.programa.calculadoravlsm.Database.NodoDatabase.NodosContract;
import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosContract;

public class CalculadoraDBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Calculadora.db";

	public CalculadoraDBHelper(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(ProyectosContract.SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		String entries = ProyectosContract.SQL_DELETE_ENTRIES +
				NodosContract.SQL_CREATE_ENTRIES;
		sqLiteDatabase.execSQL(entries);
		onCreate(sqLiteDatabase);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}
