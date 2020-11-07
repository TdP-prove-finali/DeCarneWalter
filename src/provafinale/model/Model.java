package provafinale.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import provafinale.database.SpotifyDAO;

public class Model {
	
	int affinitaMin;
	
	public Song canzonePiuPopolare (List<Song> canzoni) {
		int maxPop = 0;
		Song mostPopularSong = null;
		
			for(Song s : canzoni) {
				if(s.getPop()>maxPop) {
					maxPop = s.getPop();
					mostPopularSong = s;
				}
			}
		return mostPopularSong;
	}
	
	public int durataMedia (List<Song> canzoni) {
		int durataSum = 0;
			for(Song s : canzoni) {
				durataSum += s.getDur();
			}
		return durataSum/canzoni.size();
	}

	public List<Song> generaPlaylistOttima(int durata, double popularity, double energy, double danceability) {
		
		affinitaMin = Integer.MAX_VALUE;
		
		List<Song> listaCanzoniAffini = SpotifyDAO.getCanzoniAffini(durata, popularity, energy, danceability);
		
		List<Song> parziale = new ArrayList<>();
		List<Song> best = new ArrayList<>();
		
		double indice = popularity + energy + danceability;
		
		cerca(parziale, best, durata, listaCanzoniAffini, indice);
		
		for(Song s : best) {
			System.out.println(s.getTitle());
		}
		
		
		return best;
		
		
	}
	
	private void cerca(List<Song> parziale, List<Song> best, int durata, List<Song> lista, double indice) {
		int sommaDurata = 0;
		int affinitaTot = 0;
		
		
		//caso terminale
		for(Song s : parziale) {
			sommaDurata+= s.getDur();
			affinitaTot += Math.abs(s.getAffinita()-indice);
		}
		
			if (sommaDurata>=(durata)-300 && sommaDurata<=(durata)+300) {
				
				if(affinitaTot<affinitaMin) {
					affinitaMin = affinitaTot;
					best.clear();
					best.addAll(parziale);
				}
				return;
			}
				for(Song song : lista) {
					if (!parziale.contains(song)) {
						parziale.add(song);
						cerca(parziale, best, durata, lista, indice);
						parziale.remove(parziale.size()-1);
					}
				}

		}
	
	
	public List<Genre> generaGraficoPlaylist(List<Song> canzoni) {
		if(canzoni.isEmpty()) {
			return null;
		}
		
		Map<String, Genre> generiGiaInseriti = new HashMap<>();
		for(Song s : canzoni) {
			if(!generiGiaInseriti.containsKey(s.getTopGenre())) {
				generiGiaInseriti.put(s.getTopGenre(), new Genre(s.getTopGenre(), 1));
			} else {
				generiGiaInseriti.get(s.getTopGenre()).increaseCounter();
			}
		}
		
		List<Genre> generiOrdinati = new ArrayList<>();
		for (Map.Entry<String, Genre> entry : generiGiaInseriti.entrySet()) {
			generiOrdinati.add(entry.getValue());
		}
		
		Collections.sort(generiOrdinati);
		
		List<Genre> generiFinali = new ArrayList<>();
		int i;
		for(i=0; i<5 && i<generiOrdinati.size(); i++) {
			generiFinali.add(generiOrdinati.get(i));
		}
		int contatore = 0;
		while (i<generiOrdinati.size()) {
			contatore += generiOrdinati.get(i).getCounter();
			i++;
		}
		if(contatore>0) {
			generiFinali.add(new Genre("altro", contatore));
		}
	
		
		return generiFinali;
		
	}

	
	
	
}
