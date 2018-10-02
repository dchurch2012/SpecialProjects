public class Coordinate {
	private double x_coord = 0;
	private double y_coord = 0;
	
	public Coordinate() {
		//Do nothing for now
	}

	public Coordinate(double x_coord, double y_coord) {
		this.x_coord = x_coord;
		this.y_coord = y_coord;
	}

	
	public double getX() {
		return this.x_coord;
	}
	
	public void setX(double x_coord) {
		this.x_coord = x_coord;
	}

	public void setY(double y_coord) {
		this.y_coord = y_coord;
	}

	public double getY() {
		return this.y_coord;
	}


}