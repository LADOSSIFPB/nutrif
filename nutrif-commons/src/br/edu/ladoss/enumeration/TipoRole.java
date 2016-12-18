package br.edu.ladoss.enumeration;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tipoArquivo")
@XmlEnum
public class TipoRole implements Serializable {
	
	// Tipo Role - Administrador
	public static final String ADMIN = "admin";	

	// Tipo Role - Inspetor
	public static final String INSPETOR = "inspetor";
	
	// Tipo Role - Comensal
	public static final String COMENSAL = "comensal";	
}