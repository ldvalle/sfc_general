package entidades;

import java.util.Date;

public class ot_macDTO {
	public long	numero_cliente;
	public String	envia_sap="";
	public long	mensaje_xnear;
	public String	proced="";
	public String	estado="";
	public String	status="";
	public String	sucursal_padre="";
	public String	sucursal="";
	public int		sector;
	public int		zona;
	public long		correlativo_ruta;
	public String	tipo_traba="";
	public String	area_interloc="";
	public String	motivo="";
	public String	rol_ejecuta="";
	public String	area_ejecuta="";
	public double	potencia;
	public String	tension="";
	public String	acometida="";
	public String 	toma="";
	public String	conexion="";
	public Date		fecha_vto;
	public String	proyecto="";
	
	public ot_macDTO(long lNroMensaje, clienteDTO miCliente, tecniDTO miTecni, temaTrabajo miTemaTrab, Date fechaVto, String sAreaDestino, String sRolActual, String sAreaActual) {
		this.numero_cliente = miCliente.numero_cliente;
		this.envia_sap="S";
		this.mensaje_xnear = lNroMensaje;
		this.proced = "M_SEGEN";
		this.estado = "C";
		this.status = "INIC";
		this.sucursal_padre = miCliente.sucursal_padre;
		this.sucursal = miCliente.sucursal;
		this.sector = miCliente.sector;
		this.zona = miCliente.zona;
		this.correlativo_ruta = miCliente.correlativo_ruta;
		this.tipo_traba = "SE" +  miTemaTrab.sCodTema.substring(0,2);
		this.area_interloc = sAreaDestino.trim();
		this.motivo = miTemaTrab.sCodTrabajo.substring(0,4);
		this.rol_ejecuta = sRolActual.trim();
		this.area_ejecuta = sAreaActual.trim();
		this.potencia = miCliente.potencia_contrato;
		this.tension = miTecni.codigo_voltaje.trim();
		if(miTecni.acometida!=null) {
			this.acometida = miTecni.acometida.trim();
		}
		
		this.toma = miCliente.tipo_empalme.trim();
		this.conexion = miTecni.tipo_conexion.trim();
		this.fecha_vto = fechaVto;
	}
	
}
