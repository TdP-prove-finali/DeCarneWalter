package provafinale.model;

public class Song{
	
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

	public int getAffinita() {
		int affinita = nrgy + dnce + pop;
		return affinita;
	}
	
	public double calcolaIndice(double indiceInserito) {
		double indiceCanzone = this.pop+this.nrgy+this.dnce;
		double index = Math.abs(indiceInserito - indiceCanzone);
		return Math.round(index * 100.0) / 100.0;
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
