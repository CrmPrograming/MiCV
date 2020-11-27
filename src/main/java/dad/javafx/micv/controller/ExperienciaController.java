package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.Experiencia;
import dad.javafx.micv.utils.dialogo.DialogoExperiencia;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateStringConverter;

public class ExperienciaController implements Initializable {

	// model

	private ListProperty<Experiencia> experiencias = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Experiencia> seleccionado = new SimpleObjectProperty<>();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private TableView<Experiencia> tvExperiencia;
	
	@FXML
    private TableColumn<Experiencia, LocalDate> tcDesde;

    @FXML
    private TableColumn<Experiencia, LocalDate> tcHasta;

    @FXML
    private TableColumn<Experiencia, String> tcDenominacion;

    @FXML
    private TableColumn<Experiencia, String> tcEmpleador;

	@FXML
	private Button btAnadir;

	@FXML
	private Button btEliminar;

	public ExperienciaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcDesde.setCellValueFactory(v -> v.getValue().desdeProperty());
		tcHasta.setCellValueFactory(v -> v.getValue().hastaProperty());
		tcDenominacion.setCellValueFactory(v -> v.getValue().denominacionProperty());
		tcEmpleador.setCellValueFactory(v -> v.getValue().empleadorProperty());
		
		tcDesde.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		tcHasta.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		tcDenominacion.setCellFactory(TextFieldTableCell.forTableColumn());
		tcEmpleador.setCellFactory(TextFieldTableCell.forTableColumn());
		
		this.experiencias.addListener((o, ov, nv) -> onExperienciaChanged(o, ov, nv));
	}

	private void onExperienciaChanged(ObservableValue<? extends ObservableList<Experiencia>> o, ObservableList<Experiencia> ov, ObservableList<Experiencia> nv) {
		if (ov != null) {
			tvExperiencia.setItems(null);
			seleccionado.unbind();
			btEliminar.disableProperty().unbind();
		}
		
		if (nv != null) {
			tvExperiencia.setItems(nv);
			seleccionado.bind(tvExperiencia.getSelectionModel().selectedItemProperty());
			btEliminar.disableProperty().bind(Bindings.isEmpty(tvExperiencia.getItems()));
		}

	}

	@FXML
	void onClickAnadir(ActionEvent event) {
		DialogoExperiencia dialog = new DialogoExperiencia();
		Optional<Experiencia> result = dialog.showAndWait();
		
		if (result.isPresent())
			experiencias.get().add(result.get());
	}

	@FXML
	void onClickEliminar(ActionEvent event) {
		String title = "Eliminar formación";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n¿Está seguro de borrar la experiencia?";
		Experiencia formacion = seleccionado.get();
		
		if (formacion != null && App.confirm(title, header, content))
			experiencias.get().remove(formacion);
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Experiencia> experienciasProperty() {
		return this.experiencias;
	}
	

	public final ObservableList<Experiencia> getExperiencias() {
		return this.experienciasProperty().get();
	}
	

	public final void setExperiencias(final ObservableList<Experiencia> experiencias) {
		this.experienciasProperty().set(experiencias);
	}
	

}
