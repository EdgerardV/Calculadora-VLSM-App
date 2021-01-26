package com.programa.calculadoravlsm.Herramientas;

import android.util.Log;

import com.programa.calculadoravlsm.Database.NodoDatabase.NodoDataHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Calculadora {
	ArrayList<NodoDataHolder> nodos;
	int ipIngresada[];
	int ipInicial[];
	int mascara;
	int totalNodos;
	boolean calculable;

	public Calculadora(int ipInicial[], int mask, ArrayList<NodoDataHolder> _nodos) {
		this.ipIngresada = ipInicial;
		this.mascara = mask;
		this.calculable = true;
		this.ipInicial = this.calcularIp(ipIngresada, this.calcularMask(mask));
		this.totalNodos = 0;
		nodos = _nodos;
		this.calcularTotalNodos();
	}

	public Calculadora(String ipInicial, int mask) {
		nodos = new ArrayList<>();
		this.ipInicial = StringHelper.getIp(ipInicial);
		this.mascara = mask;
		this.calcularTotalNodos();
	}

	private void calcularTotalNodos(){
		for (NodoDataHolder n : this.nodos) {
			totalNodos += n.getCantidadNodos();
		}
		calculable = totalNodos < 65535;
	}

	public ArrayList<NodoDataHolder> calcularNodos() {
		//this.nodos = _nodos;
		this.calcularTotalNodos();
		if(this.calculable){
			Collections.sort(nodos);
			int[] lasIp = this.ipInicial;
			for (NodoDataHolder n : nodos) {
				n.setIpInicial(lasIp);
				int[] tmpIp = this.sumarIps(lasIp, n.getDirecciones());
				n.setIpFinal(restarIps(tmpIp,1));
				lasIp = tmpIp;
			}
		}
		return this.nodos;
	}
	public static int[] calcularMask(int mask){
		String[] tmpIp = new String[4];
		String tmp = "";
		int count = 0;
		int index = 0;
		for(int i = 0; i<32; i++){
			if(i<mask){
				tmp+="1";
			}else{
				tmp+="0";
			}
			count++;
			if(count >= 8){
				tmpIp[index] = tmp;
				index++;
				//tmp = "";
				tmp = new String();
				count = 0;
			}
		}
		int[] out = new int[4];
		int x;
		for(int i= 0; i<4;i++){
			x = StringHelper.binaryToInteger(tmpIp[i]);
			out[i] = x;
		}
		return out;
	}

	public static int[] calcularIp(int ip[],int mask[]){
		int nMask[] = new int[mask.length];
		for(int i= 0; i<mask.length; i++){
			nMask[i] = mask[i] & ip[i];
		}
		return nMask;
	}

	public static int[] tamañoDeRed(int cantidadNodos){
		double[] tmp = new double[2];
		tmp[0] = 0;
		tmp[1] = 0;
		do{
			tmp[1]++;
			tmp[0] = Math.pow(2,tmp[1]);
		}while(tmp[0] < cantidadNodos);
		int[] out = new int[2];
		out[0] = (int) tmp[0];
		out[1] = 32 - (int) tmp[1];
		return out;
	}

	public static int[] sumarIps(int[] ip, int cantidadBits){
		int[] out = Arrays.copyOf(ip, ip.length);
		int acarreo = cantidadBits;
		for(int i = out.length-1; i>=0; i--){
			if((out[i] + acarreo) > 255){
				out[i] = acarreo + out[i] - 256;
				acarreo = 1;
			}else{
				out[i] = out[i] + acarreo;
				acarreo = 0;
			}
		}
		return out;
	}

	public static int[] restarIps(int[] ip,int cantidad) {
		int[] out = Arrays.copyOf(ip, ip.length);
		int acarreo = cantidad;
		for (int i = out.length - 1; i >= 0; i--) {
			if((out[i] - acarreo) < 0){
				out[i] = 256 + (out[i] - acarreo);
				acarreo = 1;
			}else{
				out[i] = out[i] - acarreo;
				acarreo = 0;
			}
		}
		return out;
	}
}

	/*
	public static int tamañoDeRed(int cantidadNodos){
		return (int)bitHelper.sacarNodos(cantidadNodos+2,0.0,0);
	}

	private static double sacarNodos(int nNodos,double limite,int pot){
		if(limite>nNodos){
			return limite;
		}else{
			limite = Math.pow(2, pot);
			return sacarNodos(nNodos,limite,pot+1);
		}
	}

	public static int[] romperVinculos(int[] array){
		int tmp;
		int[] aux = new int[array.length];

		for(int i = 0; i <array.length; i++){
			//aux = Arrays.copyOf(array, i);
			aux[i] = array[i];
		}
		return aux;
	}
*/