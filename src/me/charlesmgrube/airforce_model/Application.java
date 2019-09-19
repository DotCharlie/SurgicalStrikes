package me.charlesmgrube.airforce_model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Application {

	private final ArrayList<Location> targets = new ArrayList<Location>();
	private final ArrayList<Location> bases = new ArrayList<Location>();
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
				bases.add(new Location(data.get(i)));
			} else if (i > numBases && i <= numTargets+numBases) {
				targets.add(new Location(data.get(i)));
			} else {
				String[] split = data.get(i).split(" ");
				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				mainBase = new Location(x, y, -1);
			}
		}
		
		ArrayList<Integer> times = new ArrayList<Integer>();
		for (Location base: bases) {
			int quickestTime = -1;
			for(Location target : targets) {
				int time = Location.timeElapsed(base, target) + Location.timeElapsed(target, mainBase);
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

	public static List<Integer> primeFactorization(int n) {
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
