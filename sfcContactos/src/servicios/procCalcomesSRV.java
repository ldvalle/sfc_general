package servicios;

import entidades.*;
import dao.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;
import java.util.regex.Pattern;

public class procCalcomesSRV {
	private Collection<Date>lstFeriados=null;
	private Date dFechaHoy = new Date();
	private ceParametrosDTO regPar = null;
	
	public void CalComes() {
		SimpleDateFormat fmtDate= new SimpleDateFormat("yyyy-MM-dd");
		String sFechaHoy=fmtDate.format(dFechaHoy);
		
		calcomDAO miDao = new calcomDAO();
		
		// Cargar Feriados
		try {
			lstFeriados = miDao.getFeriados();
		} catch (SQLException e) {
			System.out.println("No se pudieron cargar los Feriados");
			e.printStackTrace();
			System.exit(1);
		}
		
		// Cargar Parametros Globales
		try {
			regPar = miDao.getParam();
		}catch (SQLException e) {
			System.out.println("No se pudieron cargar los Parametros Globales");
			e.printStackTrace();
			System.exit(1);
		}

		// Cargar Pedidos
		Collection<interfaceDTO>lstInterface = null;
		try {
			lstInterface = miDao.getLstCasos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Procesa Pedidos
		for(interfaceDTO fila: lstInterface) {
			if(!ProcesaCalCom(fila)) {
				System.out.println("Fallo carga de caso " + fila.caso);
			}
		}
		

		
	}
	
	private Boolean ProcesaCalCom(interfaceDTO regInter) {
		calcomDAO miDao = new calcomDAO();
		String sRolAnalista="";
		
		//Cargar Data JSON
		dataInDTO regData = new dataInDTO(regInter, "CALCOM");
		
		
		paramLocalDTO regParLocal = null; 
		try {
			if(PasaValida(regData)) {
				//***** Etapa Reclamo *****
				long lNroReclamo;
				
				//Cargar Parametros para el tema/motivo
				regParLocal = miDao.getParamLocal(regData.tema);
				regParLocal.esTemaSVP = miDao.esTemaSvp(regData.tema);
				regParLocal.cambiaMarcas = miDao.motCambiaMarcas(Integer.parseInt(regData.motivo));
				regParLocal.cambiaCobrabilida=false;
				regParLocal.cambiaCorte=false;
				
				//Cargar el cliente
				clienteDTO regCliente = null;
				if(regData.tarifa.equals("T1")) {
					regCliente = miDao.getDataClienteT1(regData.numero_cliente);
					regCliente.desc_estCobrabilidad = miDao.getDesriTabla("ESTCOB", regCliente.estado_cobrabilida.trim());
				}else{
					regCliente = miDao.getDataClienteT2(regData.numero_cliente);
				}
				
				if(regParLocal.cambiaMarcas.equals("S")) {
					if(!regCliente.estado_cobrabilida.equals("9") && !regCliente.estado_cobrabilida.equals("B")
							&& !regCliente.estado_cobrabilida.equals("J") && !regCliente.estado_cobrabilida.equals("C")) {
						regParLocal.cambiaCobrabilida=true;
					}
					if(!regCliente.tiene_corte_rest.equals("S")) {
						regParLocal.cambiaCorte=true;
					}
				}
				
				//Obtener nro.de reclamo
				lNroReclamo = miDao.getNroReclamo();

				//Obtener Analista Edesur
				sRolAnalista = miDao.getAnalista(regCliente.sucursal);
							
				
				//Cargar CE_RECLAMO
				ceReclamoDTO reclamo = new ceReclamoDTO(lNroReclamo, dFechaHoy, sRolAnalista, dFechaHoy, regData, regParLocal, regCliente, lstFeriados);
				if(regParLocal.esTemaSVP) {
					reclamo.setTipoDocumento(miDao.getTipoDocumento(reclamo.getCodTema()));	
				}else {
					reclamo.setTipoDocumento("CALCOM");
				}
				reclamo.setDescriTema(miDao.getDesriTabla("TEMAS", Integer.toString(reclamo.getCodTema())));
				
				
				//Cargar CE_CLIENTE_RECLAMO
				ceClienteReclamoDTO clienteReclamo = new ceClienteReclamoDTO(reclamo, regCliente);
				
				//********************************
				//***** Seccion Mensaje/Orden ****
				//********************************
				
				long lNroMensaje = miDao.getNroMensaje();
			
				//Carga carpeta para mensaje
				reclaTecniProce recla = null;
				recla = miDao.getReclaTecniProce(reclamo.getCodMotivo(), reclamo.getCodTema());
	
				//Busca Proveedor
				String sProveedor = miDao.getProveedor(recla.getCarProcPendiente());
				String partes[] = sProveedor.split(Pattern.quote("|"));
				String sCodProveedor = partes[0];
				String sAreaProveedor ="";
				if(partes.length>1) {
					sAreaProveedor = partes[1];
				}else {
					sAreaProveedor = sCodProveedor;
				}
				postalLinkDTO delivery = new postalLinkDTO("EVDI", "SIC0","","",sRolAnalista, sCodProveedor, sAreaProveedor);

				//Arma Mensaje
				ceMensajeDTO mensaCalcom = new ceMensajeDTO(lNroMensaje, dFechaHoy, delivery.getRolOrigen(), recla.getCarProcPendiente(), "CALCOM", regCliente, reclamo);
				
				//Busca Nro de Orden
				String sNroOrden = miDao.getNroNumao("CAL", delivery.getAreaOrigen());
				
				//Carga Datos Tecnicos
				tecniDTO tecni = miDao.getTecni(reclamo.getCodCliente(), reclamo.getTarifa());
				
				//Armo RecRecUni
				recRecUniDTO recUni = new recRecUniDTO(lNroMensaje, reclamo, recla, regParLocal, tecni, regCliente);
				
				//Lanzar todo !!!
				if(!miDao.regiCalcom(lNroReclamo, lNroMensaje, sNroOrden, regData, regPar, regParLocal, regCliente, delivery, reclamo, clienteReclamo, recla, recUni, mensaCalcom )) {
					System.out.println(String.format("No se pudo registrar el calcom %d para tema %d.", regInter.caso, regData.tema));
				}else {
					System.out.println(String.format("Se generó Mensaje: %d para reclamo: %d y Orden Segen: %s", lNroMensaje, lNroReclamo, sNroOrden));
				}
				
				
			}
		}catch(SQLException ex) {
			System.out.println(String.format("No se pudo cargar el calcom %d para tema %d.", regInter.caso, regData.tema));
			ex.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	private boolean PasaValida(dataInDTO reg) {
		calcomDAO miDao = new calcomDAO();
		boolean bValor=false;
		
		//Validar la tarifa
		if(! reg.tarifa.equals("T1") && !reg.tarifa.equals("T2")) {
			System.out.println("Valor de Tarifa Inválida.");
			return false;
		}
		try {
			//Validar el cliente para la tarifa
			if(reg.tarifa.equals("T1")) {
				bValor = miDao.esClienteT1(reg.numero_cliente);
			}else {
				bValor = miDao.esClienteT2(reg.numero_cliente);
			}
			if(!bValor) {
				System.out.println("El cliente no existe como cliente activo.");
				return false;
			}
			
			//Validar Tema
			bValor=miDao.validoTema(reg.tema);
			if(!bValor) {
				System.out.println("El Tema no existe.");
				return false;
			}
			
			//Validar Motivo
			bValor=miDao.validoMotivo(reg.tema, Integer.parseInt(reg.motivo));
			if(!bValor) {
				System.out.println("La dupla Tema/Motivo no Existe.");
				return false;
			}
		
		}catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	// ------------ General ------------
	
}
