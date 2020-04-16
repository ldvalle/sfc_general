package entidades;

import java.util.Date;

public class ceParametrosDTO {
	private int	doc_resol;
	private int doc_peri;
	private int	docum_inf24;
	private int	docum_inf48;
	private int	docum_inf30;
	private Date	horacierre; //Date time minute to second
	private int	tema_svp;
	private int	tema_avp;
	private int	tema_rec;
	private int	tema_rpd;
	private int	tema_qja;
	private int	tema_req;
	private int	doc_cierre;
	private int	doc_cednot;
	private int	doc_srpta;
	
	public int getDocResol() {
		return doc_resol;
	}
	public void setDocResol(int doc_resol) {
		this.doc_resol = doc_resol;
	}
	
	public int getDocPeri() {
		return doc_peri;
	}
	public void setDocPeri(int doc_peri) {
		this.doc_peri = doc_peri;
	}
	
	public int getDocumInf24() {
		return docum_inf24;
	}
	public void setDocumInf24(int docum_inf24) {
		this.docum_inf24 = docum_inf24;
	}

	public int getDocumInf48() {
		return docum_inf48;
	}
	public void setDocumInf48(int docum_inf48) {
		this.docum_inf48 = docum_inf48;
	}
	
	public int getDocumInf30() {
		return docum_inf30;
	}
	public void setDocumInf30(int docum_inf30) {
		this.docum_inf30 = docum_inf30;
	}

	public Date getHoraCierre() {
		return horacierre;
	}
	public void setHoraCierre(Date horacierre) {
		this.horacierre = horacierre;
	}
	
	public int getTemaSvp() {
		return tema_svp;
	}
	public void setTemaSvp(int tema_svp) {
		this.tema_svp = tema_svp;
	}

	public int getTemaAvp() {
		return tema_avp;
	}
	public void setTemaAvp(int tema_avp) {
		this.tema_avp = tema_avp;
	}

	public int getTemaRec() {
		return tema_rec;
	}
	public void setTemaRec(int tema_rec) {
		this.tema_rec = tema_rec;
	}

	public int getTemaRpd() {
		return tema_rpd;
	}
	public void setTemaRpd(int tema_rpd) {
		this.tema_rpd = tema_rpd;
	}
	
	public int getTemaQja() {
		return tema_qja;
	}
	public void setTemaQja(int tema_qja) {
		this.tema_qja = tema_qja;
	}

	public int getTemaReq() {
		return tema_req;
	}
	public void setTemaReq(int tema_req) {
		this.tema_req = tema_req;
	}
	
	public int getDocCierre() {
		return doc_cierre;
	}
	public void setDocCierre(int doc_cierre) {
		this.doc_cierre = doc_cierre;
	}

	public int getDocCedNot() {
		return doc_cednot;
	}
	public void setDocCedNot(int doc_cednot) {
		this.doc_cednot = doc_cednot;
	}

	public int getDocSrPta() {
		return doc_srpta;
	}
	public void setDocSrPta(int doc_srpta) {
		this.doc_srpta = doc_srpta;
	}
	
}
