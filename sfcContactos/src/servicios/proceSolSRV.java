package servicios;

import entidades.*;
import dao.*;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.Collection;
import java.util.Vector;

public class proceSolSRV {

	public Collection<Date> lstFeriados = null;
	
	public Boolean CargaSolicitudes() {
		contactoDAO miDAO = new contactoDAO();
		String sTipo="";
		
		//Traer Fecha Actual
		Date dFechaActual = miDAO.getFechaActual();

		SimpleDateFormat fechaF = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		
		//Carga Feriados
		lstFeriados = miDAO.getLstFeriados(dFechaActual);

		Collection<interfaceDTO>lstInterface = null;
		lstInterface = miDAO.getLstContactos();
		lstInterface = miDAO.getLstSolicitudes();
		
		for(interfaceDTO fila: lstInterface) {
			
			sTipo=fila.tipo_sol.trim();
			switch(sTipo) {
				case "CONTACTO":
					if(!ProcesaContacto(dFechaActual, fila)) {
						System.out.println("Error al procesar Contacto");
						return false;
					}
					break;
					
				default:
					System.out.println(String.format("Caso: %d Tipo de solicitud desconocida", fila.caso));
					break;
			}
		}
		
		
		return true;
	}
	
	public Boolean CargaContactos() {
		contactoDAO miDAO = new contactoDAO();
		
		//Traer Fecha Actual
		Date dFechaActual = miDAO.getFechaActual();
		//System.out.println("Fecha Actual: " + dFechaActual);

		SimpleDateFormat fechaF = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		//System.out.println("Fecha Actual con Formato: " + fechaF.format(dFechaActual));
		
		//Carga Feriados
		lstFeriados = miDAO.getLstFeriados(dFechaActual);
/*		
		for(Date fecha: lstFeriados) {
			System.out.println("Feriado: " + fecha);
		}
*/		
		Collection<interfaceDTO>lstInterface = null;
		lstInterface = miDAO.getLstContactos();
		
		for(interfaceDTO fila: lstInterface) {
			if(!ProcesaContacto(dFechaActual, fila)) {
				System.out.println("Error al procesar Contacto");
				return false;
			}
		}
		
		return true;
	}
	
	private Boolean ProcesaContacto(Date dFechaActual, interfaceDTO regInter) {
		contactoDAO miDAO = new contactoDAO();
		clienteDTO miCliente = null;
		contactoDTO miCto = null;
		Collection<observaCtoDTO> lstObserva = null;
		tecniDTO miTecni=null;
System.out.println("Entro a procesaContacto");
		//Cargar Data JSON
		dataInDTO regData = new dataInDTO(regInter, "CONTACTO");

		//Cargar Parametros
		parametrosDTO regParam = null;
		regParam = miDAO.getParametros(regData.motivo, regData.sub_motivo, regData.sucursal);

		//Calcular Vencimientos
		regParam.dFechaVto = calculaVenc(dFechaActual, regParam.iPlazo);
		regParam.dFchaVtoCt = calculaVenc(dFechaActual, regParam.iPlazo + 1);
		
		//Cargar Datos del Cliente
		miCliente = miDAO.getCliente(regData.numero_cliente);

		//Cargar Datos Tecnicos
		miTecni = miDAO.getTecni(regData.numero_cliente);
		
		//Cargar Contacto
		miCto = cargaCto(regInter, regData, regParam, miCliente);
		
		//Cargar Motivo
		motContactoDTO miMotivo = new motContactoDTO(regData, regParam);
		miMotivo.mo_rol_inicio = miCto.co_rol_inicio;
		motivosTrafoDTO motTrafo = null;
		motTrafo = miDAO.getMotivosTrafo(regData.motivo, regData.sub_motivo);
		miMotivo.mo_cod_motivo = motTrafo.codMotivo.trim();
		miMotivo.mo_cod_mot_empresa = motTrafo.codSubmotivo.trim();
		miMotivo.descMotCliente = motTrafo.motCliente.trim();
		miMotivo.descMotEmpresa = motTrafo.motEmpresa.trim();
		
		//Cargar Observaciones
		lstObserva = getLstObserva(regData.observaciones);
		
		//Genero Contacto BD (obtener nro.cto + grabar contacto motivo observa + Upd tabla interface)
		if(!miDAO.genContacto(regInter, miCto, miMotivo, lstObserva, miCliente, regParam, miTecni)) {
			System.out.println("Error al grabar datos.");
			return false;
		}

		
		return true;
	}
	
	private Collection<observaCtoDTO>getLstObserva(String sObserva){
		Vector<observaCtoDTO> miLista = new Vector<observaCtoDTO>();
		int i;
		int iPagina;
		int iTope=250;
		int iLargo;
		String sCadena=sObserva;
		String sLinea="";
		
		sCadena=sCadena.trim();
		
		iLargo=sCadena.length();
		i=1;
		iPagina=1;
		observaCtoDTO fila = new observaCtoDTO();
		try {
			if(iLargo<=iTope) {
				fila.ob_pagina=iPagina;
				fila.ob_descrip=sCadena;
				
				miLista.add(fila);
			}else {
				while(iLargo > iTope) {
					sLinea = sCadena.substring(i, iTope);
					
					fila.ob_pagina=iPagina;
					fila.ob_descrip=sLinea;
					
					miLista.add(fila);
					
					sCadena=sCadena.substring(iTope+1);
					iLargo=sCadena.length();
					iPagina++;
				}
				
				
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
		return miLista;
	}
	
	private contactoDTO cargaCto(interfaceDTO regInter, dataInDTO regData, parametrosDTO regParam, clienteDTO regCliente) {
		contactoDTO miCto = new contactoDTO();
		
		miCto.sfc_caso = regInter.caso;
		miCto.sfc_nro_orden = regInter.nro_orden;
		//miCto.co_numero
		miCto.co_numero_cliente = regData.numero_cliente;
		String sDire = "";
		
		if(regData.numero_cliente > 0) {
			miCto.co_tipo_doc = regCliente.tip_doc;
			miCto.co_nro_doc = String.valueOf(regCliente.nro_doc);
			miCto.co_tarifa = regCliente.tarifa;
			//miCto.co_suc_cli = regParam.sSucurPadre;
			miCto.co_suc_cli = regCliente.sucursal;
			miCto.co_cen_cli = regCliente.sucursal;
			miCto.co_plan = regCliente.sector;
			miCto.co_nombre = regCliente.nombre.trim();
			miCto.co_telefono = regCliente.telefono;
		
			sDire = regCliente.nom_calle.trim() + " " + regCliente.nro_dir.trim() + " piso: " + regCliente.piso_dir.trim() + " dpto: " + regCliente.depto_dir.trim(); 
			miCto.co_direccion = sDire;
			
			miCto.co_partido = regCliente.nom_partido.trim();
			miCto.co_codpos = regCliente.cod_postal;
			miCto.co_nro_cuit = regCliente.rut;
			//miCto.co_rol_inicio = "SF" + regCliente.sucursal;
			miCto.co_rol_inicio = "SALESFORCE";
			//miCto.co_cod_medio;
			//miCto.co_fecha_inicio;   
			miCto.co_suc_ag_contacto = "0100";
			miCto.co_suc_contacto = "0100";
			miCto.co_oficina = "0100";
			//miCto.co_fecha_proceso;
			//miCto.co_fecha_estado;
			miCto.co_multi = "0";   
			miCto.co_es_cliente= "0";
		}else {
			miCto.co_es_cliente= "1";
		}

		miCto.co_backoffice = "B";
		
		SimpleDateFormat fechaF = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		miCto.co_fecha_vto = fechaF.format(regParam.dFechaVto);
		
		return miCto;
	}
	
	private Date calculaVenc(Date d, int iPlazo) {
		Date miFecha = null;
		int  iDia;
		int	 iFeriado;
		int  iContador=0;
		
		miFecha = sumaDias(d, iPlazo);
		iDia=getDiaSemana(miFecha);
		iFeriado = getFeriado(miFecha);
		
		while((iDia == 1 || iDia == 7 || iFeriado == 1) && iContador <= 10) {
			miFecha = sumaDias(miFecha, 1);
			iDia=getDiaSemana(miFecha);
			iFeriado = getFeriado(miFecha);
			iContador++;
		}

		return miFecha;
	}
	
	private int getFeriado(Date d) {
		int iFeriado=0;

		for(Date fecha: lstFeriados) {
			if(d==fecha) {
				iFeriado=1;
			}
		}
		
		return iFeriado;
	}
	
	private Date sumaDias(Date d, int iPlazo) {
		Date miFecha = d;
		
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(miFecha.getTime());
		cal.add(Calendar.DATE, iPlazo);
		
		return (Date) cal.getTime();
	}
	
	private int getDiaSemana(Date d){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);		
	}
}
