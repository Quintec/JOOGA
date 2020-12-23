import java.util.*;

public class Chromosome implements Cloneable {
	
	private static final Random r = new Random();
	
	private int numData;
	private double min;
	private double max;
	
	private double[] data;
	
	public Chromosome(double[] d) {
		this.numData = d.length;
		this.min = d[0];
		this.max = d[0];
		
		for (int i = 0; i < d.length; i++) {
			min = Math.min(min, d[i]);
			max = Math.max(max, d[i]);
		}
		
		this.data = d;
	}
	
	public Chromosome(double[] d, double min, double max) {
		this.numData = d.length;
		this.min = min;
		this.max = max;
		
		this.data = d;
	}
	
	public double[] getData() {
		return this.data;
	}
	
	public void setData(double[] d) {
		this.data = d;
	}

	public static Chromosome getRandomChromosome(int numData) {
		return getRandomChromosome(numData, 0, 1);
	}
	
	private Set<Integer> getLocations(int numLocs) {
		Set<Integer> locations = new HashSet<Integer>();
		for (int i = 0; i < numLocs; i++) {
			int loc = r.nextInt(numData);
			while (locations.contains(loc))
				loc = r.nextInt(numData);
			locations.add(loc);
		}
		return locations;
	}
	
	private Set<Integer> getLocationsRepeatable(int numLocs) {
		Set<Integer> locations = new HashSet<Integer>();
		for (int i = 0; i < numLocs; i++) {
			int loc = r.nextInt(numData);
			locations.add(loc);
		}
		return locations;
	}
	
	public static Chromosome getRandomChromosome(int numData, double min, double max) {
		double range = max - min;
		double[] d = new double[numData];
		for (int i = 0; i < numData; i++)
			d[i] = getRandomData(min, max);
		return new Chromosome(d, min, max);
	}
	
	
	public static double getRandomData(double min, double max) {
		return r.nextDouble() * (max - min) + min;
	}
	
	public void crossover(Chromosome c2) {
		Set<Integer> locations = getLocations(numData / 2);
		double[] d2 = c2.getData();
		
		for (int loc : locations) {
			data[loc] = d2[loc];
		}
	}
	
	public void mutate(int numMutations) {
		Set<Integer> locations = getLocationsRepeatable(numMutations);
		
		for (int loc : locations) {
			data[loc] = getRandomData(min, max);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		long temp;
		temp = Double.doubleToLongBits(max);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(min);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + numData;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		if (Double.doubleToLongBits(max) != Double.doubleToLongBits(other.max))
			return false;
		if (Double.doubleToLongBits(min) != Double.doubleToLongBits(other.min))
			return false;
		if (numData != other.numData)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.data);
	}
	
	@Override
	public Chromosome clone() {
		return new Chromosome(Arrays.copyOf(data, data.length), min, max);
	}
}
