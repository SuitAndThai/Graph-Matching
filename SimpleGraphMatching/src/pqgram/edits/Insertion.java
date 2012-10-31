package pqgram.edits;

public class Insertion extends PositionalEdit {
	private int end;
	
	public Insertion(String a, String b, int start, int end) {
		super(a, b, start);
		this.end = end;
	}
	
	public int getStart() {
		return super.getPosition();
	}
	
	public int getEnd() {
		return this.end;
	}

	@Override
	public String toString() {
		return "Insert " + this.b + " on to " + this.a + " (" + this.start + ", " + this.end + ")";
	}
}
