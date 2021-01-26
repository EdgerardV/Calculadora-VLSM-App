package com.programa.calculadoravlsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosDataHolder;
import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosDatabaseHelper;
import com.programa.calculadoravlsm.Database.ProfileTools;

import java.util.ArrayList;

public class ProyectsActivity extends AppCompatActivity {
	private ListView proyectos_listview;
	private String usuarioLogeado;
	private ProyectosDatabaseHelper proyectosDB;
	private ArrayList<ProyectosDataHolder> tablaDeProyectos;
	private ArrayList<String> lista;
	private ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proyects);
		usuarioLogeado = ProfileTools.getUsuarioLoged(this);
		proyectosDB = new ProyectosDatabaseHelper(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		tablaDeProyectos = proyectosDB.getAllDataOf(usuarioLogeado);
		setUpListView();
		setUpListViewActions();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.proyects_menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();

		switch(id){
			case R.id.crearProyecto_menuitem_proyectos:
				Intent toCreateProyect = new Intent(ProyectsActivity.this,CreateProyectActivity.class);
				startActivity(toCreateProyect);
			break;
			case R.id.borrarUsuario_menuitem_proyectos:
				borraUsuario();
			break;
			case R.id.cerrarsesion_menuitem_proyectos:
				ProfileTools.cerrarSesion(this);
				Intent toLoginIntent = new Intent(ProyectsActivity.this,Login_Activity.class);
				startActivity(toLoginIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setUpListView(){
		proyectos_listview =findViewById(R.id.ListView_Proyectos);
		this.setLista();
		arrayAdapter = new ArrayAdapter<>(ProyectsActivity.this,android.R.layout.simple_list_item_1,this.lista);
		proyectos_listview.setAdapter(arrayAdapter);
	}

	private void setLista(){
		lista = new ArrayList<>();
		for (ProyectosDataHolder a : this.tablaDeProyectos) {
			lista.add(a.getNombre());
		}
	}

	private void setUpListViewActions(){
		proyectos_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
				Intent toEditProyect = new Intent(ProyectsActivity.this,EditProyectActivity.class);
				toEditProyect.putExtra(getString(R.string.iddelProyectoAEditar),tablaDeProyectos.get(position).getId());
				startActivity(toEditProyect);
				return true;
			}
		});
		proyectos_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				Intent toProyectView = new Intent(ProyectsActivity.this,ProyectViewActivity.class);
				toProyectView.putExtra(getString(R.string.iddelProyectoAEditar),tablaDeProyectos.get(position).getId());
				startActivity(toProyectView);
			}
		});
	}

	private void borraUsuario(){
		AlertDialog.Builder dialongclick = new AlertDialog.Builder(ProyectsActivity.this);
		dialongclick.setTitle("Eliminar Usuario?");
		dialongclick.setMessage(ProfileTools.getUsuarioLoged(this));

		dialongclick.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				ProfileTools.eliminarUsuario(ProyectsActivity.this);
				ProyectsActivity.this.finish();
			}
		});
		dialongclick.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {}
		});
		dialongclick.show();
	}
}