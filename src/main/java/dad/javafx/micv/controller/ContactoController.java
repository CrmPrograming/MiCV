package dad.javafx.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.micv.App;
import dad.javafx.micv.model.contacto.Contacto;
import dad.javafx.micv.model.contacto.Email;
import dad.javafx.micv.model.contacto.Telefono;
import dad.javafx.micv.model.contacto.TipoTelefono;
import dad.javafx.micv.model.contacto.Web;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ContactoController implements Initializable {
	
	// model
	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<>();
	private ObjectProperty<Telefono> tlfSeleccionado = new SimpleObjectProperty<>();
	private ObjectProperty<Email> emailSeleccionado = new SimpleObjectProperty<>();
	private ObjectProperty<Web> webSeleccionada = new SimpleObjectProperty<>();
	
	// view
	@FXML
	private SplitPane view;
	
	@FXML
    private TableView<Telefono> tvTelefonos;

    @FXML
    private TableColumn<Telefono, String> tcNumero;

    @FXML
    private TableColumn<Telefono, TipoTelefono> tcTipo;
    
    @FXML
    private Button btEliminarTelefono;
    
    @FXML
    private TableView<Email> tvCorreo;

    @FXML
    private TableColumn<Email, String> tcEmail;
    
    @FXML
    private Button btEliminarCorreo;
    
    @FXML
    private TableView<Web> tvWeb;

    @FXML
    private TableColumn<Web, String> tcURL;
    
    @FXML
    private Button btEliminarWeb;
	
	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Configuración tabla teléfonos		
		tcNumero.setCellValueFactory(v -> v.getValue().numeroProperty());
		tcTipo.setCellValueFactory(v -> v.getValue().tipoTelefonoProperty());
		
		tcNumero.setCellFactory(TextFieldTableCell.forTableColumn());
		tcTipo.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));
		
		// Configuración tabla email
		tcEmail.setCellValueFactory(v -> v.getValue().direccionProperty());
		tcEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		
		// Configuración tabla web
		tcURL.setCellValueFactory(v -> v.getValue().urlProperty());
		tcURL.setCellFactory(TextFieldTableCell.forTableColumn());
		
		this.contacto.addListener((o, ov, nv) -> onContactoChanged(o, ov, nv));
	}
	
	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if (ov != null) {
			tvTelefonos.itemsProperty().unbind();
			tlfSeleccionado.unbind();
			tvCorreo.itemsProperty().unbind();
			emailSeleccionado.unbind();
			tvWeb.itemsProperty().unbind();
			webSeleccionada.unbind();
			btEliminarTelefono.disableProperty().unbind();
			btEliminarCorreo.disableProperty().unbind();
			btEliminarWeb.disableProperty().unbind();
		}
		
		if (nv != null) {
			tvTelefonos.itemsProperty().bind(nv.telefonosProperty());
			tlfSeleccionado.bind(tvTelefonos.getSelectionModel().selectedItemProperty());
			tvCorreo.itemsProperty().bind(nv.emailsProperty());
			emailSeleccionado.bind(tvCorreo.getSelectionModel().selectedItemProperty());
			tvWeb.itemsProperty().bind(nv.websProperty());
			webSeleccionada.bind(tvWeb.getSelectionModel().selectedItemProperty());
			btEliminarTelefono.disableProperty().bind(Bindings.isEmpty(tvTelefonos.getItems()));
			btEliminarCorreo.disableProperty().bind(Bindings.isEmpty(tvCorreo.getItems()));
			btEliminarWeb.disableProperty().bind(Bindings.isEmpty(tvWeb.getItems()));
		}
	}

	@FXML
	void onClickAddCorreo(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("E-mail:");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
				dialog.getEditor().textProperty().isEmpty()
		);
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Email email = new Email();
			email.setDireccion(result.get());
			contacto.get().emailsProperty().add(email);
		}
	}

	@FXML
	void onClickAddTelefono(ActionEvent event) {
		Dialog<Pair<String, TipoTelefono>> dialog = new Dialog<>();
		
		dialog.setTitle("Nuevo teléfono");
		dialog.setContentText("Introduzca el nuevo número de teléfono.");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		ButtonType btAnadir = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btAnadir, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField tfNumero = new TextField();
		tfNumero.setPromptText("Número de teléfono");
		
		ComboBox<TipoTelefono> cbTipoTelefono = new ComboBox<>();
		cbTipoTelefono.getItems().addAll(TipoTelefono.values());
		cbTipoTelefono.setPromptText("Seleccione un tipo");
		
		Node nodeBtAnadir = dialog.getDialogPane().lookupButton(btAnadir);
		nodeBtAnadir.setDisable(true);
		
		nodeBtAnadir.disableProperty().bind(
				tfNumero.textProperty().isEmpty().or(
				cbTipoTelefono.valueProperty().isNull()));
		
		grid.add(new Label("Número:"), 0, 0);
		grid.add(tfNumero, 1, 0);
		grid.add(new Label("Tipo:"), 0, 1);
		grid.add(cbTipoTelefono, 1, 1);
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> tfNumero.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == btAnadir) {
				return new Pair<>(tfNumero.getText(), cbTipoTelefono.getSelectionModel().getSelectedItem());
			}
			return null;
		});
		
		Optional<Pair<String, TipoTelefono>> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			Telefono tlf = new Telefono();
			tlf.setNumero(result.get().getKey());
			tlf.setTipoTelefono(result.get().getValue());
			contacto.get().getTelefonos().add(tlf);
		}
	}

	@FXML
	void onClickAddWeb(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("Nueva web");
		dialog.setHeaderText("Crear una nueva dirección web.");
		dialog.setContentText("URL:");
		dialog.initOwner(App.getPrimaryStage());
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		
		dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
				dialog.getEditor().textProperty().isEmpty()
		);
		
		dialog.getEditor().setText("http://");
		
		Platform.runLater(() -> dialog.getEditor().requestFocus());
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Web web = new Web();
			web.setUrl(result.get());
			contacto.get().websProperty().add(web);
		}
	}

	@FXML
	void onClickRemoveCorreo(ActionEvent event) {
		String title = "Eliminar e-mail";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n¿Está seguro de borrar el e-mail?";
		Email email = emailSeleccionado.get();
		
		if (email != null && App.confirm(title, header, content))
			contacto.get().getEmails().remove(email);
	}

	@FXML
	void onClickRemoveTelefono(ActionEvent event) {
		String title = "Eliminar teléfono";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n¿Está seguro de borrar el teléfono?";
		Telefono telefono = tlfSeleccionado.get();
		
		if (telefono != null && App.confirm(title, header, content))
			contacto.get().getTelefonos().remove(telefono);
	}

	@FXML
	void onClickRemoveWeb(ActionEvent event) {
		String title = "Eliminar web";
		String header = "Antes de continuar, confirme";
		String content = "Esta operación es irreversible.\n¿Está seguro de borrar la dirección web?";
		Web web = webSeleccionada.get();
		
		if (web != null && App.confirm(title, header, content))
			contacto.get().getWebs().remove(web);
	}

	public SplitPane getView() {
		return view;
	}

	public void setView(SplitPane view) {
		this.view = view;
	}

	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}
	

	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}
	

	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}	
	    
}
