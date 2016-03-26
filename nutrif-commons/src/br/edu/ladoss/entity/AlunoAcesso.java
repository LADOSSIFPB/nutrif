package br.edu.ladoss.entity;

import javax.xml.bind.annotation.XmlElement;

public class AlunoAcesso extends Pessoa {

	private static final long serialVersionUID = 8011399615861874456L;

	@XmlElement
	@Override
	public String getSenha() {
		return super.getSenha();
	}
	
	@XmlElement
	@Override
	public String getKeyAuth() {
		return super.getKeyAuth();
	}
}
