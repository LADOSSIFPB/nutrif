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

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}
	
	@Override
	public String toString() {
		return "Refeicao [id=" + id + ", tipo=" + tipo + "]";
	}
}
