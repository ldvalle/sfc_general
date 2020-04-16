package entidades;

import java.text.SimpleDateFormat;

public class motContactoDTO {

	public long  mo_co_numero;
	public String  mo_cod_motivo;
	public String  mo_cod_mot_empresa;
	public String  mo_fecha_vto;
	public String  mo_vto_real_com;
	public String  mo_suc_ag_contacto;
	public String  mo_suc_contacto;
	public String  mo_oficina;
	public String  mo_fecha_inicio;
	public String  mo_rol_inicio;
	public String  mo_tipo_contacto;
	public String  mo_fecha_proceso;
	public String  mo_principal;
	public String  mo_estado;
	public String  mo_fecha_estado;
	public String  descMotCliente;
	public String  descMotEmpresa;
	
	public motContactoDTO(dataInDTO regData, parametrosDTO regPar) {
		this.mo_cod_motivo = regData.motivo;
		this.mo_cod_mot_empresa = regData.sub_motivo;
		
		SimpleDateFormat fechaF = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		this.mo_fecha_vto = fechaF.format(regPar.dFechaVto);
		this.mo_vto_real_com = fechaF.format(regPar.dFechaVto);
		
	}
}
