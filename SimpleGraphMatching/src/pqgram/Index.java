package pqgram;

public class Index extends Multiset<Tuple<String>> {
	
	public void add(String[] tuple) {
		Tuple<String> t = new Tuple<String>(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			t.set(i, tuple[i]);
		}
		this.add(t);
	}
	
	public Index union(Index other) {
		return makeIndex(super.union(other));
	}
	
	public Index intersect(Index other) {
		return makeIndex(super.intersect(other));
	}
	
	public Index difference(Index other) {
		return makeIndex(super.difference(other));
	}
	
	@Override
	public Index clone() {
		Index i = new Index();
		for (Tuple<String> tup : this.getAllElements()) {
			i.add(tup);
		}
		return i;
	}
	
	private Index makeIndex(Multiset<Tuple<String>> set) {
		Index i = new Index();
		
		for (Tuple<String> tup : set.getAllElements()) {
			i.add(tup);
		}
		
		return i;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (Tuple<String> tup : this.getAllElements()) {
			for (int i = 0; i < tup.size(); i++) {
				str += tup.get(i) + " ";
			}
			str += "\n";
		}
		str += "\n";
		return str;
	}

}
