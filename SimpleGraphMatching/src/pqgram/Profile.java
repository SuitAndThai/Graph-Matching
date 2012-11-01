package pqgram;

public class Profile extends Multiset<Tuple<String>> {
	
	public void add(String[] tuple) {
		Tuple<String> t = new Tuple<String>(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			t.set(i, tuple[i]);
		}
		this.add(t);
	}
	
	public Profile union(Profile other) {
		return makeIndex(super.union(other));
	}
	
	public Profile intersect(Profile other) {
		return makeIndex(super.intersect(other));
	}
	
	public Profile difference(Profile other) {
		return makeIndex(super.difference(other));
	}
	
	@Override
	public Profile clone() {
		Profile i = new Profile();
		for (Tuple<String> tup : this.getAllElements()) {
			i.add(tup);
		}
		return i;
	}
	
	private Profile makeIndex(Multiset<Tuple<String>> set) {
		Profile i = new Profile();
		
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
