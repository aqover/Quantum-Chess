package helper;

public class Tuple<X, Y> {
	private final X i;
	private final Y j;
	
	public Tuple(X i, Y j) {
		this.i = i;
		this.j = j;
	}

	public Tuple(Tuple<X, Y> tuple) {
		this.i = tuple.getI();
		this.j = tuple.getJ();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) return false;
		if (!(obj instanceof Tuple)) return false;
		if (obj == this) return true;
		
		@SuppressWarnings("unchecked")
		Tuple<Integer, Integer> tuple = (Tuple<Integer, Integer>) obj;
		return (i == tuple.getI() && j == tuple.getJ());
	}

	public X getI() {
		return i;
	}
	
	public Y getJ() {
		return j;
	}
}
