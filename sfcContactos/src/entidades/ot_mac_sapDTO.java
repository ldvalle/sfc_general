package entidades;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;

public class ot_mac_sapDTO {

	public String	nro_orden="";
	public String	tipo_ifaz="";
	public String	tipo_traba="";
	public String	sucursal="";
	public String	area_ejecuta="";
	public String	motivo="";
	public String	obs_dir="";
	public String	obs_lectu="";
	public String	obs_segen="";
	public String	area_interloc="";
	public long		nro_medidor;
	public String	marca_med="";
	public String	modelo_med="";
	public String	cla_servi="";
	public double	potencia;
	public String	tension="";
	public String	acometida="";
	public String	toma="";
	public String	conexion="";
	public String	pre1_ubic="";
	public String	pre2_ubic="";
	public String	pre3_ubic="";
	public String	ruta_lectura="";
	public String	nombre_cli="";
	public long		nro_cli;
	public String	nom_entre="";
	public String	nom_entre1="";
	public String	telefono="";
	public String	nom_calle="";
	public String	nro_dir="";
	public String	nom_partido="";
	public String	piso_dir="";
	public String	depto_dir="";
	public String	nom_comuna="";
	public int		cod_postal;
	public Date		fecha_vto;
	public String	rol_creador="";
	public String	nombre_rol="";
	public String	proced="";
	public long		nro_proced;
	public String	cod_barrio="";
	public String	nom_barrio="";
	public String	cod_barra="";
	
	public ot_mac_sapDTO(long lNroOrden, long lNroMensaje, clienteDTO miCliente, tecniDTO miTecni, temaTrabajo miTemaTrab, Date fechaVto, String sAreaDestino, String sRolActual, String sAreaActual, String[] sPrecintos, String sNroCB) {
		this.nro_orden = String.format("SE%d010", lNroOrden);
		this.tipo_ifaz = "G001";
		this.tipo_traba = "SE" + miTemaTrab.sCodTema.substring(0,2);
		this.sucursal = miCliente.sucursal_padre.trim();
		this.area_ejecuta = sAreaActual.trim();
		this.motivo = miTemaTrab.sCodTrabajo.substring(0,4);
		this.obs_dir = miCliente.obs_dir.trim();
		this.obs_lectu = miCliente.info_adic_lectura.trim();
		this.obs_segen = miTemaTrab.sDescTrabajo.trim();
		this.area_interloc = sAreaDestino.trim();
		this.nro_medidor = miCliente.numero_medidor;
		this.marca_med = miCliente.marca_medidor.trim();
		this.modelo_med = miCliente.modelo_medidor.trim();
		this.cla_servi = miCliente.tipo_cliente.trim();
		this.potencia = miCliente.potencia_contrato;
		this.tension = miTecni.codigo_voltaje.trim();
		if(miTecni.acometida != null) {
			this.acometida = miTecni.acometida.trim();
		}
		
		this.toma = miCliente.tipo_empalme.trim();
		this.conexion = miTecni.tipo_conexion.trim();

		int iMax=sPrecintos.length;
		int i;
		
		if(iMax>3)
			iMax=3;
		
		for(i=0; i<iMax; i++) {
			switch (i) {
				case 0:
					this.pre1_ubic = sPrecintos[i];
					break;
				case 1:
					this.pre2_ubic = sPrecintos[i];
					break;
				case 2:
					this.pre3_ubic = sPrecintos[i];
					break;
			}
		}
		
		this.ruta_lectura = miCliente.sucursal + "-" + String.format("%d03", miCliente.sector) + "-" + String.format("%d05", miCliente.zona) + "-" + String.format("%d05", miCliente.correlativo_ruta);
		this.nombre_cli = miCliente.nombre.trim();
		this.nro_cli = miCliente.numero_cliente;
		if(miCliente.nom_entre != null) {
			this.nom_entre = miCliente.nom_entre.trim();
		}
		if(miCliente.nom_entre1 != null)
			this.nom_entre1 = miCliente.nom_entre1.trim();
		this.telefono = miCliente.telefono.trim();
		this.nom_calle = miCliente.nom_calle.trim();
		this.nro_dir = miCliente.nro_dir.trim();
		this.nom_partido = miCliente.nom_partido.trim();
		this.piso_dir = miCliente.piso_dir.trim();
		this.depto_dir = miCliente.depto_dir.trim();
		this.nom_comuna = miCliente.nom_comuna.trim();
		this.cod_postal = miCliente.cod_postal;
		this.fecha_vto = fechaVto;
		this.rol_creador = sRolActual.trim();
		this.nombre_rol = "SALESFORCES";
		this.proced = "M_SEGEN";
		this.nro_proced = lNroMensaje;
		if(miCliente.barrio !=null)
			this.cod_barrio = miCliente.barrio.trim();
		if(miCliente.nom_barrio != null)
			this.nom_barrio = miCliente.nom_barrio.trim();
		
		String sAux = String.format("%d03", Integer.parseInt(sNroCB)) + String.format("%d09", miCliente.numero_medidor);
		this.cod_barra= sAux;
		
	}
	
}
