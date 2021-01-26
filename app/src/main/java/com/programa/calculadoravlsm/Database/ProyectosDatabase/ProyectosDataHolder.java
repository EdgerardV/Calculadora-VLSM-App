package com.programa.calculadoravlsm.Database.ProyectosDatabase;

public class ProyectosDataHolder {
	private String id;
	private String nombre;
	private String usuario;
	private String ipInicial;
	private String mascara;

	public ProyectosDataHolder(String _id, String _nombre, String _usuario,String _ipInicial, String _mascara){
		this.id = _id;
		this.nombre = _nombre;
		this.usuario = _usuario;
		this.ipInicial = _ipInicial;
		this.mascara = _mascara;
	}

	public String getId() {	return id;	}

	public String getNombre() {	return nombre;	}

	public String getUsuario() {	return usuario;	}

	public String getIpInicial() {	return ipInicial;	}

	public String getMascara() {	return mascara;	}

	public void setId(String id) { this.id = id;	}

	public void setNombre(String nombre) {	this.nombre = nombre;	}

	public void setUsuario(String usuario) { this.usuario = usuario;	}

	public void setIpInicial(String ipInicial) { this.ipInicial = ipInicial; }

	public void setMascara(String mascara) { this.mascara = mascara; }
}
