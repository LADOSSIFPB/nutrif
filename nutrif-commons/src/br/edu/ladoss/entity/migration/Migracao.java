package br.edu.ladoss.entity.migration;

import javax.persistence.Column;

public abstract class Migracao {

	@Column(name = "id_migracao")
	private int id_migracao;
}
