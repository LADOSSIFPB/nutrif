package br.edu.ladoss.enumeration;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tipoArquivo")
@XmlEnum
public enum TipoArquivo implements Serializable {
	
	// Tipo Arquivo de Projeto
	@XmlEnumValue("1") ARQUIVO_FOTO_PERFIL (1);	

	private final int id;

	TipoArquivo(int id) {
		this.id = id;
	}

	@XmlElement
	public int getId() {
		return id;
	}
}