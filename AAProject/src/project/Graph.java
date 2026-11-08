package project;

import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

public class Graph {
	
	// Instance variables to store the data internally
	private List<Location> locations;
	private double[][] distanceMatrix;
	
	// Constructor: Initializes the Graph with loaded data
	public Graph(List<Location> locations, double[][] distanceMatrix) {
		this.locations = locations;
		this.distanceMatrix = distanceMatrix;
	}
	
	// Returns the total delivery stops (N)
	public int size() {
		return locations.size();
	}
	
	// Returns the distance between location i and location j
	public double getDistance(int i, int j) {
		return distanceMatrix[i][j];
	}
	
	// Getter for locations list
	public List<Location> getLocations(){
		return locations;
	}
	
	// Helper class to store coordinates for each location
	public static class Location{
		String name;
		double latitude;
		double longitude;
		
		public Location(String name, double latitude, double longitude) {
			this.name = name;
			this.latitude = latitude;
			this.longitude = longitude;
		}
	}
	
	// Loading the CSV file
	public static List<Location> loadLocationsFromCSV(String filePath){
		List<Location> locations =new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line;
			boolean isHeader = true; //skips header row
			
			while ((line = br.readLine()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}
				
				String[] values = line.split(",");
				if (values.length >= 3) {
					String name = values[0].trim();
					double latitude = Double.parseDouble(values[1].trim());
					double longitude = Double.parseDouble(values[2].trim());
					
					locations.add(new Location(name, latitude, longitude));
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading the CSV file: " + e.getMessage());
		}
		return locations;
	}
	
	// (Method) Build the 2D distance matrix
	public static double[][] createDistanceMatrix(List<Location> locations) {
		int n = locations.size();
		double[][] distanceMatrix = new double[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					distanceMatrix[i][j] = 0.0;
				} else {
					Location location1 = locations.get(i);
					Location location2 = locations.get(j);
					
					double dx = location1.latitude - location2.latitude;
					double dy = location1.longitude - location2.longitude;
					distanceMatrix[i][j] = Math.sqrt(dx * dx + dy * dy);
				}
			}
		}
		return distanceMatrix;
	}
}

/* Placeholder original code (Testing)
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
} */

