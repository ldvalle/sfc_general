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

public class calcomDAO {

	public Collection<interfaceDTO> getLstCasos() throws SQLException{
		Vector<interfaceDTO> miLista = new Vector<interfaceDTO>();
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_CASOS)){
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						interfaceDTO miReg =new interfaceDTO();
					
						miReg.caso = rs.getLong("caso");
						miReg.nro_orden = rs.getLong("nro_orden");
						miReg.data_in = rs.getString("data_in");
						
						miLista.add(miReg);
					}
				}
			}
			UConnection.nullConnection();
		}
		
		return miLista;
	}
	
	public Collection<Date> getFeriados() throws SQLException {
		Vector<Date> miLista = new Vector<Date>();

		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_FERIADOS)){
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Date dFecha=rs.getDate(1);
						miLista.add(dFecha);
					}
				}
			}
			UConnection.nullConnection();
		}

		return miLista;
	}
	
	public ceParametrosDTO getParam() throws SQLException {
		ceParametrosDTO miReg = new ceParametrosDTO();

		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_PARAMETROS)){
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						
						miReg.setDocResol(rs.getInt(1));
						miReg.setDocPeri(rs.getInt(2));
						miReg.setDocumInf24(rs.getInt(3));
						miReg.setDocumInf48(rs.getInt(4));
						miReg.setDocumInf30(rs.getInt(5));
						miReg.setHoraCierre(rs.getDate(6));
						miReg.setTemaSvp(rs.getInt(7));
						
						miReg.setTemaAvp(rs.getInt(8));
						miReg.setTemaRec(rs.getInt(9));
						miReg.setTemaRpd(rs.getInt(10));
						miReg.setTemaQja(rs.getInt(11));
						miReg.setTemaReq(rs.getInt(12));
						
						miReg.setDocCierre(rs.getInt(13));
						miReg.setDocCedNot(rs.getInt(14));
						miReg.setDocSrPta(rs.getInt(15));
					}
				}
			}
			UConnection.nullConnection();
		}
		
		return miReg;
	}
	
	public paramLocalDTO getParamLocal(int CodTema) throws SQLException {
		paramLocalDTO miReg = new paramLocalDTO();
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_PARAM_LOCAL)){
				stmt.setInt(1, CodTema);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						miReg.plazo = rs.getInt(1);
						miReg.cod_docum_ent=rs.getInt(2);
						miReg.cod_docum_sal=rs.getInt(3);
					}
				}
			}
			UConnection.nullConnection();
		}
		return miReg;
	}

	public boolean esTemaSvp(int iCodTema) throws SQLException {
		int iValor=0;
		boolean bValor;
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_TEMA_SVP)){
				stmt.setInt(1, iCodTema);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						iValor=rs.getInt(1);
					}
				}
			}
			UConnection.nullConnection();
		}
		return (iValor>0)?true:false;
	}

	public boolean esClienteT1(long lNroCliente) throws SQLException {
		int iValor=0;
		boolean bValor;
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(VAL_CLIENTE_T1)){
				stmt.setLong(1, lNroCliente);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						iValor=rs.getInt(1);
					}
				}
			}
			UConnection.nullConnection();
		}
		return (iValor>0)?true:false;
	}

	public boolean esClienteT2(long lNroCliente) throws SQLException {
		int iValor=0;
		boolean bValor;
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(VAL_CLIENTE_T2)){
				stmt.setLong(1, lNroCliente);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						iValor=rs.getInt(1);
					}
				}
			}
			UConnection.nullConnection();
		}
		return (iValor>0)?true:false;
	}

	public boolean validoTema(int iCodTema) throws SQLException {
		int iValor=0;
		boolean bValor;
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(VAL_TEMA)){
				stmt.setInt(1, iCodTema);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						iValor=rs.getInt(1);
					}
				}
			}
			UConnection.nullConnection();
		}
		return (iValor>0)?true:false;
	}

	public boolean validoMotivo(int iCodTema, int iCodMotivo) throws SQLException {
		int iValor=0;
		boolean bValor;
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(VAL_MOTIVO)){
				stmt.setInt(1, iCodMotivo);
				stmt.setInt(2, iCodTema);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						iValor=rs.getInt(1);
					}
				}
			}
			UConnection.nullConnection();
		}
		return (iValor>0)?true:false;
	}
	
	public String motCambiaMarcas(int iCodMotivo) throws SQLException {
		String sValor="";
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_CAMBIA_MARCAS)){
				stmt.setInt(1, iCodMotivo);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						sValor=rs.getString(1);
					}
				}
			}
			UConnection.nullConnection();
		}

		return sValor;
	}
	
	public clienteDTO getDataClienteT1(long lNroCliente) throws SQLException {
		clienteDTO miReg = new clienteDTO();
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_CLIENTE_T1)){
				stmt.setLong(1, lNroCliente);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						miReg.nombre = rs.getString(1);
						miReg.nom_partido = rs.getString(2);
						miReg.nom_calle = rs.getString(3);
						miReg.nro_dir = rs.getString(4);
						miReg.piso_dir = rs.getString(5);
						miReg.depto_dir = rs.getString(6);
						miReg.telefono = rs.getString(7);
						miReg.sector = rs.getInt(8);
						miReg.tip_doc = rs.getString(9);
						miReg.nro_doc = rs.getDouble(10);
						miReg.tarifa = rs.getString(11);
						miReg.tiene_corte_rest = rs.getString(12);
						miReg.estado_cobrabilida = rs.getString(13);
						miReg.numero_medidor = rs.getLong(14);
						miReg.sucursal = rs.getString(15);
						miReg.nom_comuna = rs.getString(16);
					}
				}
			}
			UConnection.nullConnection();
		}
		return miReg;
	}
	
	public clienteDTO getDataClienteT2(long lNroCliente) throws SQLException {
		clienteDTO miReg = new clienteDTO();
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_CLIENTE_T2)){
				stmt.setLong(1, lNroCliente);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						miReg.nombre = rs.getString(1);
						miReg.nom_calle=rs.getString(2);
						miReg.nro_dir=rs.getString(3);
						miReg.piso_dir=rs.getString(4);
						miReg.depto_dir=rs.getString(5);
						miReg.telefono=rs.getString(6);
						miReg.sector=rs.getInt(7);
						miReg.numero_medidor=rs.getLong(8);
						miReg.numero_medidor2=rs.getLong(9);
						miReg.numero_medidor3=rs.getLong(10);
						miReg.sucursal = rs.getString(11);
						miReg.tipo_cliente = rs.getString(12);
					}
				}
			}
			UConnection.nullConnection();
		}
		return miReg;
	}

	public long getNroReclamo() throws SQLException {
		long nroReclamo=0;
		
		try(Connection connection = UConnection.getConnection()){
			connection.setAutoCommit(false);
			PreparedStatement stmt = connection.prepareStatement(SEL_NRO_RECLAMO);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				nroReclamo = rs.getLong(1);
			}

			stmt=null;
			rs=null;
			
			stmt = connection.prepareStatement(SET_NRO_RECLAMO);
			stmt.setLong(1, nroReclamo+1);
			stmt.executeUpdate();
			
			connection.commit();
			UConnection.nullConnection();
		}
		return nroReclamo;
	}

	public String getAnalista(String sucursal) throws SQLException {
		String sValor="";
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_ANALISTA)){
				stmt.setString(1, sucursal);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						sValor=rs.getString(1);
					}else {
						System.out.println("No se encontró analísta EDESUR para sucursal " + sucursal);
					}
				}
			}
			UConnection.nullConnection();
		}

		return sValor;
	}

	public String getNroNumao(String sTipo, String sArea) throws SQLException {
		String sNroModif="";
		String sValor="";
		long lValor=0;
		boolean bExiste=true;
		
		try(Connection connection = UConnection.getConnection()){
			connection.setAutoCommit(false);
			
			PreparedStatement stmt = connection.prepareStatement(SEL_NRO_MODIF);
			stmt.setString(1, sTipo);
			stmt.setString(2, sArea);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				sValor=rs.getString(1).trim();
				lValor= Long.parseLong(sValor.substring(sArea.length()+1, sValor.length()))+1;
				sNroModif = sArea.trim() + String.format("%08d", lValor);
			}else {
				sNroModif=sArea.trim() + "00000001";
				bExiste=false;
			}
			stmt=null;
			if(bExiste) {
				stmt = connection.prepareStatement(UPD_NRO_MODIF);
				stmt.setString(1, sNroModif);
				stmt.setString(2, sTipo);
				stmt.setString(3, sArea);
			}else {
				stmt = connection.prepareStatement(INS_NRO_MODIF);
				stmt.setString(1, sTipo);
				stmt.setString(2, sArea);
				stmt.setString(3, sNroModif);
			}
			
			stmt.executeUpdate();
			
			connection.commit();
			UConnection.nullConnection();
		}

		
		return sNroModif;
	}
	
	public boolean regiCalcom(long lNroReclamo, long lNroMensaje, String sNroOrden, dataInDTO regData, ceParametrosDTO ParGlobal, paramLocalDTO ParLocal, clienteDTO cliente, postalLinkDTO delivery, ceReclamoDTO reclamo, ceClienteReclamoDTO clienteReclamo, reclaTecniProce recla, recRecUniDTO recUni, ceMensajeDTO mensaje ) throws SQLException {
		String sNroModif="";
		
		if(ParLocal.cambiaMarcas.equals("S")) {
			if(ParLocal.cambiaCobrabilida || ParLocal.cambiaCorte) {
				sNroModif = getNroNumao("MOD", delivery.getAreaOrigen());
			}
		}
		
		try(Connection connection = UConnection.getConnection()){
			connection.setAutoCommit(false);
			
			// ************ Registro el Reclamo ************
			
			//Actualizar Marcas cliente
			if(ParLocal.cambiaMarcas.equals("S")) {
				if(ParLocal.cambiaCobrabilida || ParLocal.cambiaCorte) {
					if(! ActualizaCliente(connection, sNroModif, cliente, delivery, ParLocal)) {
						return false;
					}
				}
			}
			
			//Insertar CE_RECLAMO
			if(!insReclamo(connection, reclamo)) {
				System.out.println("Falló insert de Reclamo");
				return false;
			}
			//Insertar en CE_CLIENTE_RECLAMO
			if(!insClienteReclamo(connection, clienteReclamo)) {
				System.out.println("Falló insert de ClienteReclamo");
				return false;
			}
			
			//Insertar CE_MULTIMOTIVO
			if(!insMotivo(connection, reclamo.getReclamo(), regData.motivo)) {
				System.out.println("Falló insert de ClienteReclamo");
				return false;
			}

			int iMotivo=Integer.parseInt(regData.motivo);
			if(iMotivo == ParGlobal.getTemaAvp() || iMotivo == ParGlobal.getTemaSvp()) {
				//Insertar en ReclamoAcseg
				if(!insReclamoAcseg(connection, clienteReclamo)) {
					System.out.println("Falló insert de ReclamoAcseg.");
					return false;
				}
				if(iMotivo == ParGlobal.getTemaAvp() ) {
					//Insertar en ReclamoCalles
					if(!insReclamoCalles(connection, lNroReclamo, cliente)) {
						System.out.println("Falló insert de ReclamoCalles.");
						return false;
					}
				}
				
			}
			
			//Insertar en CE_DOCUM_INGRESO
			if(!insDocumIngreso(connection, regData, reclamo)) {
				System.out.println("Falló insert de DocumIngreso.");
				return false;
			}
			
			//Insertar en CE_RECLAMO_ETAPA
			if(!insReclamoEtapa(connection, ParLocal, reclamo)) {
				System.out.println("Falló insert de ReclamoEtapa.");
				return false;
			}
			
			//Insertar en CE_RECLAMO_INTERV
			if(!insReclamoInterv(connection, delivery, reclamo)) {
				System.out.println("Falló insert de ReclamoInterv.");
				return false;
			}

			//******** Registro el Mensaje y la Orden ***********
			
			if(!setMensaCabec(connection, lNroMensaje, reclamo, delivery)) {
				System.out.println("Falló insert de CE_MENSA_CABEC.");
				return false;
			}
			
			if(!setMensaItem(connection, lNroMensaje, reclamo, delivery)) {
				System.out.println("Falló insert de CE_MENSA_ITEM.");
				return false;
			}
			
			if(!setOrden(connection, sNroOrden, lNroMensaje, reclamo, delivery, recla)) {
				System.out.println("Falló insert de ORDEN.");
				return false;
			}
			
			if(!setEtapaOrden(connection, lNroMensaje, reclamo)) {
				System.out.println("Falló insert de ETAPA_ORDEN.");
				return false;
			}
			
			if(!sendMensaje(connection, mensaje)) {
				System.out.println("Falló insert de Enviar Mensaje.");
				return false;
			}
			
			if(!setRecRecUni(connection, recUni)) {
				System.out.println("Falló insert de REC_REC_UNI.");
				return false;
			}
			connection.commit();
			UConnection.nullConnection();
		}
	
		
		return true;
	}
	
	private boolean insReclamoInterv(Connection connection, postalLinkDTO delivery, ceReclamoDTO reclamo)throws SQLException {
		try(PreparedStatement stmt = connection.prepareStatement(INS_RECLAMO_INTERV)){
			stmt.setLong(1, reclamo.getReclamo());
			stmt.setString(2, reclamo.getCodEjecEdes());
			stmt.setString(3, delivery.getRolOrigen());
						
			stmt.executeUpdate();
		}
		
		return true;
	}
	
	private boolean insReclamoEtapa(Connection connection, paramLocalDTO ParLocal, ceReclamoDTO reclamo) throws SQLException {

		try(PreparedStatement stmt = connection.prepareStatement(INS_RECLAMO_ETAPA)){
			stmt.setLong(1, reclamo.getReclamo());
			stmt.setInt(2, reclamo.getCodDocumEnt());
			stmt.setInt(3, ParLocal.cod_docum_sal);
			stmt.setTimestamp(4, new Timestamp(reclamo.getFechaVto().getTime()));
			stmt.setTimestamp(5, new Timestamp(reclamo.getFechaVto().getTime()));
			stmt.setString(6, reclamo.getNDocumEnre());
			
			stmt.executeUpdate();
		}
	
		return true;
	}
	
	private boolean insDocumIngreso(Connection connection, dataInDTO data, ceReclamoDTO recla)throws SQLException {
		try(PreparedStatement stmt = connection.prepareStatement(INS_DOCUM_INGRESO)){
			stmt.setLong(1, recla.getReclamo());
			stmt.setInt(2, recla.getCodDocumEnt());
			stmt.setString(3, recla.getNDocumEnre());
			stmt.setString(4, data.origen);
			
			stmt.executeUpdate();
		}

		return true;
	}
	
	private boolean insReclamoCalles(Connection connection, long lNroReclamo, clienteDTO cliente)throws SQLException {
		try(PreparedStatement stmt = connection.prepareStatement(INS_RECLAMO_CALLES)){
			stmt.setString(1, cliente.sucursal);
			stmt.setString(2, cliente.nom_calle);
			stmt.setString(3, cliente.nro_dir);
			stmt.setString(4, cliente.nom_entre);
			stmt.setString(5, cliente.nom_entre1);
			stmt.setLong(6, lNroReclamo);

			stmt.executeUpdate();
		}

		
		return true;
	}
	
	private boolean insReclamoAcseg(Connection connection, ceClienteReclamoDTO clienteReclamo) throws SQLException {
		try(PreparedStatement stmt = connection.prepareStatement(INS_RECLAMO_ACSEG)){
			stmt.setLong(1, clienteReclamo.getReclamo());
			stmt.setString(2, clienteReclamo.getDomicilio());
			stmt.setString(3, clienteReclamo.getSucursal());

			stmt.executeUpdate();
		}
		
		return true;
	}
	
	private boolean insMotivo(Connection connection, long lNroReclamo, String sMotivo) throws SQLException {
		
		try(PreparedStatement stmt = connection.prepareStatement(INS_MOTIVO)){
			stmt.setLong(1, lNroReclamo);
			stmt.setString(2, sMotivo);

			stmt.executeUpdate();
		}
		
		return true;
	}
	
	private boolean insClienteReclamo(Connection connection, ceClienteReclamoDTO cliRecla) throws SQLException {
		
		try(PreparedStatement stmt = connection.prepareStatement(INS_CLIENTE_RECLAMO)){
			stmt.setLong(1, cliRecla.getReclamo());
			stmt.setString(2, cliRecla.getNombre());
			stmt.setString(3, cliRecla.getTaria());
			stmt.setString(4, cliRecla.getDomicilio());
			stmt.setString(5, cliRecla.getLocalidad());
			stmt.setString(6, cliRecla.getTelefono());
			stmt.setString(7, cliRecla.getSucursal());
			stmt.setString(8, cliRecla.getMedidor1());
			stmt.setString(9, cliRecla.getMedidor2());			 
			stmt.setString(10, cliRecla.getMedidor3());			 
			stmt.setInt(11, cliRecla.getPlan());
			stmt.setString(12, cliRecla.getDocumento());
			stmt.setString(13, cliRecla.getTipoDoc());
			stmt.setString(14, cliRecla.getTarifaCliente());

			stmt.executeUpdate();
		}
		
		return true;
	}
	
	private boolean insReclamo(Connection connection, ceReclamoDTO reclamo) throws SQLException {
		try(PreparedStatement stmt = connection.prepareStatement(INS_RECLAMO)){

			stmt.setLong(1, reclamo.getReclamo());
			stmt.setInt(2, reclamo.getCodTema());
			stmt.setString(3, reclamo.getCodCanal());
			stmt.setInt(4, reclamo.getCodDocumEnt());
			stmt.setInt(5, reclamo.getCodMotivo());
			stmt.setString(6, reclamo.getCodEjecEdes());
			stmt.setString(7, reclamo.getEjeCedesIni());
			stmt.setString(8, reclamo.getNDocumEnre());
			stmt.setTimestamp(9, new Timestamp(reclamo.getFechaReclamo().getTime()));
			stmt.setTimestamp(10, new Timestamp(reclamo.getFechaSalEnre().getTime()));
			stmt.setTimestamp(11, new Timestamp(reclamo.getFechaIgEdes().getTime()));
			stmt.setTimestamp(12, new Timestamp(reclamo.getFechaIgSist().getTime()));
			stmt.setTimestamp(13, new Timestamp(reclamo.getFechaVto().getTime()));
			stmt.setLong(14, reclamo.getCodCliente());	
			stmt.setString(15, reclamo.getTarifa());
			stmt.setString(16, reclamo.getSucursal());

			stmt.executeUpdate();
		}
		
		return true;
	}
	
	private boolean ActualizaCliente(Connection connection, String sNroModif, clienteDTO cliente, postalLinkDTO delivery, paramLocalDTO ParLocal) throws SQLException {
			
		if(!updCliente(connection, cliente.numero_cliente)) {
			System.out.println("Fallo updCliente.");
			connection.rollback();
			return false;
		}
		
		if(ParLocal.cambiaCobrabilida) {
			if(!setModif(connection, sNroModif, cliente.numero_cliente, delivery.getRolOrigen(), "55", cliente.estado_cobrabilida, "C")) {
				System.out.println("Fallo Modif Cobrabilidad.");
				connection.rollback();
				return false;
			}
		}
		if(ParLocal.cambiaCorte) {
			if(!setModif(connection, sNroModif, cliente.numero_cliente, delivery.getRolOrigen(), "53", "N", "S")) {
				System.out.println("Fallo Modif Corte Rest.");
				connection.rollback();
				return false;
			}
		}
			
		return true;
	}
	
	private boolean updCliente(Connection connection, long lNroCliente) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(UPD_CLIENTE)){
			stmt.setLong(1, lNroCliente);
			stmt.executeUpdate();
		}
		
		return true;
	}
	
	private boolean setModif(Connection connection, String sNroModif, long lNroCliente, String sRolOrigen, String sCodigoModif, String datoViejo, String datoNuevo) throws SQLException{
		
		try(PreparedStatement stmt = connection.prepareStatement(INS_MODIF)){
			stmt.setLong(1, lNroCliente);
			stmt.setString(2, sNroModif);
			stmt.setString(3, sRolOrigen);
			stmt.setString(4, sCodigoModif);
			stmt.setString(5, datoViejo);
			stmt.setString(6, datoNuevo);
			
			stmt.executeUpdate();
		}
	
		return true;
	}
	
	public long getNroMensaje() throws SQLException {
		long nroMensaje=0;
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_NRO_MENSAJE)){
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						nroMensaje = rs.getLong(2);
					}else {
						System.out.println("No se obtuvo Nro.de Mensaje");
					}
				}
			}
			UConnection.nullConnection();
		}
		return nroMensaje;
	}

	public String getDesriTabla(String sNomTabla, String sCodigo) throws SQLException {
		String sDescri="";
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_DESCRI_TABLA)){
				stmt.setString(1, sNomTabla);
				stmt.setString(2, sCodigo);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						sDescri = rs.getString(1);
					}
				}
			}
			UConnection.nullConnection();
		}
		return sDescri;
	}

	public String getTipoDocumento(int iTema) throws SQLException {
		String sDescri="";
		String sTema = Integer.toString(iTema);
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_TIPO_DOCUMENTO)){
				stmt.setString(1, sTema);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						sDescri = rs.getString(1);
					}
				}
			}
			UConnection.nullConnection();
		}
		return sDescri;
	}
	
	public reclaTecniProce getReclaTecniProce(int Motivo, int Tema) throws SQLException {
		reclaTecniProce recla = new reclaTecniProce();
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_RECLA_TECNI_PROCE)){
				stmt.setString(1, Integer.toString(Motivo));
				stmt.setString(2, Integer.toString(Tema));

				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						recla.setCarProcPendiente(rs.getString(1));
						recla.setPlazo(rs.getInt(2));
						recla.setCodAgrupacion(rs.getString(3));
						recla.setPlazoAdic1(rs.getInt(4));
						recla.setPlazoAdic2(rs.getInt(5));
					}
				}
			}
			UConnection.nullConnection();
		}
		return recla;
	}

	
	public String getProveedor(String sCarpeta) throws SQLException {
		String sProve="";
		
		try(Connection connection = UConnection.getConnection()){
			try(PreparedStatement stmt = connection.prepareStatement(SEL_PROVEEDOR)){
				stmt.setString(1, sCarpeta);
				try(ResultSet rs = stmt.executeQuery()){
					if(rs.next()) {
						sProve = rs.getString(1) + "|" + rs.getString(2);
					}
				}
			}
			UConnection.nullConnection();
		}
		return sProve.trim();
	}

	public tecniDTO getTecni(long lNroCliente, String sTarifa) throws SQLException {
		tecniDTO miTecni = new tecniDTO();

		if (sTarifa.equals("T1")) {
			try(Connection connection = UConnection.getConnection()){
				try(PreparedStatement stmt = connection.prepareStatement(SEL_TECNI)){
					stmt.setLong(1, lNroCliente);
					try(ResultSet rs = stmt.executeQuery()){
						if(rs.next()) {
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
						}
					}
				}
				UConnection.nullConnection();
			}
		}else {
			try(Connection connection = UConnection.getConnection()){
				try(PreparedStatement stmt = connection.prepareStatement(SEL_T3_CADENA)){
					stmt.setLong(1, lNroCliente);
					try(ResultSet rs = stmt.executeQuery()){
						if(rs.next()) {
							miTecni.tec_cod_suc=rs.getString(1); 
							miTecni.tec_sucursal=rs.getString(2);
							miTecni.tec_cod_part=rs.getString(3); 
							miTecni.tec_partido=rs.getString(4);
							miTecni.tec_cod_local=rs.getString(5);
							miTecni.tec_localidad=rs.getString(6); 
							miTecni.tec_cod_calle=rs.getString(7); 
							miTecni.tec_nom_calle=rs.getString(8); 
							miTecni.tec_nro_dir=rs.getString(9);
							miTecni.tec_piso_dir=rs.getString(10);
							miTecni.tec_depto_dir=rs.getString(11); 
							miTecni.tec_cod_entre=rs.getString(12); 
							miTecni.tec_entre_calle1=rs.getString(13); 
							miTecni.tec_cod_ycalle=rs.getString(14);
							miTecni.tec_entre_calle2=rs.getString(15);
							miTecni.tec_manzana=rs.getString(16);
							miTecni.tec_centro_trans=rs.getString(17);
							miTecni.tec_alimentador=rs.getString(18);
							miTecni.tec_subestacion=rs.getString(19); 
							miTecni.tec_nom_subest=rs.getString(20);
						}
					}
				}
				UConnection.nullConnection();
			}
			
		}
		return miTecni;
	}


	private boolean setMensaCabec(Connection connection, long lNroMensaje, ceReclamoDTO reclamo, postalLinkDTO delivery) throws SQLException{
System.out.println("Mensaje " + lNroMensaje);		
		try(PreparedStatement stmt = connection.prepareStatement(INS_MENSA_CABEC)){

			stmt.setLong(1, lNroMensaje);
			stmt.setLong(2, reclamo.getReclamo());
			stmt.setInt(3, reclamo.getCodDocumEnt());
			stmt.setString(4, delivery.getRolOrigen());
			stmt.setString(5, delivery.getCodProveedor());
			stmt.setTimestamp(6, new Timestamp(reclamo.getFechaVto().getTime()));
			stmt.setTimestamp(7, new Timestamp(reclamo.getFechaIgEdes().getTime()));

			stmt.executeUpdate();
		}
		return true;
	}
	

	private boolean setMensaItem(Connection connection, long lNroMensaje, ceReclamoDTO reclamo, postalLinkDTO delivery) throws SQLException{
		
		try(PreparedStatement stmt = connection.prepareStatement(INS_MENSA_ITEM)){

			stmt.setLong(1, lNroMensaje);
			stmt.setString(2, delivery.getCodProveedor());
			stmt.setString(3, delivery.getCodProveedor());
			stmt.setTimestamp(4, new Timestamp(reclamo.getFechaIgEdes().getTime()));
			stmt.setString(5, delivery.getAreaProveedor());
			stmt.setTimestamp(6, new Timestamp(reclamo.getFechaVto().getTime()));
			stmt.setString(7, delivery.getRolOrigen());
			stmt.setString(8, delivery.getAreaOrigen());
			
			stmt.executeUpdate();
		}
		return true;
	}

	private boolean setOrden(Connection connection,String sNroOrden, long lNroMensaje, ceReclamoDTO reclamo, postalLinkDTO delivery, reclaTecniProce recla) throws SQLException{
		
		try(PreparedStatement stmt = connection.prepareStatement(INS_ORDEN)){

			stmt.setString(1, sNroOrden);
			stmt.setLong(2, lNroMensaje);
			stmt.setString(3, delivery.getAreaOrigen());
			stmt.setTimestamp(4, new Timestamp(reclamo.getFechaIgEdes().getTime()));
			stmt.setString(5, recla.getCarProcPendiente());
			stmt.setString(6, delivery.getRolOrigen());
			stmt.setLong(7, reclamo.getCodCliente());
			stmt.setTimestamp(8, new Timestamp(reclamo.getFechaVto().getTime()));
			
			stmt.executeUpdate();
		}
		return true;
	}

	private boolean setEtapaOrden(Connection connection,long lNroMensaje, ceReclamoDTO reclamo) throws SQLException{
		
		try(PreparedStatement stmt = connection.prepareStatement(INS_ETAPA_ORDEN)){
			stmt.setLong(1, lNroMensaje);
			stmt.setTimestamp(2, new Timestamp(reclamo.getFechaIgEdes().getTime()));
			
			stmt.executeUpdate();
		}
		return true;
	}

	private boolean setRecRecUni(Connection connection,recRecUniDTO recUni) throws SQLException{
		
		try(PreparedStatement stmt = connection.prepareStatement(INS_REC_REC_UNI)){

			stmt.setLong(1, recUni.numero_cliente);
			stmt.setString(2, recUni.etapa);
			stmt.setString(3, recUni.tarifa);
			stmt.setString(4, recUni.origen);
			stmt.setString(5, recUni.tipo_documento); 
			stmt.setLong(6, recUni.nro_reclamo); 
			stmt.setLong(7, recUni.nro_mensaje); 
			stmt.setString(8, recUni.tipo_reclamo); 
			stmt.setString(9, recUni.motivo_empresa); 
			stmt.setString(10, recUni.motivo_cliente);
			stmt.setTimestamp(11, new Timestamp(recUni.fecha_ini_contacto.getTime()));
			stmt.setInt(12, recUni.plazo);
			stmt.setTimestamp(13, new Timestamp(recUni.fecha_vto_con.getTime())); 
			stmt.setTimestamp(14, new Timestamp(recUni.fecha_ingreso_ct.getTime())); 
			stmt.setTimestamp(15, new Timestamp(recUni.fecha_vto_ct.getTime()));
			stmt.setString(16, recUni.nombre_cliente);
			stmt.setString(17, recUni.telefono);
			stmt.setString(18,  recUni.cod_calle); 
			stmt.setString(19, recUni.calle); 
			stmt.setString(20, recUni.piso); 
			stmt.setString(21, recUni.dpto); 
			stmt.setString(22, recUni.nro_puerta); 
			stmt.setString(23, recUni.cod_entre_calle); 
			stmt.setString(24, recUni.entre_calle); 
			stmt.setString(25, recUni.cod_entre_calle2);
			stmt.setString(26, recUni.entre_calle2);
			stmt.setString(27, recUni.nro_manzana);
			stmt.setString(28, recUni.cod_localidad); 
			stmt.setString(29, recUni.localidad); 
			stmt.setString(30, recUni.cod_partido);
			stmt.setString(31, recUni.partido); 
			stmt.setString(32, recUni.sucursal_tecnica); 
			stmt.setString(33, recUni.nom_suc_tecnica); 
			stmt.setString(34, recUni.cod_subestacion); 
			stmt.setString(35, recUni.nombre_subestacion); 
			stmt.setString(36, recUni.alimentador); 
			stmt.setString(37, recUni.centro_trans);
			stmt.setString(38, recUni.trabajo_requerido);			 
			stmt.setString(39, recUni.cod_agrupacion); 
			stmt.setString(40, recUni.reclamo_reincident); 
			stmt.setInt(41, recUni.plazo_tecnico);
			stmt.setTimestamp(42, new Timestamp(recUni.fecha_vto_real_con.getTime())); 
			stmt.setString(43, recUni.nro_expediente);
			stmt.setString(44, recUni.sucursal_comercial);
			stmt.setString(45, recUni.sin_tecni);
			stmt.setTimestamp(46, new Timestamp(recUni.fecha_ini_edesur.getTime()));
			stmt.setString(47, recUni.derivado);
			stmt.setTimestamp(48, new Timestamp(recUni.fecha_reclamo.getTime()));
 			
			stmt.executeUpdate();
		}
		return true;
	}
	
	private boolean sendMensaje(Connection connection,ceMensajeDTO regMen) throws SQLException{
		
		try(PreparedStatement stmt = connection.prepareStatement(XPRO_ENVIAR)){
			stmt.setLong(1, regMen.getNroMensaje());
			stmt.setString(2, regMen.getProcedimiento());
			stmt.setString(3, regMen.getEtapa());
			stmt.setString(4, regMen.getPrivacidad());
			stmt.setString(5, regMen.getUrgencia());
			stmt.setString(6, regMen.getEncriptado());
			stmt.setString(7, regMen.getReferencia());
			stmt.setString(8, regMen.getRolCon());
			stmt.setString(9, regMen.getRolOrg());
			stmt.setString(10, regMen.getRolDst());
			stmt.setInt(11, regMen.getEmpCon());
			stmt.setInt(12, regMen.getEmpOrg());
			stmt.setInt(13, regMen.getEmpDst());
			stmt.setString(14, regMen.getTexto());
			
			stmt.execute();
		}
		return true;
	}

	
	
	private static final String SEL_FERIADOS = "SELECT fecha FROM feriados "+
			"WHERE fecha >= TODAY "+
			"ORDER BY fecha ASC ";
			
			
	private static final String SEL_CASOS = "SELECT caso, nro_orden, data_in " +
			"FROM sfc_interface " +
			"WHERE estado = 0 " +
			"AND tarifa = 'T1' " +
			"AND tipo_sol = 'CALCOM' ";
	
	private static final String SEL_PARAMETROS = "SELECT pa_doc_resol, "+ 
			  "pa_doc_peri, "+
			  "pa_docum_inf24, "+
			  "pa_docum_inf48, "+
			  "pa_docum_inf30, "+
			  "pa_horacierre, "+
			  "pa_tema_svp, "+
			  "pa_tema_avp, "+
			  "pa_tema_rec, "+
			  "pa_tema_rpd, "+
			  "pa_tema_qja, "+
			  "pa_tema_req, "+
			  "pa_doc_cierre, "+
			  "pa_doc_cednot, "+
			  "pa_doc_srpta "+
			  "FROM contacto:ce_parametros ";
	
	private static final String VAL_CLIENTE_T1 = "SELECT COUNT(*) FROM synergia:cliente "+
			"WHERE numero_cliente = ? "+
			"AND estado_cliente = 0 ";

	private static final String VAL_CLIENTE_T2 = "SELECT COUNT(*) FROM contacto:t3_cliente "+
			"WHERE t3_numero_cliente = ? ";
	
	private static final String VAL_TEMA = "SELECT COUNT(*) FROM contacto:ce_tab_tema "+
			"WHERE tt_tema = ? "+
			"AND (tt_fecha_baja IS NULL OR tt_fecha_baja > TODAY) ";

	private static final String VAL_MOTIVO = "SELECT COUNT(*) FROM contacto:ce_tab_motivo "+
			"WHERE tv_motivo = ? "+
			"AND tv_cod_tema = ? "+
			"AND (tv_fecha_baja IS NULL OR tv_fecha_baja > TODAY) ";

	private static final String SEL_PARAM_LOCAL = "SELECT mt_plazo, "+
		    "mt_cod_docum_ent, "+
		    "mt_cod_docum_sal "+
		    "FROM contacto:ce_mot_transi "+
		    "WHERE mt_cod_tema = ? "+
		    "AND mt_reclamo_ini = 'S' "+
		    "AND mt_default = 'S' ";

	private static final String SEL_TEMA_SVP = "SELECT  COUNT(*) "+
		    "FROM synergia:tabla "+
		    "WHERE nomtabla = 'TEMSVP' "+
		    "AND sucursal = '0000' "+
		    "AND codigo = ? "+
		    "AND fecha_activacion <= TODAY "+
		    "AND (fecha_desactivac > TODAY OR fecha_desactivac IS  NULL )";
	
	private static final String SEL_CAMBIA_MARCAS = "SELECT tv_cambia_marcas "+
			"FROM contacto:ce_tab_motivo "+
			"WHERE tv_motivo = ? ";

			
	private static final String SEL_CLIENTE_T1 = "SELECT c.nombre, "+
			"c.nom_partido, "+
			"c.nom_calle, "+
			"c.nro_dir, "+
			"c.piso_dir, "+
			"c.depto_dir, "+
			"c.telefono, "+
			"c.sector, "+
			"c.tip_doc, "+
			"c.nro_doc, "+
			"c.tarifa, "+
			"c.tiene_corte_rest , "+
			"c.estado_cobrabilida, "+
			"m.numero_medidor, "+
			"c.sucursal, " +
			"c.nom_comuna "+
			"FROM synergia:cliente c, synergia:medid m "+
			"WHERE c.numero_cliente = ? "+
			"AND m.numero_cliente = c.numero_cliente "+
			"AND m.estado = 'I' ";
	
	private static final String SEL_CLIENTE_T2 = "SELECT t3_nombre, "+
			"t3_nom_calle, "+
			"t3_nro_dir, "+
			"t3_piso_dir, "+
			"t3_depto_dir, "+
			"t3_telefono, "+
			"t3_plan, "+
			"t3_medidor1, "+
			"t3_medidor2, "+
			"t3_medidor3, "+
			"t3_centro_operativo, "+
			"t3_tipo_cliente "+
			"FROM contacto:t3_cliente "+
			"WHERE t3_numero_cliente = ? ";
			
	private static final String SEL_NRO_RECLAMO = "SELECT tc_ult_numero "+
			"FROM contacto:ce_tab_codigo "+
			"WHERE tc_codigo = 'R' "+
			"FOR UPDATE ";
	
	private static final String SET_NRO_RECLAMO = "UPDATE contacto:ce_tab_codigo SET "+
			"tc_ult_numero = ? "+
			"WHERE tc_codigo = 'R' ";

	private static final String SEL_ANALISTA = "SELECT rol, area FROM sfc_roles "+
			"WHERE procedimiento = 'CALCOM' "+
			"AND sucursal = ? ";

	private static final String INS_RECLAMO = "INSERT INTO ce_reclamo( "+
			"re_reclamo, "+
			"re_cod_tema, "+
			"re_cod_canal, "+
			"re_cod_docum_ent, "+
			"re_cod_motivo, "+
			"re_cod_ejec_edes, "+
			"re_ejecedes_ini, "+
			"re_ndocum_enre, "+
			"re_fecha_reclamo, "+
			"re_fecha_sal_enre, "+
			"re_fecha_ig_edes, "+
			"re_fecha_ig_sist, "+
			"re_fecha_vto, "+
			"re_cerrado, "+
			"re_multimotivo, "+
			"re_cod_cliente, "+
			"re_tarifa, "+
			"re_sucursal "+
			")VALUES( "+
			"?,?,?,?,?,?, "+
			"?,?,?,?, "+
			"?,?, "+
			"?,'N','N',?, "+
			"?,?) ";
		
	private static final String SEL_NRO_MODIF = "SELECT numero FROM synergia:numao "+ 
			"WHERE tipo_orden = ? "+
			"AND area = ? "+
			"FOR UPDATE ";
	
	private static final String INS_NRO_MODIF = "INSERT INTO synergia:numao ( "+
			"tipo_orden, area, numero "+
			")VALUES( "+
			"?, ?, ?) ";
	
	private static final String UPD_NRO_MODIF =	"UPDATE synergia:numao SET "+
			"numero = ? "+
			"WHERE tipo_orden = ? "+
			"AND area = ? ";

	private static final String INS_MODIF = "INSERT INTO synergia:modif "+ 
			"(numero_cliente, "+
			"tipo_orden, "+
			"numero_orden, "+
			"ficha, "+
			"fecha_modif, "+
			"tipo_cliente, "+
			"codigo_modif, "+
			"dato_anterior, "+
			"dato_nuevo, "+
			"proced, "+
			"dir_ip "+
			")VALUES( "+
			"?, 'MOD', ?, ?, CURRENT, 'A', ?, ?, ?, 'CALCOM-SFC', 'SALEFORCE') ";
	
	private static final String UPD_CLIENTE = "UPDATE synergia:cliente SET "+
			"estado_cobrabilida = 'C', "+
			"tiene_corte_rest = 'S' "+
			"WHERE numero_cliente = ? ";

	private static final String INS_CLIENTE_RECLAMO = "INSERT INTO contacto:ce_cliente_reclamo "+ 
			"(cr_reclamo, "+
			"cr_nombre, "+
			"cr_tarifa, "+
			"cr_domicilio, "+ 
			"cr_localidad, "+
			"cr_tel, "+
			"cr_sucursal, "+ 
			"cr_medidor1, "+
			"cr_medidor2, "+
			"cr_medidor3, "+
			"cr_plan, "+
			"cr_documento, "+ 
			"cr_tipo_doc, "+
			"cr_tarifa_cli "+
			")VALUES( "+
			"?, ?, ?, ?, ?, ?, ?, "+ 
			"?, ?, ?, ?, ?, ?, ?) ";
	
	private static final String INS_MOTIVO = "INSERT INTO contacto:ce_multimotivo "+
			"(mu_reclamo, "+
			"mu_cod_motivo, "+
			"mu_principal "+
			")VALUES( "+
			"?, ?, 'S') ";
			
	private static final String INS_RECLAMO_ACSEG = "INSERT INTO contacto:ce_reclamo_acseg "+
			"(as_reclamo, "+
			"as_domicilio, "+
			"as_sucursal "+
			")VALUES( ?, ?, ?) ";

	private static final String INS_RECLAMO_CALLES = "INSERT INTO contacto:ce_reclamo_calles "+
			"(rc_sucursal, "+
			"rc_calle, "+
			"rc_nro, "+
			"rc_entre_calle, "+
			"rc_y_calle, "+
			"rc_reclamo "+
			")VALUES( "+
			" ?, ?, ?, ?, ?, ?)";

	private static final String INS_DOCUM_INGRESO = "INSERT INTO contacto:ce_docum_ingreso "+ 
			"(di_reclamo, "+
			"di_cod_docum_ent, "+
			"di_ndocum_enre, "+
			"di_fecha_enre, "+
			"di_fecha_ig_edes, "+
			"di_fecha_ig_sist, "+
			"di_origen, "+
			"di_conf_expediente "+
			")VALUES( "+
			"?, ?, ?, CURRENT, CURRENT, CURRENT, ?, 'N')";
	
	private static final String INS_RECLAMO_ETAPA = "INSERT INTO contacto:ce_reclamo_etapa "+  
			"(rt_reclamo, "+
			"rt_cod_docum_ent, "+
			"rt_cod_docum_sal, "+
			"rt_fecha_inicio, "+
			"rt_fecha_fin_sug, "+
			"rt_fecha_fin_camb, "+
			"rt_prorroga, "+
			"rt_mensa_pendiente, "+
			"rt_aceptada, "+
			"rt_cerrado, "+
			"rt_ndocum_enre, "+
			"rt_histo_mensa "+
			")VALUES( "+
			"?, ?, ?, CURRENT, ?, ?, "+
			"'N', 'S', 'N', 'N', "+ //El segundo campo 'S' es xq se envia el mensaje en el mismo acto
			"?, 'N') ";

	private static final String INS_RECLAMO_INTERV = "INSERT INTO contacto:ce_reclamo_interv "+
			"(ri_reclamo, "+
			"ri_ejec_serv, "+
			"ri_rol_intervi, "+
			"ri_fecha_cambio "+
			")VALUES( "+
			"?, ?, ?, CURRENT) ";
	

	private static final String SEL_NRO_MENSAJE = "{call xpro_crear(1, 'CALCOM')}";
	
	private static final String SEL_DESCRI_TABLA = "SELECT TRIM(descripcion) FROM synergia:tabla "+
			"WHERE nomtabla = ? "+
			"AND codigo = ? "+
			"AND sucursal = '0000' "+
			"AND fecha_activacion <= TODAY "+
			"AND (fecha_desactivac > TODAY OR fecha_desactivac IS NULL) ";
	

	private static final String SEL_RECLA_TECNI_PROCE = "SELECT car_proc_pendiente, plazo, cod_agrupacion "+
			", plazo_adic1, plazo_adic2 "+
			"FROM synergia:recla_tecni_proce "+
			"WHERE motivo_empresa= ? "+
			"AND motivo_cliente= ? "+
			"AND tema IS NULL "+
			"AND trabajo IS NULL "+
			"AND procedimiento = 'CALCOM' "+
			"AND fecha_activacion <= TODAY "+   
			"AND (fecha_desactivac IS NULL OR fecha_desactivac > TODAY) ";
			
	private static final String SEL_PROVEEDOR = "SELECT DISTINCT tp_proveedor , tp_area "+
			"FROM contacto:ce_tab_proveed_int, outer contacto:ct_tab_suctrof "+
			"WHERE tp_proveedor = ? "+
			"AND (tp_fecha_baja IS NULL OR tp_fecha_baja > TODAY) "+
			"AND su_cod_suctrof = tp_sucursal "+
			"AND su_cate_suctrof = 'S' ";
	
	private static final String SEL_TECNI = "SELECT codigo_voltaje, "+ 
			"tec_cod_suc, "+
			"tec_sucursal, "+
			"tec_cod_part, "+
			"tec_partido, "+
			"tec_cod_local, "+
			"tec_localidad, "+
			"tec_cod_calle, "+
			"tec_nom_calle, "+
			"tec_nro_dir, "+
			"tec_piso_dir, "+
			"tec_depto_dir, "+
			"tec_cod_entre, "+
			"tec_entre_calle1, "+ 
			"tec_cod_ycalle, "+
			"tec_entre_calle2, "+
			"tec_manzana, "+
			"tec_centro_trans, "+ 
			"tec_alimentador, "+
			"tec_subestacion, "+
			"tec_nom_subest "+
			"FROM SYNERGIA:tecni WHERE numero_cliente = ? ";
	
	private static final String SEL_T3_CADENA = "SELECT sucursal, "+
			"nombre_sucursal, "+
			"partido, "+
			"nombre_partido, "+
			"codigo_localidad, "+ 
			"nombre_localidad, "+
			"codigo_calle, "+
			"nombre_calle, "+
			"nro_dir, "+
			"piso_dir, "+
			"dpto_dir, "+
			"codigo_entre1, "+
			"nom_calle_entre1, "+
			"codigo_entre2, "+
			"nom_calle_entre2, "+
			"manzana, "+
			"salida_bt_ct, "+
			"alim_red_normal, "+
			"codigo_subestacion, "+
			"nombre_subestacion "+
			"FROM contacto:t3_cad_electrica "+
			"WHERE codigo_cuenta = ? ";

	private static final String SEL_TIPO_DOCUMENTO = "SELECT  TRIM(valor_alf) "+
			"FROM synergia:tabla "+
			"WHERE nomtabla = 'TEMSVP' "+
			"AND sucursal = '0000' "+
			"AND codigo = ? "+
			"AND fecha_activacion <=  TODAY "+
			"AND (fecha_desactivac > TODAY OR fecha_desactivac IS NULL ) ";
	
	private static final String INS_MENSA_CABEC = "INSERT INTO  contacto:ce_mensa_cabec( "+
			"mc_nmensaje, "+
			"mc_recexpe, "+
			"mc_cod_docum_ent, "+
			"mc_tipo_recexpe, "+
			"mc_rolinicial, "+
			"mc_proveedor, "+
			"mc_fecha_venc, "+
			"mc_fecha_inicio, "+
			"mc_vencido "+
			")VALUES( "+
			"?, ?, ?, 'R', ?, ?, ?, ?, 'N') ";
	
	private static final String INS_MENSA_ITEM = "INSERT INTO contacto:ce_mensa_item( "+
			"mi_nmensaje, "+
			"mi_cod_proveedor, "+
			"mi_carpeta, "+
			"mi_fecha_mensaje, "+
			"mi_area_prov, "+
			"mi_fecha_venc, "+
			"mi_origen, "+
			"mi_area_origen "+
			")VALUES ( "+
			"?, ?, ?, ?, ?, ?, ?, ?) ";
	
	private static final String INS_ORDEN = "INSERT INTO synergia:orden "+
			"(tipo_orden, "+
			"numero_orden, "+
			"mensaje_xnear, "+
			"servidor, "+
			"area_emisora, "+
			"fecha_inicio, "+
			"ident_etapa, "+
			"term_dir, "+
			"area_ejecutora, "+
			"rol_usuario, "+
			"numero_orden_rel, "+
			"valor_cobro, "+
			"numero_cliente, "+
			"vencimiento, "+
			"cuenta_conver "+
			")VALUES( " +
			"'CAL', ?, ?, 1, "+
			"?, ?, 'RQ', 'SALESFORCE', ?, "+
			"?, 0, 0, ?, ?, "+
			"'0000000000000000') ";
	
	private static final String INS_ETAPA_ORDEN = "INSERT INTO synergia:etapa_orden "+
			"(mensaje_xnear, "+
			"ident_etapa, "+
			"fecha_etapa "+
			")VALUES( "+
			"?, 'RQ' , ?) ";
	
	private static final String INS_REC_REC_UNI = "INSERT INTO synergia:rec_rec_uni ( "+
			"numero_cliente, "+
			"etapa, "+
			"tarifa, "+
			"origen, "+
			"tipo_documento, "+
			"nro_reclamo, "+
			"nro_mensaje, "+
			"tipo_reclamo, "+
			"motivo_empresa, "+
			"motivo_cliente, "+
			"fecha_ini_contacto, "+
			"plazo, "+
			"fecha_vto_con, "+
			"fecha_ingreso_ct, "+
			"fecha_vto_ct, "+
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
			"trabajo_requerido, "+
			"cod_agrupacion, "+
			"reclamo_reincident, "+
			"plazo_tecnico, "+
			"fecha_vto_real_con, "+
			"nro_expediente, "+
			"sucursal_comercial, "+
			"sin_tecni, "+
			"fecha_ini_edesur, "+
			"derivado, "+
			"fecha_reclamo "+
			")VALUES( "+
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+
			"?, ?, ?, ?, ?, ?, ?, ?) ";
	
	private static final String XPRO_ENVIAR = "EXECUTE PROCEDURE xpro_enviar ( " +
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

}
