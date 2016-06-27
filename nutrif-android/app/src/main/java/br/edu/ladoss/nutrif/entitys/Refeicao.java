package br.edu.ladoss.nutrif.entitys;


import java.io.Serializable;

public class Refeicao implements Serializable{
	private Integer id;
	private String tipo;
	private String horaInicio;
	private String horaFinal;

	public Refeicao(Integer id) {
		this.id = id;
	}

	public Refeicao(){

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
}