package com.programa.calculadoravlsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.programa.calculadoravlsm.Database.ProfileTools;

public class RegisterActivity extends AppCompatActivity {
	private EditText usuario_edtitext, contra_editext;
	private Button registrar_btn;
	private ProfileTools profileTools;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		usuario_edtitext = findViewById(R.id.editText_Usuario_Register);
		contra_editext = findViewById(R.id.editText_Password_Register);
		registrar_btn = findViewById(R.id.button_completar_Register);

		profileTools = new ProfileTools(this,getString(R.string.datos_usuarios));

		registrar_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				registrarDatos();
			}
		});
	}
	private void registrarDatos(){
		String usuarioIn = usuario_edtitext.getText().toString();
		String passIn = contra_editext.getText().toString();
		boolean esepcion = false;
		if(!usuarioIn.isEmpty() && !passIn.isEmpty()){
			try{
				this.profileTools.escribir(usuarioIn,passIn);
			}catch (Exception ex){
				Toast.makeText(this,"usuario ya registrado",Toast.LENGTH_SHORT).show();
				esepcion = true;
			}
			if(!esepcion) {
				this.profileTools.setUsuarioLoged();
				Intent intent = new Intent(RegisterActivity.this, ProyectsActivity.class);
				startActivity(intent);
				Toast.makeText(this,"ya estuvo", Toast.LENGTH_LONG).show();
			}

		}else{
			Toast.makeText(this,"no se pudo registrar", Toast.LENGTH_LONG).show();
		}
	}
}