package provafinale.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import provafinale.database.SpotifyDAO;

public class Model {
	
	int affinitaMin;
	SpotifyDAO dao = new SpotifyDAO();
	
	Set<Song> best = new HashSet<>();
	Set<Set<Song>> parzialeGiaAnalizzato = new HashSet<>();
	
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

	public Set<Song> generaPlaylistOttima(int durata, double popularity, double energy, double danceability) {
		
		long start = System.currentTimeMillis();
		
		affinitaMin = Integer.MAX_VALUE;
		
		parzialeGiaAnalizzato.clear();
		
		List<Song> canzoniEstratte = new ArrayList<>(dao.getCanzoniAffini(durata, popularity, energy, danceability));
		Set<Song> listaCanzoniAffini = new HashSet<>();
		
		System.out.println("Num canzoni prima: "+canzoniEstratte.size());
		if (canzoniEstratte.size()>20) {
			listaCanzoniAffini.addAll(canzoniEstratte.subList(0, 21));
		} else {
			listaCanzoniAffini.addAll(canzoniEstratte);
		}
		
		System.out.println("Num canzoni: "+listaCanzoniAffini.size());
		
		for(Song s : listaCanzoniAffini) {
			System.out.println(s.getArtist()+" - "+s.getTitle()+" - "+s.calcolaIndice(popularity+energy+danceability)+" - "+s.getDur()/60+" min e "+s.getDur()%60+" sec");
		}
		
		best = new HashSet<>();
		Set<Song> parziale = new HashSet<>();
		
		int sommaDurata = 0;
		int affinitaTot = 0;
		
		if(durata<0)
			return best;
		
		double indice = popularity + energy + danceability;
		
		cerca(parziale, durata, listaCanzoniAffini, indice, sommaDurata, affinitaTot);
		
		if(best.isEmpty()) {
			return listaCanzoniAffini;
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Durata: "+(int)(end-start)/1000+" s");
		
		
		return best;
		
		
	}
	
	private void cerca(Set<Song> parziale, int durata, Set<Song> lista, double indice, int sommaDurata, int affinitaTot) {
		
			//caso terminale
			if (sommaDurata>=(durata)-180 && sommaDurata<=(durata)+180) {
				if(affinitaTot<affinitaMin) {
					affinitaMin = affinitaTot;
					affinitaTot = 0;
					sommaDurata = 0;
					this.best.clear();
					this.best.addAll(parziale);
				}
				return;
			}
				for(Song song : lista) {
					//System.out.println(sommaDurata);
					if (!parziale.contains(song)) {
						parziale.add(song);
						if(aggiuntaValida(parziale)) {
							parzialeGiaAnalizzato.add(parziale);
							sommaDurata += song.getDur();
							affinitaTot += Math.abs(song.getAffinita()-indice);
							cerca(parziale, durata, lista, indice, sommaDurata, affinitaTot);
							parziale.remove(song);
							sommaDurata -= song.getDur();
							affinitaTot -= Math.abs(song.getAffinita()-indice);
						}
						else {
							parziale.remove(song);
						}
						
						
					}
					
				}
		}
	
	
	private boolean aggiuntaValida(Set<Song> parziale) {
			if(parzialeGiaAnalizzato.contains(parziale)) {
				return false;
			}
			
		return true;
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
