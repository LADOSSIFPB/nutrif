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

import br.edu.ladoss.data.DataEntity;

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
	
	@Column(name = "hr_pretensao")
	@Temporal(TemporalType.TIME)
	private Date horaPretensao;	
	
	@Column(name = "nr_dia_previsto_pretensao", columnDefinition = "int default 1")
	private int diaPrevistoPretensao;
	
	@Column(name = "is_previsto_pretensao", columnDefinition = "tinyint default false")
	private boolean isPrevistoPretensao;

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
	public Date getHoraPretensao() {
		return horaPretensao;
	}

	public void setHoraPretensao(Date horaPretensao) {
		this.horaPretensao = horaPretensao;
	}
	
	@XmlElement
	public int getDiaPrevistoPretensao() {
		return diaPrevistoPretensao;
	}

	public void setDiaPrevistoPretensao(int diaPrevistoPretensao) {
		this.diaPrevistoPretensao = diaPrevistoPretensao;
	}
	
	@XmlElement
	public boolean isPrevistoPretensao() {
		return isPrevistoPretensao;
	}

	public void setPrevistoPretensao(boolean isPrevistoPretensao) {
		this.isPrevistoPretensao = isPrevistoPretensao;
	}
	
	@Override
	public String toString() {
		return "Refeicao [id=" + id + ", tipo=" + tipo + ", horaInicio=" 
				+ horaInicio + ", horaFim=" + horaFinal + ", horaPretensao="
				+ horaPretensao + ", diPrevistoPretensao=" + diaPrevistoPretensao 
				+ ", isPrevistoPretensao=" + isPrevistoPretensao + "]";
	}
}
