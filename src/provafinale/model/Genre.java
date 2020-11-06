package provafinale.model;

public class Genre implements Comparable<Genre>{
	
	private String name;
	private int counter;
	
	public Genre(String name, int counter) {
		this.name = name;
		this.counter = counter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCounter() {
		return counter;
	}
	
	public void resetCounter() {
		this.counter = 0;
	}
	
	public void increaseCounter() {
		counter++;
	}
	@Override
	public int compareTo(Genre altro) {
		return (int) altro.counter-this.getCounter();
	}
}
