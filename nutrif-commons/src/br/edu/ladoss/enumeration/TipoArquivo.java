package br.edu.ladoss.enumeration;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tipoArquivo")
@XmlEnum
public enum TipoArquivo implements Serializable {
	
	// Tipo Arquivo de Foto do Perfil
	@XmlEnumValue("1") ARQUIVO_FOTO_PERFIL (1),	

	// Tipo Arquivo de Foto do Perfil
	@XmlEnumValue("2") ARQUIVO_REFEICAO_ALMOCO (2);
		
	private final int id;

	TipoArquivo(int id) {
		this.id = id;
	}

	@XmlElement
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TipoArquivo [id=" + id + "]";
	}
}