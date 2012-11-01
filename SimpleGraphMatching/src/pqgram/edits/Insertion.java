package pqgram.edits;

import java.util.ArrayList;
import java.util.Collection;

public class Insertion extends PositionalEdit {
	private int end;
	private Collection<String> inheritedChildren;
	
	public Insertion(String a, String b, int start, int end) {
		super(a, b, start);
		this.end = end;
		this.inheritedChildren = new ArrayList<String>();
	}
	
	public int getStart() {
		return super.getPosition();
	}
	
	public int getEnd() {
		return this.end;
	}
	
	public void addInheritedChild(String inheritedChild) {
		this.inheritedChildren.add(inheritedChild);
	}

	@Override
	public String toString() {
		String str = "Insert " + this.b + " on to " + this.a + " (" + this.start + ", " + this.end + ") {";
		for (String inheritedChild : this.inheritedChildren) {
			str += inheritedChild + ", ";
		}
		str += "}";
		return str;
	}
}
