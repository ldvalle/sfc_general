package entidades;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ordenDTO {
	public String		tipo_orden;
	public String		numero_orden;
	public long			mensaje_xnear;
	public int			servidor;
	public String		sucursal;
	public String		area_emisora;
	public String		fecha_inicio; //year to second
	public String		ident_etapa;
	public String		term_dir;
	public String		area_ejecutora;
	public String		duracion; // year to second
	public String		prioridad;
	public String		estado;
	public String		rol_usuario;
	public String		tema;
	public String		trabajo;
	public String		clase;
	public String		tipo_orden_rel;
	public long			numero_orden_rel;
	public long			valor_cobro;
	public long			numero_cliente;
	public String		vencimiento; //Date
	public String		cuenta_conver;
	public String		sucu_usu;	
	public long			lCaso;
	public long 		lNroOrden;

	public ordenDTO(contactoDTO regConta, motContactoDTO regMot, temaTrabajo miTema, long lNroMensaje, String sNroOrden, String sRolOrigen, String sRolDestino, String sAreaDestino, long lCaso, long lNroOrden, clienteDTO miCliente) {
		SimpleDateFormat formater= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		SimpleDateFormat fmtDate= new SimpleDateFormat("yyyy-MM-dd");
		String sFecha=regMot.mo_fecha_vto.substring(0, 10);
		
		this.tipo_orden="AC";
		this.numero_orden=sNroOrden.trim();
		this.mensaje_xnear=lNroMensaje;
		this.servidor=1;
		this.sucursal=miCliente.sucursal.trim();
		this.area_emisora=sRolOrigen;
		this.ident_etapa="RQ";
		this.term_dir ="SALEFORCE";
		this.area_ejecutora=sAreaDestino;
		this.prioridad="N";
		this.estado="0";
		this.rol_usuario=sRolOrigen;
		this.tema=miTema.sCodTema.trim();
		this.trabajo=miTema.sCodTrabajo.trim();
		this.clase="0005";
		this.numero_orden_rel=0;
		this.valor_cobro=0;
		this.numero_cliente=miCliente.numero_cliente;
		
/*		
		try {
			Date dtFecha = formater.parse(regMot.mo_fecha_vto);
			this.vencimiento= fmtDate.format(dtFecha);
		}catch (ParseException e) {
            e.printStackTrace();
        }
*/
		this.vencimiento=sFecha;
		this.cuenta_conver=miCliente.cuenta_conver.trim();
		this.sucu_usu=miCliente.sucursal.trim();
		this.lCaso=lCaso;
		this.lNroOrden=lNroOrden;
	}
	
}
