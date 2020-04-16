package entidades;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class recRecUniDTO {
	public long		numero_cliente;
	public String	tarifa;
	public String	origen;
	public String	tipo_documento;
	public long		nro_reclamo;
	public long		nro_mensaje;
	public String	tipo_reclamo;
	public String	trabajo_requerido;
	public String	motivo_empresa;
	public String	motivo_cliente;
	public Date	fecha_ini_contacto; //DateTime
	public int		plazo;
	public Date	fecha_vto_con;		//DateTime
	public Date	fecha_ingreso_ct;	//DateTime
	public Date	fecha_vto_ct ;		//DateTime
	public String	nombre_cliente;
	public String	telefono;
	public String	cod_calle;
	public String	calle;
	public String	piso;
	public String	dpto;
	public String	nro_puerta;
	public String	cod_entre_calle;
	public String	entre_calle;
	public String	cod_entre_calle2;
	public String	entre_calle2;
	public String	nro_manzana;
	public String	cod_localidad;
	public String	localidad;
	public String	cod_partido;
	public String	partido;
	public String	sucursal_tecnica;
	public String	nom_suc_tecnica;
	public String	cod_subestacion;
	public String	nombre_subestacion;
	public String	alimentador;
	public String	centro_trans;
	public String	cod_agrupacion;
	public String	reclamo_reincident;
	public int		plazo_tecnico;
	public String	etapa;
	public String	sin_tecni;
	public String	rt_nombre;
	public String	rt_telofono;
	public String	tipo_sum;
	public Double	pot_definitiva;
	public String	mot_pot_futura;
	public Double	potencia_hp_solic;
	public String	nivel_tension_sol;
	public Date	fecha_ini_edesur;	//DateTime
	public Date	fecha_excepcion;	//DateTime
	public Date fecha_vto_real_con;
	public String nro_expediente;
	public String sucursal_comercial;
	public String derivado;
	public Date	fecha_reclamo;
	
	//Para Segenes
	public recRecUniDTO(Long lNroMensaje, String sReincidencia, String sCodAgrupa, contactoDTO regConta, motContactoDTO regMot, clienteDTO miCliente, parametrosDTO regPar, tecniDTO regTecni, temaTrabajo miTema) {
		String sTipoReclamo="";
		SimpleDateFormat formater= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		Date dFechaInicio=null;
		String sFechaVtoCT="";

		sFechaVtoCT= formater.format(regPar.dFchaVtoCt);
		
		try {
			dFechaInicio= formater.parse(regConta.co_fecha_inicio);
			sFechaVtoCT= formater.format(regPar.dFchaVtoCt);
		}catch (ParseException e) {
            e.printStackTrace();
        }
		
		switch(regPar.sTipoContacto) {
			case "0":
				sTipoReclamo="Consulta";
				break;
			case "1":
				sTipoReclamo="Reclamo";
				break;
			case "2":
				sTipoReclamo="Requerimiento";
				break;
			case "3":
				sTipoReclamo="Consulta Rapida";
				break;
		}
		
		this.numero_cliente=regConta.co_numero_cliente;
		if(regConta.co_numero_cliente>0) {
			this.tarifa="T1B";
		}else {
			this.tarifa="T1";
		}
		this.origen="MAC";
		this.tipo_documento = "SEGEN";
		this.nro_reclamo = regConta.co_numero;
		this.nro_mensaje = lNroMensaje;
		this.tipo_reclamo="C";
		this.trabajo_requerido = miTema.sDescTema.trim() + "-" + miTema.sDescTrabajo.trim();
		this.motivo_cliente = regMot.mo_cod_motivo;
		this.motivo_empresa=regMot.mo_cod_mot_empresa;
		this.fecha_ini_contacto= dFechaInicio; //regConta.co_fecha_inicio;
		this.plazo = regPar.iPlazo;
		this.fecha_vto_con = regPar.dFechaVto; // regMot.mo_fecha_vto;
		/*
		try {
			this.fecha_vto_con= formater.parse(regMot.mo_fecha_vto);
		}catch (ParseException e) {
            e.printStackTrace();
        }
        */
		this.fecha_vto_ct = regPar.dFchaVtoCt; // sFechaVtoCT;
		
		this.nombre_cliente = miCliente.nombre.trim();
		this.telefono = miCliente.telefono.trim();
		
		if(regTecni != null) {
			if(regTecni.tec_cod_calle != null)
				this.cod_calle = regTecni.tec_cod_calle.trim();
			if(regTecni.tec_nom_calle != null)
				this.calle=regTecni.tec_nom_calle.trim();
			if(regTecni.tec_piso_dir != null)
				this.piso=regTecni.tec_piso_dir.trim();
			if(regTecni.tec_depto_dir != null)
				this.dpto=regTecni.tec_depto_dir.trim();
			if(regTecni.tec_nro_dir != null)
				this.nro_puerta=regTecni.tec_nro_dir.trim();
			if(regTecni.tec_cod_entre != null)
				this.cod_entre_calle=regTecni.tec_cod_entre.trim();
			if(regTecni.tec_entre_calle1 != null)
				this.entre_calle=regTecni.tec_entre_calle1.trim();
			if(regTecni.tec_cod_ycalle != null)
				this.cod_entre_calle2=regTecni.tec_cod_ycalle.trim();
			if(regTecni.tec_entre_calle2 != null)
				this.entre_calle2=regTecni.tec_entre_calle2.trim();
			if(regTecni.tec_manzana != null)
				this.nro_manzana=regTecni.tec_manzana.trim();
			if(regTecni.tec_cod_local != null)
				this.cod_localidad=regTecni.tec_cod_local.trim();
			if(regTecni.tec_localidad != null)
				this.localidad=regTecni.tec_localidad.trim();
			if(regTecni.tec_cod_part != null)
				this.cod_partido=regTecni.tec_cod_part.trim();
			if(regTecni.tec_partido != null)
				this.partido=regTecni.tec_partido.trim();
			if(regTecni.tec_cod_suc != null)
				this.sucursal_tecnica=regTecni.tec_cod_suc.trim();
			if(regTecni.tec_sucursal != null)
				this.nom_suc_tecnica=regTecni.tec_sucursal.trim();
			if(regTecni.tec_subestacion != null)
				this.cod_subestacion=regTecni.tec_subestacion.trim();
			if(regTecni.tec_nom_subest != null)
				this.nombre_subestacion=regTecni.tec_nom_subest.trim();
			if(regTecni.tec_alimentador != null)
				this.alimentador=regTecni.tec_alimentador.trim();
			if(regTecni.tec_centro_trans != null)
				this.centro_trans=regTecni.tec_centro_trans.trim();
		}else {
			if(miCliente.cod_calle != null)
				this.cod_calle = miCliente.cod_calle.trim();
			if(miCliente.nom_calle != null)
				this.calle=miCliente.nom_calle.trim();
			if(miCliente.piso_dir != null)
				this.piso=miCliente.piso_dir.trim();
			if(miCliente.depto_dir != null)
				this.dpto=miCliente.depto_dir.trim();
			if(miCliente.nro_dir != null)
				this.nro_puerta=miCliente.nro_dir.trim();
			if(miCliente.cod_entre != null)
				this.cod_entre_calle=miCliente.cod_entre.trim();
			if(miCliente.nom_entre != null)
				this.entre_calle=miCliente.nom_entre.trim();
			if(miCliente.cod_entre1 != null)
				this.cod_entre_calle2=miCliente.cod_entre1.trim();
			if(miCliente.nom_entre1 != null)
				this.entre_calle2=miCliente.nom_entre1.trim();
			
			this.nro_manzana="";
			if(miCliente.comuna != null)
				this.cod_localidad=miCliente.comuna.trim();
			if(miCliente.nom_comuna != null)
				this.localidad=miCliente.nom_comuna.trim();
			if(miCliente.partido != null)
				this.cod_partido=miCliente.partido.trim();
			if(miCliente.nom_partido != null)
				this.partido=miCliente.nom_partido.trim();
			if(miCliente.sucursal != null)
				this.sucursal_tecnica=miCliente.sucursal.trim();
			if(miCliente.nom_sucursal != null)
				this.nom_suc_tecnica=miCliente.nom_sucursal.trim();
			
			this.cod_subestacion="";
			
			this.nombre_subestacion="";
			
			this.alimentador="";
			
			this.centro_trans="";
		}

		this.cod_agrupacion=sCodAgrupa.trim();
		this.reclamo_reincident=sReincidencia.trim();
		this.plazo_tecnico=regPar.iPlazo;
		this.etapa=sTipoReclamo.trim();
		if(regTecni == null) {
			this.sin_tecni="S";
		}else {
			this.sin_tecni="N";
		}
		if(regTecni.codigo_voltaje=="1") {
			this.tipo_sum="1";
		}else {
			this.tipo_sum="2";
		}
		this.fecha_ini_edesur= dFechaInicio;   //regConta.co_fecha_inicio;
		
	}
	
	//Para Calcom
	public recRecUniDTO(long lNroMensaje, ceReclamoDTO reclamo, reclaTecniProce recla, paramLocalDTO parLocal, tecniDTO regTecni, clienteDTO miCliente) {
		this.numero_cliente = reclamo.getCodCliente();
		this.etapa="Reclamo";
		this.tarifa=reclamo.getTarifa();
		this.origen ="MAC";
		this.tipo_documento=reclamo.getTipoDocumento();
		this.nro_reclamo = reclamo.getReclamo();
		this.nro_mensaje=lNroMensaje;
		this.tipo_reclamo="E";
		this.motivo_empresa= Integer.toString(reclamo.getCodMotivo());
		this.motivo_cliente = Integer.toString( reclamo.getCodTema());
		this.fecha_ini_contacto = reclamo.getFechaIgEdes();
		this.plazo = parLocal.plazo;
		this.fecha_vto_con = reclamo.getFechaVto();
		this.fecha_ingreso_ct = reclamo.getFechaIgEdes();
		this.fecha_vto_ct = reclamo.getFechaVto();
		this.nombre_cliente = miCliente.nombre;
		this.telefono = miCliente.telefono;
		
		if(regTecni != null) {
			if(regTecni.tec_cod_calle != null)
				this.cod_calle = regTecni.tec_cod_calle.trim();
			if(regTecni.tec_nom_calle != null)
				this.calle=regTecni.tec_nom_calle.trim();
			if(regTecni.tec_piso_dir != null)
				this.piso=regTecni.tec_piso_dir.trim();
			if(regTecni.tec_depto_dir != null)
				this.dpto=regTecni.tec_depto_dir.trim();
			if(regTecni.tec_nro_dir != null)
				this.nro_puerta=regTecni.tec_nro_dir.trim();
			if(regTecni.tec_cod_entre != null)
				this.cod_entre_calle=regTecni.tec_cod_entre.trim();
			if(regTecni.tec_entre_calle1 != null)
				this.entre_calle=regTecni.tec_entre_calle1.trim();
			if(regTecni.tec_cod_ycalle != null)
				this.cod_entre_calle2=regTecni.tec_cod_ycalle.trim();
			if(regTecni.tec_entre_calle2 != null)
				this.entre_calle2=regTecni.tec_entre_calle2.trim();
			if(regTecni.tec_manzana != null)
				this.nro_manzana=regTecni.tec_manzana.trim();
			if(regTecni.tec_cod_local != null)
				this.cod_localidad=regTecni.tec_cod_local.trim();
			if(regTecni.tec_localidad != null)
				this.localidad=regTecni.tec_localidad.trim();
			if(regTecni.tec_cod_part != null)
				this.cod_partido=regTecni.tec_cod_part.trim();
			if(regTecni.tec_partido != null)
				this.partido=regTecni.tec_partido.trim();
			if(regTecni.tec_cod_suc != null)
				this.sucursal_tecnica=regTecni.tec_cod_suc.trim();
			if(regTecni.tec_sucursal != null)
				this.nom_suc_tecnica=regTecni.tec_sucursal.trim();
			if(regTecni.tec_subestacion != null)
				this.cod_subestacion=regTecni.tec_subestacion.trim();
			if(regTecni.tec_nom_subest != null)
				this.nombre_subestacion=regTecni.tec_nom_subest.trim();
			if(regTecni.tec_alimentador != null)
				this.alimentador=regTecni.tec_alimentador.trim();
			if(regTecni.tec_centro_trans != null)
				this.centro_trans=regTecni.tec_centro_trans.trim();
		}else {
			if(miCliente.cod_calle != null)
				this.cod_calle = miCliente.cod_calle.trim();
			if(miCliente.nom_calle != null)
				this.calle=miCliente.nom_calle.trim();
			if(miCliente.piso_dir != null)
				this.piso=miCliente.piso_dir.trim();
			if(miCliente.depto_dir != null)
				this.dpto=miCliente.depto_dir.trim();
			if(miCliente.nro_dir != null)
				this.nro_puerta=miCliente.nro_dir.trim();
			if(miCliente.cod_entre != null)
				this.cod_entre_calle=miCliente.cod_entre.trim();
			if(miCliente.nom_entre != null)
				this.entre_calle=miCliente.nom_entre.trim();
			if(miCliente.cod_entre1 != null)
				this.cod_entre_calle2=miCliente.cod_entre1.trim();
			if(miCliente.nom_entre1 != null)
				this.entre_calle2=miCliente.nom_entre1.trim();
			
			this.nro_manzana="";
			if(miCliente.comuna != null)
				this.cod_localidad=miCliente.comuna.trim();
			if(miCliente.nom_comuna != null)
				this.localidad=miCliente.nom_comuna.trim();
			if(miCliente.partido != null)
				this.cod_partido=miCliente.partido.trim();
			if(miCliente.nom_partido != null)
				this.partido=miCliente.nom_partido.trim();
			if(miCliente.sucursal != null)
				this.sucursal_tecnica=miCliente.sucursal.trim();
			if(miCliente.nom_sucursal != null)
				this.nom_suc_tecnica=miCliente.nom_sucursal.trim();
			
			this.cod_subestacion="";
			
			this.nombre_subestacion="";
			
			this.alimentador="";
			
			this.centro_trans="";
		}
		
		this.trabajo_requerido = reclamo.getDescriTema();
		this.cod_agrupacion = recla.getCodAgrupacion();
		this.plazo_tecnico = parLocal.plazo;
		this.fecha_vto_real_con = reclamo.getFechaVto();
		this.nro_expediente = reclamo.getNDocumEnre();
		this.sucursal_comercial = miCliente.sucursal;
		
		if(regTecni == null) {
			this.sin_tecni="S";
		}else {
			this.sin_tecni="N";
		}
		
		this.fecha_ini_edesur = reclamo.getFechaIgEdes();
		
		if(reclamo.getTipoDocumento().equals("CALCOMSVP")) {
			this.derivado = "operaciones";
		}
		
		this.fecha_reclamo = reclamo.getFechaIgEdes();
	}
}
