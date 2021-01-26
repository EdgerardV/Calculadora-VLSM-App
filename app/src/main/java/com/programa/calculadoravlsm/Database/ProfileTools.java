package com.programa.calculadoravlsm.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.programa.calculadoravlsm.R;

public class ProfileTools {
	SharedPreferences sharedPreferences;
	Context context;
	private String usuario;
	public ProfileTools(Context context, String prefName){
		this.context = context;
		sharedPreferences = context.getSharedPreferences(prefName,Context.MODE_PRIVATE);
	}

	public void escribir(String key, String value) throws Exception{
		String msj = key + " se ha registrado";
		if(validateUser(key)){
			throw new Exception();
		}else{
			SharedPreferences.Editor editor = sharedPreferences.edit();
			this.usuario = key;
			editor.putString(key,value);
			editor.commit();
			Toast.makeText(context,msj, Toast.LENGTH_LONG).show();
		}
	}

	public boolean validateUser(String _usuario){
		String tmp = "nel";
		try{
			tmp = sharedPreferences.getString(_usuario,"nel");
		}catch (ClassCastException ex){
			Toast.makeText(context,"Existe otro valor \n" + ex.getMessage(),Toast.LENGTH_SHORT);
		}
		if(tmp.equals("nel")){
			return false;
		}else{
			return true;
		}
	}

	public boolean validateCredentials(String _usuario,String _contraseña){
		this.usuario = _usuario;
		String tmp = "nel";
		boolean result = false;
		try{
			tmp = sharedPreferences.getString(_usuario,"nel");
		}catch (ClassCastException ex){
			Toast.makeText(context,"Existe otro valor \n" + ex.getMessage(),Toast.LENGTH_SHORT);
		}
		if(!tmp.equals("nel")){
			if(tmp.equals(_contraseña)){
				result =  true;
			}
		}
		return result;
	}

	public void setUsuarioLoged(){
		SharedPreferences usuarioLoged = context.getSharedPreferences(context.getString(R.string.usuarioLogedFile),Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = usuarioLoged.edit();
		editor.putString(context.getString(R.string.usuariologeadoData),this.usuario);
		editor.commit();
	}

	public static String getUsuarioLoged(Context context){
		SharedPreferences usuarioLoged = context.getSharedPreferences(context.getString(R.string.usuarioLogedFile),Context.MODE_PRIVATE);
		return usuarioLoged.getString(context.getString(R.string.usuariologeadoData),"nel");
	}

	public static void cerrarSesion(Context context){
		SharedPreferences usuarioLoged = context.getSharedPreferences(context.getString(R.string.usuarioLogedFile),Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = usuarioLoged.edit();
		editor.remove(context.getString(R.string.usuariologeadoData));
		editor.commit();
	}

	public static void eliminarUsuario(Context context){
		SharedPreferences usuariosLista = context.getSharedPreferences(context.getString(R.string.datos_usuarios),Context.MODE_PRIVATE);
		SharedPreferences usuarioLogged = context.getSharedPreferences(context.getString(R.string.usuarioLogedFile),Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = usuariosLista.edit();
		editor.remove(usuarioLogged.getString(context.getString(R.string.usuariologeadoData),null));
		editor.commit();
	}
}
