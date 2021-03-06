package dad.javafx.micv.utils.dialogo;

import dad.javafx.micv.model.conocimiento.Idioma;
import dad.javafx.micv.model.conocimiento.Nivel;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class DialogoIdioma extends Dialog<Idioma> {
	
	public DialogoIdioma() {
		setTitle("Nuevo conocimiento");

		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		stage.setMinWidth(550);
		stage.setMinHeight(300);

		ButtonType btCrear = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(btCrear, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));

		TextField tfDenominacion = new TextField();
		ComboBox<Nivel> cbNivel = new ComboBox<>();
		Button btLimpiarCombo = new Button("X");
		TextField tfCertificacion = new TextField();
		HBox hBox = new HBox();

		hBox.getChildren().addAll(cbNivel, btLimpiarCombo);
		hBox.setSpacing(5);

		tfCertificacion.setPrefWidth(160);

		cbNivel.getItems().addAll(Nivel.values());

		Node nodeBtAnadir = getDialogPane().lookupButton(btCrear);
		nodeBtAnadir.setDisable(true);

		nodeBtAnadir.disableProperty().bind(tfDenominacion.textProperty().isEmpty().or(cbNivel.valueProperty().isNull())
				.or(tfCertificacion.textProperty().isEmpty()));

		btLimpiarCombo.setOnAction(e -> {
			cbNivel.setValue(null);
		});

		grid.add(new Label("Denominación"), 0, 0);
		grid.add(tfDenominacion, 1, 0);
		grid.add(new Label("Nivel"), 0, 1);
		grid.add(hBox, 1, 1);
		grid.add(new Label("Certificación"), 0, 2);

		GridPane.setColumnSpan(tfDenominacion, 2);
		grid.add(tfCertificacion, 1, 2);

		ColumnConstraints[] cols = { new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints() };

		cols[0].setHalignment(HPos.RIGHT);
		cols[2].setHgrow(Priority.ALWAYS);
		cols[2].setFillWidth(true);
		cols[2].setHalignment(HPos.LEFT);

		grid.getColumnConstraints().setAll(cols);

		getDialogPane().setContent(grid);

		Platform.runLater(() -> tfDenominacion.requestFocus());

		setResultConverter(dialogButton -> {
			if (dialogButton == btCrear) {
				Idioma result = new Idioma();
				result.setDenominacion(tfDenominacion.getText());
				result.setNivel(cbNivel.getValue());
				result.setCertificacion(tfCertificacion.getText());
				return result;
			}
			return null;
		});
	}

}
