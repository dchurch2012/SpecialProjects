import java.util.*;
import java.io.*;

/**
 * =====================================================================
 * Vector Solver
 * =====================================================================
 * 
 * 3. An airplane starting at city A flies 350 km
 * at 30 degrees east of north, 125 km at east of south
 * and finally 500 km at 30 degrees south of west to
 * city B.
 * SE
 * A second airplane flies directly from city A to B.
 * 
 * How far and in what direction must the second plane
 * fly?
 * 
 * =====================================================================
 * Summary:
 * =====================================================================
 * 
 * 1.1	Leg 1 : 350 km, 30 deg east-of-north;NNE
 * 1.2	Leg 2 : 125 km, 30 deg east-of-south;SES
 * 1.3	Leg 3 : 500 km, 30 deg south-of-west;WSW
 * 
 * =====================================================================
 * Compass Points:
 * =====================================================================
 * N, E		;North-to-East(Due-North);1st quadrant;+,+;NULL
 * N, NE	;North,East-of-North;1st quadrant;+,+
 * N, NW	;North,West-of-North;2nd quadrant;+,-
 * ---------------------------------------------------------------------
 * S, E		;South-to-East;(Due-South)4th quadrant;-,+;NULL
 * S, ES	;South,East-of-South;4th quadrant;-,+
 * S, WS	;South,West-of-South,3rd quadrant;-,-
 * ---------------------------------------------------------------------
 * E, S 	;East-to-South;(Due-East)1st quadrant;+,-;NULL
 * E, NE	;East,North-of-East;1st quadrant;+,+;direct angle
 * E, SE	;East,South-of-East;4th quadrant;+,-;direct angle
 * ---------------------------------------------------------------------
 * W, S		;West-to-South;(Due-West)2nd quadrant;-,-;NULL
 * W, NW	;West,North-of-West;2nd quadrant;-,+;direct angle
 * W, SW	;West,South-of-West;3rd quadrant;-,-;direct angle
 * ---------------------------------------------------------------------
 * 
 * Above problem, restated:
 * 
 * 2.1 Leg 1	: 350 km, 30 deg east-of-north	=>	<350,N|E,30>
 * 2.2 Leg 2	: 125 km, 30 deg east-of-south	=>	<125,N|NE,30>
 * 2.3 Leg 3	: 500 km, 30 deg south-of-west	=>	<500 km,W|SW,30>
 * =====================================================================
 * 
 * ALGORITHM
 * 
 * Enter Data
 * 
 * For N = 0 to NumVectors
 * 
 * 	'Direct angle used
 * 	Case 
 * 		N, E
 * 		N, NE
 * 		N, NW
 * 		S, E
 * 		S, ES
 * 		S, WS
 * 		
 * 		ComputeXYComponents(theta)
 * 
 * 	Case
 * 		E, S 
 * 		E, NE	
 * 		E, SE
 * 		W, S
 * 		W, NW
 * 		W, SW
 * 
 * 		ComputeXYComponents(90-theta)
 * 		
 * 		AddVectors(compassPoints)
 * 		
 * End For 
 */
public class VectorContainer {
	private  CompassPoint compassPoint;
	private short POSITIVE = 1;
	private short NEGATIVE = -1;
	
	private  final int NUM_VECTORS = 3;

	private  UserMessage userMessage[] = 
	{
		new UserMessage("NE",CompassPoint.NE,POSITIVE,POSITIVE),
		new UserMessage("NNE",CompassPoint.NNE,POSITIVE,POSITIVE),
		new UserMessage("NNW",CompassPoint.NNW,POSITIVE,NEGATIVE),
		new UserMessage("SE",CompassPoint.SE,POSITIVE,NEGATIVE),
		new UserMessage("SES",CompassPoint.SES,POSITIVE,NEGATIVE),
		new UserMessage("SWS",CompassPoint.SWS,NEGATIVE,NEGATIVE),
		new UserMessage("ES",CompassPoint.ES,POSITIVE,POSITIVE),
		new UserMessage("ENE",CompassPoint.ENE,POSITIVE,POSITIVE),
		new UserMessage("ESE",CompassPoint.ESE,POSITIVE,NEGATIVE),
		new UserMessage("WS",CompassPoint.WS,NEGATIVE,POSITIVE),
		new UserMessage("WNW",CompassPoint.WNW,NEGATIVE,POSITIVE),
		new UserMessage("WSW",CompassPoint.WSW,NEGATIVE,NEGATIVE)
	};
	
	public  void readVectorsFromConsole() {
		Vector[] vectors = getVectors();
		
		for(int index = 0; index < vectors.length; index++) {
			vectors[index].computeVector();
			
			System.out.println("Leg L" + (index+1));
			System.out.println();
        
			vectors[index].printVector();
			System.out.println();
	  }
		
		//----------------------------------------------------------------
		ResultantVector resultantVector = sumResultantVector(vectors);
	    
		System.out.println();
		
		System.out.println("RESULTANT - X : " + resultantVector.getXCoord());
		System.out.println("RESULTANT - Y : " + resultantVector.getYCoord());
		System.out.println("MAGNITUDE -   : " + resultantVector.getMagnitude());
		System.out.println("DIRECTION -   : " + resultantVector.getDirection());
	}

	public void readVectorsFromFile(String fileName) {
		FileReader fr = null;
		BufferedReader br = null;
		String input = "";
		ArrayList<Vector> vectorList = new ArrayList<Vector>();
		int index = 0;
		
		try 
		{
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			 
			while ((input = br.readLine()) != null)
			{
				//System.out.println(input);
				String[] columns = input.split(",");

				double magnitude = Double.parseDouble(columns[0]);
				double angle = Double.parseDouble(columns[1]);
				String compass = columns[2];

				CompassPoint compassPoint = findCompassPoint(compass);
				
				Vector vector = new Vector(magnitude,angle,compassPoint);

				if(index == 0) {
					vector.setBegin(0,0);
				}
				else {
					vector.setBegin(vectorList.get(index-1).getEnd().getX(),vectorList.get(index-1).getEnd().getY());
				}

				index++;
				
				vector.computeVector();
				vectorList.add(vector);
			}
		}
		catch(IOException except)
		{
			System.out.println(except.getMessage());
		}
		finally 
		{
			try
			{
				if (fr != null) 
				{
				  fr.close();
				}
				}
				catch(IOException except)
				{
					System.out.println(except.getMessage());
				}
		}
	
	
		index = 0;

		for(Vector vector : vectorList) 
		{

			
			System.out.println("Leg L" + (index+1));
			System.out.println();
        
			vector.printVector();
			System.out.println();
			
			index++;
		}
		
		
		//return vectorList;
		
		
		//----------------------------------------------------------------
		ResultantVector resultantVector = sumResultantVector(vectorList);
	    
		System.out.println();
		
		System.out.println("RESULTANT - X : " + resultantVector.getXCoord());
		System.out.println("RESULTANT - Y : " + resultantVector.getYCoord());
		System.out.println("MAGNITUDE -   : " + resultantVector.getMagnitude());
		System.out.println("DIRECTION -   : " + resultantVector.getDirection());
	}

	public  Vector[] getVectors() {
		int numVectors = 0;
		double magnitude = 0;
		double angle = 0;
		String compassPoint = null;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter number of vectors to compute : ");
		numVectors = scan.nextInt();
		
		Vector[] vectors = new Vector[numVectors];
		
		printDirections() ;
		   
		InputStreamReader reader = null;    
		BufferedReader br = null;
			
		try {
			reader = new InputStreamReader(System.in);    
			br = new BufferedReader(reader);
		}
		catch(Exception except) {
			System.out.println("Error opening BufferedReader");
		}
		
		for(int index = 0; index < numVectors; index++) {
			System.out.print("Enter compass direction    : ");
			
			try {
				compassPoint = br.readLine();
			}
			catch(Exception except) {
				System.out.println("Error executing readLine()");
			}
			
			System.out.print("Enter Magnitude of Vector  : ");
			magnitude = scan.nextDouble();

			CompassPoint compass = findCompassPoint(compassPoint);
			
			if(compass != null) {
				
				switch(compass) {
					//Due NORTH or Due SOUTH or Due EAST or due WEST
					case NE:
					case SE:
					case ES:
					case WS:
						break;
						
					default:
						System.out.print("Enter the angle in degrees : ");
						angle = scan.nextDouble();
						break;
				}
			
				vectors[index] = new Vector(magnitude, angle, compass);
				//vectors[index].computeVector(compass);
				
				if(index == 0) {
					vectors[index].setBegin(0,0);
				}
				else {
					vectors[index].setBegin(vectors[index-1].getEnd().getX(),vectors[index-1].getEnd().getY());
				}
			}
			else {
				System.out.println("Please select appropriate Compass Direction");
			}
		}
		
		return vectors;
	}

	//---------------------------------------------------------------------------------------------------------------
		public  Vector[] getVectorsVersion02() {
		int numVectors = 0;
		double magnitude = 0;
		double angle = 0;
		String compassPoint = null;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter number of vectors to compute : ");
		numVectors = scan.nextInt();
		
		Vector[] vectors = new Vector[numVectors];
		
		printDirections() ;
		   
		InputStreamReader reader = null;    
		BufferedReader br = null;
			
		try {
			reader = new InputStreamReader(System.in);    
			br = new BufferedReader(reader);
		}
		catch(Exception except) {
			System.out.println("Error opening BufferedReader");
		}
		
		for(int index = 0; index < numVectors; index++) {
			System.out.print("Enter compass direction    : ");
			
			try {
				compassPoint = br.readLine();
			}
			catch(Exception except) {
				System.out.println("Error executing readLine()");
			}
			
			System.out.print("Enter Magnitude of Vector  : ");
			magnitude = scan.nextDouble();

			CompassPoint compass = findCompassPoint(compassPoint);
			
			if(compass != null) {
				
				switch(compass) {
					//Due NORTH or Due SOUTH or Due EAST or due WEST
					case NE:
					case SE:
					case ES:
					case WS:
						break;
						
					default:
						System.out.print("Enter the angle in degrees : ");
						angle = scan.nextDouble();
						break;
				}
			
				vectors[index] = new Vector(magnitude, angle, compass);
				//vectors[index].computeVector(compass);
				
				if(index == 0) {
					vectors[index].setBegin(0,0);
				}
				else {
					vectors[index].setBegin(vectors[index-1].getEnd().getX(),vectors[index-1].getEnd().getY());
				}
			}
			else {
				System.out.println("Please select appropriate Compass Direction");
			}
		}
		
		return vectors;
	}

	public  CompassPoint findCompassPoint(String compassPoint) {
		for(int index = 0; index < userMessage.length; index++) {
			if(userMessage[index].getMessage().equals(compassPoint)) {
				return userMessage[index].getCompassPoint();
			}
		}
		return null;
	}
	
		
	public  ResultantVector sumResultantVector(Vector[] vectors) {
		double sum_x = 0;
		double sum_y = 0;
		double x_value = 0;
		double y_value = 0;
		
		for(Vector vector : vectors) {
			x_value = vector.getXLength();
			y_value = vector.getYLength();
			
			System.out.println("X : " + x_value * vector.getXDirection());
			System.out.println("Y : " + y_value * vector.getYDirection());
			
			
			sum_x += vector.getXDirection() * vector.getXLength();
			sum_y += vector.getYDirection() * vector.getYLength();
		}
		
		ResultantVector resultant = new ResultantVector(sum_x,sum_y);
		return resultant;
	}
	
	public  ResultantVector sumResultantVector(ArrayList<Vector> vectorList) {
		double sum_x = 0;
		double sum_y = 0;
		double x_value = 0;
		double y_value = 0;
		
		for(Vector vector : vectorList) {
			x_value = vector.getXLength();
			y_value = vector.getYLength();
			
			System.out.println("X : " + x_value * vector.getXDirection());
			System.out.println("Y : " + y_value * vector.getYDirection());
			
			
			sum_x += vector.getXDirection() * vector.getXLength();
			sum_y += vector.getYDirection() * vector.getYLength();
		}
		
		ResultantVector resultant = new ResultantVector(sum_x,sum_y);
		return resultant;
	}
	
	public  void demoSampleProblem() {
		//Leg 1 of Flight (L1) Starting : At Point A
		
		Vector vect[] = new Vector[NUM_VECTORS ];
		
		for(int index = 0; index < NUM_VECTORS; index++) {
			vect[index] = new Vector();
		}
		
		//========================================================
		// Leg 1 (L1);
		//========================================================
		// 2.1 Leg 1	: 350 km, 30 deg east-of-north	=>	<350,NNE,30>
		compassPoint = compassPoint.NNE;

		vect[0] = new Vector(350,30,compassPoint);
		
		vect[0].setBegin(0,0);
		
		vect[0].computeVector(compassPoint);

		System.out.println("Leg L1");
		System.out.println();
		
		vect[0].printVector();
		
		System.out.println();
		
		//========================================================
		// Leg 2 (L2);
		//========================================================
		// 2.2 Leg 2	: 125 km, 30 deg east-of-south	=>	<125,SES,30>

		 compassPoint = compassPoint.SES;

		vect[1] = new Vector(125,30,compassPoint);
		
		vect[1].setBegin(vect[0].getEnd().getX(),vect[0].getEnd().getY());
		
		vect[1].computeVector(compassPoint);
		
		System.out.println("Leg L2");
		System.out.println();

		vect[1].printVector();
		
		System.out.println();
		
		//========================================================
		// Leg 3 (L3);
		//========================================================
		// 2.3 Leg 3	: 500 km, 30 deg south-of-west	=>	<500 km,WSW,30>
		compassPoint = compassPoint.WSW;

		vect[2] = new Vector(500,30,compassPoint);
		
		vect[2].setBegin(vect[1].getEnd().getX(),vect[1].getEnd().getY());
		
		System.out.println("Leg L3");
		System.out.println();

		vect[2].computeVector(compassPoint);
		
		vect[2].printVector();
		
		System.out.println();
		
		//----------------------------------------------------------------
		ResultantVector resultantVector = sumResultantVector(vect);

		System.out.println();
		
		System.out.println("RESULTANT - X : " + resultantVector.getXCoord());
		System.out.println("RESULTANT - Y : " + resultantVector.getYCoord());
		System.out.println("MAGNITUDE -   : " + resultantVector.getMagnitude());
		System.out.println("DIRECTION -   : " + resultantVector.getDirection());
	}
	
	public  void printDirections() {
		System.out.println("For angles measured DUE NORTH       : Enter NE");
		System.out.println("For angles measured EAST OF NORTH   : Enter NNE");
		System.out.println("For angles measured WEST OF NORTH   : Enter NNW");
		System.out.println("For angles measured DUE SOUTH       : Enter SE");
		System.out.println("For angles measured EAST OF SOUTH   : Enter SES");
		System.out.println("For angles measured WEST OF SOUTH   : Enter SWS");
		System.out.println("For angles measured DUE EAST        : Enter ES");
		System.out.println("For angles measured NORTH OF EAST   : Enter ENE");
		System.out.println("For angles measured SOUTH OF EAST   : Enter ESE");
		System.out.println("For angles measured WEST OF SOUTH   : Enter WS");
		System.out.println("For angles measured NORTH OF WEST   : Enter WNW");
		System.out.println("For angles measured SOUTH OF WEST   : Enter WSW");
		System.out.println();
	}
	
 }
