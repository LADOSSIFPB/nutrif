package br.edu.ladoss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "edital")
@Entity
@Table(name = "tb_edital")
@NamedQuery(name = "Edital.getAll", query = "from Edital")
public class Edital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_edital")
	private Integer id;
	
	@Column(name = "nm_edital")
	private String nome;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_campus")
	private Campus campus;
	
	@Column(name = "qtd_contemplados")
	private int quantidadeContemplados;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_inicial", insertable = true, updatable = false)
	private Date dataInicial;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_final", insertable = true, updatable = false)
	private Date dataFinal;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_evento")
	private Evento evento;
	
	/**
	 * Responsável pelos atos do Edital.
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_responsavel")
	private Funcionario responsavel;
	
	/**
	 * Responsável pelo cadastramento do Edital.
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_funcionario")
	private Funcionario funcionario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_insercao", nullable = false,
		    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date dataInsercao;
	
	@Column(name = "is_ativo", columnDefinition = "boolean default true", 
			nullable = false, insertable = false, updatable = true)
	private boolean ativo;

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@XmlElement
	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	@XmlElement
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	@XmlElement
	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	@XmlElement
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@XmlElement
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@XmlElement
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@XmlElement
	public int getQuantidadeContemplados() {
		return quantidadeContemplados;
	}

	public void setQuantidadeContemplados(int quantidadeContemplados) {
		this.quantidadeContemplados = quantidadeContemplados;
	}
	
	@XmlElement
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	@XmlElement
	public Funcionario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Funcionario responsavel) {
		this.responsavel = responsavel;
	}	
	
	@Override
	public String toString() {
		return "Edital [id=" + id 
				+ ", nome=" + nome
				+ ", campus=" + campus
				+ ", quantidadeContemplados=" + quantidadeContemplados
				+ ", dataInicial=" + dataInicial
				+ ", dataFinal=" + dataFinal 
				+ ", evento=" + evento 
				+ ", responsavel=" + responsavel + "]";
	}	
}
