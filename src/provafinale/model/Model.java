package provafinale.model;

import java.util.ArrayList;
import java.util.List;

import provafinale.database.SpotifyDAO;

public class Model {
	
	int affinitaMin;
	
	public Song canzonePiuPopolare (List<Song> canzoni) {
		int maxPop = 0;
		Song mostPopularSong = null;
		if(canzoni.size()>1) {
			for(Song s : canzoni) {
				if(s.getPop()>maxPop) {
					maxPop = s.getPop();
					mostPopularSong = s;
				}
			}
		}
		return mostPopularSong;
	}
	
	public int durataMedia (List<Song> canzoni) {
		int durataSum = 0;
		if(canzoni.size()>1) {
			for(Song s : canzoni) {
				durataSum += s.getDur();
			}
		}
		return durataSum/canzoni.size();
	}

	public List<Song> generaPlaylistOttima(int durata, double popularity, double energy, double danceability,
			boolean tollBassa, boolean tollAlta) {
		
		affinitaMin = Integer.MAX_VALUE;
		
		List<Song> listaCanzoniAffini = SpotifyDAO.getCanzoniAffini(durata, popularity, energy, danceability, tollBassa, tollAlta);
		
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
		
			if (sommaDurata>=(durata*60)-300 && sommaDurata<=(durata*60)+300) {
				
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

	
	
	
}
