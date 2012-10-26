package pqgram;

public class Insertion extends Edit {

	public Insertion(String a, String b) {
		super(a, b);
	}

	@Override
	public String toString() {
		return "Insert " + this.b + " on to " + this.a;
	}
}
