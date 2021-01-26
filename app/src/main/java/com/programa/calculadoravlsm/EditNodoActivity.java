package com.programa.calculadoravlsm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.programa.calculadoravlsm.Database.NodoDatabase.NodoDataHolder;
import com.programa.calculadoravlsm.Database.NodoDatabase.NodosDatabaseHelper;

public class EditNodoActivity extends AppCompatActivity {
	private String idNodo;
	private NodoDataHolder nodo;

	private TextView idNodo_TxtView;
	private EditText descripcion_Editxt,cantidad_Editxt;
	private Button guardar_Btn,eliminar_Btn;

	private NodosDatabaseHelper nodoData;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_nodo);
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.initialice();
		this.setOnClicks();
	}

	private void initialice(){
		this.nodoData = new NodosDatabaseHelper(this);
		this.bundle = this.getIntent().getExtras();
		this.idNodo = bundle.getString(getString(R.string.iddelNodoAEditar));
		nodo = nodoData.getElementoById(idNodo);

		this.idNodo_TxtView = findViewById(R.id.textView_idNodo_NodoEdit);
		this.idNodo_TxtView.setText(nodo.getId());

		this.descripcion_Editxt = findViewById(R.id.editText_Descripcion_NodoEdit);
		this.descripcion_Editxt.setText(nodo.getDescripcion());

		this.cantidad_Editxt = findViewById(R.id.editText_Cantidad_NodoEdit);
		this.cantidad_Editxt.setText(Integer.toString(nodo.getCantidadNodos()));

		this.guardar_Btn = findViewById(R.id.button_Guardar_NodoEdit);
		this.eliminar_Btn = findViewById(R.id.button_Eliminar_NodoEdit);
	}

	private void setOnClicks(){
		this.guardar_Btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String descripcion = descripcion_Editxt.getText().toString();
				String cantidad = cantidad_Editxt.getText().toString();

				if(!descripcion.isEmpty() && !cantidad.isEmpty()){
					nodoData.editarElemento(idNodo,descripcion,cantidad);
				}else{
					Toast.makeText(EditNodoActivity.this,"Rellene los campos", Toast.LENGTH_LONG);
				}
				EditNodoActivity.this.finish();
			}
		});
		this.eliminar_Btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder dialongclick = new AlertDialog.Builder(EditNodoActivity.this);
				dialongclick.setTitle("Eliminar Elemento?");
				dialongclick.setMessage(nodo.getDescripcion());

				dialongclick.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						nodoData.eliminarNodo(nodo.getId());
						EditNodoActivity.this.finish();
					}
				});
				dialongclick.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {}
				});
				dialongclick.show();
			}
		});
	}
}