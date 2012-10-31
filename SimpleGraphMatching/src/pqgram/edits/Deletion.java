package pqgram.edits;

public class Deletion extends PositionalEdit {

	public Deletion(String a, String b, int start) {
		super(a, b, start);
	}

	@Override
	public String toString() {
		return "Delete " + this.b + " from " + this.a + " (" + this.start + ")";
	}
}
