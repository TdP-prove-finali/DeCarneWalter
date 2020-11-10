package provafinale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import provafinale.database.SpotifyDAO;
import provafinale.model.Genre;
import provafinale.model.Model;
import provafinale.model.Song;

public class SpotifyController {
	
	Model model;
	SpotifyDAO dao;
	
	ObservableList<Integer> anni;
	List<String> artistiGrafico;
	List<String> generiGrafico;
	int contatoreGrafico;
	
	List<Song> canzoniAggiunteManualmente;

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
    private Text txtCanzoneFamosa;

    @FXML
    private Text txtDurataMedia;

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
    private Button buttonReset;

    @FXML
    private Button buttonGenera;

    @FXML
    private TextArea txtAreaGenera;

    @FXML
    private BarChart<String, Integer> yearsBarChart;
    
    @FXML
    private PieChart genresPieChart;
    
    @FXML
    private Text txtYearPieChart;
    
    @FXML
    private Text txtDurataPlaylist;
    
    @FXML
    private ChoiceBox<String> choiceBoxArtista;
    
    @FXML
    private Button btnSelezionaArtista;

    @FXML
    private Button btnCancellaSelezione;

    @FXML
    private ChoiceBox<Song> choiceBoxCanzone;
    

    @FXML
    private Button btnAggiungi;
    
    @FXML
    private PieChart pieChartPlaylist;

    @FXML
    void doCancella(ActionEvent event) {
    	txtFieldArtista.clear();
    	choiceBoxGenere.getSelectionModel().clearSelection();
    	choiceBoxAnno.getSelectionModel().clearSelection();
    	txtAreaRicerca.clear();
    	txtCanzoneFamosa.setText("");
    	txtDurataMedia.setText("");
    	
    	yearsBarChart.getData().clear();
    	artistiGrafico.clear();
    	generiGrafico.clear();
    	genresPieChart.getData().clear();
    	
    	txtYearPieChart.setText("");
    	
    	contatoreGrafico = 0;
    	
    	yearsBarChart.setVisible(false);
    	
    }

    @FXML
    void doCerca(ActionEvent event) {
    	txtAreaRicerca.clear();
    	txtCanzoneFamosa.setText("");
    	txtDurataMedia.setText("");
    	
    	String artista = txtFieldArtista.getText();
    	String genere = choiceBoxGenere.getSelectionModel().getSelectedItem();
    	int anno = 0;
    	try{
    		anno = choiceBoxAnno.getSelectionModel().getSelectedItem();
    	} catch(NullPointerException e) {
    	}
    	List<Song> canzoni = new ArrayList<>();
    	
    	if(!artista.equals("") && genere != null) {
    		txtAreaRicerca.setText("E' possibile la ricerca per artista, per genere o per anno.\nE' inoltre possibile la ricerca per artista e anno  e per genere e anno.\n");
    		yearsBarChart.getData().clear();
    		yearsBarChart.setVisible(false);
    		genresPieChart.getData().clear();
    		txtYearPieChart.setText("");
    		return;
    	}
    	else if(!artista.equals("") && genere==null) {
    		if(anno!=0) {
    			canzoni = dao.getAllYearArtistSongs(artista, anno);
    			yearsBarChart.getData().clear();
    			genresPieChart.getData().clear();
    			txtYearPieChart.setText("");
    			yearsBarChart.setVisible(false);
    		}
    		else {
    			canzoni = dao.getAllArtistSongs(artista);
    			disegnaBarChartArtista(canzoni, artista);
    			genresPieChart.getData().clear();
    			txtYearPieChart.setText("");
    		}
    		
        	if(canzoni.isEmpty()) {
        		txtAreaRicerca.setText("Nessuna canzone trovata per l'artista cercato\n");
        		yearsBarChart.setVisible(false);
        	} else {
        		for(Song s : canzoni) {
            		txtAreaRicerca.appendText(s.getTitle()+" ("+s.getYear()+")\n");
            	}
        		txtAreaRicerca.setText(txtAreaRicerca.getText().substring(0, txtAreaRicerca.getText().length()-1));
        	}
    	} 
    	
    	else if (artista.equals("") && genere!=null) {
    		if(anno!=0) {
    			canzoni = dao.getAllYearGenreSongs(genere, anno);
    			yearsBarChart.getData().clear();
    			genresPieChart.getData().clear();
    			txtYearPieChart.setText("");
    			yearsBarChart.setVisible(false);
    		} else {
    			canzoni = dao.getAllGenreSongs(genere);
    			disegnaBarChartGenere(canzoni, genere);
    			genresPieChart.getData().clear();
    			txtYearPieChart.setText("");
    		}
    		if(canzoni.isEmpty()) {
    			txtAreaRicerca.setText("Nessuna canzone di quel genere trovata per l'anno selezionato\n");
    			yearsBarChart.setVisible(false);
    		}
    		for(Song s : canzoni) {
    			txtAreaRicerca.appendText(s.getArtist()+" - "+s.getTitle()+" ("+s.getYear()+")\n");
    		}
    		txtAreaRicerca.setText(txtAreaRicerca.getText().substring(0, txtAreaRicerca.getText().length()-1));
    	} else if(artista.equals("") && genere == null && anno!=0) {
    		canzoni = dao.getAllYearSongs(anno);
    		disegnaPieChart(canzoni, anno);
    		yearsBarChart.getData().clear();
    		for(Song s : canzoni) {
    			txtAreaRicerca.appendText(s.getArtist()+" - "+s.getTitle()+"\n");
    		}
    		txtAreaRicerca.setText(txtAreaRicerca.getText().substring(0, txtAreaRicerca.getText().length()-1));
    	}
    	else if (artista.equals("") && genere == null && anno == 0) {
    		txtAreaRicerca.setText("Inserire un artista o un genere o un anno da cercare\n");
    		yearsBarChart.getData().clear();
    		genresPieChart.getData().clear();
    		txtYearPieChart.setText("");
    	}
    	try {
    		if(model.canzonePiuPopolare(canzoni).getTitle().length()>40) {
    			txtCanzoneFamosa.setText(model.canzonePiuPopolare(canzoni).getTitle().substring(0, 40));
    		} else {
    			txtCanzoneFamosa.setText(model.canzonePiuPopolare(canzoni).getTitle());
    		}
    		
    		int durataMedia = model.durataMedia(canzoni);
    		int min = durataMedia/60;
    		int sec = durataMedia%60;
    		txtDurataMedia.setText(min+" minuti e "+sec+" secondi");
    	} catch(NullPointerException e) {
    		
    	}
    	
    }

	@SuppressWarnings("unchecked")
	private void disegnaBarChartArtista(List<Song> canzoni, String artista) {
		if(canzoni.isEmpty())
			return;
		
		 yearsBarChart.setVisible(true);
		
		if(!artistiGrafico.contains(artista.toLowerCase())) {
			artistiGrafico.add(artista.toLowerCase());
		} else {
			return;
		}
		if(contatoreGrafico>1) {
			yearsBarChart.getData().clear();
			contatoreGrafico=0;
		}
    	 XYChart.Series<String, Integer> series = new Series<>();
    	 series.setName(artista);
    	 contatoreGrafico++;
    		for(int year : anni) {
        		series.getData().add(new XYChart.Data<String, Integer>(year+"", 0));
        		}
    		
    	for(Song s : canzoni) {
    		series.getData().add(new XYChart.Data<String, Integer>(s.getYear()+"", dao.getAllYearArtistSongs(s.getArtist(), s.getYear()).size()));
    		}
    	
    	 yearsBarChart.getData().addAll(series);
    	
	}
	
	@SuppressWarnings("unchecked")
	private void disegnaBarChartGenere(List<Song> canzoni, String genere) {
		if(canzoni.isEmpty())
			return;
		
		 yearsBarChart.setVisible(true);
		
		if(!generiGrafico.contains(genere.toLowerCase())) {
			generiGrafico.add(genere.toLowerCase());
		} else {
			return;
		}
		
		if(contatoreGrafico>1) {
			yearsBarChart.getData().clear();
			contatoreGrafico=0;
		}
		
    	 XYChart.Series<String, Integer> series = new Series<>();
    	 series.setName(genere);
    	 contatoreGrafico++;
    		for(int year : anni) {
        		series.getData().add(new XYChart.Data<String, Integer>(year+"", 0));
        		}
    		
    	for(Song s : canzoni) {
    		series.getData().add(new XYChart.Data<String, Integer>(s.getYear()+"", dao.getAllYearGenreSongs(s.getTopGenre(), s.getYear()).size()));
    		}
    	
    	 yearsBarChart.getData().addAll(series);
	}
	
	private void disegnaPieChart(List<Song> canzoni, int anno) {
		
		if(canzoni.isEmpty())
			return;
		
		ObservableList<PieChart.Data> generi = FXCollections.observableArrayList();
		
		List<Genre> generiGrafico = model.generaGraficoPlaylist(canzoni);
		
			for(Genre g : generiGrafico) {
				generi.add(new PieChart.Data(g.getName(), g.getCounter()));
			}
		
		txtYearPieChart.setText(""+anno);
		genresPieChart.setData(generi);
	}

	@FXML
    void doGenera(ActionEvent event) {
    	txtAreaGenera.clear();
    	txtDurataPlaylist.setText("");
    	int durata = 0;
    	try {
    		durata = Integer.parseInt(txtFieldDurata.getText());
    	} catch (NumberFormatException e) {
    		txtAreaGenera.appendText("Inserire nel campo \"Durata\" i minuti di durata massima della playlist");
    		return;
    	}
    	
    	if(durata<0) {
    		txtAreaGenera.setText("Valore durata non valido\n");
    		return;
    	}
    	
    	double popularity = sliderPopularity.getValue();
    	double energy = sliderEnergy.getValue();
    	double danceability = sliderDanceability.getValue();
    	
    	durata = durata*60;
    	
    	if(!canzoniAggiunteManualmente.isEmpty()) {
    		for(Song s : canzoniAggiunteManualmente) {
        			durata -= s.getDur();
        	}
    	}
    	
    	Set<Song> risultato = model.generaPlaylistOttima(durata, popularity, energy, danceability);
    	
    	if(risultato == null) {
    		return;
    	}
    	
    	List<Song> playlistFinale = new ArrayList<>();
    	playlistFinale.addAll(canzoniAggiunteManualmente);
    	playlistFinale.addAll(risultato);
    	
    	int dur = 0;
    	for(Song s : playlistFinale) {
    		dur+=s.getDur();
    		txtAreaGenera.appendText(s.getArtist()+" - "+s.getTitle()+" - "+s.calcolaIndice(popularity+energy+danceability)+"\n");
    	}
    	disegnaPieChartPlaylist(playlistFinale);
    	txtDurataPlaylist.setText(dur/60+" minuti e "+dur%60+" secondi");
    }
	
	private void disegnaPieChartPlaylist(List<Song> canzoni) {
		ObservableList<PieChart.Data> generi = FXCollections.observableArrayList();
		generi.clear();
		List<Genre> generiPerGrafico = model.generaGraficoPlaylist(canzoni);
		try{
			for(Genre g : generiPerGrafico) {
				generi.add(new PieChart.Data(g.getName(), g.getCounter()));
			}
		} catch (NullPointerException e) {
			return;
		}
		
		pieChartPlaylist.setData(generi);
	}
	

	@FXML
    void doReset(ActionEvent event) {
    	sliderPopularity.setValue(50);
    	sliderEnergy.setValue(50);
    	sliderDanceability.setValue(50);
    	txtFieldDurata.clear();
    	txtAreaGenera.clear();
    	txtDurataPlaylist.setText("");
    	pieChartPlaylist.getData().clear();
    	
    	if(!canzoniAggiunteManualmente.isEmpty()) {
    		int durata=0;
    		for(Song s : canzoniAggiunteManualmente) {
    			txtAreaGenera.appendText(s.getArtist()+" - "+s.getTitle()+"\n");
    			durata+=s.getDur();
    		}
    		
    		txtDurataPlaylist.setText(durata/60+" minuti e "+durata%60+" secondi");
    		disegnaPieChartPlaylist(canzoniAggiunteManualmente);
    	}
    }
	
    @FXML
    void doSelezionaArtista(ActionEvent event) {
    	String artista = choiceBoxArtista.getSelectionModel().getSelectedItem();
    	if(artista==null) {
    		return;
    	}
    	choiceBoxCanzone.setDisable(false);
    	btnAggiungi.setDisable(false);
    	ObservableList<Song> canzoni = FXCollections.observableArrayList();
    	canzoni.addAll(dao.getAllArtistSongs(artista));
    	choiceBoxCanzone.setItems(canzoni);
    }
	
    @FXML
    void doCancellaSelezione(ActionEvent event) {
    	choiceBoxArtista.getSelectionModel().clearSelection();
    	choiceBoxCanzone.getSelectionModel().clearSelection();;
    	choiceBoxCanzone.setDisable(true);
    	btnAggiungi.setDisable(true);
    	canzoniAggiunteManualmente.clear();
    	txtAreaGenera.clear();
    	txtDurataPlaylist.setText("");
    	pieChartPlaylist.getData().clear();
    }
    
    @FXML
    void doAggiungi(ActionEvent event) {
    	if(canzoniAggiunteManualmente.isEmpty()) {
    		doReset(event);
    	}
    	Song canzoneSelezionata;
    	try {
    		canzoneSelezionata = choiceBoxCanzone.getSelectionModel().getSelectedItem();
    		
    		if(canzoneSelezionata == null) {
    			return;
    		}
    		
    		if(!canzoniAggiunteManualmente.contains(canzoneSelezionata)) {
    			canzoniAggiunteManualmente.add(canzoneSelezionata);
            	txtAreaGenera.appendText(canzoneSelezionata.getArtist()+" - "+canzoneSelezionata.getTitle()+"\n");
    		}
    			
    	} catch (NullPointerException e) {
    		return;
    	}
    	
    	choiceBoxCanzone.getSelectionModel().clearSelection();
    	choiceBoxArtista.getSelectionModel().clearSelection();
    	choiceBoxCanzone.setDisable(true);
    	btnAggiungi.setDisable(true);
    	
    	int durata = 0;
    	for(Song s : canzoniAggiunteManualmente) {
    		durata += s.getDur();
    	}
    	txtDurataPlaylist.setText(durata/60+" minuti e "+durata%60+" secondi");
    	
    	disegnaPieChartPlaylist(canzoniAggiunteManualmente);
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
        assert txtCanzoneFamosa != null : "fx:id=\"txtCanzoneFamosa\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtDurataMedia != null : "fx:id=\"txtDurataMedia\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert tabGenera != null : "fx:id=\"tabGenera\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtFieldDurata != null : "fx:id=\"txtFieldDurata\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert sliderPopularity != null : "fx:id=\"sliderPopularity\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert sliderEnergy != null : "fx:id=\"sliderEnergy\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert sliderDanceability != null : "fx:id=\"sliderDanceability\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert buttonReset != null : "fx:id=\"buttonReset\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert buttonGenera != null : "fx:id=\"buttonGenera\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtAreaGenera != null : "fx:id=\"txtAreaGenera\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert yearsBarChart != null : "fx:id=\"yearsBarChart\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert genresPieChart != null : "fx:id=\"genresPieChart\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert txtDurataPlaylist != null : "fx:id=\"txtDurataPlaylist\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert choiceBoxArtista != null : "fx:id=\"choiceBoxArtista\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert btnSelezionaArtista != null : "fx:id=\"btnSelezionaArtista\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert btnCancellaSelezione != null : "fx:id=\"btnCancellaSelezione\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert choiceBoxCanzone != null : "fx:id=\"choiceBoxCanzone\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert btnAggiungi != null : "fx:id=\"btnAggiungi\" was not injected: check your FXML file 'Spotify.fxml'.";
        assert pieChartPlaylist != null : "fx:id=\"pieChartPlaylist\" was not injected: check your FXML file 'Spotify.fxml'.";


    	sliderPopularity.setValue(50);
    	sliderEnergy.setValue(50);
    	sliderDanceability.setValue(50);
        
        dao = new SpotifyDAO();
        model = new Model();
        ObservableList<String> generi = FXCollections.observableArrayList();
        anni = FXCollections.observableArrayList();
        generi.addAll(dao.getAllGenres());
        anni.addAll(dao.getAllYears());
        Collections.sort(generi);
        Collections.sort(anni);
        choiceBoxGenere.setItems(generi);
        choiceBoxAnno.setItems(anni);
        
        ObservableList<String> artisti = FXCollections.observableArrayList();
        artisti.addAll(dao.getAllArtists());
        choiceBoxArtista.setItems(artisti);
        
        artistiGrafico = new ArrayList<>();
        generiGrafico = new ArrayList<>();
        contatoreGrafico = 0;
        yearsBarChart.setVisible(false);
        
        canzoniAggiunteManualmente = new ArrayList<>();
    }

}
