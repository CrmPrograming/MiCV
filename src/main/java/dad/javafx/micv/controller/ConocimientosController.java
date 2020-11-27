package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.conocimiento.Conocimiento;
import dad.javafx.micv.model.conocimiento.Idioma;
import dad.javafx.micv.model.conocimiento.Nivel;
import dad.javafx.micv.utils.dialogo.DialogoConocimiento;
import dad.javafx.micv.utils.dialogo.DialogoIdioma;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

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
		DialogoConocimiento dialog = new DialogoConocimiento();
		Optional<Conocimiento> result = dialog.showAndWait();

		if (result.isPresent())
			conocimientos.get().add(result.get());
	}

	@FXML
	void onClickAnadirIdioma(ActionEvent event) {
		DialogoIdioma dialog = new DialogoIdioma();
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
