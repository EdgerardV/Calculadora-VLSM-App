package com.programa.calculadoravlsm.Database.ProyectosDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.programa.calculadoravlsm.Database.CalculadoraDBHelper;
import com.programa.calculadoravlsm.Database.NodoDatabase.NodosContract;
import com.programa.calculadoravlsm.Database.NodoDatabase.NodosDatabaseHelper;

import java.util.ArrayList;

public class ProyectosDatabaseHelper {
	private CalculadoraDBHelper dbHelper;
	private Context context;

	public ProyectosDatabaseHelper(Context _context){
		this.context = _context;
		dbHelper  = new CalculadoraDBHelper(context);
		comprobarTabla();
	}

	public void insertNewData(String nombre,String usuario, String ipInicial, String mascara){
		SQLiteDatabase tmpDB= dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(ProyectosContract.COLUMN_NOMBRE,nombre);
		contentValues.put(ProyectosContract.COLUMN_USUARIO,usuario);
		contentValues.put(ProyectosContract.COLUMN_IPINICIAL,ipInicial);
		contentValues.put(ProyectosContract.COLUMN_MASCARA,mascara);

		long newRowId = tmpDB.insert(ProyectosContract.TABLE_NAME,null,contentValues);
		if(newRowId == -1){
			Toast.makeText(this.context,"error al insertar dato",Toast.LENGTH_LONG).show();
		}
	}

	public ArrayList<ProyectosDataHolder> getAllDataOf(String usuario){
		ArrayList<ProyectosDataHolder> datos = new ArrayList<>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		/*String[] proyection = {
				ProyectosContract._ID,
				ProyectosContract.COLUMN_NOMBRE,
				ProyectosContract.COLUMN_USUARIO,
				ProyectosContract.COLUMN_IPINICIAL,
				ProyectosContract.COLUMN_MASCARA
		};*/
		// Filter results WHERE "title" = 'My Title'
		String selection = ProyectosContract.COLUMN_USUARIO + " = ?";
		String[] selectionArgs = { usuario };
/*
		// How you want the results sorted in the resulting Cursor
		String sortOrder =
				ProyectosContract.ProyectosContractEntry._ID + " DESC";
*/

		Cursor cursor = db.query(
				ProyectosContract.TABLE_NAME,   // The table to query
				null,             // The array of columns to return (pass null to get all)
				selection,              // The columns for the WHERE clause
				selectionArgs,          // The values for the WHERE clause
				null,                   // don't group the rows
				null,                   // don't filter by row groups
				null               // The sort order
		);

		while(cursor.moveToNext()){
			String itemId = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract._ID));
			String itemNombre = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_NOMBRE));
			String itemUsuario = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_USUARIO));
			String itemIpInicial = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_IPINICIAL));
			String itemMascara = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_MASCARA));
			datos.add(new ProyectosDataHolder(itemId,itemNombre,itemUsuario,itemIpInicial,itemMascara));
		}
		cursor.close();
		return datos;
	}

	public int eliminarElemento(String elementoId){
		NodosDatabaseHelper dbNodos = new NodosDatabaseHelper(context);
		dbNodos.eliminarNodosDeTabla(elementoId);

		SQLiteDatabase db = dbHelper.getWritableDatabase();/*
		db.execSQL("DELETE FROM "+ ProyectosContract.TABLE_NAME +
				" WHERE " + ProyectosContract.COLUMN_NOMBRE + " = " + nombre);*/
		String args[] = {elementoId};

		int x = db.delete(ProyectosContract.TABLE_NAME,ProyectosContract._ID + "=?",args);
		return x;
	}

	public ProyectosDataHolder getElemento(String nombreElemento){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] proyection = {
				ProyectosContract._ID,
				ProyectosContract.COLUMN_NOMBRE,
				ProyectosContract.COLUMN_USUARIO,
				ProyectosContract.COLUMN_IPINICIAL,
				ProyectosContract.COLUMN_MASCARA
		};

		String selection = ProyectosContract._ID + "=?";
		String[] selectionArgs= {nombreElemento};

		Cursor cursor = db.query(
				ProyectosContract.TABLE_NAME,   // The table to query
				proyection,             // The array of columns to return (pass null to get all)
				selection,              // The columns for the WHERE clause
				selectionArgs,          // The values for the WHERE clause
				null,                   // don't group the rows
				null,                   // don't filter by row groups
				null               // The sort order
		);
		String id = "";
		String nombre = "";
		String usuario = "";
		String ipInicial = "";
		String mascara = "";
		if(cursor.getCount()==1){
			cursor.moveToFirst();
			id = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract._ID));
			nombre = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_NOMBRE));
			usuario = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_USUARIO));
			ipInicial = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_IPINICIAL));
			mascara = cursor.getString(cursor.getColumnIndexOrThrow(ProyectosContract.COLUMN_MASCARA));
		}
		cursor.close();
		return new ProyectosDataHolder(id,nombre,usuario,ipInicial,mascara);
	}

	public void editarElemento(String id,String newNombre, String newIpinicial, String newMask){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(ProyectosContract.COLUMN_NOMBRE,newNombre);
		contentValues.put(ProyectosContract.COLUMN_IPINICIAL,newIpinicial);
		contentValues.put(ProyectosContract.COLUMN_MASCARA,newMask);
		String whereClause = ProyectosContract._ID + " LIKE ?";
		String[] whereArgs = {id};
		int x = db.update(ProyectosContract.TABLE_NAME,contentValues,
				whereClause,
				whereArgs
		);
		Toast.makeText(this.context, x + " Fueron Actualizadas",Toast.LENGTH_LONG).show();
	}
/*
	public void eliminarTabla(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("DROP TABLE " + ProyectosContract.TABLE_NAME);
		Log.e("pueba","se eliminó " + ProyectosContract.TABLE_NAME);
	}
*/
	public void comprobarTabla(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(ProyectosContract.SQL_CREATE_ENTRIES);
	}
}
