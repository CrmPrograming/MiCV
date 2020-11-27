package dad.javafx.micv.utils;

import dad.javafx.micv.model.Experiencia;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class DialogoExperiencia extends Dialog<Experiencia> {
	
	public DialogoExperiencia() {
		setTitle("Nueva experiencia");
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		stage.setMinWidth(550);
		stage.setMinHeight(200);
		
		ButtonType btCrear = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(btCrear, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));
		
		TextField tfDenominacion = new TextField();
		TextField tfEmpleador = new TextField();
		DatePicker dpDesde = new DatePicker();
		DatePicker dpHasta = new DatePicker();
		
		Node nodeBtAnadir = getDialogPane().lookupButton(btCrear);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(
				tfDenominacion.textProperty().isEmpty().or(
				tfEmpleador.textProperty().isEmpty()).or(
				dpDesde.valueProperty().isNull()).or(
				dpHasta.valueProperty().isNull()));
		
		grid.add(new Label("Denominación"), 0, 0);
		grid.add(tfDenominacion, 1, 0);
		grid.add(new Label("Organizador"), 0, 1);
		grid.add(tfEmpleador, 1, 1);
		grid.add(new Label("Desde"), 0, 2);
		grid.add(dpDesde, 1, 2);
		grid.add(new Label("Hasta"), 0, 3);
		grid.add(dpHasta, 1, 3);
		
		GridPane.setColumnSpan(tfDenominacion, 2);
		GridPane.setColumnSpan(tfEmpleador, 2);
		
		ColumnConstraints[] cols = {
				new ColumnConstraints(),
				new ColumnConstraints(),
				new ColumnConstraints()
		};
		
		cols[0].setHalignment(HPos.RIGHT);
		cols[1].setHgrow(Priority.ALWAYS);
		cols[1].setFillWidth(true);
		
		grid.getColumnConstraints().setAll(cols);
		
		getDialogPane().setContent(grid);
		
		Platform.runLater(() -> tfDenominacion.requestFocus());
		
		setResultConverter(dialogButton -> {
			if (dialogButton == btCrear) {
				Experiencia resultado = new Experiencia();
				resultado.setDenominacion(tfDenominacion.getText());
				resultado.setEmpleador(tfEmpleador.getText());
				resultado.setDesde(dpDesde.getValue());
				resultado.setHasta(dpHasta.getValue());
				return resultado;
			}
			return null;
		});
	}

}
