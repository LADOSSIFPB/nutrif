package br.edu.ladoss.entity.migration;

import java.math.BigDecimal;
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

import br.edu.ladoss.data.DataEntity;

@XmlRootElement(name = "campusRefeicao")
@Entity
@Table(name = "tb_campus_refeicao_migration")
@NamedQuery(name = "CampusRefeicao.getAll", query = "from CampusRefeicao")
public class CampusRefeicao implements DataEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_campus_refeicao")
	private Integer id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_id_campus")
	private Campus campus;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_refeicao")
	private Refeicao refeicao;
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_insercao",
		    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date dataInsercao;
	
	@Column(name = "is_ativo", nullable = false, insertable = true, updatable = true)
	private boolean ativo;
		
	public CampusRefeicao() {
		super();
	}
	
	@Override
	@XmlElement
	public Integer getId() {
		return id;
	}	

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement
	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	@XmlElement
	public Refeicao getRefeicao() {
		return refeicao;
	}

	public void setRefeicao(Refeicao refeicao) {
		this.refeicao = refeicao;
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
	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}
	
	@Override
	public String toString() {
		return "CampusRefeicao [id=" + id + ""
				+ ", campus=" + campus
				+ ", refeicao=" + refeicao
				+ ", horaInicio=" + horaInicio 
				+ ", horaFim=" + horaFinal 
				+ ", horaPretensao=" + horaPrevisaoPretensao
				+ ", custo=" + custo
				+ ", dataInsercao=" + dataInsercao
				+ ", ativo=" + ativo + "]";
	}	
}
