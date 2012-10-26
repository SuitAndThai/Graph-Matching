package pqgram;

public class Deletion extends Edit {

	public Deletion(String a, String b) {
		super(a, b);
	}

	@Override
	public String toString() {
		return "Delete " + this.b + " from " + this.a;
	}
}
