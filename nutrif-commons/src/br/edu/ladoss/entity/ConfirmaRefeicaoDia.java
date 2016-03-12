package br.edu.ladoss.entity;

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

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@XmlRootElement(name = "confirmaRefeicaoDia")
@Embeddable
public class ConfirmaRefeicaoDia implements Serializable {

	private static final long serialVersionUID = 4103942093838478635L;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_dia_refeicao", referencedColumnName="id_dia_refeicao")
	private DiaRefeicao diaRefeicao;

	@Generated(GenerationTime.INSERT)
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_refeicao", insertable = true, nullable = false)
	private Date dataRefeicao;

	@XmlElement
	public DiaRefeicao getDiaRefeicao() {
		return diaRefeicao;
	}

	public void setDiaRefeicao(DiaRefeicao diaRefeicao) {
		this.diaRefeicao = diaRefeicao;
	}

	@XmlElement
	public Date getDataRefeicao() {
		return dataRefeicao;
	}

	public void setDataRefeicao(Date dataRefeicao) {
		this.dataRefeicao = dataRefeicao;
	}

	@Override
	public String toString() {
		return "ConfirmaRefeicaoDia [DiaRefeicao=" + diaRefeicao 
				+ ", dataRefeicao=" + dataRefeicao + "]";
	}
}