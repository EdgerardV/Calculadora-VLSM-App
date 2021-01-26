package com.programa.calculadoravlsm.Database.NodoDatabase;

import com.programa.calculadoravlsm.Herramientas.Calculadora;

public class NodoDataHolder implements Comparable{
	private String id;
	private String idProyecto;
	private String descripcion;
	private int cantidadNodos;
	private int[] ipInicial;
	private int[] ipFinal;
	private int direcciones;
	private int mascara;

	public NodoDataHolder(String _id,String _idProyecto,String _descripcion, String _cantidadNodos){
		this.id = _id;
		this.idProyecto = _idProyecto;
		this.descripcion = _descripcion;
		this.cantidadNodos = Integer.parseInt(_cantidadNodos);
		int[] tmp = Calculadora.tamañoDeRed(this.cantidadNodos);
		this.direcciones = tmp[0];
		this.mascara = tmp[1];
	}
/*
	public NodoDataHolder(String _id,String _idProyecto,String _descripcion, String _cantidadNodos,String _ipInicial, String _ipFinal){
		this.id = _id;
		this.idProyecto = _idProyecto;
		this.descripcion = _descripcion;
		this.cantidadNodos = _cantidadNodos;
		this.ipInicial = _ipInicial;
		this.ipFinal = _ipFinal;
		int[] tmp = Calculadora.tamañoDeRed(Integer.parseInt(this.cantidadNodos));
		this.direcciones = tmp[0];
		this.mascara = tmp[1];
	}
*/
	public void setId(String id) {	this.id = id; }

	public void setIdProyecto(String idProyecto) {	this.idProyecto = idProyecto; }

	public void setDescripcion(String descripcion) {	this.descripcion = descripcion; }

	public void setCantidadNodos(int cantidadNodos) {	this.cantidadNodos = cantidadNodos; }

	public void setIpInicial(int[] ipInicial) {	this.ipInicial = ipInicial;	}

	public void setIpFinal(int[] ipFinal) {	this.ipFinal = ipFinal;	}

	public void setMascara(int mascara) {	this.mascara = mascara;	}

	public void setDirecciones(int direcciones) {	this.direcciones = direcciones;	}

	public String getId() {	return id; }

	public String getIdProyecto() {	return idProyecto;	}

	public String getDescripcion() {	return descripcion; }

	public int getCantidadNodos() {	return cantidadNodos; }

	public int[] getIpInicial() {	return ipInicial;	}

	public String getIpInicialString(){
		if(this.ipInicial == null){
			return "...";
		}
		return this.ipInicial[0] + "." +
				this.ipInicial[1] + "." +
				this.ipInicial[2] + "." +
				this.ipInicial[3] ;
	}

	public int[] getIpFinal() {	return ipFinal;	}

	public String getIpFinalString(){
		if(this.ipFinal == null){
			return "...";
		}
		return this.ipFinal[0] + "." +
				this.ipFinal[1] + "." +
				this.ipFinal[2] + "." +
				this.ipFinal[3] ;
	}

	public int getMascara() {	return mascara;	}

	public int getDirecciones() {	return direcciones;	}

	@Override
	public int compareTo(Object o) {
		int compare = ((NodoDataHolder) o).getCantidadNodos();
		return compare - this.getCantidadNodos();
	}
}
