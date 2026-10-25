package project;

import java.util.List;

public class Graph {
	private double[][] distanceMatrix;
	private int numLocations;
	
	// Constructor to initialize the graph with a pre-calculated distance matrix
	public Graph(double[][] distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
		this.numLocations = distanceMatrix.length;
	}
	
	public double getDistance(int i, int j) {
		return distanceMatrix[i][j];
	}
	
	public int getNumLocations() {
		return numLocations;
	}
}
