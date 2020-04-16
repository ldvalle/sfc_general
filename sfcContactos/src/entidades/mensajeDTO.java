package entidades;

import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class mensajeDTO {

	public long	lMensaje;
	public String	sProced;
	public String	sEtapa;
	public String	sPrivacidad;
	public String	sUrgencia;
	public String	sEncriptado;
	public String	sReferencia;
	public String	sRolCon;
	public String	sRolOrg;
	public String	sRolDst;
	public int	iEmpCon;
	public int	iEmpOrg;
	public int	iEmpDst;
	public String	sTexto;
/*
	public mensajeDTO(long lNroMensaje, long lNroContacto, long lNroCliente, int iMotClie, int iMotEmpre, String sFecha, String sOrigen, String sDestino, String sAreaDestino, String sProcedimiento) {
		this.lMensaje = lNroMensaje;
		this.sProced = sProcedimiento;
		this.sEtapa = "MODIFICACION";
		this.sPrivacidad = "1";
		this.sUrgencia = "4";
		this.sEncriptado = "N";
		this.sReferencia = sFecha + "-" + iMotClie + "/" + iMotEmpre + "-$0.00-Cl" + lNroCliente + "-Co" + lNroContacto; 
		this.sRolCon = sDestino;
		this.sRolOrg = sOrigen;
		this.sRolDst = sDestino;
		this.iEmpCon = 1;
		this.iEmpOrg =1;
		this.iEmpDst = 1;
		this.sTexto = String.format("%08d", lNroMensaje) + "þ";
		
	}
*/
	public mensajeDTO(long lNroMensaje, Date fechaInicio, String sRolOrigen, String sRolDestino, String sAreaDestino, String sProcedimiento, clienteDTO miCliente, contactoDTO regConta, motContactoDTO regMot, temaTrabajo miTema, parametrosDTO regPar, Collection<observaCtoDTO> vecObserva) {
		int iMotClie=Integer.parseInt(regMot.mo_cod_motivo);
		int iMotEmpre=Integer.parseInt(regMot.mo_cod_mot_empresa);
		SimpleDateFormat fechaF = new SimpleDateFormat("yy/MM/dd");
		String sFechaCorta=fechaF.format(fechaInicio);
		String sLinea="";
		
		
		this.lMensaje = lNroMensaje;
		this.sProced = sProcedimiento;
		this.sEtapa = "MODIFICACION";
		this.sPrivacidad = "1";
		this.sUrgencia = "4";
		this.sEncriptado = "N";
		this.sReferencia = sFechaCorta + "-" + iMotClie + "/" + iMotEmpre + "-$0.00-Cl" + regConta.co_numero_cliente + "-Co" + regConta.co_numero; 
		this.sRolCon = sRolDestino;
		this.sRolOrg = sRolOrigen;
		this.sRolDst = sRolDestino;
		this.iEmpCon = 1;
		this.iEmpOrg =1;
		this.iEmpDst = 1;
		
		sLinea=getCabecera(lNroMensaje, fechaInicio, sRolOrigen, miCliente, regConta, regMot, vecObserva);
		
		sLinea += getData(sRolDestino, sAreaDestino, miCliente, regConta, regMot, miTema, regPar);
		sLinea+=String.format("%08d", lNroMensaje) + "þ";

		this.sTexto = sLinea;
		
	}
	
	private String getCabecera(long lNroMensaje, Date fechaInicio, String sRolOrigen, clienteDTO miCliente, contactoDTO regConta, motContactoDTO regMot, Collection<observaCtoDTO> Observa) {
		int i;
		String sLinea="";
		SimpleDateFormat fmtDateTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sFechaLarga=fmtDateTime.format(fechaInicio);
		String sAux=sRolOrigen.trim()+"RQ"+sFechaLarga.trim();
		int		iAux=sAux.length();
		int 	iVeces=61-iAux;
		
		sLinea = "-" + sRolOrigen.trim() + "-RQ-" + sFechaLarga.trim() + sRellena(iVeces,'-') + " " + (char)13 + (char)10;
		sLinea += "N.Contacto: " + regConta.co_numero + " Motivo: " + regMot.descMotCliente.trim() + "/" + regMot.descMotEmpresa.trim() + (char)13 + (char)10; 
		
		if(Observa.size()>0) {

			for(observaCtoDTO sComent : Observa) {
				sLinea+=  sComent.ob_descrip.trim();
			}
			
			sLinea += (char)13 + (char)10;
		}
		
		if(miCliente.telefono != null || !miCliente.telefono.trim().equals("")) {
			sLinea+="Tel: " + miCliente.telefono + (char)13 + (char)10;
		}
		
		return sLinea;
	}
	
	private String getData(String sRolDestino, String sAreaDestino, clienteDTO miCliente, contactoDTO regConta, motContactoDTO regMot, temaTrabajo miTema, parametrosDTO regPar){
		String sLinea="";
		String sDireccion="";
		String sRuta="";
		SimpleDateFormat fmtDate= new SimpleDateFormat("dd/MM/yyyy");
		String sVtoCT = fmtDate.format(regPar.dFchaVtoCt);
		
		sDireccion = getDireccion(miCliente);
		sRuta = getRuta(miCliente);
		
		//Area Ejecutora
		sLinea = "þ" + sRolDestino.trim();
		//Nombre Cliente
		sLinea += "þ" + miCliente.nombre.trim();
		//Dir.Suministro
		sLinea += "þ" + sDireccion.trim();
		//Sector + Zona + Correlativo ruta
		sLinea += "þ" + sRuta.trim();
		//Localidad (descripcion)
		sLinea += "þ" + miCliente.nom_comuna.trim();
		//Nro.medidor
		sLinea += "þ" + miCliente.numero_medidor;
		//Tipo Orden (descripcion)
		sLinea += "þAtencion Comercial";
		//Tema (descripcion)
		sLinea += "þ" + miTema.sDescTema.trim();
		//trabajo (descripcion)
		sLinea += "þ" + miTema.sDescTrabajo.trim();
		//Clase (descripcion)
		sLinea += "þTeléfono";
		//Nro.de Cliente
		sLinea += "þ" + miCliente.numero_cliente;
		//Fecha Vto.CT d/m/y
		sLinea += "þ" +sVtoCT;
		//Tipo Orden (cod)
		sLinea += "þAC";
		//Clase (cod)
		sLinea += "þ0005";
		//Trabajo (cod)
		sLinea += "þ" + miTema.sCodTrabajo.trim();
		//Suc.Cliente (desc)
		sLinea += "þ" + miCliente.nom_sucursal.trim();
		//Formulario
		sLinea += "þ";
		//Tema (cod)
		sLinea += "þ" + miTema.sCodTema.trim();
		//Cuenta Conver
		sLinea += "þ" + miCliente.cuenta_conver.trim();
		//dos vacios
		sLinea += "þþ";
		//Suc.del cliente (cod)
		sLinea += "þ" + miCliente.sucursal.trim();
		//Fecha Excepcion
		sLinea += "þ";
		
		return sLinea;
	}
	
	private String getDireccion(clienteDTO miCliente) {
		String dire="";
		
		dire = miCliente.nom_calle.trim();
		dire += " " +miCliente.nro_dir.trim();
		if(miCliente.piso_dir.trim() != null && miCliente.piso_dir.trim()!= "") {
			dire += " Piso:" + miCliente.piso_dir.trim();
		}
		if(miCliente.depto_dir.trim() != null && miCliente.depto_dir.trim()!= "") {
			dire += " Depto:" + miCliente.depto_dir.trim();
		}
		if(miCliente.cod_postal>0) {
			dire += " -(" + miCliente.cod_postal + ")";
		}
		if(miCliente.nom_partido.trim() != null && miCliente.nom_partido.trim()!= "") {
			dire += miCliente.nom_partido.trim();
		}
		
		return dire;
	}
	
	private String getRuta(clienteDTO miCliente) {
		String sRuta="";
		
		sRuta = String.format("%03d%05d%05d", miCliente.sector, miCliente.zona, miCliente.correlativo_ruta);
	
		return sRuta;
	}
	
	private String sRellena(int iVeces, char car) {
		String sCadena="";
		int i=1;
		
		if(iVeces<1)
			return sCadena;
		
		for(i=1; i<= iVeces; i++) {
			sCadena+=car;
		}
		
		return sCadena;
	}
}
