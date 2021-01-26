package com.programa.calculadoravlsm.Database.NodoDatabase;

import android.provider.BaseColumns;

public class NodosContract implements BaseColumns {
	private NodosContract(){}

	public static final String TABLE_NAME = "NodosTable";
	public static final String COLUMN_ID_PROYECTO = "idProyecto";
	public static final String COLUMN_DESCRIPCIÓN = "Descripcion";
	public static final String COLUMN_CANTIDAD = "Cantidad";

	/*
	public static final String COLUMN_IPINICIAL = "IpInicial";
	public static final String COLUMN_IPFINAL = "IpFinal";
	public static final String COLUMN_MASCARA = "Mascara";
	*/

	public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					COLUMN_ID_PROYECTO + " TEXT, " +
					COLUMN_DESCRIPCIÓN + " TEXT, " +
					COLUMN_CANTIDAD + " TEXT) ";
					//COLUMN_IPINICIAL + " TEXT, " +
					//COLUMN_IPFINAL + " TEXT, " +
					//COLUMN_MASCARA + " TEXT) ";

	public static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + TABLE_NAME;
}
