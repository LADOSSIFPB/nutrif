package br.edu.ladoss.entity.migration;

import java.math.BigDecimal;
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

import br.edu.ladoss.data.DataEntity;

/**
 * Refeição
 * 
 * @author Rhavy Maia Guedes 
 */
@XmlRootElement(name = "refeicao")
@Entity
@Table(name = "tb_refeicao_migration")
@NamedQuery(name = "Refeicao.getAll", query = "from Refeicao")
public class Refeicao implements DataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_refeicao")
	private Integer id;

	@Column(name = "nm_tipo_refeicao")
	private String tipo;
	
	@Column(name = "hr_inicio")
	@Temporal(TemporalType.TIME)
	private Date horaInicio;
	
	@Column(name = "hr_fim")
	@Temporal(TemporalType.TIME)
	private Date horaFinal;
	
	/**
	 * O tempo previsto para lançamento da pretenção será em horas antes que inicie a Refeição.
	 * Nos casos que o dia da refeição sejam posteriores a dias não úteis acrescentar prazo. 
	 */
	@Column(name = "hr_previsao_pretensao")
	@Temporal(TemporalType.TIME)
	private Date horaPrevisaoPretensao;
	
	@Column(name = "vl_custo")
    private BigDecimal custo;
	
	@Column(name = "is_ativo", nullable = false, insertable = true, updatable = true)
	private boolean ativo;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_campus", nullable = false)
	private Campus campus;

	public Refeicao() {
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
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@XmlElement
	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	@XmlElement
	public Date getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}
	
	@XmlElement
	public Date getHoraPrevisaoPretensao() {
		return horaPrevisaoPretensao;
	}

	public void setHoraPrevisaoPretensao(Date horaPrevisaoPretensao) {
		this.horaPrevisaoPretensao = horaPrevisaoPretensao;
	}
	
	@XmlElement
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@XmlElement
	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}
	
	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	
	@Override
	public String toString() {
		return "Refeicao [id=" + id 
				+ ", tipo=" + tipo
				+ ", horaInicio=" + horaInicio 
				+ ", horaFim=" + horaFinal 
				+ ", horaPretensao=" + horaPrevisaoPretensao
				+ ", custo=" + custo
				+ ", isAtivo=" + ativo + "]";
	}
}
