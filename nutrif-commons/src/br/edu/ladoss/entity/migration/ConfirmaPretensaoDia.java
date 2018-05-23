package br.edu.ladoss.entity.migration;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "confirmaPretensaoDia")
@Embeddable
public class ConfirmaPretensaoDia implements Serializable {

	private static final long serialVersionUID = 4103942093838478635L;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_dia_refeicao", referencedColumnName="id_dia_refeicao")
	private DiaRefeicao diaRefeicao;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_pretensao", insertable = true, updatable = false)
	private Date dataPretensao;
	
	@XmlElement
	public DiaRefeicao getDiaRefeicao() {
		return diaRefeicao;
	}

	public void setDiaRefeicao(DiaRefeicao diaRefeicao) {
		this.diaRefeicao = diaRefeicao;
	}

	@XmlElement
	public Date getDataPretensao() {
		return new Date(dataPretensao.getTime());
	}

	public void setDataPretensao(Date dataPretensao) {
		
		if (dataPretensao != null) {
			
	        this.dataPretensao = new Date(dataPretensao.getTime());
	        
	    } else {
	    	
	        this.dataPretensao = null;
	    }
	}

	@Override
	public String toString() {
		return "ConfirmaPretensaoDia [DiaRefeicao=" + diaRefeicao 
				+ ", dataPretensao=" + dataPretensao + "]";
	}
	
	@Override
	public boolean equals(Object o) {		
		
		if ((o instanceof ConfirmaPretensaoDia)
				&& ((ConfirmaPretensaoDia) o).getDiaRefeicao().getId() == this.diaRefeicao.getId()
				&& ((ConfirmaPretensaoDia) o).getDataPretensao().compareTo(this.dataPretensao) == 0
				&& ((ConfirmaPretensaoDia) o).getDiaRefeicao().isAtivo() == this.diaRefeicao.isAtivo()) { 
			
			return true; 
			
		} else {
			
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		
		return this.diaRefeicao.getId();
	}
}