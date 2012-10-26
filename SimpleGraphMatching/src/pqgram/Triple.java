package pqgram;

public class Triple<T, U, V> {
	private T a;
	private U b;
	private V c;

	public Triple(T a, U b, V c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public T getA() {
		return a;
	}

	public U getB() {
		return b;
	}

	public V getC() {
		return c;
	}

	public void setA(T a) {
		this.a = a;
	}

	public void setB(U b) {
		this.b = b;
	}

	public void setC(V c) {
		this.c = c;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Triple) {
			Triple t = (Triple) obj;
			return t.getA().equals(this.a) && t.getB().equals(this.b) && t.getC().equals(this.c);
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + this.a + ", " + this.b + ", " + this.c + ")";
	}
}