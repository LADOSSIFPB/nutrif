package br.edu.ladoss.enumeration;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tipoArquivo")
@XmlEnum
public class TipoRole implements Serializable {

	private static final long serialVersionUID = -5367063261525453982L;

	// Tipo Role - Administrador
	public static final String ADMIN = "admin";	

	// Tipo Role - Inspetor
	public static final String INSPETOR = "inspetor";
	
	// Tipo Role - Comensal
	public static final String COMENSAL = "comensal";
	
	// Tipo Role - Comensal
	public static final String NUTRICIONISTA = "nutricionista";
}