package provafinale;

import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import provafinale.database.SpotifyDAO;
import provafinale.model.Model;
import provafinale.model.Song;

public class SpotifyController {
	
	Model model;
	SpotifyDAO dao;

    @FXML
    private Tab tabRicerca;

    @FXML
    private TextField txtFieldArtista;

    @FXML
    private ChoiceBox<String> choiceBoxGenere;

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
    private Slider sliderDanceability;

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
    	txtFieldArtista.clear();
    	choiceBoxGenere.getSelectionModel().clearSelection();
    	txtAreaRicerca.clear();
    }

    @FXML
    void doCerca(ActionEvent event) {
    	txtAreaRicerca.clear();
    	String artista = txtFieldArtista.getText();
    	List<Song> canzoniArtista = dao.getAllArtistSong(artista);
    	if(canzoniArtista.isEmpty()) {
    		txtAreaRicerca.setText("Nessuna canzone trovata per l'artista cercato");
    	} else {
    		for(Song s : canzoniArtista) {
        		txtAreaRicerca.appendText(s.getTitle()+"\n");
        	}
    	}
    	
    }

    @FXML
    void doGenera(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {
    	sliderPopularity.setValue(50);
    	sliderEnergy.setValue(50);
    	sliderDanceability.setValue(50);
    	txtFieldDurata.clear();
    	radioBassa.setSelected(false);
    	radioAlta.setSelected(false);
    }
    

    @FXML
    void initialize() {
        assert tabRicerca != null : "fx:id=\"tabRicerca\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtFieldArtista != null : "fx:id=\"txtFieldArtista\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert choiceBoxGenere != null : "fx:id=\"choiceBoxGenere\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert buttonCancella != null : "fx:id=\"buttonCancella\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert buttonCerca != null : "fx:id=\"buttonCerca\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtAreaRicerca != null : "fx:id=\"txtAreaRicerca\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert tabGenera != null : "fx:id=\"tabGenera\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtFieldDurata != null : "fx:id=\"txtFieldDurata\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert sliderPopularity != null : "fx:id=\"sliderPopularity\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert sliderEnergy != null : "fx:id=\"sliderEnergy\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert sliderDanceability != null : "fx:id=\"sliderDanceability\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert radioBassa != null : "fx:id=\"radioBassa\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert radioAlta != null : "fx:id=\"radioAlta\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert buttonReset != null : "fx:id=\"buttonReset\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert buttonGenera != null : "fx:id=\"buttonGenera\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtAreaGenera != null : "fx:id=\"txtAreaGenera\" was not injected: check your FXML file 'Spotify.fxml'.";

    	sliderPopularity.setValue(50);
    	sliderEnergy.setValue(50);
    	sliderDanceability.setValue(50);
        
        dao = new SpotifyDAO();
        ObservableList<String> generi = FXCollections.observableArrayList();
        generi.addAll(dao.getAllGenres());
        Collections.sort(generi);
        choiceBoxGenere.setItems(generi);
    }

}
