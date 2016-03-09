package br.edu.ladoss.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "refeicaoRealizada")
@Entity
@Table(name = "tb_refeicao_realizada")
public class RefeicaoRealizada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_cronograma_refeicao")
	CronogramaRefeicao cronogramaRefeicao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_e_hora")
	Timestamp timestamp;

	public RefeicaoRealizada() {
		super();
	}

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public CronogramaRefeicao getCronogramaRefeicao() {
		return cronogramaRefeicao;
	}

	public void setCronogramaRefeicao(CronogramaRefeicao cronogramaRefeicao) {
		this.cronogramaRefeicao = cronogramaRefeicao;
	}

	@XmlElement
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "RefeicaoRealizada [id=" + id + ", cronogramaRefeicao=" + cronogramaRefeicao
				+ ", timestamp=" + timestamp + "]";
	}

}
