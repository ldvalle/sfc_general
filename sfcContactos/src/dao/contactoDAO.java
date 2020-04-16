package dao;

import conectBD.UConnection;
import entidades.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.Date;
import java.sql.Timestamp;

//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.*;

public class contactoDAO {

	public Collection<interfaceDTO> getLstSolicitudes(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Vector<interfaceDTO> miLista = new Vector<interfaceDTO>();
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_SOLICITUDES);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				interfaceDTO miReg = new interfaceDTO();
				
				miReg.caso = rs.getLong("caso");
				miReg.nro_orden = rs.getLong("nro_orden");
				miReg.tipo_sol = rs.getString("tipo_sol");
				miReg.data_in = "[" + rs.getString("data_in").trim() + "]";
				
				miLista.add(miReg);
			}
			
		}catch(SQLException ex){
			
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				System.out.println("No hay filas en cursor ppal.");
				return miLista;
			}
		}
		
		return miLista;
	}
	
	public Collection<interfaceDTO> getLstContactos(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Vector<interfaceDTO> miLista = new Vector<interfaceDTO>();
		
		String sql = query1();

		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				interfaceDTO miReg = new interfaceDTO();
				
				miReg.caso = rs.getLong("caso");
				miReg.nro_orden = rs.getLong("nro_orden");
				miReg.data_in = "[" + rs.getString("data_in").trim() + "]";
				
				miLista.add(miReg);
			}
			
		}catch(SQLException ex){
			
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				System.out.println("No hay filas en cursor ppal.");
				return miLista;
				//ex.printStackTrace();
				//throw new RuntimeException(ex);
			}
		}
		
		return miLista;
	}
	
	public Date getFechaActual() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Date miFechaActual = null;
		String sql = "SELECT TODAY fecha FROM dual "; 
		
		try{
			con = UConnection.getConnection();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				miFechaActual = rs.getDate("fecha");
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		
		return miFechaActual;
	}
	
	public Collection<Date> getLstFeriados(Date d){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		java.sql.Date fechaBD = (java.sql.Date)d;
		
		//Collection<Date> miLista = null;
		Vector<Date> miLista = new Vector<Date>();
		
		String sql = query4(); 
		
		try{
			con = UConnection.getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setDate(1, fechaBD);
			rs = pstm.executeQuery();
			
			while(rs.next()){
				Date dFecha = rs.getDate("fecha");
				
				miLista.add(dFecha);
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		
		return miLista;
	}
	
	public parametrosDTO getParametros(String motCliente, String motEmpresa, String sucursal) {
		parametrosDTO regSal = new parametrosDTO();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try{
			String sql = query2();
			con = UConnection.getConnection();
			pstm = con.prepareStatement(sql);
			
			pstm.setString(1, motCliente);
			pstm.setString(2, motEmpresa);
			
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				regSal.iPlazo = rs.getInt("te_plazo_com");
				regSal.sTipoContacto = rs.getString("tipo_cto");
			}
			
			rs=null;
			pstm=null;
			
			sql = query3();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, sucursal);
			
			rs = pstm.executeQuery();
			if(rs.next()) {
				regSal.sSucurPadre = rs.getString("su_cod_superior");
			}
			
			
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}		
		return regSal;
	}
	
	public clienteDTO getCliente(long nroCliente) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		clienteDTO miClie = new clienteDTO();
		
		int iFilas=0;
		
		try{
			con = UConnection.getConnection();
			pstm = con.prepareStatement(SEL_CLIENTE);
			pstm.setLong(1, nroCliente);
						
			rs = pstm.executeQuery();
			
			if(rs.next()) {
			
				miClie.numero_cliente = rs.getLong("numero_cliente");
				miClie.nombre = rs.getString("nombre");
				miClie.tip_doc = rs.getString("tip_doc");
				miClie.nro_doc = rs.getDouble("nro_doc");
				miClie.origen_doc = rs.getString("origen_doc");
				miClie.provincia = rs.getString("provincia");
				miClie.nom_provincia = rs.getString("nom_provincia");
				miClie.sucursal = rs.getString("sucursal");
				miClie.sector = rs.getInt("sector");
				miClie.partido = rs.getString("partido");
				miClie.nom_partido = rs.getString("nom_partido");
				miClie.comuna = rs.getString("comuna");
				miClie.nom_comuna = rs.getString("nom_comuna");
				miClie.cod_calle = rs.getString("cod_calle");
				miClie.nom_calle = rs.getString("nom_calle");
				miClie.nro_dir = rs.getString("nro_dir");
				miClie.piso_dir = rs.getString("piso_dir");
				miClie.depto_dir = rs.getString("depto_dir");
				miClie.cod_postal = rs.getInt("cod_postal");
				miClie.telefono = rs.getString("telefono");
				miClie.tarifa = rs.getString("tarifa");
				miClie.tipo_iva = rs.getString("tipo_iva");
				miClie.tipo_cliente = rs.getString("tipo_cliente");
				miClie.rut = rs.getString("rut");
				miClie.cuenta_conver = rs.getString("cuenta_conver").trim();
				miClie.nom_sucursal=rs.getString("nom_sucursal");
				miClie.cod_entre=rs.getString("cod_entre");
				miClie.nom_entre=rs.getString("nom_entre");
				miClie.cod_entre1=rs.getString("cod_entre1");
				miClie.nom_entre1=rs.getString("nom_entre1");
				miClie.zona = rs.getInt("zona");
				miClie.correlativo_ruta = rs.getLong("correlativo_ruta");
				miClie.numero_medidor=rs.getLong("numero_medidor");
				miClie.marca_medidor = rs.getString("marca_medidor");
				miClie.modelo_medidor = rs.getString("modelo_medidor");
				miClie.potencia_contrato = rs.getDouble("potencia_contrato");
				miClie.tipo_empalme = rs.getString("tipo_empalme");
				miClie.obs_dir = rs.getString("obs_dir");
				miClie.info_adic_lectura = rs.getString("info_adic_lectura");
				miClie.barrio = rs.getString("barrio");
				miClie.nom_barrio = rs.getString("nom_barrio");
				miClie.sucursal_padre=rs.getString("suc_padre");
				iFilas++;
			}

			if(iFilas==0) {
				System.out.println("No existe cliente " + nroCliente);
			}
				
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		
		return miClie;
	}
	
	
	public Boolean genContacto(interfaceDTO regInt, contactoDTO regConta, motContactoDTO regMot, Collection<observaCtoDTO> vecObserva, clienteDTO miCliente, parametrosDTO regPar, tecniDTO regTecni) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql, sRolOrigen, sRolDestino, sAreaDestino, sMotRST;
		String sArea= "0000"; //"SIC0";
		String sNroOrden="";
		long lNroContacto;
		long lNroMensaje;
		int i, iValor;
		SimpleDateFormat fechaF = new SimpleDateFormat("yy/MM/dd");
		SimpleDateFormat fmtDate= new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicio = new Date();
		String sFecha=fechaF.format(fechaInicio);
		String sDireDestino="";
		temaTrabajo miTema = null;
		Timestamp tFechaInicio;
		String sCodAgrupa="";
		String sReincidencia="";
		String sDescriEstado="";
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			
			//Buscar Nro.Contacto
			sql = query6();
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
			
			pstm=null;
			sql = query7();
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			if(!rs.next()) {
				System.out.println("No se recuperó Nro.de Contacto");
				return false;
			}
			
			lNroContacto = rs.getLong(1);
			
			pstm=null;
			rs=null;
			
			regConta.co_numero=lNroContacto;
			regMot.mo_co_numero=lNroContacto;
			
			//Grabar Contacto
			sql = query8(regConta);
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();

			pstm=null;
System.out.println("Grabo contacto");			
			//Grabar Motivo
			sql = query9(regMot);
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
			
			pstm=null;
System.out.println("Grabo motivo");			
			//Grabar observaciones
			for(observaCtoDTO regObs :vecObserva ) {
				sql = query10(lNroContacto, regObs);
				pstm = con.prepareStatement(sql);
				pstm.executeUpdate();
				pstm=null;
			}
			pstm=null;
			sDescriEstado="Contacto Generado";
System.out.println("Grabo observaciones");

			//********** AREA SEGEN ********
			
			pstm = con.prepareStatement(GET_INI_CONTACTO);
			pstm.setLong(1, regConta.co_numero);
			pstm.setString(2, regConta.co_suc_contacto);
			
			rs = pstm.executeQuery();
			if(rs.next()) {
				tFechaInicio = rs.getTimestamp(1);
			}else {
				System.out.println("No se recuperó el Contacto");
				return false;
			}

			//Obtener Tema/Trabajo
			miTema=getTemaTrabajo(regMot.mo_cod_motivo.trim(), regMot.mo_cod_mot_empresa.trim());
			
			if(miTema==null) {
				System.out.println("No se encontró Tema/Trabajo para motivo Cliente " + regMot.mo_cod_motivo + " motivo Empresa " + regMot.mo_cod_mot_empresa );
				return false;
			}
			
			//Obtener Nro.Mensaje
			lNroMensaje = getNroMensaje();

			if(lNroMensaje<=0) {
				System.out.println("No se recuperó Nro.de Mensaje");
				return false;
			}
			
			//Obtener Destino
			//sRolOrigen = "E17317";
			sRolOrigen = "SALESFORCE";
			sDireDestino = getDestino(regConta, regMot, miTema);
			
			String partes[] = sDireDestino.split(Pattern.quote("|"));
			sRolDestino = partes[0];
			sAreaDestino = partes[1];
			sMotRST= partes[2];

			if(partes.length == 4) {
				sCodAgrupa=partes[3];
			}

			//Armar Mensaje
			mensajeDTO regMen = new mensajeDTO(lNroMensaje, fechaInicio, sRolOrigen, sRolDestino, sAreaDestino, "M_SEGEN", miCliente, regConta, regMot, miTema, regPar, vecObserva);
			if(miTema.sGeneraOT.equals("OT")) {
				regMen.sTexto += getOTActivas(regConta.co_numero_cliente);
			}
			
			//mensajeDTO regMen = new mensajeDTO(lNroMensaje, regConta.co_numero, regConta.co_numero_cliente, Integer.parseInt(regMot.mo_cod_motivo), Integer.parseInt(regMot.mo_cod_mot_empresa), sFecha, sRolOrigen, sRolDestino, sAreaDestino, "M_SEGEN");

			//Enviar Mensaje
			//pstm = con.prepareStatement(sql);
			pstm = con.prepareStatement(XPRO_ENVIAR);
			pstm.setLong(1, regMen.lMensaje);
			pstm.setString(2, regMen.sProced.trim());
			
			pstm.setString(3, regMen.sEtapa.trim());
			pstm.setString(4, regMen.sPrivacidad.trim());
			pstm.setString(5, regMen.sUrgencia.trim());
			pstm.setString(6, regMen.sEncriptado.trim());
			pstm.setString(7, regMen.sReferencia.trim());
			pstm.setString(8, regMen.sRolCon.trim());
			pstm.setString(9, regMen.sRolOrg.trim());
			pstm.setString(10, regMen.sRolDst.trim());
			pstm.setInt(11, regMen.iEmpCon);
			pstm.setInt(12, regMen.iEmpOrg);
			pstm.setInt(13, regMen.iEmpDst);
			pstm.setString(14, regMen.sTexto.trim()); 
			
			pstm.execute();
			pstm=null;
System.out.println("Grabo mensaje " + regMen.lMensaje);			
			//Obtiene nro.de Orden
			sNroOrden = getNroOrden();
			
			//Prepara Orden
			ordenDTO regOrden = new ordenDTO(regConta, regMot, miTema, lNroMensaje, sNroOrden, sRolOrigen, sRolDestino, sAreaDestino, regInt.caso, regInt.nro_orden, miCliente);
			
			//Graba Orden
			pstm = con.prepareStatement(SET_ORDEN);
			pstm.setString(1, regOrden.numero_orden.trim());
			pstm.setLong(2, regOrden.mensaje_xnear);
			pstm.setString(3, regOrden.sucursal);
			pstm.setString(4, regOrden.area_emisora.trim());
			pstm.setString(5, regOrden.area_ejecutora.trim());
			pstm.setString(6, regOrden.rol_usuario.trim());


			pstm.setString(7, regOrden.tema.trim());
			pstm.setString(8, regOrden.trabajo.trim());
			pstm.setLong(9, regOrden.numero_cliente);

			pstm.setString(10,  regOrden.vencimiento);
			
			pstm.setString(11, regOrden.cuenta_conver.trim());
			pstm.setString(12, regOrden.sucu_usu.trim());
			pstm.setLong(13, regOrden.lCaso);
			pstm.setLong(14, regOrden.lNroOrden);
			
			pstm.executeUpdate();
			
			pstm=null;
System.out.println("Grabo Orden");			
			//Graba Etapa Orden
			pstm = con.prepareStatement(SET_ETAPA_ORDEN);
			pstm.setLong(1, regOrden.mensaje_xnear);

			pstm.executeUpdate();
			
			pstm=null;
System.out.println("Graba Etapa Orden");			
			//grabar cto_segen
			pstm = con.prepareStatement(SET_CTO_SEGEN);
			pstm.setLong(1, regOrden.mensaje_xnear);
			pstm.setString(2, regOrden.numero_orden.trim());
			pstm.setString(3, regMot.mo_cod_mot_empresa.trim());
			pstm.setString(4,regMot.mo_cod_motivo.trim());
			pstm.setLong(5, regConta.co_numero);
			pstm.setString(6, regConta.co_suc_contacto.trim());
			pstm.setTimestamp(7, tFechaInicio);	//fecha creacion
			pstm.setString(8, sRolOrigen);
			pstm.setString(9, sArea); //SIC0
			//pstm.setDate(10, (java.sql.Date) regOrden.vencimiento);  //Vencimiento Real
			//pstm.setDate(11, (java.sql.Date) regOrden.vencimiento);	//Vencimiento
			pstm.setString(10, regMot.mo_fecha_vto);
			pstm.setString(11, regMot.mo_fecha_vto);
			pstm.setTimestamp(12, tFechaInicio);//fecha Modif
			pstm.setString(13, sRolDestino);
			pstm.setString(14, regOrden.tema);
			pstm.setString(15, regOrden.trabajo);
			
			pstm.executeUpdate();
			sDescriEstado="Segen Enviado";
			
			pstm=null;
			
System.out.println("Grabo Contacto - segen");			
			
			//si la carpeta es de Recla Tecni Proce
			if(partes.length == 4) {
System.out.println("Va a hacer Tecni Proce");			
				// Ver si hay reiteracion
				pstm = con.prepareStatement(SEL_REINCIDENCIA);
				pstm.setLong(1, regOrden.mensaje_xnear);
				rs=pstm.executeQuery();
				if(rs.next()) {
					iValor=rs.getInt(1);
					if(iValor>0) {
						sReincidencia="S";
					}else{
						sReincidencia="N";
					}
				}else {
					sReincidencia="N";
				}
				
				pstm=null;
				rs=null;

				// Preparar rec_rec_uni
				recRecUniDTO miRecUni = new recRecUniDTO(lNroMensaje, sReincidencia, sCodAgrupa, regConta, regMot, miCliente, regPar, regTecni, miTema);
				
				// Grabar en rec_rec_uni
				pstm=con.prepareStatement(INS_REC_REC_UNI);
				pstm.setLong(1, miRecUni.numero_cliente);
				pstm.setString(2, miRecUni.tarifa);
				pstm.setLong(3, miRecUni.nro_reclamo);
				pstm.setLong(4, miRecUni.nro_mensaje);
				pstm.setString(5, miRecUni.trabajo_requerido);
				pstm.setString(6, miRecUni.motivo_empresa);
				pstm.setString(7, miRecUni.motivo_cliente);
				//java.sql.Timestamp tFechaBDini = (java.sql.Timestamp)miRecUni.fecha_ini_contacto;
				//pstm.setString(8, miRecUni.fecha_ini_contacto);
				pstm.setTimestamp(8, tFechaInicio);
				pstm.setInt(9, miRecUni.plazo);
				//java.sql.Timestamp tFechaVtoCon = (java.sql.Timestamp)miRecUni.fecha_vto_con;
		
				pstm.setTimestamp(10, new Timestamp(miRecUni.fecha_vto_con.getTime()));
				pstm.setTimestamp(11, new Timestamp(miRecUni.fecha_vto_ct.getTime()));
				
				pstm.setString(12, miRecUni.nombre_cliente);
				pstm.setString(13, miRecUni.telefono);
				pstm.setString(14, miRecUni.cod_calle);
				pstm.setString(15, miRecUni.calle);
				pstm.setString(16, miRecUni.piso);
				pstm.setString(17, miRecUni.dpto);
				pstm.setString(18, miRecUni.nro_puerta);
				pstm.setString(19, miRecUni.cod_entre_calle);
				pstm.setString(20, miRecUni.entre_calle);
				pstm.setString(21, miRecUni.cod_entre_calle2);
				pstm.setString(22, miRecUni.entre_calle2);
				pstm.setString(23, miRecUni.nro_manzana);
				pstm.setString(24, miRecUni.cod_localidad);
				pstm.setString(25, miRecUni.localidad);
				pstm.setString(26, miRecUni.cod_partido);
				pstm.setString(27, miRecUni.partido);
				pstm.setString(28, miRecUni.sucursal_tecnica);
				pstm.setString(29, miRecUni.nom_suc_tecnica);
				pstm.setString(30, miRecUni.cod_subestacion);
				pstm.setString(31, miRecUni.nombre_subestacion);
				pstm.setString(32, miRecUni.alimentador);
				pstm.setString(33, miRecUni.centro_trans);
				pstm.setString(34, miRecUni.cod_agrupacion);
				pstm.setString(35, miRecUni.reclamo_reincident);
				pstm.setInt(36, miRecUni.plazo_tecnico);
				pstm.setString(37, miRecUni.etapa);
				pstm.setString(38, miRecUni.sin_tecni);
				pstm.setString(39, miRecUni.tipo_sum);
				pstm.setTimestamp(40, tFechaInicio);
				
				pstm.executeUpdate();
				
				pstm=null;
System.out.println("Grabo rec_rec_uni");				
			}
			
			//Generar la OT
			if(miTema.sGeneraOT.equals("OT")) {
System.out.println("Entro en OT");				
				String sNroCB = getNroCb(miCliente.marca_medidor, miCliente.modelo_medidor);
				ot_macDTO miOtMac = new ot_macDTO(lNroMensaje, miCliente, regTecni, miTema, regPar.dFchaVtoCt, sAreaDestino, sRolOrigen, sArea);
				// Inserta en OT_MAC
				pstm=con.prepareStatement(INS_OT_MAC);
				
				pstm.setLong(1, miOtMac.numero_cliente);
				pstm.setLong(2, miOtMac.mensaje_xnear);
				pstm.setString(3, miOtMac.proced.trim());
				pstm.setString(4, miOtMac.envia_sap.trim());
				pstm.setString(5, miOtMac.estado.trim());
				pstm.setString(6, miOtMac.status.trim());
				pstm.setString(7, miOtMac.sucursal_padre.trim());
				pstm.setString(8, miOtMac.sucursal.trim());
				pstm.setInt(9, miOtMac.sector);
				pstm.setInt(10, miOtMac.zona);
				pstm.setLong(11, miOtMac.correlativo_ruta);
				pstm.setString(12, miOtMac.tipo_traba.trim());
				pstm.setString(13, miOtMac.area_interloc.trim());
				pstm.setString(14, miOtMac.motivo.trim());
				pstm.setString(15, miOtMac.rol_ejecuta.trim());
				pstm.setString(16, miOtMac.area_ejecuta.trim());
				pstm.setDouble(17, miOtMac.potencia);
				pstm.setString(18, miOtMac.tension.trim());
				pstm.setString(19, miOtMac.acometida.trim());
				pstm.setString(20, miOtMac.toma.trim());
				pstm.setString(21, miOtMac.conexion.trim());
				pstm.setTimestamp(22, new Timestamp(miOtMac.fecha_vto.getTime()));
				
				pstm.executeUpdate();
				
				pstm=null;
System.out.println("Grabo ot_mac");				
				long lNroOrdenOT = getNroOt(miOtMac.mensaje_xnear);
				
				//Inserta en OT_HISEVEN
				pstm=con.prepareStatement(INS_OT_HISEVEN);
				
				pstm.setLong(1, lNroOrdenOT);
				pstm.setLong(2, miOtMac.numero_cliente);
				pstm.setString(3, miOtMac.status.trim());

				pstm.executeUpdate();
				
				pstm=null;
System.out.println("Grabo ot_hiseven");				
				//RecuperaPrecintos
				String[] lstPrecintos = getPrecintos(miCliente).toArray(new String[0]);
				pstm=null;
				
				//OT_MAC_SAP
				
				ot_mac_sapDTO miOtMacSap = new ot_mac_sapDTO(lNroOrdenOT, lNroMensaje, miCliente, regTecni, miTema, regPar.dFchaVtoCt, sAreaDestino, sRolOrigen, sArea, lstPrecintos, sNroCB);
				String sCodBar = miOtMacSap.cod_barra.substring(0,2);
				
				pstm=con.prepareStatement(INS_OT_MAC_SAP);
				
				pstm.setString(1, miOtMacSap.tipo_ifaz);
				pstm.setString(2, miOtMacSap.nro_orden);
				pstm.setString(3, miOtMacSap.tipo_traba);
				pstm.setString(4, miOtMacSap.sucursal);
				pstm.setString(5, miOtMacSap.area_ejecuta);
				pstm.setString(6, miOtMacSap.motivo);
				pstm.setString(7, miOtMacSap.obs_dir);
				pstm.setString(8, miOtMacSap.obs_lectu);
				pstm.setString(9, miOtMacSap.obs_segen);
				pstm.setString(10, miOtMacSap.area_interloc);
				pstm.setLong(11, miOtMacSap.nro_medidor);
				pstm.setString(12, miOtMacSap.marca_med);
				pstm.setString(13, miOtMacSap.modelo_med);
				pstm.setString(14, miOtMacSap.cla_servi);
				pstm.setDouble(15, miOtMacSap.potencia);
				pstm.setString(16, miOtMacSap.tension);
				pstm.setString(17, miOtMacSap.acometida);
				pstm.setString(18, miOtMacSap.toma);
				pstm.setString(19, miOtMacSap.conexion);
				pstm.setString(20, miOtMacSap.pre1_ubic);
				pstm.setString(21, miOtMacSap.pre2_ubic);
				pstm.setString(22, miOtMacSap.pre3_ubic);
				pstm.setString(23, miOtMacSap.ruta_lectura);
				pstm.setString(24, miOtMacSap.nombre_cli);
				pstm.setLong(25, miOtMacSap.nro_cli);
				pstm.setString(26, miOtMacSap.nom_entre);
				pstm.setString(27, miOtMacSap.nom_entre1);
				pstm.setString(28, miOtMacSap.telefono);
				pstm.setString(29, miOtMacSap.nom_calle);
				pstm.setString(30, miOtMacSap.nro_dir);
				pstm.setString(31, miOtMacSap.nom_partido);
				pstm.setString(32, miOtMacSap.piso_dir);
				pstm.setString(33, miOtMacSap.depto_dir);
				pstm.setString(34, miOtMacSap.nom_comuna);
				pstm.setInt(35, miOtMacSap.cod_postal);
				pstm.setTimestamp(36, new Timestamp(miOtMacSap.fecha_vto.getTime()));
				pstm.setString(37, sCodBar);
				pstm.setString(38, miOtMacSap.rol_creador);
				pstm.setString(39, miOtMacSap.nombre_rol);
				pstm.setString(40, miOtMacSap.proced);
				pstm.setLong(41, miOtMacSap.nro_proced);
				
				
				int iFilas = pstm.executeUpdate();
				sDescriEstado="OT Generada";
				
				pstm=null;
				
System.out.println("Grabo OT_MAC_SAP filas " + iFilas);
				
			}
			
			//Upd. tabla interface
			String sInfoEnlace=getInfoEnlace(lNroContacto, lNroMensaje, sNroOrden);
			sql = query11();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, sDescriEstado);
			pstm.setString(2, sInfoEnlace);
			pstm.setLong(3, regInt.caso);
			pstm.setLong(4, regInt.nro_orden);
			pstm.executeUpdate();
	
System.out.println("Actualizo interface");

			//con.commit();
			
			System.out.println("Se Grabó Contacto: " + lNroContacto);
			System.out.println("Se Grabó Mensaje: " + lNroMensaje);
			System.out.println("Se Grabó Orden: " + sNroOrden);
			
		}catch(SQLException sqle){
			System.out.println("genContacto()");
			System.out.println("Se aborta pedido porque no se pudo generar contacto");
			System.out.println("Caso " + regInt.caso + " Orden " + regInt.nro_orden);
			
			System.out.println("Código de Error: " + sqle.getErrorCode() + "\n" +
					    "SLQState: " + sqle.getSQLState() + "\n" +
					    "Mensaje: " + sqle.getMessage() + "\n");			
			try {
				con.rollback();
				System.out.println("Rollback establecido");
			}catch(SQLException exSQL) {
				exSQL.printStackTrace();
			}
			
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}		
			
		
		return true;
	}
	
	public motivosTrafoDTO getMotivosTrafo(String Motivo, String Submotivo) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		motivosTrafoDTO miReg = new motivosTrafoDTO();
		
		miReg.codMotivo = Motivo;
		miReg.codSubmotivo=Submotivo;
		
		miReg.motCliente=Motivo;
		miReg.motEmpresa=Submotivo;

		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_MOTIVOS_DESC);
			pstm.setString(1, Motivo);
			pstm.setString(2, Submotivo);
			
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				miReg.motCliente = rs.getString(1);
				miReg.motEmpresa = rs.getString(2);
			}else {
				System.out.println("No se encontraron motivos para motivo " + Motivo + " submotivo " + Submotivo);
			}
			
			
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
/*		
		try{
			con = UConnection.getConnection();
			pstm = con.prepareStatement(sql);
			pstm.setString(1, Motivo);
			pstm.setString(2, Submotivo);
			
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				miReg.motCliente = rs.getString("mot_cliente");
				miReg.motEmpresa = rs.getString("mot_empresa");
			}else {
				System.out.println("No se encontraron motivos para motivo " + Motivo + " submotivo " + Submotivo);
			}
			
			
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		*/
		return miReg;
	}
	
	private long getNroMensaje() {
		Connection con = null;
		PreparedStatement pstm = null;
		CallableStatement cal = null;
		
		ResultSet rs = null;
		
		String sEstado;
		long lNroMensaje=0;
		String sEtapa;
		String sql = "{call xpro_crear(1, 'M_SEGEN')}";
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			cal = con.prepareCall(sql);
			rs = cal.executeQuery();

			if(rs.next()){
				sEstado = rs.getString(1);
				lNroMensaje = rs.getLong(2);
				sEtapa = rs.getString(3);
			}
		}catch(Exception ex){
			System.out.println("getNroMensaje()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(cal != null) cal.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	
		return lNroMensaje;
	}

	private long getNroOt(long lNroMensaje) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		long lNroOt=0;
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_NRO_OT);
			pstm.setLong(1, lNroMensaje);

			rs = pstm.executeQuery();

			if(rs.next()){
				lNroOt = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("getNroOt()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
					}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		return lNroOt;
	}
	
	private String getRolDestino(String sProcedimiento, String sSucursal) {
		String sRol="";
		
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
	
		String sql;
		
		sql = query13(sProcedimiento, sSucursal);
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(sql);

			pstm.setString(1, sProcedimiento);
			pstm.setString(2, sSucursal);

			rs = pstm.executeQuery();
			
			while(rs.next()){
				sRol = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("getRolDestino()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}		
		return sRol;
	}

	private temaTrabajo getTemaTrabajo(String sMotClie, String sMotEmpre) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		temaTrabajo miTema = new temaTrabajo();

		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_TEMA_TRABAJO);
			pstm.setString(1, sMotClie);
			pstm.setString(2, sMotEmpre);
			rs = pstm.executeQuery();
		
			if(rs.next()){
				miTema.sCodTema=rs.getString(1);
				miTema.sCodTrabajo=rs.getString(2);
				miTema.sDescTema=rs.getString(3).trim();
				miTema.sDescTrabajo=rs.getString(4).trim();
				miTema.sGeneraOT = rs.getString(5).trim();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		
		return miTema;
	}
	
	private String getNroOrden() {
		String sNroOrden="";
		String sAux="";
		long	lAux;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(GET_NRO_ORDEN);
			rs = pstm.executeQuery();
			
			if(rs.next()){
				sAux=rs.getString(1);
				sAux.substring(5, sAux.length());
				lAux=Long.parseLong(sAux);
				lAux++;
				String padded="000000".substring(Long.toString(lAux).length()) + lAux;
				sNroOrden= "SIC0" + padded; 
				
				pstm=null;
				rs=null;
				
				pstm = con.prepareStatement(SET_NRO_ORDEN);
System.out.println("nro orden a insertar en numao [" + sNroOrden + "]");				
				pstm.setString(1, sNroOrden);
				pstm.executeUpdate();
				pstm=null;
			}
			
		}catch(Exception ex){
			System.out.println("Error al obtener Nro.de orden. getNroOrden()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		
		return sNroOrden;
	}
	
	private String getDestino(contactoDTO regCto, motContactoDTO regMot, temaTrabajo regTema) {
		String sDatos="";
		int iValor=0;
		String sSucur="";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try{
			
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_DATA_DESTINO);
			pstm.setString(1, regMot.mo_cod_motivo.trim());
			pstm.setString(2, regMot.mo_cod_mot_empresa.trim());
			pstm.setString(3, regTema.sCodTema.trim());
			pstm.setString(4, regTema.sCodTrabajo.trim());
	
			rs = pstm.executeQuery();
			
			if(rs.next()){
				sDatos = rs.getString(1).trim() + "|"; //rol
				sDatos+= rs.getString(2).trim()+"|";  //area
				sDatos+= rs.getString(3).trim()+ "|"; //Tipo (rst)
				sDatos+= rs.getString(4).trim()+"|"; //Cod Agrupacion
			}else {
				pstm=null;
				rs=null;
				//Sucursal del Centro Operativo del Cliente
				pstm = con.prepareStatement(SEL_SUCUR_CLIE);
				pstm.setString(1, regCto.co_suc_cli.trim());
				rs=pstm.executeQuery();

				if(rs.next()) {
					iValor = rs.getInt(1);
				}else {
					System.out.println("Error en getDestino() para contacto/segen ");
					System.out.println("No se pudo encontrar sucursal para CO " + regCto.co_suc_cli);
					System.exit(1);
				}
				switch(iValor) {
					case 2:
						sSucur="CAPITAL";
						break;
					case 3:
						sSucur="RIBERA";
						break;
					case 4:
						sSucur="ROCA";
						break;
				}
				
				pstm=null;
				rs=null;
				
				//El rol destino
				if(regTema.sGeneraOT.equals("OT")) {
					pstm = con.prepareStatement(SEL_ROL_DESTINO_OT);
					pstm.setString(1, regCto.co_suc_cli.trim());
				}else {
					pstm = con.prepareStatement(SEL_ROL_DESTINO);
					pstm.setString(1, regMot.mo_cod_motivo.trim());
					pstm.setString(2, regMot.mo_cod_mot_empresa.trim());
				}
				rs=pstm.executeQuery();
				
				if(rs.next()) {
					if(regTema.sGeneraOT.equals("OT")) {
						sDatos = rs.getString(1).trim();
					}else {
						sDatos = rs.getString(1).trim() + sSucur.trim();
					}
				}else {
					System.out.println("No se pudo encontrar Rol destino para el SEGEN");
				}
				
				//El Area de ese rol
				pstm=null;
				rs=null;
				
				//El Area destino
				pstm = con.prepareStatement(SEL_AREA_DESTINO);
				pstm.setString(1, sDatos.trim());
				rs=pstm.executeQuery();
				
				if(rs.next()) {
					sDatos += "|" + rs.getString(1).trim() + "|N";
				}else {
					System.out.println("No se pudo encontrar Area destino para el SEGEN");
				}
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}

		
		return sDatos;
	}
	
	public tecniDTO getTecni(long lNroCliente) {
		tecniDTO miTecni = new tecniDTO();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_TECNI);
			pstm.setLong(1, lNroCliente);
			rs = pstm.executeQuery();
			
			if(rs.next()){
				miTecni.codigo_voltaje=rs.getString(1);
				miTecni.tec_cod_suc=rs.getString(2); 
				miTecni.tec_sucursal=rs.getString(3); 
				miTecni.tec_cod_part=rs.getString(4); 
				miTecni.tec_partido=rs.getString(5);
				miTecni.tec_cod_local=rs.getString(6);
				miTecni.tec_localidad=rs.getString(7); 
				miTecni.tec_cod_calle=rs.getString(8); 
				miTecni.tec_nom_calle=rs.getString(9); 
				miTecni.tec_nro_dir=rs.getString(10);
				miTecni.tec_piso_dir=rs.getString(11);
				miTecni.tec_depto_dir=rs.getString(12); 
				miTecni.tec_cod_entre=rs.getString(13); 
				miTecni.tec_entre_calle1=rs.getString(14); 
				miTecni.tec_cod_ycalle=rs.getString(15);
				miTecni.tec_entre_calle2=rs.getString(16);
				miTecni.tec_manzana=rs.getString(17);
				miTecni.tec_centro_trans=rs.getString(18);
				miTecni.tec_alimentador=rs.getString(19);
				miTecni.tec_subestacion=rs.getString(20); 
				miTecni.tec_nom_subest=rs.getString(21);
				miTecni.acometida = rs.getString(22);
				miTecni.tipo_conexion = rs.getString(23);
			}
			
		}catch(Exception ex){
			System.out.println("Error al cargar datos tecnicos. getTecni()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
		
		return miTecni;
	}
	
	private String getOTActivas(long lNroCliente) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sCadena="";
		
		long lOTNroCliente;
		long lNroMsg;
		String sProced;
		long lNroSolicitud;
		String sOTStatus;
		Date dFechaInicio;
		
		SimpleDateFormat fmtDate= new SimpleDateFormat("dd/mm/yyyy HH:MM:SS");
		String sFechaAux;
		int i=0;
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_OT_ACTIVAS);
			pstm.setLong(1, lNroCliente);
			rs = pstm.executeQuery();
			
			if(rs.next()){
				if(i==0) {
					sCadena="El cliente tiene las siguientes OTs Activas\r\n";
					i=1;
				}
				
				lOTNroCliente = rs.getLong(1);
				lNroMsg = rs.getLong(2);
				sProced = rs.getString(3);
				lNroSolicitud = rs.getLong(4);
				sOTStatus = rs.getString(5);
				dFechaInicio = rs.getDate(6);
				
				sFechaAux = fmtDate.format(dFechaInicio);
			
				sCadena += "Mensaje " + sProced.trim() + " " + lNroMsg + " del " + sFechaAux + "\r\n";
			}
			
		}catch(Exception ex){
			System.out.println("Error al cargar OTs Activas. getOTActivas()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		
		}
		return sCadena;
	}
	
	private Collection<String> getPrecintos(clienteDTO miCliente){
		Vector<String> miLista = new Vector<String>();
		
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sValor="";
System.out.println("Precintos para cliente " + miCliente.numero_cliente);		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_PRECINTOS);
			pstm.setLong(1, miCliente.numero_cliente);
			pstm.setLong(2, miCliente.numero_medidor);
			pstm.setString(3, miCliente.marca_medidor.trim());
			pstm.setLong(4, miCliente.numero_cliente);
			rs = pstm.executeQuery();
			
			while(rs.next()){
System.out.println("Encontro precinto");				
				sValor=rs.getString(1);
				miLista.add(sValor);
			}
			
		}catch(Exception ex){
			System.out.println("Error al cargar precintos. getPrecintos()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		
		}
System.out.println("Le va a dar los precintos");		
		return miLista;
	}
	
	private String getNroCb(String sMarca, String sModelo) {
		String sCod="";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try{
			con = UConnection.getConnection();
			con.setAutoCommit(false);
			pstm = con.prepareStatement(SEL_NRO_CB);
			pstm.setString(1, sMarca.trim());
			pstm.setString(2, sModelo.trim());
			rs = pstm.executeQuery();
			
			if(rs.next()){
				sCod=rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error al Buscar Nro.Cb del Medidor . getNroCb()");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstm != null) pstm.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		
		}
		return sCod;
	}
	
	private String getInfoEnlace(long lContacto, long lMensaje, String sOrden) {
		String sJson="";
		
		sJson = "{";
		sJson += "\"numeroContacto\":\"" + lContacto + "\", ";
		sJson += "\"numeroMensaje\":\"" + lMensaje + "\", ";
		sJson += "\"numeroOrden\":\"" + sOrden.trim() + "\", ";
		sJson += "}";
		
		return sJson;
	}
	
	private static final String SEL_SOLICITUDES = "SELECT caso, " + 
			"nro_orden, " + 
			"tipo_sol, " +
			"data_in " + 
			"FROM sfc_interface " + 
			"WHERE estado = 0 " + 
			"AND tarifa = 'T1' ";
	
	private String query1() {
		String sql;
		
		sql = "SELECT caso, ";
		sql += "nro_orden, ";
		sql += "data_in ";
		sql += "FROM sfc_interface ";
		sql += "WHERE estado = 0 ";
		sql += "AND tarifa = 'T1' ";
		sql += "AND tipo_sol = 'CONTACTO' ";
		
		return sql;
	}

	private String query2() {
		String sql;
		
		sql = "SELECT te_plazo_com, NVL(te_tipo_contacto, '0') tipo_cto ";
		sql += "FROM contacto:ct_tab_mot_empresa ";
		sql += "WHERE te_cod_motivo = ? ";
		sql += "AND te_cod_mot_empresa = ? ";
		   
		return sql;
	}
	
	private String query3() {
		String sql;

		sql = "SELECT su_cod_superior ";
		sql += "FROM contacto:ct_tab_suctrof ";
		sql += "WHERE su_cod_suctrof = ? ";
		sql += "AND su_cate_suctrof = 'C' ";
		
		return sql;
	}
	
	private String query4() {
		String sql;
		
		sql = "SELECT fecha FROM feriados ";
		sql += "WHERE fecha >= ? ";
		sql += "ORDER BY fecha ASC ";
		
		return sql;
	}
	
	private static String SEL_CLIENTE =	"SELECT c.numero_cliente, "+
			"c.nombre, "+
			"c.tip_doc, "+
			"c.nro_doc, "+
			"c.origen_doc, "+
			"c.provincia, "+
			"c.nom_provincia, "+
			"c.sucursal, "+
			"c.sector, "+
			"c.partido, "+
			"c.nom_partido, "+
			"c.comuna, "+
			"c.nom_comuna, "+
			"c.cod_calle, "+
			"c.nom_calle, "+
			"c.nro_dir, "+
			"c.piso_dir, "+
			"c.depto_dir, "+
			"c.cod_postal, "+
			"c.telefono, "+
			"c.tarifa, "+
			"c.tipo_iva, "+
			"c.tipo_cliente, "+
			"c.rut, "+
			"NVL(c.cuenta_conver, '0') cuenta_conver, "+
			"c.nom_sucursal, "+
			"c.cod_entre, "+
			"c.nom_entre, "+
			"c.cod_entre1, "+
			"c.nom_entre1, "+
			"c.zona, " +
			"c.correlativo_ruta, "+
			"m.numero_medidor, " +
			"m.marca_medidor, " +
			"m.modelo_medidor, " +
			"c.potencia_contrato, " +
			"c.tipo_empalme, " +
			"c.obs_dir, " +
			"c.info_adic_lectura, " +
			"c.barrio, " +
			"c.nom_barrio, " +
			"o.suc_padre " +
			"FROM cliente c, medid m, OUTER ot_sucursal o " +
			"WHERE c.numero_cliente = ? " +
			"AND m.numero_cliente = c.numero_cliente " +
			"AND m.estado = 'I' " +
			"AND o.suc_hijo = c.sucursal ";
			

	private String query6() {
		String sql;

		sql = "INSERT INTO contacto:ct_numero_suc0100 ( ";
		sql += "nu_sucursal_contac, nu_usuario, nu_fecha_proceso ";
		sql += ")VALUES('0100', 'SALESFORCE', CURRENT) ";
		
		return sql;
	}
	
	private String query7() {
		String sql;
		
		sql = "SELECT MAX(nu_co_numero) FROM contacto:ct_numero_suc0100 "; 
		sql += "WHERE nu_sucursal_contac = '0100' ";
		sql += "AND nu_usuario = 'SALESFORCE' ";
		
		return sql;
	}
	
	private String query8(contactoDTO regCto) {
		String sql;
		
	   sql = "INSERT INTO contacto:ct_contacto ( ";
	   sql += "sfc_caso, ";
	   sql += "sfc_nro_orden, ";
	   sql += "co_numero, ";
	   sql += "co_numero_cliente, ";
	   sql += "co_tipo_doc, ";
	   sql += "co_nro_doc, ";
	   sql += "co_es_cliente, ";
	   sql += "co_tarifa, ";
	   sql += "co_suc_cli, ";
	   sql += "co_cen_cli, ";
	   sql += "co_plan, ";
	   sql += "co_nombre, ";
	   sql += "co_telefono, ";
	   sql += "co_backoffice, ";
	   sql += "co_fecha_vto, ";
	   sql += "co_direccion, ";
	   sql += "co_partido, ";
	   sql += "co_codpos, ";
	   sql += "co_nro_cuit, ";
	   sql += "co_rol_inicio, ";
	   sql += "co_rol_resp, ";
	   
	   sql += "co_cod_medio, ";
	   sql += "co_fecha_inicio, ";
	   sql += "co_suc_ag_contacto, ";
	   sql += "co_suc_contacto, ";
	   sql += "co_oficina, ";
	   sql += "co_fecha_proceso, ";
	   sql += "co_fecha_estado, ";
	   sql += "co_multi ";
	   sql += ")VALUES( ";

System.out.println(String.format("Tarifa [%s] Sucursal [%s]", regCto.co_tarifa, regCto.co_suc_cli));

	   sql += regCto.sfc_caso + ", ";
	   sql += regCto.sfc_nro_orden + ", ";
	   sql += regCto.co_numero + ", ";
	   sql += regCto.co_numero_cliente + ", ";
	   sql += "'" + regCto.co_tipo_doc.trim() + "', ";
	   sql += "'" + regCto.co_nro_doc.trim() + "', ";
	   sql += "'" + regCto.co_es_cliente.trim() + "', ";
	   sql += "'" + regCto.co_tarifa.trim() + "', ";
	   sql += "'" + regCto.co_suc_cli.trim() + "', ";
	   sql += "'" + regCto.co_cen_cli.trim() + "', ";
	   sql += regCto.co_plan + ", ";
	   sql += "'" + regCto.co_nombre.trim() + "', ";
	   sql += "'" + regCto.co_telefono.trim() + "', ";
	   sql += "'" + regCto.co_backoffice.trim() + "', ";
	   sql += "'" + regCto.co_fecha_vto.trim() + "', ";
	   sql += "'" + regCto.co_direccion.trim() + "', ";
	   sql += "'" + regCto.co_partido.trim() + "', ";
	   sql += regCto.co_codpos + ", ";
	   sql += "'" + regCto.co_nro_cuit.trim() + "', ";
	   sql += "'" + regCto.co_rol_inicio.trim() + "', ";
	   sql += "'" + regCto.co_rol_inicio.trim() + "', ";
	   
	   sql += "'25', ";
	   sql += "CURRENT, ";
	   sql += "'0100', ";
	   sql += "'0100', ";
	   sql += "'0100', ";
	   sql += "CURRENT, ";
	   sql += "CURRENT, ";
	   sql += "'0') ";
		
		return sql;
	}
	
	private String query9(motContactoDTO reg) {
		String sql;
		
		sql = "INSERT INTO contacto:ct_motivo ( ";
		sql += "mo_co_numero, ";
		sql += "mo_cod_motivo, ";
		sql += "mo_cod_mot_empresa, ";
		sql += "mo_fecha_vto, ";
		sql += "mo_vto_real_com, ";
		sql += "mo_rol_inicio, ";
	   
		sql += "mo_suc_ag_contacto, ";
		sql += "mo_suc_contacto, ";
		sql += "mo_oficina, ";
		sql += "mo_fecha_inicio, ";
		sql += "mo_tipo_contacto, ";
		sql += "mo_fecha_proceso, ";
		sql += "mo_principal, ";
		sql += "mo_estado, ";
		sql += "mo_fecha_estado ";
		sql += ")VALUES( ";   
		sql += reg.mo_co_numero + ", ";
		sql += "'" + reg.mo_cod_motivo.trim() + "', ";
		sql += "'" + reg.mo_cod_mot_empresa.trim() + "', ";
		sql += "'" + reg.mo_fecha_vto.trim() + "', ";
		sql += "'" + reg.mo_vto_real_com.trim() + "', ";
		sql += "'" + reg.mo_rol_inicio.trim() + "', ";
		sql += "'0100', ";
		sql += "'0100', ";
		sql += "'0100', ";
		sql += "CURRENT, ";
		sql += "'0', ";
		sql += "CURRENT, ";
		sql += "'1', ";
		sql += "'B', ";
		sql += "CURRENT) ";
		
		return sql;
	}
	
	private String query10(long nroCto, observaCtoDTO reg) {
		String sql;
		
		sql = "INSERT INTO contacto:ct_observ( ";
		sql += "ob_co_numero, ";
		sql += "ob_suc_contacto, ";
		sql += "ob_descrip, ";
		sql += "ob_pagina ";
		sql += ")VALUES( ";
		sql += nroCto + ", ";
		sql += "'0100', ";
		sql += "'" + reg.ob_descrip.trim() + "', ";
		sql += reg.ob_pagina + ") ";
				
		return sql;
	}
	
	private String query11() {
		String sql;
		
		sql = "UPDATE sfc_interface SET ";
		sql += "estado = 1, ";
		sql += "descri_estado = ?, ";
		sql += "fecha_estado = CURRENT,  ";
		sql += "info_enlace = ? ";
		sql += "WHERE caso = ? ";
		sql += "AND nro_orden = ? ";
		sql += "AND tarifa = 'T1' ";
		sql += "AND tipo_sol = 'CONTACTO' ";
		
		return sql;
	}
	
	private String query12() {
		String sql;		

		sql = "SELECT mot_cliente, mot_empresa ";
		sql += "FROM sfc_trafo_motivos ";
		sql += "WHERE motivo_sfc = ? ";
		sql += "AND sub_motivo_sfc = ? ";
		
		return sql;		
	}
	
	private String query13(String sProcedimiento, String sSucursal) {
		String sql;
		
		sql = "SELECT rol FROM sfc_roles ";
		sql += "WHERE procedimiento = ? ";
		sql += "AND sucursal = ? ";
		
		return sql;
	}

	private static String XPRO_ENVIAR = "EXECUTE PROCEDURE xpro_enviar ( " +
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private String query14(mensajeDTO reg) {
		String sql;
		
		sql = "EXECUTE PROCEDURE xpro_enviar( ";

        sql += reg.lMensaje + ", ";
        sql += "'" + reg.sProced.trim() + "', ";
        sql += "'" + reg.sEtapa.trim() + "', ";
        sql += "'" + reg.sPrivacidad.trim() + "', ";
        sql += "'" + reg.sUrgencia.trim() + "', ";
        sql += "'" + reg.sEncriptado.trim() + "', ";
        sql += "'" + reg.sReferencia.trim() + "', ";
        sql += "'" + reg.sRolCon.trim() + "', ";
        sql += "'" + reg.sRolOrg.trim() + "', ";
        sql += "'" + reg.sRolDst.trim() + "', ";
        sql += reg.iEmpCon + ", ";
        sql += reg.iEmpOrg + ", ";
        sql += reg.iEmpDst + ", ";
        sql += "'" + reg.sTexto.trim() +"') "; 
		
		//sql = "EXECUTE PROCEDURE xpro_enviar(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		
		return sql;
	}

	private static String SEL_TEMA_TRABAJO= "SELECT tt_cod_tema, tt_cod_trabajo, "+
			"t1.descripcion, t2.descripcion, TRIM(t1.valor_alf) "+
			"FROM contacto:ct_tab_mot_tt, tabla t1, tabla t2 "+
			"WHERE tt_cod_motivo = ? "+
			"AND tt_cod_mot_empresa = ? "+
			"AND t1.sucursal = '0000' "+
			"AND t1.nomtabla = 'TEMAS' "+
			"AND t1.codigo = tt_cod_tema "+
			"AND t1.fecha_activacion <= TODAY "+
			"AND (t1.fecha_desactivac IS NULL OR t1.fecha_desactivac > TODAY) "+
			"AND t2.sucursal = '0000' "+
			"AND t2.nomtabla = 'TRABAJ' "+
			"AND t2.codigo = tt_cod_trabajo "+
			"AND t2.fecha_activacion <= TODAY "+
			"AND (t2.fecha_desactivac IS NULL OR t2.fecha_desactivac > TODAY) ";			
	
	private static String GET_NRO_ORDEN= "SELECT numero[5, 10] "+
					"FROM numao " +
					"WHERE tipo_orden = 'AC' "+
					"AND area = 'SIC0'";
	
	private static String SET_NRO_ORDEN= "UPDATE numao SET "+
			"numero = ? "+
			"WHERE tipo_orden = 'AC' "+
			"AND area = 'SIC0' ";

			
	private static String SEL_SUCUR_CLIE = "SELECT cod_sucur "+
			"FROM sucur_centro_op "+
			"WHERE cod_centro_op = ? "+
			"AND fecha_activacion <= TODAY "+
			"AND (fecha_desactivac IS NULL OR fecha_desactivac > TODAY) ";
	
	
	private static String SEL_ROL_DESTINO = "SELECT te_carpeta_destino FROM contacto:ct_tab_mot_empresa "+
			"WHERE te_cod_motivo = ? "+
			"AND te_cod_mot_empresa = ? "+
			"AND te_fecha_alta <= TODAY "+
			"AND (te_fecha_baja IS NULL OR te_fecha_baja > TODAY) ";
	
	private static String SEL_ROL_DESTINO_OT = "SELECT TRIM(otx_carpeta) " + 
			"FROM ot_xpro_accion " + 
			"WHERE otx_sucursal = ? " + 
			"AND otx_proced   = 'M_SEGEN' " + 
			"AND otx_accion   = '10' ";
	
	private static String SEL_AREA_DESTINO = "SELECT area FROM rol "+
			"WHERE rol = ? ";

	private static String SEL_DATA_DESTINO = "SELECT t.car_proc_pendiente, r.area, " + 
			"CASE " +
			"	WHEN t.tipo = 'RST' THEN 'S' "+
			"	ELSE 'N' "+
			"END, " +
			"t.cod_agrupacion, t.plazo_adic2, t.plazo, t.car_proc_para "+
			"FROM recla_tecni_proce t, OUTER rol r "+
			"WHERE procedimiento = 'SEGEN' "+
			"AND motivo_cliente = ? "+
			"AND motivo_empresa = ? "+
			"AND tema = ? "+
			"AND trabajo = ? "+
			"AND fecha_activacion <= TODAY "+
			"AND (fecha_desactivac IS NULL OR fecha_desactivac > TODAY) "+
			"AND r.rol = t.car_proc_pendiente ";
			
	private static String SET_ORDEN = "INSERT INTO orden(tipo_orden, "+ 
			"numero_orden, mensaje_xnear, "+
			"servidor, sucursal, area_emisora, "+
			"ident_etapa, term_dir, area_ejecutora, "+
			"prioridad, estado, rol_usuario, "+
			"tema, trabajo, clase, "+
			"numero_orden_rel, valor_cobro, numero_cliente, "+
			"vencimiento, cuenta_conver, sucu_usu, "+
			"sfc_caso, sfc_nro_orden, fecha_inicio "+
			")VALUES('AC', ?, ?, 1, ?, ?, "+
			"'RQ', 'SALESFORCE', ?, 'N', '0', "+
			"?, ?, ?, '0005', 0, 0, ?, ?, ?, ?, "+
			"?, ?, CURRENT) ";
	
	private static String SET_ETAPA_ORDEN ="INSERT INTO etapa_orden ( " + 
			"mensaje_xnear, ident_etapa, fecha_etapa " + 
			")VALUES(?, 'RQ', CURRENT) ";
	
	private static String SET_CTO_SEGEN = "INSERT INTO cto_segen (se_servidor, se_mensaje, se_orden, se_cod_mot_empresa, se_cod_motivo, se_co_numero, "+
			"se_suc_contacto, se_fecha_creacion, se_rol_creacion, se_area_creacion, se_fecha_vto_real, se_fecha_vto, "+
			"se_fecha_modif, se_etapa, se_estado, se_sub_estado, se_rol_destino, "+
			"se_tema, se_trabajo, se_fecha_proceso "+
			") VALUES ( "+ 
			"1, ?, ?, ?, ?, ?, "+
			"?, ?, ?, ?, ?, ?, "+
			"?, 'MODIFICACION', 0, 0, ?, "+
			"?, ?, CURRENT) ";

	private static String GET_INI_CONTACTO = "SELECT co_fecha_inicio FROM contacto:ct_contacto "+ 
			"WHERE co_numero = ? " +
			"AND co_suc_contacto = ? ";
	
	private static String SEL_REINCIDENCIA ="SELECT COUNT(*) FROM rec_rec_uni " + 
			"WHERE origen = 'MAC' " + 
			"AND tipo_documento = 'SEGEN' " + 
			"AND nro_mensaje = ? ";

	private static String SEL_TECNI = "SELECT codigo_voltaje, tec_cod_suc, tec_sucursal, tec_cod_part , tec_partido, "+
			"tec_cod_local, tec_localidad, tec_cod_calle, tec_nom_calle, "+ 
			"tec_nro_dir, tec_piso_dir, tec_depto_dir, "+
			"tec_cod_entre, tec_entre_calle1, tec_cod_ycalle, tec_entre_calle2, "+
			"tec_manzana, tec_centro_trans, tec_alimentador, tec_subestacion , tec_nom_subest "+ 
			", acometida, tipo_conexion " +
			"FROM synergia:tecni "+
			"WHERE numero_cliente = ? ";

	private static String INS_REC_REC_UNI =	"INSERT INTO rec_rec_uni ( "+
				"numero_cliente , "+
				"tarifa, "+
				"origen, "+
				"tipo_documento, "+
				"nro_reclamo, "+
				"nro_mensaje, "+
				"tipo_reclamo, "+
				"trabajo_requerido, "+
				"motivo_empresa, "+
				"motivo_cliente, "+
				"fecha_ini_contacto, "+
				"plazo, "+
				"fecha_vto_con, "+
				"fecha_ingreso_ct, "+
				"fecha_vto_ct , "+
				"nombre_cliente, "+
				"telefono, "+
				"cod_calle, "+
				"calle, "+
				"piso, "+
				"dpto, "+
				"nro_puerta, "+
				"cod_entre_calle, "+
				"entre_calle, "+
				"cod_entre_calle2, "+
				"entre_calle2, "+
				"nro_manzana, "+
				"cod_localidad, "+
				"localidad, "+
				"cod_partido, "+
				"partido, "+
				"sucursal_tecnica, "+
				"nom_suc_tecnica, "+
				"cod_subestacion, "+
				"nombre_subestacion, "+
				"alimentador, "+
				"centro_trans, "+
				"cod_agrupacion, "+
				"reclamo_reincident, "+
				"plazo_tecnico, "+
				"etapa, "+
				"sin_tecni, "+
				"tipo_sum, "+
				"fecha_ini_edesur "+
				")VALUES( " +
				"?, ?, 'MAC', 'SEGEN', ?, ?, 'C', ?, ?, ?, ?, ?, ?, "+
				"CURRENT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	private static String SEL_MOTIVOS_DESC = "SELECT TRIM(tc_desc_motivo), TRIM(te_desc_mot_empres) "+
			"FROM contacto:ct_tab_motivo, contacto:ct_tab_mot_empresa "+
			"WHERE tc_cod_motivo = ? "+
			"AND tc_fecha_alta <= TODAY "+
			"AND (tc_fecha_baja IS NULL OR tc_fecha_baja > TODAY) "+
			"AND te_cod_motivo = tc_cod_motivo "+
			"AND te_cod_mot_empresa = ? "+
			"AND te_fecha_alta <= TODAY "+
			"AND (te_fecha_baja IS NULL OR te_fecha_baja > TODAY) ";

	private static String SEL_OT_ACTIVAS = "SELECT ot_numero_cliente, " + 
			"ot_mensaje_xnear, " + 
			"ot_proced, " + 
			"ot_nro_solicitud, " + 
			"ot_status, " + 
			"ot_fecha_inicio " + 
			"FROM ot_mac " + 
			"WHERE ot_numero_cliente = ? " + 
			"AND ot_status <> 'A' " + 
			"ORDER BY ot_fecha_inicio ";
	
	private static String INS_OT_MAC = "INSERT INTO ot_mac ( " + 
			"ot_numero_cliente, " + 
			"ot_mensaje_xnear, " + 
			"ot_proced, " + 
			"ot_envia_sap, " + 
			"ot_estado, " + 
			"ot_fecha_est, " + 
			"ot_status, " + 
			"ot_fecha_status, " + 
			"ot_fecha_inicio, " + 
			"ot_sucursal_padre, " + 
			"ot_sucursal, " + 
			"ot_sector, " + 
			"ot_zona, " + 
			"ot_corr_ruta, " + 
			"ot_tipo_traba, " + 
			"ot_area_interloc, " + 
			"ot_motivo, " + 
			"ot_rol_ejecuta, " + 
			"ot_area_ejecuta, " + 
			"ot_potencia, " + 
			"ot_tension, " + 
			"ot_acometida, " + 
			"ot_toma, " + 
			"ot_conexion, " + 
			"ot_fecha_vto " + 
			")VALUES( " + 
			"?, ?, ?, ? " + 
			",?, CURRENT, ?, CURRENT " + 
			",CURRENT, ?, ?, ?, ?, ?, " + 
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	private static String SEL_NRO_OT = "SELECT o1.ot_nro_orden " + 
			"FROM ot_mac o1 " + 
			"WHERE o1.ot_mensaje_xnear = ? " + 
			"AND o1.ot_fecha_inicio = (SELECT MAX(o2.ot_fecha_inicio) FROM ot_mac o2 " + 
			"	WHERE o2.ot_mensaje_xnear = o1.ot_mensaje_xnear " + 
			"	AND o2.ot_estado = 'C' ) ";
	
	private static String INS_OT_HISEVEN = "INSERT INTO ot_hiseven ( " + 
			"ots_nro_orden, " + 
			"ots_numero_cliente, " + 
			"ots_status, " + 
			"ots_fecha, " + 
			"ots_observac, " + 
			"ots_fecha_proc " + 
			")VALUES( " + 
			"?, ?, ?, CURRENT, 'INICIADA', CURRENT) ";
	
	private static String SEL_PRECINTOS = "SELECT codigo_ubicacion, " + 
			"fecha_movimiento " + 
			"FROM sellos " + 
			"WHERE numero_cliente = ? " + 
			"AND numero_medidor = ? " + 
			"AND marca_medidor  = ? " + 
			"AND estado_insta = '6' " + 
			"UNION " + 
			"SELECT '0', " + 
			"DATE(fecha_estado) " + 
			"FROM prt_precintos e " + 
			"WHERE e.numero_cliente = ? " + 
			"AND e.estado_actual = '08' " + 
			"AND e.fecha_estado = (SELECT MAX(e2.fecha_estado) " + 
			"   FROM prt_precintos e2 " + 
			"   WHERE e.numero_cliente = e2.numero_cliente " + 
			"   AND e.estado_actual = e2.estado_actual ) " + 
			"ORDER BY 2 DESC "; 
			
	private static String INS_OT_MAC_SAP = "INSERT INTO ot_mac_sap ( " + 
			"oms_tipo_ifaz, " + 
			"oms_nro_orden, " + 
			"oms_tipo_traba, " + 
			"oms_sucursal, " + 
			"oms_area_ejecuta, " + 
			"oms_motivo, " + 
			"oms_fecha_ini, " + 
			"oms_obs_dir, " + 
			"oms_obs_lectu, " + 
			"oms_obs_segen, " + 
			"oms_area_interloc, " + 
			"oms_nro_medidor, " + 
			"oms_marca_med, " + 
			"oms_modelo_med, " + 
			"oms_cla_servi, " + 
			"oms_potencia, " + 
			"oms_tension, " + 
			"oms_acometida, " + 
			"oms_toma, " + 
			"oms_conexion, " + 
			"oms_pre1_ubic, " + 
			"oms_pre2_ubic, " + 
			"oms_pre3_ubic, " + 
			"oms_ruta_lectura, " + 
			"oms_nombre_cli, " + 
			"oms_nro_cli, " + 
			"oms_nom_entre, " + 
			"oms_nom_entre1, " + 
			"oms_telefono, " + 
			"oms_nom_calle, " + 
			"oms_nro_dir, " + 
			"oms_nom_partido, " + 
			"oms_piso_dir, " + 
			"oms_depto_dir, " + 
			"oms_nom_comuna, " + 
			"oms_cod_postal, " + 
			"oms_fecha_vto, " + 
			"oms_codbar, " + 
			"oms_rol_creador, " + 
			"oms_nombre_rol, " + 
			"oms_proced, " + 
			"oms_nro_proced " + 
			")VALUES( " + 
			"?, ?, ?, ?, ?, ?, CURRENT, " + 
			"?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
			"?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
			"?, ?, ?, ?, ?, ?, ?, ?, ?, " + 
			"?, ?, ?, ?, ?, ?, ?, ?) ";
	
	private static String SEL_NRO_CB = "SELECT mod_nrocb FROM modelo " + 
			"WHERE mar_codigo = ? " + 
			"AND mod_codigo = ? ";
	
}
