package pqgram.edits;

public abstract class Edit {
	protected String a;
	protected String b;
	
	public Edit(String a, String b) {
		this.a = a;
		this.b = b;
	}
	
	public String getA() {
		return this.a;
	}
	
	public void setA(String a) {
		this.a = a;
	}
	
	public String getB() {
		return this.b;
	}
	
	public void setB(String b) {
		this.b = b;
	}
}
