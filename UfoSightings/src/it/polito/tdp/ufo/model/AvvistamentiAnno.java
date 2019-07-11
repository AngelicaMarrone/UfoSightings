package it.polito.tdp.ufo.model;

public class AvvistamentiAnno {

	private int anno;
	private int avvistamenti;
	public AvvistamentiAnno(int anno, int avvistamenti) {
		super();
		this.anno = anno;
		this.avvistamenti = avvistamenti;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getAvvistamenti() {
		return avvistamenti;
	}
	public void setAvvistamenti(int avvistamenti) {
		this.avvistamenti = avvistamenti;
	}
	@Override
	public String toString() {
		return anno+ " " + avvistamenti;
	}
	
	
}
