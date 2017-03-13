package br.edu.ladoss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "turno")
@Entity
@Table(name = "tb_turno")
@NamedQuery(name = "Turno.getAll", query = "from Turno")
public class Turno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_turno")
	private Integer id;
	
	@Column(name = "nm_turno")
	private String nome;
}
