package com.programa.calculadoravlsm.Database.ProyectosDatabase;

import android.provider.BaseColumns;

public class ProyectosContract implements BaseColumns {
	private ProyectosContract() {}

		public static final String TABLE_NAME = "ProyectosTable";
		public static final String COLUMN_NOMBRE = "Nombre";
		public static final String COLUMN_USUARIO = "Usuario";
		public static final String COLUMN_IPINICIAL = "IpInicial";
		public static final String COLUMN_MASCARA = "Mascara";

	public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_NOMBRE + " TEXT, " +
					COLUMN_USUARIO + " TEXT, " +
					COLUMN_IPINICIAL + " TEXT, " +
					COLUMN_MASCARA + " TEXT) ";

	public static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + TABLE_NAME;
}
