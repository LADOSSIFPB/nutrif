package br.edu.ladoss.entity.migration;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bloqueio")
@Entity
@Table(name = "tb_bloqueio_migration")
@NamedQuery(name = "Bloqueio.getAll", query = "from Bloqueio")
public class Bloqueio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bloqueio")
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "fk_id_dia_refeicao", referencedColumnName="id_dia_refeicao")
	private DiaRefeicao diaRefeicao;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_motivo")
	private Motivo motivo;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_funcionario")
	private Funcionario funcionario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_bloqueio", nullable = false,
		    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date dataBloqueio;

	@Column(name = "is_ativo", nullable = false, insertable = true, updatable = true)
	private boolean ativo;

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public DiaRefeicao getDiaRefeicao() {
		return diaRefeicao;
	}

	public void setDiaRefeicao(DiaRefeicao diaRefeicao) {
		this.diaRefeicao = diaRefeicao;
	}

	@XmlElement
	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	@XmlElement
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@XmlElement
	public Date getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	@XmlElement
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public String toString() {
		return "Bloqueio [id=" + id 
				+ ", diaRefeicao=" + diaRefeicao
				+ ", motivo=" + motivo
				+ ", funcionario=" + funcionario
				+ ", dataBloqueio=" + dataBloqueio
				+ ", ativo=" + ativo
				+ "]";
	}
}
