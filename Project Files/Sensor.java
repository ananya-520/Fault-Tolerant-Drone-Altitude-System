package projectfiles;
import java.util.Random;
/**
* Class for drone altitude sensors
*/
public class Sensor {
	
	private String sensorID;
	private Random random_val;
	
	
	/**
	 * Constructor for sensor class
	 * @param sensorID
	 */
	public Sensor(String sensorID) {
		this.sensorID = sensorID;
		random_val = new Random();
	}
	
	
	/**
	 * Generates a simulated sensor reading
	 * @return sensor reading
	 * @throws SensorReadException if sensor fails
	 */
	public int sensorReading() throws SensorReadException {
		
		int chance = random_val.nextInt(100);
		
		//Sensor Failure
		if (chance < 15) {
			throw new SensorReadException (sensorID + " FAILED.");
		}
		
		// Corrupted Reading
		else if (chance < 30) {
			int corruptedValue = 201 + random_val.nextInt();
			return corruptedValue;
		}
		
		// Valid Reading
		else {
			return random_val.nextInt(201);
		}
	}
	
	
	/**
	 * Method to get sensor ID
	 * @return sensor ID
	 */
	public String getSensorID() {
		return sensorID;
	}
	
}
