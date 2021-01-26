package com.programa.calculadoravlsm.Database.NodoDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.programa.calculadoravlsm.Database.CalculadoraDBHelper;
import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosContract;

import java.util.ArrayList;
import java.util.List;

public class NodosDatabaseHelper {
	private CalculadoraDBHelper dbHelper;
	private SQLiteDatabase tmpDatabase;
	private Context context;

	public NodosDatabaseHelper(Context _context){
		this.context = _context;
		dbHelper  = new CalculadoraDBHelper(context);
		comprobarTabla();
	}

	private void comprobarTabla(){
		tmpDatabase = dbHelper.getWritableDatabase();
		tmpDatabase.execSQL(NodosContract.SQL_CREATE_ENTRIES);
	}

	public void insertarNodo(String _idProyecto,String _descripcion, String _cantidadNodos){//,String _ipInicial, String _ipFinal,String _mascara){
		tmpDatabase = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(NodosContract.COLUMN_ID_PROYECTO,_idProyecto);
		contentValues.put(NodosContract.COLUMN_DESCRIPCIÓN,_descripcion);
		contentValues.put(NodosContract.COLUMN_CANTIDAD,_cantidadNodos);

		/*
		contentValues.put(NodosContract.COLUMN_IPINICIAL,_ipInicial);
		contentValues.put(NodosContract.COLUMN_IPFINAL,_ipFinal);
		contentValues.put(NodosContract.COLUMN_MASCARA,_mascara);
		*/

		long newRowId = tmpDatabase.insert(NodosContract.TABLE_NAME,null,contentValues);
		if(newRowId == -1){
			Toast.makeText(this.context,"error al insertar dato",Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this.context,"Nodo insertado",Toast.LENGTH_LONG).show();
		}
	}

	public void editarElemento(String idNodo,String descripcion,String cantidad){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(NodosContract.COLUMN_DESCRIPCIÓN,descripcion);
		contentValues.put(NodosContract.COLUMN_CANTIDAD,cantidad);
		String whereClause = NodosContract._ID + " LIKE ?";
		String[] whereArgs = {idNodo};

		int x = db.update(NodosContract.TABLE_NAME,contentValues,
				whereClause,
				whereArgs
		);
		Toast.makeText(this.context, x + " Fueron Actualizadas",Toast.LENGTH_LONG).show();
	}

	public List<NodoDataHolder> getAllPerProyect(String ProyectId){
		List<NodoDataHolder> out = new ArrayList<>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		String[] proyection = {
				NodosContract._ID,
				NodosContract.COLUMN_ID_PROYECTO,
				NodosContract.COLUMN_DESCRIPCIÓN,
				NodosContract.COLUMN_CANTIDAD
		};
		String selection = NodosContract.COLUMN_ID_PROYECTO + "=?";
		String[] selectionArgs= {ProyectId};

		// String sortOrder = NodosContract.COLUMN_CANTIDAD + " ASC";

		Cursor cursor = db.query(
				NodosContract.TABLE_NAME,	// The table to query
				proyection,	// The array of columns to return (pass null to get all)
				selection,	// The columns for the WHERE clause
				selectionArgs,	// The values for the WHERE clause
				null,	// don't group the rows
				null,	// don't filter by row groups
				null	// The sort order
		);
		if(cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract._ID));
				String idProyecto = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract.COLUMN_ID_PROYECTO));
				String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract.COLUMN_DESCRIPCIÓN));
				String cantidad = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract.COLUMN_CANTIDAD));
				out.add(new NodoDataHolder(id, idProyecto, descripcion, cantidad));
			}
		}
		cursor.close();
		return out;
	}


	public NodoDataHolder getElementoById(String NodoId){
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		String[] proyection = {
				NodosContract._ID,
				NodosContract.COLUMN_ID_PROYECTO,
				NodosContract.COLUMN_DESCRIPCIÓN,
				NodosContract.COLUMN_CANTIDAD
		};
		String selection = NodosContract._ID + "=?";
		String[] selectionArgs= {NodoId};

		// String sortOrder = NodosContract.COLUMN_CANTIDAD + " ASC";

		Cursor cursor = db.query(
				NodosContract.TABLE_NAME,	// The table to query
				proyection,	// The array of columns to return (pass null to get all)
				selection,	// The columns for the WHERE clause
				selectionArgs,	// The values for the WHERE clause
				null,	// don't group the rows
				null,	// don't filter by row groups
				null	// The sort order
		);

		String id ="";
		String idProyecto = "";
		String descripcion = "";
		String cantidad= "";
		if(cursor.getCount() == 1){
			cursor.moveToFirst();
			id = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract._ID));
			idProyecto = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract.COLUMN_ID_PROYECTO));
			descripcion = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract.COLUMN_DESCRIPCIÓN));
			cantidad = cursor.getString(cursor.getColumnIndexOrThrow(NodosContract.COLUMN_CANTIDAD));
		}
		cursor.close();
		return new NodoDataHolder(id, idProyecto, descripcion, cantidad);
	}

	public String eliminarNodo(String elementoId){
		SQLiteDatabase db = dbHelper.getWritableDatabase();/*
		db.execSQL("DELETE FROM "+ ProyectosContract.TABLE_NAME +
				" WHERE " + ProyectosContract.COLUMN_NOMBRE + " = " + nombre);*/
		String args[] = {elementoId};

		int x = db.delete(NodosContract.TABLE_NAME,NodosContract._ID + "=?",args);
		return String.valueOf(x);
	}

	public String eliminarNodosDeTabla(String ProyectoId){
		SQLiteDatabase db = dbHelper.getWritableDatabase();/*
		db.execSQL("DELETE FROM "+ ProyectosContract.TABLE_NAME +
				" WHERE " + ProyectosContract.COLUMN_NOMBRE + " = " + nombre);*/
		String args[] = {ProyectoId};

		int x = db.delete(NodosContract.TABLE_NAME,NodosContract.COLUMN_ID_PROYECTO + "=?",args);
		return String.valueOf(x);
	}
}
