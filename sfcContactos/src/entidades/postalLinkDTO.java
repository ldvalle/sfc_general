package entidades;

public class postalLinkDTO {
	private String sRolOrigen;
	private String sAreaOrigen;
	private String sRolDestino;
	private String sAreaDestino;
	private String sAnalEdesur;
	private String sCodProveedor;
	private String sAreaProveedor;
	
	public postalLinkDTO(String sRolOrigen, String sAreaOrigen, String sRolDestino, String sAreaDestino, String sAnalEdesur, String sCodProveedor, String sAreaProveedor) {
		this.sRolOrigen = sRolOrigen;
		this.sAreaOrigen = sAreaOrigen;
		this.sRolDestino = sRolDestino;
		this.sAreaDestino = sAreaDestino;
		this.sAnalEdesur = sAnalEdesur;
		this.sCodProveedor = sCodProveedor;
		this.sAreaProveedor = sAreaProveedor;
	}
	
	public String getRolOrigen() {
		return sRolOrigen;
	}
	public String getAreaOrigen() {
		return sAreaOrigen;
	}
	public String getRolDestino() {
		return sRolDestino;
	}
	public String getAreaDestino() {
		return sAreaDestino;
	}
	public String getAnalEdesur() {
		return sAnalEdesur;
	}
	public String getCodProveedor() {
		return sCodProveedor;
	}
	public String getAreaProveedor() {
		return sAreaProveedor;
	}
	
}
