package dad.javafx.micv.utils;

import dad.javafx.micv.model.conocimiento.Nivel;

public class ResultadosDialogoConocimiento {
		
	private String denominacion;
	private Nivel nivel;
	private String certificacion;
	
	public ResultadosDialogoConocimiento(String denominacion, Nivel nivel, String certificacion) {		
		this.denominacion = denominacion;
		this.nivel = nivel;
		this.certificacion = certificacion;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public String getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(String certificacion) {
		this.certificacion = certificacion;
	}
	
}
