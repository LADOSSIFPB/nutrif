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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.edu.ladoss.data.DataEntity;

@XmlRootElement(name = "diaRefeicao")
@Entity
@Table(name = "tb_dia_refeicao_migration")
@NamedQuery(name = "DiaRefeicao.getAll", query = "from DiaRefeicao")
public class DiaRefeicao implements DataEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dia_refeicao")
	private Integer id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_id_matricula")
	private Matricula matricula;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_dia")
	private Dia dia;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_refeicao")
	private Refeicao refeicao;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_edital")
	private Edital edital;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_funcionario")
	private Funcionario funcionario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_insercao", nullable = false,
		    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	private Date dataInsercao;
	
	@Column(name = "is_ativo", nullable = false, insertable = true, updatable = true)
	private boolean ativo;
	
	@Column(name = "is_migracao", nullable = false, insertable = true, updatable = true)
	private boolean migracao;
	
	@Transient
	private boolean pretensao;
	
	@Transient
	private boolean refeicaoRealizada;
		
	public DiaRefeicao() {
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
	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	@XmlElement
	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	@XmlElement
	public Refeicao getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(Refeicao refeicao) {
		this.refeicao = refeicao;
	}
	
	@XmlElement
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@XmlElement
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@XmlElement
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@XmlElement
	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}
	
	@XmlElement
	public boolean isMigracao() {
		return migracao;
	}

	public void setMigracao(boolean migracao) {
		this.migracao = migracao;
	}
	
	@XmlElement
	public boolean isPretensao() {
		return pretensao;
	}

	public void setPretensao(boolean pretensao) {
		this.pretensao = pretensao;
	}

	@XmlElement
	public boolean isRefeicaoRealizada() {
		return refeicaoRealizada;
	}

	public void setRefeicaoRealizada(boolean refeicaoRealizada) {
		this.refeicaoRealizada = refeicaoRealizada;
	}
	
	@Override
	public String toString() {
		return "DiaRefeicao [id=" + id + ""
				+ ", matricula=" + matricula 
				+ ", dia=" + dia 
				+ ", refeicao=" + refeicao
				+ ", edital=" + edital 
				+ ", dataInsercao=" + dataInsercao 
				+ ", funcionario=" + funcionario 
				+ ", ativo=" + ativo
				+ ", migracao=" + migracao + "]";
	}	
}
