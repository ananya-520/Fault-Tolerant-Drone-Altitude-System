/**
*
*/
package projectfiles;
/**
*
*/
/**
* Main driver class for the drone navigation system.
*/
public class Driver {
   /**
    * Main method.
    * @param args Command line arguments
    */
   public static void main(String[] args) {
       DroneController controller = new DroneController();
       try {
           while (true) {
               controller.processSensors();
               Thread.sleep(2000);
           }
       } catch (SystemReliabilityException e) {
           System.out.println("SAFE MODE ACTIVATED.");
           System.out.println(e.getMessage());
       } catch (InterruptedException e) {
           System.out.println("Program interrupted.");
       }
   }
}
