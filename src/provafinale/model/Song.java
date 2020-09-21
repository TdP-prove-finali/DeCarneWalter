package provafinale.model;

public class Song {
	
	int id;
	String title; 
	String artist;
	String topGenre; 
	int year; 
	int bpm; 
	int nrgy;
	int dnce;
	int dB; 
	int live;
	int val;
	int dur;
	int acous; 
	int spch; 
	int pop;
	
	public Song(int id, String title, String artist, String topGenre, int year, int bpm, int nrgy, int dnce, int dB,
			int live, int val, int dur, int acous, int spch, int pop) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.topGenre = topGenre;
		this.year = year;
		this.bpm = bpm;
		this.nrgy = nrgy;
		this.dnce = dnce;
		this.dB = dB;
		this.live = live;
		this.val = val;
		this.dur = dur;
		this.acous = acous;
		this.spch = spch;
		this.pop = pop;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getTopGenre() {
		return topGenre;
	}

	public int getYear() {
		return year;
	}

	public int getBpm() {
		return bpm;
	}

	public int getNrgy() {
		return nrgy;
	}

	public int getDnce() {
		return dnce;
	}

	public int getdB() {
		return dB;
	}

	public int getLive() {
		return live;
	}

	public int getVal() {
		return val;
	}

	public int getDur() {
		return dur;
	}

	public int getAcous() {
		return acous;
	}

	public int getSpch() {
		return spch;
	}

	public int getPop() {
		return pop;
	}
	
	
	
	

}
