public class Main {
	
	public static final double CROSSOVER_RATE = 0.5;
	public static final double MUTATION_RATE = 0.1;
	public static final int POPULATION = 100;
	
	public static void main(String[] args) throws InterruptedException {
		Algorithm a = new Algorithm(2, CROSSOVER_RATE, MUTATION_RATE, c -> {
			double[] coords = c.getData();
			return coords[0] + coords[1];
		});
		
		for (int i = 0; i < POPULATION; i++)
			a.addChromosome(Chromosome.getRandomChromosome(2));
		
		Visualizer v = new Visualizer(0, 1, 0, 1);
		v.addGen(a.getGeneticPool());
		Thread.sleep(2000);
		
		for (int i = 0; i < 1000; i++) {
			a.runGeneration();
			v.addGen(a.getGeneticPool());
			v.setBest(a.getBest());
			v.refresh();
			Thread.sleep(2000);
		}
	}

}
