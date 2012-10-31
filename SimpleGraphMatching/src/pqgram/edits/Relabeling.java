package pqgram.edits;

public class Relabeling extends Edit {
	
	public Relabeling(String a, String b) {
		super(a, b);
	}
	
	@Override
	public String toString() {
		return "Relabel " + this.a + " to " + this.b;
	}
}