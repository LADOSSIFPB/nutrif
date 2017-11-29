package br.edu.ladoss.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_refeicao_realizada", unique = true) // columnDefinition = "INT(10) UNSIGNED AUTO_INCREMENT"
	private Integer id;
	
	@EmbeddedId	  
	private ConfirmaRefeicaoDia confirmaRefeicaoDia;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "hr_refeicao", insertable = true, updatable = false)
	private Date horaRefeicao;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_funcionario")
	private Funcionario inspetor;
	
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
	
	@XmlElement
	public Funcionario getInspetor() {
		return inspetor;
	}

	public void setInspetor(Funcionario inspetor) {
		this.inspetor = inspetor;
	}

	@Override
	public String toString() {
		return "RefeicaoRealizada [id=" + id + ", confirmaRefeicaoDia=" + confirmaRefeicaoDia.toString() 
		+ ", dataHora=" + horaRefeicao + "]";
	}
}
