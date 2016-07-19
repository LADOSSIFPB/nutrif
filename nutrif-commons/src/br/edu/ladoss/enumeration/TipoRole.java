package br.edu.ladoss.enumeration;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tipoArquivo")
@XmlEnum
public enum TipoRole implements Serializable {
	
	// Tipo Role - Administrador
	@XmlEnumValue("1") ADMIN (1, "admin"),	

	// Tipo Role - Inspetor
	@XmlEnumValue("2") INSPETOR (2, "inspetor"),
	
	// Tipo Role - Comensal
	@XmlEnumValue("3") COMENSAL (3, "comensal");
		
	private int id;
	
	private String nome;

	TipoRole(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@XmlElement
	public int getId() {
		return id;
	}
	
	@XmlElement
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TipoArquivo [id=" + id + "]";
	}	
}