package helpper;

public class Tuple<X, Y> {
	private final X i;
	private final Y j;
	private final Team team;
	
	public Tuple(X i, Y j, Team team) {
		this.i = i;
		this.j = j;
		this.team = team;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) return false;
		if (!(obj instanceof Tuple)) return false;
		if (obj == this) return true;
		
		@SuppressWarnings("unchecked")
		Tuple<Integer, Integer> tuple = (Tuple<Integer, Integer>) obj;
		return (i == tuple.getI() && j == tuple.getJ() && team == tuple.getTeam());
	}

	public X getI() {
		return i;
	}

	public Y getJ() {
		return j;
	}
	
	public Team getTeam() {
		return team;
	}
}
