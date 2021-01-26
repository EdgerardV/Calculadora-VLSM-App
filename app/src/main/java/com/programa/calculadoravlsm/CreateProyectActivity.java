package com.programa.calculadoravlsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosDatabaseHelper;
import com.programa.calculadoravlsm.Database.ProfileTools;

import static android.widget.Toast.LENGTH_LONG;

public class CreateProyectActivity extends AppCompatActivity {
	private EditText proyectoNombre_Editxt, ipInicial_Editxt, mascara_Editxt;
	private Button aceptar_Btn;
	private ProyectosDatabaseHelper proyectosData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_proyect);
		inicializar();
		setUpButton();
	}

	private void inicializar(){
		proyectoNombre_Editxt = findViewById(R.id.editText_ProyectoNombre_createProyect);
		ipInicial_Editxt = findViewById(R.id.editText_ipInicial_createProyect);
		mascara_Editxt = findViewById(R.id.editText_Mascara_createProyect);
		aceptar_Btn = findViewById(R.id.button_Aceptar_createProyect);

		proyectosData = new ProyectosDatabaseHelper(this);
	}

	private void setUpButton(){
		aceptar_Btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String proyecto = proyectoNombre_Editxt.getText().toString();
				String usuario = ProfileTools.getUsuarioLoged(CreateProyectActivity.this);
				String ipinicial = ipInicial_Editxt.getText().toString();
				String mascara = mascara_Editxt.getText().toString();
				if(!proyecto.isEmpty() && !ipinicial.isEmpty() && !mascara.isEmpty()){
					proyectosData.insertNewData(proyecto,usuario,ipinicial,mascara);
					CreateProyectActivity.this.finish();
				}else{
					Toast.makeText(CreateProyectActivity.this,"No se pudo insertar", LENGTH_LONG).show();
				}
			}
		});
	}
}