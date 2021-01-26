package com.programa.calculadoravlsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.programa.calculadoravlsm.Adapters.NodoAdapter;
import com.programa.calculadoravlsm.Database.NodoDatabase.NodoDataHolder;
import com.programa.calculadoravlsm.Database.NodoDatabase.NodosDatabaseHelper;
import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosDataHolder;
import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosDatabaseHelper;
import com.programa.calculadoravlsm.Herramientas.Calculadora;
import com.programa.calculadoravlsm.Herramientas.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class ProyectViewActivity extends AppCompatActivity {
	private String idRecibido;
	private TextView nombreProyecto_TextView, ipInicial_TextView, mascara_TextView;
	private EditText descripcion_Editxt, cantidadNodos_Editxt;
	private ImageButton agregar_btn;
	private ListView nodos_ListView;
	private ProyectosDatabaseHelper proyectosData;
	private NodosDatabaseHelper nodoData;
	private NodoAdapter nodoAdapter;
	private ProyectosDataHolder proyectoDataHolder;
	private List<NodoDataHolder> nodosList;
	private Calculadora calc;

	private Bundle dataBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proyect_view);
		//initialice();
		//setOnClicks();
	}

	@Override
	protected void onStart() {
		super.onStart();
		initialice();
		setOnClicks();
	}

	private void initialice(){
		nombreProyecto_TextView = findViewById(R.id.textView_NombreProyecto_ProyectView);
		ipInicial_TextView = findViewById(R.id.textView_ipInicial_ProyectView);
		mascara_TextView = findViewById(R.id.textView_Mascara_ProyectView);
		cantidadNodos_Editxt = findViewById(R.id.editText_CantidadNodos_ProyectView);
		descripcion_Editxt = findViewById(R.id.editText_Descripcion_ProyectView);
		agregar_btn = findViewById(R.id.imageButton_AgregarNodo);

		proyectosData = new ProyectosDatabaseHelper(this);
		dataBundle = this.getIntent().getExtras();
		idRecibido = dataBundle.getString(getString(R.string.iddelProyectoAEditar));
		proyectoDataHolder = proyectosData.getElemento(idRecibido);

		nombreProyecto_TextView.setText(proyectoDataHolder.getNombre());
		ipInicial_TextView.setText(proyectoDataHolder.getIpInicial());
		mascara_TextView.setText(proyectoDataHolder.getMascara());

		nodoData = new NodosDatabaseHelper(this);
		nodosList = nodoData.getAllPerProyect(proyectoDataHolder.getId());
/*
		for (NodoDataHolder a: nodosList) {
			Log.e("prueba","iniciando lista " + a.getDescripcion());
		}*/


		 calc = new Calculadora(StringHelper.getIp(proyectoDataHolder.getIpInicial()),
				 				Integer.parseInt(proyectoDataHolder.getMascara()), (ArrayList<NodoDataHolder>) nodosList);

		 calcularVLSM();
		nodos_ListView = findViewById(R.id.ListView_Nodos_ProyectView);
		nodoAdapter = new NodoAdapter(this,R.layout.list_item_nodo,nodosList);
		nodos_ListView.setAdapter(nodoAdapter);
	}

	private void setOnClicks(){
		agregar_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String nodoDescripcion = descripcion_Editxt.getText().toString();
				String cantidadNodos = cantidadNodos_Editxt.getText().toString();
				if(!nodoDescripcion.isEmpty() && !cantidadNodos.isEmpty()){
					nodoData.insertarNodo(proyectoDataHolder.getId(),nodoDescripcion,cantidadNodos);
					nuevaLista();
				}else{
					Toast.makeText(ProyectViewActivity.this,"Rellene los campos",Toast.LENGTH_SHORT);
				}
			}
		});
	}

	private void calcularVLSM(){
		this.nodosList = calc.calcularNodos();
	}

	private void nuevaLista(){
		List<NodoDataHolder> newList = nodoData.getAllPerProyect(proyectoDataHolder.getId());;
		nodosList.clear();
		nodosList.addAll(newList);
		calcularVLSM();
		nodoAdapter.notifyDataSetChanged();
	}
}