package com.davidpablos.calculadora;

public class Calculadora {
	
	public Calculadora() {
		
	}
	
	public String realizarOperacion(double op1, double op2, String typeOp){
		if(typeOp == "+") {
			
			return ((Double)(op1 + op2)).toString();
		}
		
		return "";
	}

}
