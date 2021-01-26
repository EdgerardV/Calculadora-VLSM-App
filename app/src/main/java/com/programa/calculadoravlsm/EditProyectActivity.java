package com.programa.calculadoravlsm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosDataHolder;
import com.programa.calculadoravlsm.Database.ProyectosDatabase.ProyectosDatabaseHelper;

import static android.widget.Toast.LENGTH_LONG;

public class EditProyectActivity extends AppCompatActivity {
	private TextView proyectoId_TxtView;
	private EditText proyectoNombre_Editxt, ipInicial_Editxt, mascara_Editxt;
	private Button guardar_Btn, eliminar_Btn;
	private ProyectosDatabaseHelper proyectosData;
	private String viejoidProyecto;
	private Bundle dataBundle;
	private	ProyectosDataHolder ProyectTmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_proyect);
		inicializar();
		setUpButton();
	}

	private void inicializar(){
		proyectoId_TxtView = findViewById(R.id.textView_idProyecto_EditProyect);
		proyectoNombre_Editxt = findViewById(R.id.editText_ProyectoNombre_editProyect);
		ipInicial_Editxt = findViewById(R.id.editText_ipInicial_editProyect);
		mascara_Editxt = findViewById(R.id.editText_Mascara_editProyect);
		guardar_Btn = findViewById(R.id.button_Guardar_editProyect);
		eliminar_Btn = findViewById(R.id.button_eliminarProyecto_editProyect);

		proyectosData = new ProyectosDatabaseHelper(this);
		dataBundle= getIntent().getExtras();
		viejoidProyecto = dataBundle.getString(getString(R.string.iddelProyectoAEditar));
		ProyectTmp = proyectosData.getElemento(viejoidProyecto);

		proyectoId_TxtView.setText(ProyectTmp.getId());
		proyectoNombre_Editxt.setText(ProyectTmp.getNombre());
		ipInicial_Editxt.setText(ProyectTmp.getIpInicial());
		mascara_Editxt.setText(ProyectTmp.getMascara());
	}

	private void setUpButton(){
		guardar_Btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String proyecto = proyectoNombre_Editxt.getText().toString();
				String ipinicial = ipInicial_Editxt.getText().toString();
				String mascara = mascara_Editxt.getText().toString();
				if(!proyecto.isEmpty() && !ipinicial.isEmpty() && !mascara.isEmpty()){
					proyectosData.editarElemento(viejoidProyecto,proyecto,ipinicial,mascara);
					Toast.makeText(EditProyectActivity.this,"Dato insertado", LENGTH_LONG).show();
				}else{
					Toast.makeText(EditProyectActivity.this,"No se pudo insertar", LENGTH_LONG).show();
				}
				EditProyectActivity.this.finish();
			}
		});

		eliminar_Btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder dialongclick = new AlertDialog.Builder(EditProyectActivity.this);
				dialongclick.setTitle("Eliminar Elemento?");
				dialongclick.setMessage(proyectoNombre_Editxt.getText());

				dialongclick.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						int x = proyectosData.eliminarElemento(viejoidProyecto);
						EditProyectActivity.this.finish();
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