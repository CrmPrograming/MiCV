package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.conocimiento.Conocimiento;
import dad.javafx.micv.model.conocimiento.Idioma;
import dad.javafx.micv.model.conocimiento.Nivel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ConocimientosController implements Initializable {

	// model

	private ListProperty<Conocimiento> conocimientos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Conocimiento> seleccionado = new SimpleObjectProperty<>();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private TableView<Conocimiento> tvConocimiento;

	@FXML
	private TableColumn<Conocimiento, String> tcDenominacion;

	@FXML
	private TableColumn<Conocimiento, Nivel> tcNivel;

	@FXML
	private Button btAnadir;

	@FXML
	private Button btEliminar;

	public ConocimientosController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcDenominacion.setCellValueFactory(v -> v.getValue().denominacionProperty());
		tcNivel.setCellValueFactory(v -> v.getValue().nivelProperty());

		tcDenominacion.setCellFactory(TextFieldTableCell.forTableColumn());
		tcNivel.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));

		this.conocimientos.addListener((o, ov, nv) -> onConocimientoChanged(o, ov, nv));
	}

	private void onConocimientoChanged(ObservableValue<? extends ObservableList<Conocimiento>> o,
			ObservableList<Conocimiento> ov, ObservableList<Conocimiento> nv) {
		if (ov != null) {
			tvConocimiento.setItems(null);
			seleccionado.unbind();
			btEliminar.disableProperty().unbind();
		}

		if (nv != null) {
			tvConocimiento.setItems(nv);
			seleccionado.bind(tvConocimiento.getSelectionModel().selectedItemProperty());
			btEliminar.disableProperty().bind(Bindings.isEmpty(tvConocimiento.getItems()));
		}
	}

	@FXML
	void onClickAnadirConocimiento(ActionEvent event) {
		Dialog<Conocimiento> dialog = new Dialog<>();

		dialog.setTitle("Nuevo conocimiento");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		stage.setMinWidth(550);
		stage.setMinHeight(300);

		ButtonType btCrear = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btCrear, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));

		TextField tfDenominacion = new TextField();
		ComboBox<Nivel> cbNivel = new ComboBox<>();
		Button btLimpiarCombo = new Button("X");

		cbNivel.getItems().addAll(Nivel.values());

		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(btCrear);
		nodeBtAnadir.setDisable(true);

		nodeBtAnadir.disableProperty()
				.bind(tfDenominacion.textProperty().isEmpty().or(cbNivel.valueProperty().isNull()));

		btLimpiarCombo.setOnAction(e -> {
			cbNivel.setValue(null);
		});

		grid.add(new Label("Denominación"), 0, 0);
		grid.add(tfDenominacion, 1, 0);
		grid.add(new Label("Nivel"), 0, 1);
		grid.add(cbNivel, 1, 1);
		grid.add(btLimpiarCombo, 2, 1);

		GridPane.setColumnSpan(tfDenominacion, 2);

		ColumnConstraints[] cols = { new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints() };

		cols[0].setHalignment(HPos.RIGHT);
		cols[2].setHgrow(Priority.ALWAYS);
		cols[2].setFillWidth(true);
		cols[2].setHalignment(HPos.LEFT);

		grid.getColumnConstraints().setAll(cols);

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> tfDenominacion.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == btCrear) {
				Conocimiento result = new Conocimiento();
				result.setDenominacion(tfDenominacion.getText());
				result.setNivel(cbNivel.getValue());
				return result;
			}
			return null;
		});

		Optional<Conocimiento> result = dialog.showAndWait();

		if (result.isPresent())
			conocimientos.get().add(result.get());
	}

	@FXML
	void onClickAnadirIdioma(ActionEvent event) {
		Dialog<Idioma> dialog = new Dialog<>();

		dialog.setTitle("Nuevo conocimiento");

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		stage.setMinWidth(550);
		stage.setMinHeight(300);

		ButtonType btCrear = new ButtonType("Crear", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btCrear, ButtonType.CANCEL);
		
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

		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(btCrear);
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

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> tfDenominacion.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == btCrear) {
				Idioma result = new Idioma();
				result.setDenominacion(tfDenominacion.getText());
				result.setNivel(cbNivel.getValue());
				result.setCertificacion(tfCertificacion.getText());
				return result;
			}
			return null;
		});

		Optional<Idioma> result = dialog.showAndWait();

		if (result.isPresent())
			conocimientos.get().add(result.get());
	}

	@FXML
	void onClickEliminar(ActionEvent event) {
		String title = "Eliminar conocimiento";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n¿Está seguro de borrar el conocimiento?";
		Conocimiento conocimiento = seleccionado.get();

		if (conocimiento != null && App.confirm(title, header, content))
			conocimientos.get().remove(conocimiento);
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Conocimiento> conocimientosProperty() {
		return this.conocimientos;
	}

	public final ObservableList<Conocimiento> getConocimientos() {
		return this.conocimientosProperty().get();
	}

	public final void setConocimientos(final ObservableList<Conocimiento> conocimientos) {
		this.conocimientosProperty().set(conocimientos);
	}

}
