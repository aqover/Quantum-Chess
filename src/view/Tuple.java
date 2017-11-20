package view;

import control.Team;

public class Tuple<X, Y> {
	private final X i;
	private final Y j;
	private final Team team;
	
	public Tuple(X i, Y j, Team team) {
		this.i = i;
		this.j = j;
		this.team = team;
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
