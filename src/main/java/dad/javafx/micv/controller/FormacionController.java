package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.Titulo;
import dad.javafx.micv.utils.dialogo.DialogoTitulo;
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

public class FormacionController implements Initializable {

	// model

	private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Titulo> seleccionado = new SimpleObjectProperty<>();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private TableView<Titulo> tvFormacion;
	
	@FXML
    private TableColumn<Titulo, LocalDate> tcDesde;

    @FXML
    private TableColumn<Titulo, LocalDate> tcHasta;

    @FXML
    private TableColumn<Titulo, String> tcDenominacion;

    @FXML
    private TableColumn<Titulo, String> tcOrganizador;

	@FXML
	private Button btAnadir;

	@FXML
	private Button btEliminar;

	public FormacionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcDesde.setCellValueFactory(v -> v.getValue().desdeProperty());
		tcHasta.setCellValueFactory(v -> v.getValue().hastaProperty());
		tcDenominacion.setCellValueFactory(v -> v.getValue().denominacionProperty());
		tcOrganizador.setCellValueFactory(v -> v.getValue().organizadorProperty());
		
		tcDesde.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		tcHasta.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		tcDenominacion.setCellFactory(TextFieldTableCell.forTableColumn());
		tcOrganizador.setCellFactory(TextFieldTableCell.forTableColumn());
		
		this.titulos.addListener((o, ov, nv) -> onTituloChanged(o, ov, nv));
	}

	private void onTituloChanged(ObservableValue<? extends ObservableList<Titulo>> o, ObservableList<Titulo> ov, ObservableList<Titulo> nv) {
		if (ov != null) {
			tvFormacion.setItems(null);
			seleccionado.unbind();
			btEliminar.disableProperty().unbind();
		}
		
		if (nv != null) {
			tvFormacion.setItems(nv);
			seleccionado.bind(tvFormacion.getSelectionModel().selectedItemProperty());
			btEliminar.disableProperty().bind(Bindings.isEmpty(tvFormacion.getItems()));
		}
	}

	@FXML
	void onClickAnadir(ActionEvent event) {
		DialogoTitulo dialog = new DialogoTitulo();
		Optional<Titulo> result = dialog.showAndWait();
		
		if (result.isPresent())
			titulos.get().add(result.get());
	}

	@FXML
	void onClickEliminar(ActionEvent event) {
		String title = "Eliminar formación";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n¿Está seguro de borrar la formación?";
		Titulo formacion = seleccionado.get();
		
		if (formacion != null && App.confirm(title, header, content))
			titulos.get().remove(formacion);
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Titulo> titulosProperty() {
		return this.titulos;
	}
	

	public final ObservableList<Titulo> getTitulos() {
		return this.titulosProperty().get();
	}
	

	public final void setTitulos(final ObservableList<Titulo> titulos) {
		this.titulosProperty().set(titulos);
	}

}
