package dad.javafx.micv.formacion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Titulo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class FormacionController implements Initializable {

	// model

	private ObjectProperty<Titulo> titulo = new SimpleObjectProperty<>();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private TableView<?> tvFormacion;

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

		this.titulo.addListener((o, ov, nv) -> onTituloChanged(o, ov, nv));

	}

	private void onTituloChanged(ObservableValue<? extends Titulo> o, Titulo ov, Titulo nv) {

		if (ov != null) {

		}

		if (nv != null) {

		}

	}

	@FXML
	void onClickAnadir(ActionEvent event) {

	}

	@FXML
	void onClickEliminar(ActionEvent event) {

	}

	public BorderPane getView() {
		return view;
	}

	public final ObjectProperty<Titulo> tituloProperty() {
		return this.titulo;
	}

	public final Titulo getTitulo() {
		return this.tituloProperty().get();
	}

	public final void setTitulo(final Titulo titulo) {
		this.tituloProperty().set(titulo);
	}

}
