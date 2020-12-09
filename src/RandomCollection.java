import java.util.*;

/*
 * Modified from https://stackoverflow.com/a/6409791/3482890
 */
public class RandomCollection<E> {
    private final NavigableMap<Double, E> map;
    private static final Random random = new Random();;
    private double total;

    public RandomCollection() {
    	 map = new TreeMap<Double, E>();
    	 total = 0;
    }
    
    public void add(double weight, E result) {
    	if (weight <= 0)
    		return;
        total += weight;
        map.put(total, result);
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}