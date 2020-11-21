package dad.javafx.micv.personal;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.micv.model.Nacionalidad;
import dad.javafx.micv.model.Personal;
import dad.javafx.micv.utils.LectorCSV;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();

	// view

	@FXML
	private GridPane view;

	@FXML
	private TextField identificacionText;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField apellidosText;
	
	@FXML
    private DatePicker fechaNacimientoDatePicker;

    @FXML
    private TextArea direccionTextArea;

    @FXML
    private TextField codPostalText;

    @FXML
    private TextField localidadText;

    @FXML
    private ComboBox<String> paisCombo;

    @FXML
    private ListView<Nacionalidad> nacionalidadList;

	public PersonalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO cargar el combo de paises
		try {
			paisCombo.getItems().addAll(LectorCSV.leerFichero("/csv/paises.csv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.personal.addListener((o, ov, nv) -> onPersonalChanged(o, ov, nv));
		
	}

	private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {
		
		if (ov != null) {
			identificacionText.textProperty().unbindBidirectional(ov.identificacionProperty());
			nombreText.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ov.apellidosProperty());
			// TODO desbindear el resto de componentes
			fechaNacimientoDatePicker.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			direccionTextArea.textProperty().unbindBidirectional(ov.direccionProperty());
			codPostalText.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ov.localidadProperty());
			paisCombo.valueProperty().unbindBidirectional(ov.paisProperty());
			
		}

		if (nv != null) {
			identificacionText.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			// TODO bindear el resto de componentes
			fechaNacimientoDatePicker.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direccionTextArea.textProperty().bindBidirectional(nv.direccionProperty());
			codPostalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());
			paisCombo.valueProperty().bindBidirectional(nv.paisProperty());
		}
		
	}
	
	public GridPane getView() {
		return view;
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}
