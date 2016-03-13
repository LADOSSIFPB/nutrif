package br.edu.ladoss.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import br.edu.ladoss.data.DataEntity;

@XmlRootElement(name = "refeicaoRealizada")
@Entity
@Table(name = "tb_refeicao_realizada")
@NamedQuery(name = "RefeicaoRealizada.getAll", query = "from RefeicaoRealizada")
public class RefeicaoRealizada implements DataEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_refeicao_realizada", unique = true)
	private Integer id;

	@EmbeddedId	  
	private ConfirmaRefeicaoDia confirmaRefeicaoDia;

	@Generated(GenerationTime.INSERT)
	@Temporal(TemporalType.TIME)
	@Column(name = "hr_refeicao", insertable = false, updatable = false, nullable = true)
	private Date horaRefeicao;

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
		return "RefeicaoRealizada [id=" + id + ", dataHora=" + horaRefeicao + "]";
	}
}
