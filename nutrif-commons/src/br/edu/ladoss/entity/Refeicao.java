package br.edu.ladoss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import br.edu.ladoss.data.DataEntity;

/**
 * Refeição
 * 
 * @author Rhavy Maia Guedes 
 */
@XmlRootElement(name = "refeicao")
@Entity
@Table(name = "tb_refeicao")
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
	
	@Columns(columns = {@Column(name = "tp_moeda"), @Column(name = "vl_custo")})
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money custo;
	
	@Column(name = "is_ativo", columnDefinition = "boolean default true", 
			nullable = false, insertable = false, updatable = true)
	private boolean ativo;

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
	public Money getCusto() {
		return custo;
	}
	
	public void setCusto(String valor) {
		
		CurrencyUnit real = CurrencyUnit.of("BRL");
		
		this.custo = Money.of(real, Double.valueOf(valor));
	}	
	
	@Override
	public String toString() {
		return "Refeicao [id=" + id 
				+ ", tipo=" + tipo
				+ ", horaInicio=" + horaInicio 
				+ ", horaFim=" + horaFinal 
				+ ", horaPretensao=" + horaPrevisaoPretensao
				+ ", custo=" + custo.getAmount()
				+ ", isAtivo=" + ativo + "]";
	}
}
