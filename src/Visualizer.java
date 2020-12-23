import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Visualizer {
	
	public static final int DOT_SIZE = 8;
	
	private JFrame frame;
	private double minX, maxX, minY, maxY;
	
	private TreeSet<Chromosome> currPool;
	private ArrayList<Chromosome> prevPools;
	private Chromosome best;
	
	public Visualizer(int x1, int x2, int y1, int y2) {
		this.minX = x1;
		this.maxX = x2;
		this.minY = y1;
		this.maxY = y2;
		this.prevPools = new ArrayList<Chromosome>();
		this.currPool = new TreeSet<Chromosome>();
		
		frame = new JFrame("Genetic Algorithm Visualizer");
		frame.add(new GraphPanel());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void refresh() {
		frame.repaint();
	}
	
	public void addGen(TreeSet<Chromosome> pool) {
		prevPools.addAll(currPool);
		this.currPool = new TreeSet<Chromosome>(pool);
	}
	
	public void setBest(Chromosome c) {
		this.best = c;
	}
	
	private class GraphPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(128, 128, 128));
			for (Chromosome c : prevPools) {
				double[] dat = c.getData();
				double x = dat[0];
				double y = dat[1];
				drawPoint(x, y, g, DOT_SIZE);
			}
			
			g.setColor(Color.RED);
			for (Chromosome c : currPool) {
				if (c.equals(best))
					continue;
				double[] dat = c.getData();
				double x = dat[0];
				double y = dat[1];
				drawPoint(x, y, g, DOT_SIZE);
			}
			
			if (best != null) {
				g.setColor(Color.BLUE);
				double[] dat = best.getData();
				double x = dat[0];
				double y = dat[1];
				drawPoint(x, y, g, DOT_SIZE * 2);
			}
		}
		
		private void drawPoint(double x, double y, Graphics g, int size) {
			if (x < minX || x > maxX || y < minY || y > maxY)
				return;
			double cx = (x - minX) / (maxX - minX) * this.getWidth();
			double cy = this.getHeight() - (y - minY) / (maxY - minY) * this.getHeight();
			g.fillOval((int)cx - size / 2, (int)cy - size / 2, size, size);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(800, 800);
		}
	}

}
