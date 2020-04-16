package com.edesur;

import servicios.*;

public class IniciaContacto {

	public static void main(String[] args) {
		proceSolSRV miSrv = new proceSolSRV();
		
		/*
		if(!miSrv.CargaContactos()) {
			System.out.println("Fallo Carga");
		}
		*/
		if(!miSrv.CargaSolicitudes()) {
			System.out.println("Fallo Carga");
		}
		
		
		System.out.println("Termino OK");
	}

}
