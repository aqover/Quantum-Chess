package helper;

public class Tuple<X, Y> {
	private final X I;
	private final Y J;
	
	public Tuple(X i, Y j) {
		this.I = i;
		this.J = j;
	}

	public Tuple(Tuple<X, Y> tuple) {
		this.I = tuple.getI();
		this.J = tuple.getJ();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) return false;
		if (!(obj instanceof Tuple)) return false;
		if (obj == this) return true;
		
		@SuppressWarnings("unchecked")
		Tuple<Integer, Integer> tuple = (Tuple<Integer, Integer>) obj;
		return (I == tuple.getI() && J == tuple.getJ());
	}

	public X getI() {
		return I;
	}
	
	public Y getJ() {
		return J;
	}
}
