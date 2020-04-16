package entidades;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class ceMensajeDTO {
	private long	lMensaje;
	private String	sProced;
	private String	sEtapa;
	private String	sPrivacidad;
	private String	sUrgencia;
	private String	sEncriptado;
	private String	sReferencia;
	private String	sRolCon;
	private String	sRolOrg;
	private String	sRolDst;
	private int	iEmpCon;
	private int	iEmpOrg;
	private int	iEmpDst;
	private String	sTexto;
	
	public ceMensajeDTO(long lNroMensaje, Date fechaInicio, String sRolOrigen, String sRolDestino, String sProcedimiento, clienteDTO miCliente, ceReclamoDTO reclamo) {
		int iMotClie=reclamo.getCodTema();
		int iMotEmpre=reclamo.getCodMotivo();
		SimpleDateFormat fechaF = new SimpleDateFormat("yy/MM/dd");
		String sFechaCorta=fechaF.format(fechaInicio);
		String sLinea="";
		
		this.lMensaje = lNroMensaje;
		this.sProced = sProcedimiento;
		this.sEtapa = "MODIFICACION";
		this.sPrivacidad = "1";
		this.sUrgencia = "4";
		this.sEncriptado = "N";
		this.sReferencia = Long.toString(reclamo.getReclamo()); 
		this.sRolCon = sRolDestino;
		this.sRolOrg = sRolOrigen;
		this.sRolDst = sRolDestino;
		this.iEmpCon = 1;
		this.iEmpOrg =1;
		this.iEmpDst = 1;
		
		sLinea=getCabecera(lNroMensaje, fechaInicio, sRolOrigen, miCliente,reclamo);
/*		
		sLinea += getData(sRolDestino, sAreaDestino, miCliente, regConta, regMot, miTema, regPar);
		sLinea+=String.format("%08d", lNroMensaje) + "þ";
*/		
		this.sTexto = sLinea;
		
		
	}

	private String getCabecera(long lNroMensaje, Date fechaInicio, String sRolOrigen, clienteDTO cliente, ceReclamoDTO reclamo) {
		String sLinea="";
		SimpleDateFormat fmtDateTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sFechaLarga=fmtDateTime.format(fechaInicio);
		String sAux=sRolOrigen.trim()+"RQ"+sFechaLarga.trim();
		int		iAux=sAux.length();
		int 	iVeces=61-iAux;
		
		sLinea = "-" + sRolOrigen.trim() + "-RQ-" + sFechaLarga.trim() + sRellena(iVeces,'-') + " " + CH_FL;
		if(reclamo.getTarifa().equals("T1")) {
			sLinea += "Nro. Cliente : " + reclamo.getCodCliente() + CH_FL; 
			sLinea += "Nombre : " + cliente.nombre.trim() + CH_FL;
			sLinea += "Partido : " + cliente.nom_partido.trim() + CH_FL;
			sLinea += "Localidad : " + cliente.nom_comuna.trim() + CH_FL;
			sLinea += "Domicilio : " + getDireccion(cliente) + CH_FL;
			sLinea += "Estado Cobrabilidad : " + cliente.desc_estCobrabilidad.trim() + CH_FL;
			sLinea += "Corte Restringido : " + cliente.tiene_corte_rest.trim() + CH_FL;
		}else {
			sLinea += "RUE : " + reclamo.getCodCliente() + CH_FL; 
			sLinea += "Nombre : " + cliente.nombre.trim() + CH_FL;
			sLinea += "Domicilio : " + getDireccion(cliente) + CH_FL;
			
		}
		
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
	
	private static final String CH_FL = "" + (char)13 + (char)10;
	
	//Getters
	public long getNroMensaje() {
		return lMensaje;
	}
	public String getProcedimiento() {
		return sProced;
	}
	public String getEtapa() {
		return sEtapa;
	}
	public String getPrivacidad() {
		return sPrivacidad;
	}
	public String getUrgencia() {
		return sUrgencia;
	}
	public String getEncriptado() {
		return sEncriptado;
	}
	public String getReferencia() {
		return sReferencia;
	}
	public String getRolCon() {
		return sRolCon;
	}
	public String getRolOrg() {
		return sRolOrg;
	}
	public String getRolDst() {
		return sRolDst;
	}
	public int getEmpCon() {
		return iEmpCon;
	}
	public int getEmpOrg() {
		return iEmpOrg;
	}
	public int getEmpDst() {
		return iEmpDst;
	}
	public String getTexto() {
		return sTexto;
	}
	
}
