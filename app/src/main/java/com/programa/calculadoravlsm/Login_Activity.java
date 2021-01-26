package com.programa.calculadoravlsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.programa.calculadoravlsm.Database.ProfileTools;

public class Login_Activity extends AppCompatActivity {
	private String usuario;
	private EditText usuario_Editxt, contra_Editxt;
	private Button ingresar_btn, registrarse_btn;
	private ProfileTools profileTools;
//	private ProyectosDatabaseHelper dataHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_);

		ingresar_btn = findViewById(R.id.button_Ingresar_login);
		registrarse_btn = findViewById(R.id.button_Registrar_Login);
		usuario_Editxt = findViewById(R.id.editText_Usuario_Login);
		contra_Editxt = findViewById(R.id.editText_Password_Login);

		//sharedPreferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
		profileTools = new ProfileTools(this,getString(R.string.datos_usuarios));

		ingresar_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				iniciarSesion();
			}
		});

		registrarse_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gotoRegistro();
			}
		});

//		dataHelper = new ProyectosDatabaseHelper(this);
//		dataHelper.eliminarTabla();
//		dataHelper.comprobarTabla();
	}

	private void gotoRegistro(){
		Intent intent = new Intent(Login_Activity.this,RegisterActivity.class);
		startActivity(intent);
	}


	private void iniciarSesion(){

//		ProgressDialog dialog = ProgressDialog.show(Login_Activity.this,"","Cargando",true);
//		dialog.show();
		String userIn = usuario_Editxt.getText().toString();
		String pssIn = contra_Editxt.getText().toString();
		if(!userIn.isEmpty() && !pssIn.isEmpty()){
			if(profileTools.validateCredentials(userIn,pssIn)) {
				profileTools.setUsuarioLoged();
				Intent intent = new Intent(Login_Activity.this, ProyectsActivity.class);

				startActivity(intent);
			}else{
				Toast.makeText(this,"No se pudo iniciar sesion", Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(this,"No se pudo iniciar sesion", Toast.LENGTH_LONG).show();
		}
	}
}