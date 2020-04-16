package entidades;


public class ceClienteReclamoDTO {

	private long	cr_reclamo;
	private String	cr_nombre;
	private String	cr_tarifa;
	private String	cr_domicilio;
	private String	cr_localidad;
	private String	cr_tel;
	private String	cr_sucursal;
	private String	cr_medidor1;
	private String	cr_medidor2;
	private String	cr_medidor3;
	private int		cr_plan;
	private String	cr_documento;
	private String	cr_tipo_doc;
	private String	cr_tarifa_cli;
	
	
	public ceClienteReclamoDTO(ceReclamoDTO recla, clienteDTO cliente) {
		String sDomicilio="";
		
		sDomicilio=cliente.nom_calle.trim() + " " + cliente.nro_dir.trim();
		if(!cliente.piso_dir.trim().equals("") && cliente.piso_dir != null ) {
			sDomicilio += " piso: " + cliente.piso_dir.trim(); 
		}
		if(!cliente.depto_dir.trim().equals("") && cliente.depto_dir != null ) {
			sDomicilio += " depto: " + cliente.depto_dir.trim(); 
		}

		this.cr_reclamo = recla.getReclamo();
		this.cr_nombre = cliente.nombre.trim();
		this.cr_tarifa=recla.getTarifa().trim();
		this.cr_domicilio = sDomicilio;
		this.cr_localidad=cliente.nom_comuna.trim();
		this.cr_tel = cliente.telefono.trim();
		this.cr_sucursal = cliente.sucursal.trim();
		this.cr_medidor1 = Long.toString(cliente.numero_medidor);
		if(cliente.numero_medidor2>0)
			this.cr_medidor2 = Long.toString(cliente.numero_medidor2);
		if(cliente.numero_medidor3>0)
			this.cr_medidor3 = Long.toString(cliente.numero_medidor3);
		this.cr_plan=cliente.sector;
		this.cr_documento= String.format("%.0f", cliente.nro_doc);
		this.cr_tipo_doc=cliente.tip_doc.trim();
		this.cr_tarifa_cli=cliente.tarifa.trim();
	}

	public long getReclamo() {
		return cr_reclamo;
	}
	public String getNombre() {
		return cr_nombre;
	}
	public String getTaria() {
		return cr_tarifa;
	}
	public String getDomicilio() {
		return cr_domicilio;
	}
	public String getLocalidad() {
		return cr_localidad;
	}
	public String getTelefono() {
		return cr_tel;
	}
	public String getSucursal() {
		return cr_sucursal;
	}
	public String getMedidor1() {
		return cr_medidor1;
	}
	public String getMedidor2() {
		return cr_medidor2;
	}
	public String getMedidor3() {
		return cr_medidor3;
	}
	public int getPlan() {
		return cr_plan;
	}
	public String getDocumento() {
		return cr_documento;
	}
	public String getTipoDoc() {
		return cr_tipo_doc;
	}
	public String getTarifaCliente() {
		return cr_tarifa_cli;
	}
	
}
