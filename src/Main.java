public class Main {
	
	public static final double CROSSOVER_RATE = 0.5;
	public static final double MUTATION_RATE = 0.1;
	public static final int POPULATION = 1000;
	
	public static void main(String[] args) {
		Algorithm a = new Algorithm(5, CROSSOVER_RATE, MUTATION_RATE, c -> {
			double[] coords = c.getData();
			return coords[0] - coords[1] + coords[2] + coords[3] - coords[4] + 1;
		});
		
		for (int i = 0; i < POPULATION; i++)
			a.addChromosome(Chromosome.getRandomChromosome(5));
		
		for (int i = 0; i < 1000; i++) {
			a.runGeneration();
			Chromosome b = a.getBest();
			System.out.println("" + b + a.getFitness(b));
		}
	}

}
