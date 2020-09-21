package provafinale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private ChoiceBox<Integer> choiceBoxAnno;

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
    	choiceBoxAnno.getSelectionModel().clearSelection();
    	txtAreaRicerca.clear();
    }

    @FXML
    void doCerca(ActionEvent event) {
    	txtAreaRicerca.clear();
    	String artista = txtFieldArtista.getText();
    	String genere = choiceBoxGenere.getSelectionModel().getSelectedItem();
    	int anno = 0;
    	try{
    		anno = choiceBoxAnno.getSelectionModel().getSelectedItem();
    	} catch(NullPointerException e) {
    	}
    	List<Song> canzoni = new ArrayList<>();
    	
    	if(!artista.equals("") && genere != null) {
    		txtAreaRicerca.setText("E' possibile la ricerca per artista, per genere o per anno.\nE' inoltre possibile la ricerca per artista e anno  e per genere e anno.");
    		return;
    	}
    	else if(!artista.equals("") && genere==null) {
    		if(anno!=0) {
    			canzoni = dao.getAllYearArtistSong(artista, anno);
    		}
    		else {
    			canzoni = dao.getAllArtistSong(artista);
    		}
    		
        	if(canzoni.isEmpty()) {
        		txtAreaRicerca.setText("Nessuna canzone trovata per l'artista cercato");
        	} else {
        		for(Song s : canzoni) {
            		txtAreaRicerca.appendText(s.getTitle()+" ("+s.getYear()+")\n");
            	}
        	}
    	} 
    	
    	else if (artista.equals("") && genere!=null) {
    		if(anno!=0) {
    			canzoni = dao.getAllYearGenreSongs(genere, anno);
    		} else {
    			canzoni = dao.getAllGenreSongs(genere);
    		}
    		if(canzoni.isEmpty()) {
    			txtAreaRicerca.setText("Nessuna canzone di quel genere trovata per l'anno selezionato");
    		}
    		for(Song s : canzoni) {
    			txtAreaRicerca.appendText(s.getArtist()+" - "+s.getTitle()+" ("+s.getYear()+")\n");
    		}
    	} else if(artista.equals("") && genere == null && anno!=0) {
    		canzoni = dao.getAllYearSongs(anno);
    		for(Song s : canzoni) {
    			txtAreaRicerca.appendText(s.getArtist()+" - "+s.getTitle()+"\n");
    		}
    		
    	}
    	else if (artista.equals("") && genere == null && anno == 0) {
    		txtAreaRicerca.setText("Inserire un artista o un genere o un anno da cercare");
    	}
    	try {
    		txtAreaRicerca.appendText("\nCanzone più popolare: "+model.canzonePiuPopolare(canzoni).getTitle().toUpperCase()+"\n");
    		int durataMedia = model.durataMedia(canzoni);
    		int min = durataMedia/60;
    		int sec = durataMedia%60;
    		txtAreaRicerca.appendText("Durata media: "+min+" minuti e "+sec+" secondi");
    	} catch(NullPointerException e) {
    		
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
        assert choiceBoxAnno != null : "fx:id=\"choiceBoxAnno\" was not injected: check your FXML file 'Spotify.fxml'.";
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
        model = new Model();
        ObservableList<String> generi = FXCollections.observableArrayList();
        ObservableList<Integer> anni = FXCollections.observableArrayList();
        generi.addAll(dao.getAllGenres());
        anni.addAll(dao.getAllYears());
        Collections.sort(generi);
        Collections.sort(anni);
        choiceBoxGenere.setItems(generi);
        choiceBoxAnno.setItems(anni);
    }

}
