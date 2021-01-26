package com.programa.calculadoravlsm.Herramientas;

public class StringHelper {
	/*public static boolean validarDatosProyecto(String nombre, String _ip, String mascara){
		return validarIp(_ip);
	}

	public static boolean validarIp(String _ip){
		String[] partes;
		if(_ip.contains(".")){
			partes = _ip.split(".");
			if(partes.length != 4){
				return false;
			}
		}
		return true;
	}*/

	public static int[] getIp(String _ip){
		int[] out = new int[4];
		String n = "";
		String tmp;
		char c;
		int count = 0;
		for(int i = 0; i<_ip.length(); i++){
			c = _ip.charAt(i);
			if(c != '.'){
				n += c;
			}else{
				out[count] = Integer.parseInt(n);
				count++;
				n = "";
			}
		}
		if(!n.isEmpty()){
			out[count] = Integer.parseInt(n);
		}
		return out;
	}

	public static int binaryToInteger(String binary) {
		char[] numbers = binary.toCharArray();
		int result = 0;
		for(int i=numbers.length - 1; i>=0; i--)
			if(numbers[i]=='1')
				result += Math.pow(2, (numbers.length-i - 1));
		return result;
	}
}
