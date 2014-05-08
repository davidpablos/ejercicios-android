package com.davidpablos.calculadora;

import android.util.Log;


public class Calculadora {
	
	private double op1;
	private double op2;
	private String op;
	private String ultimaEntradaOperando;
	
	public Calculadora() {
		this.op1 = 0;
		this.op2 = 0;
		this.op = "";
		this.ultimaEntradaOperando = "NO";
	}
	
	public double getOp1() {
		return op1;
	}
	
	public String getOp() {
		return op;
	}
	
	public void pulsarOperador(double op1, String operador) {
		this.op = operador;
		this.op1 = op1;
	}
	
	public String realizarOperacion(String digito){
		this.op2 = Double.parseDouble(digito);
		Log.d("TAG", ((Double)(this.op1 + this.op2)).toString());
		if(this.op.equals("+")) {
			return ((Double)(this.op1 + this.op2)).toString();
		} else if(this.op.equals("-")) {
			return ((Double)(this.op1 - this.op2)).toString();
		} else if(this.op.equals("x")) {
			return ((Double)(this.op1 * this.op2)).toString();
		} else if(this.op.equals("/")){
			return ((Double)(this.op1 / this.op2)).toString();
		} else {
			return "";
		}
	}

}
