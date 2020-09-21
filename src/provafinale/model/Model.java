package provafinale.model;

import java.util.List;

public class Model {
	
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
	
}
