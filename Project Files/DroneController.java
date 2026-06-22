package projectfiles;
/**
* Controls the drone altitude decision system.
*/
public class DroneController {
	
	private Sensor sensorA;
	private Sensor sensorB;
	private Sensor sensorC;
	
	private int prevAlt;
	private int numFailures;
	
	/**
	 * Constructor for DroneController
	 */
	public DroneController() {
		
		// Create Java objects
		sensorA = new Sensor("SensorA");
		sensorB = new Sensor("SensorB");
		sensorC = new Sensor("SensorC");
		
		prevAlt = 0;
		numFailures = 0;
	}
		
		
	public void readSensors() throws SystemReliabilityException {
		Integer sensorValA = null;
		Integer sensorValB = null;
		Integer sensorValC = null;
			
		int vaildCount = 0;
		
		// ---------------------------------------------------------------------------------
		
		/** SENSOR READINGS */
			
		// Sensor A Reading
		try {
			sensorValA = sensorA.sensorReading();
			System.out.println("Sensor A: " + sensorValA);
			
			if (isValid(sensorValA)) {			
				validCount++;
			}
			
			else {
				System.out.println("Sensor A corrupted reading.");
				LoggerUtility.logEvent("Corrupted reading detected from Sensor A: "+ readingA);
			}
		}
		
		catch (SensorReadException e) {
			System.out.println(e.getMessage());
			LoggerUtility.logEvent("Sensor failure: Sensor A");			
		}
		
		
		// Sensor B Reading
		try {
			sensorValB = sensorB.sensorReading();
			System.out.println("Sensor B: " + sensorValB);
					
			if (isValid(sensorValB)) {			
				validCount++;
			}
					
			else {
				System.out.println("Sensor B corrupted reading.");
				LoggerUtility.logEvent("Corrupted reading detected from Sensor B: "+ readingA);
			}
		}
				
		catch (SensorReadException e) {
			System.out.println(e.getMessage());
			LoggerUtility.logEvent("Sensor failure: Sensor B");			
		}
				
				
		// Sensor C Reading
		try {
			sensorValC = sensorC.sensorReading();
			System.out.println("Sensor C: " + sensorValC);
			
			if (isValid(sensorValC)) {			
				validCount++;
			}
			
			else {
				System.out.println("Sensor C corrupted reading.");
				LoggerUtility.logEvent("Corrupted reading detected from Sensor C: "+ readingC);
			}
		}
        				
		catch (SensorReadException e) {
			System.out.println(e.getMessage());
			LoggerUtility.logEvent("Sensor failure: Sensor C");			
		}
		
		// ---------------------------------------------------------------------------------------------------
		
       /** RELIABILITY CHECK */
       if (validCount < 2) {
           consecutiveFailures++;
           System.out.println("Reliability Failure.");
           LoggerUtility.logEvent("Reliability failure occurred.");
           if (consecutiveFailures >= 2) {
               LoggerUtility.logEvent("SAFE MODE ACTIVATED.");
              
               throw new SystemReliabilityException("Two consecutive reliability failures occurred.");
           }
           System.out.println("----------------------------------");
           return;
       }
       // ---------------------------------------------------------------------------------------------------
      
       /** MAJORITY VOTING */
       Integer finalAltitude = determineMajority(readingA, readingB, readingC);
      
       // NO MAJORITY FOUND
      
       if (finalAltitude == null) {
           consecutiveFailures++;
          
           System.out.println("No majority found.");
           System.out.println("Fallback altitude used: " + previousAltitude);
           LoggerUtility.logEvent("Fallback decision used. Previous altitude = " + previousAltitude);
           if (consecutiveFailures >= 2) {
               LoggerUtility.logEvent("SAFE MODE ACTIVATED.");
               throw new SystemReliabilityException("Two consecutive reliability failures occurred.");
           }
       }
       // MAJORITY FOUND
       else {
           consecutiveFailures = 0;
           previousAltitude = finalAltitude;
          
           System.out.println("Majority altitude: " + finalAltitude);
           LoggerUtility.logEvent("Majority decision made. Altitude = " + finalAltitude);
           detectOutlier(readingA, readingB, readingC, finalAltitude);
       }
       System.out.println("----------------------------------");
   }
   /**
    * Checks if a reading is valid.
    *
    * @param value Sensor reading
    * @return true if valid
    */
   private boolean isValid(Integer value) {
       return value != null
               && value >= 0
               && value <= 200;
   }
   /**
    * Determines the majority value.
    *
    * @param a Sensor A reading
    * @param b Sensor B reading
    * @param c Sensor C reading
    * @return majority value or null if no majority exists
    */
   private Integer determineMajority(
           Integer a,
           Integer b,
           Integer c) {
       if (isValid(a)
               && isValid(b)
               && a.equals(b)) {
           return a;
       }
       if (isValid(a)
               && isValid(c)
               && a.equals(c)) {
           return a;
       }
       if (isValid(b)
               && isValid(c)
               && b.equals(c)) {
           return b;
       }
       return null;
   }
   /**
    * Detects and logs outlier sensors.
    *
    * @param a Sensor A reading
    * @param b Sensor B reading
    * @param c Sensor C reading
    * @param majority Majority value
    */
   private void detectOutlier(
           Integer a,
           Integer b,
           Integer c,
           Integer majority) {
       if (isValid(a)
               && !a.equals(majority)) {
           System.out.println("Outlier detected: Sensor A");
           LoggerUtility.logEvent("Outlier detected: Sensor A");
       }
       if (isValid(b)
               && !b.equals(majority)) {
           System.out.println("Outlier detected: Sensor B");
           LoggerUtility.logEvent("Outlier detected: Sensor B");
       }
       if (isValid(c)
               && !c.equals(majority)) {
           System.out.println("Outlier detected: Sensor C");
           LoggerUtility.logEvent("Outlier detected: Sensor C");
       }
   }
}
