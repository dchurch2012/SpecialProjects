public class Vector {
	private final short POSITIVE = 1;
	private final short NEGATIVE = -1;
	
	private Coordinate begin;
	private Coordinate end;
	
	private double magnitude;
	
	private double x_length;
	private double y_length;
	
	short x_direction = 0;
	short y_direction = 0;
	
	private double theta;

	private CompassPoint compassPoint;
	
	public Vector(double magnitude, double angle, CompassPoint compass) {
		this();

		this.magnitude = magnitude;
		this.theta = Math.toRadians(angle);
		this.compassPoint = compass;
	}
	
	
	public Vector() {
		begin = new Coordinate();
		end = new Coordinate();
	
		x_length = 0;
		y_length = 0;
		
		double magnitude = 0;
		double theta = 0;	
	}
	
	public double getXDirection() {
		return this.x_direction;
	}
	
	public double getYDirection() {
		return this.y_direction;
	}
	
	public double getXLength() {
		return this.x_length;
	}
	
	public double getYLength() {
		return this.y_length;
	}

	public Coordinate getBegin() {
		return this.begin;
	}
	
	public Coordinate getEnd() {
		return this.end;
	}
	
	
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	
	public void setAngle(double theta) {
		this.theta = Math.toRadians(theta);
	}
	
	public Vector(double magnitude, double angle) {
		this();
		
		this.magnitude = magnitude;
		this.theta = angle;
	}
	
	public Coordinate calculateComponents() {
		return null;
		
	}
	
	public void calculateLengths() {
		switch(compassPoint) {
			case NE:
				x_length = 0;
				y_length = magnitude;
				break;
				
			case NNE:
			case NNW:
				x_length = magnitude * Math.sin(theta);
				y_length = magnitude * Math.cos(theta);
				break;
			
			case SE:
				x_length = 0;
				y_length = magnitude;
		
			case SES:
			case SWS:
				x_length = magnitude * Math.sin(theta);
				y_length = magnitude * Math.cos(theta);
				break;

			case WS:
				x_length = magnitude;
				y_length = 0;
				break;
			
			case WNW:
			case WSW:
				x_length = magnitude * Math.cos(theta);
				y_length = magnitude * Math.sin(theta);
				break;
	
			case ES:
				x_length = magnitude;
				y_length = 0;
				break;
		
			case ENE:
			case ESE:
				x_length = magnitude * Math.sin(theta);
				y_length = magnitude * Math.cos(theta);
				break;
		}


	}
	
	public void setBegin(double x_coord, double y_coord) {
		begin.setX(x_coord);
		begin.setY(y_coord);
	}

	public void setEnd(double x_coord, double y_coord) {
		end.setX(x_coord);
		end.setY(y_coord);
	}
	
	public void ComputeXYComponents(double theta, short x_direction, short y_direction) {
		calculateLengths();
		
		this.theta = theta;
		this.x_direction = x_direction;
		this.y_direction = y_direction;
		
		double x_coord = begin.getX();
		double y_coord = begin.getY();
		
		double end_x_coord = x_coord + ((x_direction) * x_length);
		double end_y_coord = y_coord + ((y_direction) * y_length);
		
		end.setX(end_x_coord);
		end.setY(end_y_coord);
		
	}
	
	public void setCompassPoint(CompassPoint compassPoint) {
		this.compassPoint = compassPoint;
	}
	
	public void computeVector(CompassPoint compassPoint) {
		/*
		 * =====================================================================
		 * Compass Points:
		 * =====================================================================
		 */
		 
		this.compassPoint = compassPoint;
		computeVector();
	}
	
	public void computeVector() {
		/*
		 * =====================================================================
		 * Compass Points:
		 * =====================================================================
		 */
		 
		switch(compassPoint) {
			 /**
			  * ----------------------------------------------------------------------------------------------------
			  * N, E	;North-to-East;1st quadrant;+,+;NULL;delta_x is (+);delta_y is (+)
			  * N, NE	;North,East-of-North;1st quadrant;+,+;use complementary angle;delta_x is (+);delta_y is (+)
			  * N, NW	;North,West-of-North;2nd quadrant;+,-;use complementary angle;delta_x is (-);delta_y is (-)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case NE:
			case NNE:
				ComputeXYComponents(theta,POSITIVE,POSITIVE);
				break;
			
			
			case NNW:
				ComputeXYComponents(theta,POSITIVE,NEGATIVE);
				break;
			
			 /**
			  * ----------------------------------------------------------------------------------------------------
			  * S, E	;South-to-East;4th quadrant;-,+;NULL;delta_x is (+);delta_y is (-)
			  * S, ES	;South,East-of-South;4th quadrant;-,+;use complementary angle;delta_x is (+);delta_y is (-)
			  * S, WS	;South,West-of-South,3rd quadrant;-,-;use complementary angle;delta_x is (-);delta_y is (+)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case SE:
			case SES:
				ComputeXYComponents(theta,POSITIVE,NEGATIVE);
				break;

			case SWS:
				ComputeXYComponents(theta,NEGATIVE,NEGATIVE);
				break;

			 /** 
			  * ----------------------------------------------------------------------------------------------------
			  * E, S 	;East-to-South;1st quadrant;+,-;NULL;delta_x is (+);delta_y is (+)
			  * E, NE	;East,North-of-East;1st quadrant;+,+;direct angle;delta_x is (+);delta_y is (+)
			  * E, SE	;East,South-of-East;4th quadrant;+,-;direct angle;delta_x is (+);delta_y is (-)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case ES:
			case ENE:
				ComputeXYComponents(theta,POSITIVE,POSITIVE);
				break;
	
			case ESE:
				ComputeXYComponents(theta,POSITIVE,NEGATIVE);
				break;
			
			 /** 
			  * ----------------------------------------------------------------------------------------------------
			  * W, S	;West-to-South; 2nd quadrant;-,-;NULL;delta_x is (-);delta_y is (+)
			  * W, NW	;West,North-of-West;2nd quadrant;-,+;direct angle;delta_x is (-);delta_y is (+) 
			  * W, SW	;West,South-of-West;3rd quadrant;-,-;direct angle;delta_x is (-);delta_y is (-)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case WS:
			case WNW:
				ComputeXYComponents(theta,NEGATIVE,POSITIVE);
				break;
	
			case WSW:
				ComputeXYComponents(theta,NEGATIVE,NEGATIVE);
				break;
			
			default:
				System.out.println("EROR!!!!!!");
		}
	}
	//----------------------------------------------------------------------------------------------------
	public void computeVectorVersion02() {
		/*
		 * =====================================================================
		 * Compass Points:
		 * =====================================================================
		 */
		 
		switch(compassPoint) {
			 /**
			  * ----------------------------------------------------------------------------------------------------
			  * N, E	;North-to-East;1st quadrant;+,+;NULL;delta_x is (+);delta_y is (+)
			  * N, NE	;North,East-of-North;1st quadrant;+,+;use complementary angle;delta_x is (+);delta_y is (+)
			  * N, NW	;North,West-of-North;2nd quadrant;+,-;use complementary angle;delta_x is (-);delta_y is (-)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case NE:
			case NNE:
				ComputeXYComponents(theta,POSITIVE,POSITIVE);
				break;
			
			
			case NNW:
				ComputeXYComponents(theta,POSITIVE,NEGATIVE);
				break;
			
			 /**
			  * ----------------------------------------------------------------------------------------------------
			  * S, E	;South-to-East;4th quadrant;-,+;NULL;delta_x is (+);delta_y is (-)
			  * S, ES	;South,East-of-South;4th quadrant;-,+;use complementary angle;delta_x is (+);delta_y is (-)
			  * S, WS	;South,West-of-South,3rd quadrant;-,-;use complementary angle;delta_x is (-);delta_y is (+)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case SE:
			case SES:
				ComputeXYComponents(theta,POSITIVE,NEGATIVE);
				break;

			case SWS:
				ComputeXYComponents(theta,NEGATIVE,NEGATIVE);
				break;

			 /** 
			  * ----------------------------------------------------------------------------------------------------
			  * E, S 	;East-to-South;1st quadrant;+,-;NULL;delta_x is (+);delta_y is (+)
			  * E, NE	;East,North-of-East;1st quadrant;+,+;direct angle;delta_x is (+);delta_y is (+)
			  * E, SE	;East,South-of-East;4th quadrant;+,-;direct angle;delta_x is (+);delta_y is (-)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case ES:
			case ENE:
				ComputeXYComponents(theta,POSITIVE,POSITIVE);
				break;
	
			case ESE:
				ComputeXYComponents(theta,POSITIVE,NEGATIVE);
				break;
			
			 /** 
			  * ----------------------------------------------------------------------------------------------------
			  * W, S	;West-to-South; 2nd quadrant;-,-;NULL;delta_x is (-);delta_y is (+)
			  * W, NW	;West,North-of-West;2nd quadrant;-,+;direct angle;delta_x is (-);delta_y is (+) 
			  * W, SW	;West,South-of-West;3rd quadrant;-,-;direct angle;delta_x is (-);delta_y is (-)
			  * ----------------------------------------------------------------------------------------------------
			  */
			case WS:
			case WNW:
				ComputeXYComponents(theta,NEGATIVE,POSITIVE);
				break;
	
			case WSW:
				ComputeXYComponents(theta,NEGATIVE,NEGATIVE);
				break;
			
			default:
				System.out.println("EROR!!!!!!");
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	public void printVector() {
		System.out.println("BEGIN       : " + begin.getX() + "," + begin.getY());
		System.out.println("END         : " + end.getX() + "," + end.getY());
		System.out.println();
		
		System.out.println("MAGNITUDE   : " + magnitude);
		System.out.println();
		
		System.out.println("X-DIRECTION : " + x_direction);
		System.out.println("Y-DIRECTION : " + y_direction);
		System.out.println();
	
		System.out.println("X-COMPONENT : " + x_length);
		System.out.println("Y-COMPONENT : " + y_length);
		
	}
}