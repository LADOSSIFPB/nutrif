package br.edu.ladoss.entity.migration;

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

@XmlRootElement(name = "extratoRefeicao")
@Entity
@Table(name = "tb_extrato_refeicao")
@NamedQuery(name = "ExtratoRefeicao.getAll", query = "from ExtratoRefeicao")
public class ExtratoRefeicao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_extrato_refeicao")
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_extrato", insertable = true, updatable = true)
	private Date dataExtrato;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_refeicao")
	private Refeicao refeicao;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_dia")
	private Dia dia;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_campus")
	private Campus campus;
	
	@Column(name = "qtd_pretencao_refeicao")
	private int quantidadePretensaoRefeicao;
	
	@Column(name = "qtd_refeicao_realizada")
	private int quantidadeRefeicaoRealizada;
	
	@Column(name = "qtd_refeicao_nao_realizada")
	private int quantidadeRefeicaoNaoRealizada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_execucao", insertable = true, updatable = true)
	private Date dataExecucao;

	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public Date getDataExtrato() {
		return dataExtrato;
	}

	public void setDataExtrato(Date dataExtrato) {
		this.dataExtrato = dataExtrato;
	}

	@XmlElement
	public Refeicao getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(Refeicao refeicao) {
		this.refeicao = refeicao;
	}

	@XmlElement
	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	@XmlElement
	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	
	@XmlElement
	public int getQuantidadePretensaoRefeicao() {
		return quantidadePretensaoRefeicao;
	}

	public void setQuantidadePretensaoRefeicao(int quantidadePretensaoRefeicao) {
		this.quantidadePretensaoRefeicao = quantidadePretensaoRefeicao;
	}

	@XmlElement
	public int getQuantidadeRefeicaoRealizada() {
		return quantidadeRefeicaoRealizada;
	}

	public void setQuantidadeRefeicaoRealizada(int quantidadeRefeicaoRealizada) {
		this.quantidadeRefeicaoRealizada = quantidadeRefeicaoRealizada;
	}

	@XmlElement
	public int getQuantidadeRefeicaoNaoRealizada() {
		return quantidadeRefeicaoNaoRealizada;
	}

	public void setQuantidadeRefeicaoNaoRealizada(int quantidadeRefeicaoNaoRealizada) {
		this.quantidadeRefeicaoNaoRealizada = quantidadeRefeicaoNaoRealizada;
	}
	
	@XmlElement
	public Date getDataExecucao() {
		return dataExecucao;
	}

	public void setDataExecucao(Date dataExcucao) {
		this.dataExecucao = dataExcucao;
	}
	
	@Override
	public String toString() {
		return "Migracao - ExtratoRefeicao [id=" + id 
				+ ", dia=" + dia
				+ ", refeicao=" + refeicao
				+ ", campus=" + campus
				+ ", dataExtrato=" + dataExtrato
				+ ", quantidadePretensaoRefeicao=" + quantidadePretensaoRefeicao
				+ ", quantidadeRefeicaoRealizada=" + quantidadeRefeicaoRealizada 
				+ ", quantidadeRefeicaoNaoRealizada=" + quantidadeRefeicaoNaoRealizada +"]";
	}	
}