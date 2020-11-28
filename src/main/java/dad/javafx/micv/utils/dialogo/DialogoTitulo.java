package dad.javafx.micv.utils.dialogo;

import dad.javafx.micv.model.Titulo;

public class DialogoTitulo extends DialogoConFechas<Titulo> {
	
	public DialogoTitulo() {
		super("Nuevo título", "Denominación", "Organizador", "Desde", "Hasta");
		
		setResultConverter(dialogButton -> {
			if (dialogButton == btCrear) {
				Titulo resultado = new Titulo();
				resultado.setDenominacion(tfPrimero.getText());
				resultado.setOrganizador(tfSegundo.getText());
				resultado.setDesde(dpDesde.getValue());
				resultado.setHasta(dpHasta.getValue());
				return resultado;
			}
			return null;
		});
	}

}
