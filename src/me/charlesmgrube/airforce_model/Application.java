package me.charlesmgrube.airforce_model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Application {

	private ArrayList<Location> targets = new ArrayList<Location>();
	private ArrayList<Location> bases = new ArrayList<Location>();
	private Location mainBase;

	private int numBases = 0;
	private int numTargets = 0;
	private int maxTime = 0;

	public void run() {
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		boolean running = true;

		ArrayList<String> data = new ArrayList<String>();
		while (running) {
			String read = null;
			try {
				read = bfr.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(data.size() != maxInputLines()) {
				data.add(read);
				
				if(data.size() == 1) {
					String[] split = read.split(" ");
					numBases = Integer.parseInt(split[0]);
					numTargets = Integer.parseInt(split[1]);
					maxTime = Integer.parseInt(split[2]);
				}
			} else {
				running = false;
				break;
			}
		}
		
		for(int i = 0; i < data.size() ; i++) {
			if(i == 0) continue;
			if(i <= numBases) {
				String[] split = data.get(i).split(" ");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				int id = Integer.parseInt(split[2]);
				bases.add(new Location(x, y, id));
			} else if (i > numBases && i <= numTargets+numBases) {
				String[] split = data.get(i).split(" ");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				int id = Integer.parseInt(split[2]);
				targets.add(new Location(x, y, id));
			} else {
				String[] split = data.get(i).split(" ");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				mainBase = new Location(x, y, -1);
			}
		}
		
		ArrayList<Integer> times = new ArrayList<Integer>();
		for(int x = 0; x < bases.size(); x++) {
			int quickestTime = -1;
			for(int y = 0; y < targets.size(); y++) {
				int time = timeElapsed(bases.get(x), targets.get(y)) + timeElapsed(targets.get(y), mainBase);
				if(time < quickestTime || quickestTime==-1) {
					quickestTime = time;
				}
			}
			times.add(quickestTime);
		}
		
		times.sort(Comparator.naturalOrder());
		int num = 0;
		int totalTime = 0;
		for(int i = 0; i < times.size(); i++) {
			if(totalTime + times.get(i) <= maxTime) {
				num++;
				totalTime += times.get(i);
			}
		}
		
		System.out.println(num);
	}
	
	private int timeElapsed(Location loc1, Location loc2) {
		int manhattanDistance = Math.abs(loc2.getX() - loc1.getX()) + Math.abs(loc2.getY() - loc1.getY());
		List<Integer> factors = primeFactorization(manhattanDistance);
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

	private List<Integer> primeFactorization(int n) {
		int num = n;
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i < num; i++) {
			while (num % i == 0) {
				factors.add(i);
				num /= i;
			}
		}

		if (num > 1) {
			factors.add(num);
		}
		return factors;
	}

	private int maxInputLines() {
		return numTargets + numBases + 2;
	}

	public static void main(String[] args) {
		new Application().run();
	}
}
