package br.edu.ladoss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "refeicaoRealizada")
@Entity
@Table(name = "tb_refeicao_realizada")
@NamedQuery(name = "RefeicaoRealizada.getAll", query = "from RefeicaoRealizada")
public class RefeicaoRealizada {

	@EmbeddedId	  
	private ConfirmaRefeicaoDia confirmaRefeicaoDia;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hr_refeicao", insertable = false, updatable = false)
	private Date horaRefeicao;

	@XmlElement
	public ConfirmaRefeicaoDia getConfirmaRefeicaoDia() {
		return confirmaRefeicaoDia;
	}

	public void setConfirmaRefeicaoDia(ConfirmaRefeicaoDia confirmaRefeicaoDia) {
		this.confirmaRefeicaoDia = confirmaRefeicaoDia;
	}

	@XmlElement
	public Date getHoraRefeicao() {
		return horaRefeicao;
	}

	public void setHoraRefeicao(Date horaRefeicao) {
		this.horaRefeicao = horaRefeicao;
	}

	@Override
	public String toString() {
		return "RefeicaoRealizada [" + confirmaRefeicaoDia.toString() 
		+ "dataHora=" + horaRefeicao + "]";
	}
}
