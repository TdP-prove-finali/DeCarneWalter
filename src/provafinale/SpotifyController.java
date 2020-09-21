package provafinale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SpotifyController {

    @FXML
    private Tab tabRicerca;

    @FXML
    private TextField txtFieldArtista;

    @FXML
    private ChoiceBox<?> choiceBoxGenere;

    @FXML
    private Button buttonCancella;

    @FXML
    private Button buttonCerca;

    @FXML
    private TextArea txtAreaRicerca;

    @FXML
    private Tab tabGenera;

    @FXML
    private TextField txtFieldDurata;

    @FXML
    private Slider sliderPopularity;

    @FXML
    private Slider sliderEnergy;

    @FXML
    private Label sliderDanceability;

    @FXML
    private RadioButton radioBassa;

    @FXML
    private RadioButton radioAlta;

    @FXML
    private Button buttonReset;

    @FXML
    private Button buttonGenera;

    @FXML
    private TextArea txtAreaGenera;

    @FXML
    void doCancella(ActionEvent event) {

    }

    @FXML
    void doCerca(ActionEvent event) {

    }

    @FXML
    void doGenera(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {

    }

}
