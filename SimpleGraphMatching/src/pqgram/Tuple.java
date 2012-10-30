package pqgram;

import java.util.ArrayList;
import java.util.List;

public class Tuple<T> {
	private List<T> items;

	public Tuple(int length) {
		this.items = new ArrayList<T>(length);
	}
	
	public int size() {
		return this.items.size();
	}
	
	public T get(int i) {
		return this.items.get(i);
	}
	
	public void set(int i, T item) {
		this.items.add(i, item);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tuple) {
			return this.equals((Tuple<T>) obj);
		}
		return super.equals(obj);
	}
	
	public boolean equals(Tuple<T> t) {
		if (this.size() == t.size()) {
			for (int i = 0; i < this.size(); i++) {
				if (!this.items.get(i).equals(t.get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String str = "(" + this.get(0);
		for (int i = 1; i < this.size(); i++) {
			str += ", " + this.get(i);
		}
		str += ")";
		return str;
	}

}
