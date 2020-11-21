package dad.javafx.micv.contacto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ContactoController implements Initializable {
	
	// view
	
	@FXML
	private VBox view;
	/*
	@FXML
    private TableView<?> tvTelefonos;

    @FXML
    private TableColumn<?, ?> tcNumero;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    private TableView<?> tvCorreo;

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableView<?> tvWeb;

    @FXML
    private TableColumn<?, ?> tcURL;*/
	
	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	@FXML
	void onClickAddCorreo(ActionEvent event) {

	}

	@FXML
	void onClickAddTelefono(ActionEvent event) {

	}

	@FXML
	void onClickAddWeb(ActionEvent event) {

	}

	@FXML
	void onClickRemoveCorreo(ActionEvent event) {

	}

	@FXML
	void onClickRemoveTelefono(ActionEvent event) {

	}

	@FXML
	void onClickRemoveWeb(ActionEvent event) {

	}

	public VBox getView() {
		return view;
	}

	public void setView(VBox view) {
		this.view = view;
	}
	    
}
