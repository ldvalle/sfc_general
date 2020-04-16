package entidades;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class ceReclamoDTO {

	private long	re_reclamo;
	private int		re_cod_tema;
	private String	re_cod_canal;
	private int		re_cod_docum_ent;
	private int		re_cod_motivo;
	private String	re_cod_ejec_edes;
	private String	re_ejecedes_ini;
	private String	re_ndocum_enre;

	private Date	re_fecha_reclamo;
	private Date	re_fecha_sal_enre;
	private Date	re_fecha_ig_edes;
	private Date	re_fecha_ig_sist;
	
	private Date	re_fecha_vto;
	private long	re_cod_cliente;
	private String	re_tarifa;
	private String	re_sucursal;
	private String	sTipoDocumento;
	private String	sDescriTema;

	private Collection<Date> lstFeriados;
	
	public ceReclamoDTO(long lNroReclamo, Date dFechaHoy, String rolAnalista, Date fechaHoy, dataInDTO regData, paramLocalDTO parLoc, clienteDTO regClie, Collection<Date> lstFeriados) {

		this.re_reclamo=lNroReclamo;
		this.re_cod_tema = regData.tema;
		this.re_cod_canal = regData.canal.trim();
		this.re_cod_docum_ent = parLoc.cod_docum_ent;
		this.re_cod_motivo = Integer.parseInt(regData.motivo);
		this.re_cod_ejec_edes = rolAnalista.trim();
		this.re_ejecedes_ini = rolAnalista.trim();
		this.re_ndocum_enre = regData.nroReclamo.trim();
		
		this.re_cod_cliente = regData.numero_cliente;
		this.re_tarifa=regData.tarifa.trim();
		this.re_sucursal=regClie.sucursal.trim();
		
		this.lstFeriados=lstFeriados;

		this.re_fecha_reclamo=dFechaHoy;
		this.re_fecha_sal_enre=dFechaHoy;
		this.re_fecha_ig_edes=dFechaHoy;
		this.re_fecha_ig_sist=dFechaHoy;
		
		
		this.re_fecha_vto = calculaVenc(fechaHoy, parLoc.plazo, parLoc.esTemaSVP);
	}
	
	public long getReclamo() {
		return re_reclamo;
	}
	public void setReclamo(long re_reclamo) {
		this.re_reclamo = re_reclamo;
	}
	
	public int getCodTema() {
		return re_cod_tema;
	}
	public void setCodTema(int re_cod_tema) {
		this.re_cod_tema=re_cod_tema;
	}
	
	public String getCodCanal() {
		return re_cod_canal;
	}
	public void setCodCanal(String re_cod_canal) {
		this.re_cod_canal = re_cod_canal;
	}
	
	public int getCodDocumEnt() {
		return re_cod_docum_ent;
	}
	public void setCodDocumEnt(int re_cod_docum_ent) {
		this.re_cod_docum_ent=re_cod_docum_ent;
	}
	
	public int getCodMotivo() {
		return re_cod_motivo;
	}
	public void setCodMotivo(int re_cod_motivo) {
		this.re_cod_motivo = re_cod_motivo;
	}
	
	public String getCodEjecEdes() {
		return re_cod_ejec_edes;
	}
	public void setCodEjecEdes(String re_cod_ejec_edes) {
		this.re_cod_ejec_edes = re_cod_ejec_edes;
	}
	
	public String getEjeCedesIni() {
		return re_ejecedes_ini;
	}
	public void setEjeCedesIni(String re_ejecedes_ini) {
		this.re_ejecedes_ini = re_ejecedes_ini;
	}

	public String getNDocumEnre() {
		return re_ndocum_enre;
	}
	public void setNDocumEnre(String re_ndocum_enre) {
		this.re_ndocum_enre = re_ndocum_enre;
	}

	public Date getFechaVto() {
		return re_fecha_vto;
	}
	public void setFechaVto(Date re_fecha_vto) {
		this.re_fecha_vto = re_fecha_vto;
	}

	public long getCodCliente() {
		return re_cod_cliente;
	}
	public void setCodCliente(long re_cod_cliente) {
		this.re_cod_cliente = re_cod_cliente;
	}

	public String getTarifa() {
		return re_tarifa;
	}
	public void setTarifa(String re_tarifa) {
		this.re_tarifa = re_tarifa;
	}

	public String getSucursal() {
		return re_sucursal;
	}
	public void setSucursal(String re_sucursal) {
		this.re_sucursal = re_sucursal;
	}

	public String getTipoDocumento() {
		return sTipoDocumento;
	}
	public void setTipoDocumento(String sTipoDocumento) {
		this.sTipoDocumento = sTipoDocumento;
	}

	public String getDescriTema() {
		return sDescriTema;
	}
	public void setDescriTema(String sDescriTema) {
		this.sDescriTema = sDescriTema;
	}
	
	public Date getFechaReclamo() {
		return re_fecha_reclamo;
	}
	public void setFechaReclamo(Date re_fecha_reclamo) {
		this.re_fecha_reclamo = re_fecha_reclamo;
	}

	public Date getFechaSalEnre() {
		return re_fecha_sal_enre;
	}
	public void setFechaSalEnre(Date re_fecha_sal_enre) {
		this.re_fecha_sal_enre = re_fecha_sal_enre;
	}
	
	public Date getFechaIgEdes() {
		return re_fecha_ig_edes;
	}
	public void setFechaIgEdes(Date re_fecha_ig_edes) {
		this.re_fecha_ig_edes = re_fecha_ig_edes;
	}

	public Date getFechaIgSist() {
		return re_fecha_ig_sist;
	}
	public void setFechaIgSist(Date re_fecha_ig_sist) {
		this.re_fecha_ig_sist = re_fecha_ig_sist;
	}

	
	private Date calculaVenc(Date d, int iPlazo, boolean diasCorridos) {
		Date miFecha = null;
		int  iDia, i;
		int	 iFeriado;
		int  iContador=0;

		if(diasCorridos) {
			//Dias corridos pero que caiga en día hábil
			miFecha = sumaDias(d, iPlazo);
			iDia=getDiaSemana(miFecha);
			iFeriado = getFeriado(miFecha);
			
			while((iDia == 1 || iDia == 7 || iFeriado == 1) && iContador <= 10) {
				miFecha = sumaDias(miFecha, 1);
				iDia=getDiaSemana(miFecha);
				iFeriado = getFeriado(miFecha);
				iContador++;
			}
		}else {
			//días hábiles
			i=1;
			miFecha=d;
			while(i<=iPlazo) {
				miFecha = sumaDias(miFecha, 1);
				iDia=getDiaSemana(miFecha);
				iFeriado = getFeriado(miFecha);
				if(iDia != 1 && iDia != 7 && iFeriado != 1){
					i++;
				}
			}
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
