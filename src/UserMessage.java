public class UserMessage {
	private  String message;
	private CompassPoint compassPoint;
	private short x_direction;
	private short y_direction;
	
	
	public UserMessage(String message, CompassPoint compassPoint,short x_direction,short y_direction) {
		this.message = message;
		this.compassPoint = compassPoint;
		this.x_direction = x_direction;
		this.y_direction = y_direction;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public CompassPoint getCompassPoint() {
		return this.compassPoint;
	}
	
	
}



