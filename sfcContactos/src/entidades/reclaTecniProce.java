package entidades;

public class reclaTecniProce {

	private String	car_proc_pendiente; 
	private int	plazo; 
	private String	cod_agrupacion; 
	private int	plazo_adic1; 
	private int	plazo_adic2;
	
	public void setCarProcPendiente(String car_proc_pendiente) {
		this.car_proc_pendiente = car_proc_pendiente;
	}
	public String getCarProcPendiente() {
		return car_proc_pendiente;
	}
	
	public void setPlazo(int plazo) {
		this.plazo=plazo;
	}
	public int getPlazo() {
		return plazo;
	}
	
	public void setCodAgrupacion(String cod_agrupacion) {
		this.cod_agrupacion = cod_agrupacion;
	}
	public String getCodAgrupacion() {
		return cod_agrupacion;
	}
	
	public void setPlazoAdic1(int plazo_adic1) {
		this.plazo_adic1 = plazo_adic1;
	}
	public int getPlazoAdic1() {
		return plazo_adic1;
	}
	
	public void setPlazoAdic2(int plazo_adic2) {
		this.plazo_adic2 = plazo_adic2;
	}
	public int getPlazoAdic2() {
		return plazo_adic2;
	}


	
}

