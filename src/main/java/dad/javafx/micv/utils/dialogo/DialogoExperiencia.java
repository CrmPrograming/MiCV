package dad.javafx.micv.utils.dialogo;

import dad.javafx.micv.model.Experiencia;

public class DialogoExperiencia extends DialogoConFechas<Experiencia> {
	
	public DialogoExperiencia() {
		super("Nuevo título", "Denominación", "Empleador", "Desde", "Hasta");
		
		setResultConverter(dialogButton -> {
			if (dialogButton == btCrear) {
				Experiencia resultado = new Experiencia();
				resultado.setDenominacion(tfPrimero.getText());
				resultado.setEmpleador(tfSegundo.getText());
				resultado.setDesde(dpDesde.getValue());
				resultado.setHasta(dpHasta.getValue());
				return resultado;
			}
			return null;
		});
	}

}
