public class ResultantVector {
	private double x_coord;
	private double y_coord;
	private double magnitude;
	private double angle;
	
	public ResultantVector() {
		
	}
	
	public ResultantVector(double x_coord, double y_coord) {
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		
		getMagnitude();
		getDirection();
	}
	
	
	public void setXCoord(double x_coord) {
		this.x_coord = x_coord;
	}
	
	public double getXCoord() {
		return this.x_coord;
	}
	
	public void setYCoord(double y_coord) {
		this.y_coord = y_coord;
	}
	
	public double getYCoord() {
		return this.y_coord;
	}

	public double getMagnitude() {
		this.magnitude = Math.sqrt((Math.pow(x_coord,2) + Math.pow(y_coord,2)));
		computeAngle();
		
		return this.magnitude;
	}
	
	public double getDirection() {
		return computeAngle();
	}
	
	private double computeAngle() {
		this.angle = Math.asin(y_coord / magnitude);
		this.angle = Math.toDegrees(this.angle);
		
		return this.angle;
	}
}