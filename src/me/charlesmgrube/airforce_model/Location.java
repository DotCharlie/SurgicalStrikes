package me.charlesmgrube.airforce_model;

import java.util.Comparator;
import java.util.List;

public class Location {
	private int x;
	private int y;
	private int id;
	
	public Location(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public Location(String data) {
		String[] split = data.split(" ");
        this.x = Integer.parseInt(split[0]);
        this.y = Integer.parseInt(split[1]);
        this.id = Integer.parseInt(split[2]);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public int getId() {
		return id;
	}
	public static int timeElapsed(Location loc1, Location loc2) {
		int manhattanDistance = Math.abs(loc2.getX() - loc1.getX()) + Math.abs(loc2.getY() - loc1.getY());
		List<Integer> factors = Application.primeFactorization(manhattanDistance);
		factors.sort(Comparator.naturalOrder());
		int factor = -1;
		for(int i = 0; i < factors.size(); i++) {
			if(factors.get(i) > loc1.getId()) {
				factor = i;
			}
		}
		if(factor == -1) factor = loc1.getId();
		return 0;
	}
}
