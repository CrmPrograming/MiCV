package dad.javafx.micv.utils.dialogo;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class DialogoConFechas<T> extends Dialog<T> {
	
	protected ButtonType btCrear;
	protected TextField tfPrimero;
	protected TextField tfSegundo;
	protected DatePicker dpDesde;
	protected DatePicker dpHasta;
	
	public DialogoConFechas(String titulo, String label1, String label2, String label3, String label4) {
		setTitle(titulo);
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		stage.setMinWidth(550);
		stage.setMinHeight(200);
		
		btCrear = new ButtonType("Crear", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(btCrear, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));
		
		tfPrimero = new TextField();
		tfSegundo = new TextField();
		dpDesde = new DatePicker();
		dpHasta = new DatePicker();
		
		Node nodeBtAnadir = getDialogPane().lookupButton(btCrear);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(
				tfPrimero.textProperty().isEmpty().or(
				tfSegundo.textProperty().isEmpty()).or(
				dpDesde.valueProperty().isNull()).or(
				dpHasta.valueProperty().isNull()));
		
		grid.add(new Label(label1), 0, 0);
		grid.add(tfPrimero, 1, 0);
		grid.add(new Label(label2), 0, 1);
		grid.add(tfSegundo, 1, 1);
		grid.add(new Label(label3), 0, 2);
		grid.add(dpDesde, 1, 2);
		grid.add(new Label(label4), 0, 3);
		grid.add(dpHasta, 1, 3);
		
		GridPane.setColumnSpan(tfPrimero, 2);
		GridPane.setColumnSpan(tfSegundo, 2);
		
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
		
		Platform.runLater(() -> tfPrimero.requestFocus());
	}

}
