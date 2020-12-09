import java.util.*;
import java.util.function.*;

public class Algorithm {
	
	private static final Random r = new Random();
	
	private double crossoverRate;
	private double mutationRate;
	
	private int numData;
	
	private Function<Chromosome, Double> fitnessFunction;
	private TreeSet<Chromosome> geneticPool;
	
	private int genNum;
	
	public Algorithm(int nd, double cr, double mr, Function<Chromosome, Double> ff) {
		this.numData = nd;
		this.crossoverRate = cr;
		this.mutationRate = mr;
		
		this.fitnessFunction = ff;
		this.geneticPool = new TreeSet<Chromosome>(new FitnessComparator());
		genNum = 0;
	}
	
	public void addChromosome(Chromosome c) {
		geneticPool.add(c);
	}
	
	public TreeSet<Chromosome> getGeneticPool() {
		return geneticPool;
	}
	
	public void clearGeneticPool() {
		geneticPool.clear();
	}
	
	public Chromosome getBest() {
		return geneticPool.last();
	}
	
	public double getFitness(Chromosome c) {
		return fitnessFunction.apply(c);
	}
	
	public void run(int numGenerations) {
		for (int i = 0; i < numGenerations; i++)
			runGeneration();
	}
	
	public void runGeneration() {
		int n = geneticPool.size();
		
		RandomCollection<Chromosome> rc = new RandomCollection<Chromosome>();
		ArrayList<Chromosome> tempPool = new ArrayList<Chromosome>();
		tempPool.addAll(geneticPool);
		
		//System.out.print(poolInfo(tempPool));
		
		for (int i = 0; i < n; i++) {
			Chromosome c = tempPool.get(i);
			double sc = fitnessFunction.apply(c);
			rc.add(sc, c);
		}
		
		ArrayList<Chromosome> nextGen = new ArrayList<Chromosome>();
		for (int i = 0; i < n; i++)
			nextGen.add(rc.next().clone());
		
		//System.out.println("SELECTION");
		//System.out.print(poolInfo(nextGen));
		
		ArrayList<Chromosome> crossovers = new ArrayList<Chromosome>();
		for (int i = 0; i < n; i++)
			if (r.nextDouble() < crossoverRate)
				crossovers.add(nextGen.get(i));
		for (int i = 0; i < crossovers.size(); i++) {
			Chromosome next = crossovers.get((i + 1) % crossovers.size());
			crossovers.get(i).crossover(next);
		}
		
		//System.out.println("CROSSOVER");
		//System.out.print(poolInfo(nextGen));
		
		int[] mutations = new int[n];
		int numMutations = (int)(numData * n * mutationRate);
		for (int i = 0; i < numMutations; i++)
			mutations[r.nextInt(n)]++;
		//System.out.println(Arrays.toString(mutations));
		for (int i = 0; i < n; i++)
			nextGen.get(i).mutate(mutations[i]);
		
		//System.out.println("MUTATION");
		//System.out.print(poolInfo(nextGen));
		
		geneticPool.addAll(nextGen);
		//System.out.println(geneticPool.size());
		while (geneticPool.size() > n)
			geneticPool.remove(geneticPool.first());
		genNum++;
	}

	public int getGenNum() {
		return genNum;
	}

	public double getCrossoverRate() {
		return crossoverRate;
	}

	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	private String poolInfo(ArrayList<Chromosome> geneticPool) {
		String ans = "POOL GEN " + genNum + "\n";
		for (Chromosome c : geneticPool) {
			ans += "" + c + fitnessFunction.apply(c) + "\n";
		}
		ans += "----------------\n";
		return ans;
	}
	
	private class FitnessComparator implements Comparator<Chromosome> {

		@Override
		public int compare(Chromosome o1, Chromosome o2) {
			return Double.compare(fitnessFunction.apply(o1), fitnessFunction.apply(o2));
		}
		
	}
	
}
