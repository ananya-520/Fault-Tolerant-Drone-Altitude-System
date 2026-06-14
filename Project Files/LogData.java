package projectfiles;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Class for logging events into the text file
 */
public class LogData {
    
    private static final String FILENAME = "log.txt";
    
    /**
     * Method to write an event into the log file. If the file doesn't exist, it creates the file, otherwise it appends to the file.
     * @param message Event message
     */
    
    public static void logEvent(String message) {
   	 
   	 try {
   		 
   		 // Create Java object
   		 File logFile = new File(FILENAME);
   		 
   		 // Create file if it does not exist
   		 if (!logFile.exists()) {
   			 logFile.createNewFile();
   		 }
   		 
   		 // Create Java object to open file in append mode
   		 FileWriter fw = new FileWriter(logFile, true);
   		 
   		 // Create Java object to write the log details
   		 PrintWriter pw = new PrintWriter(fw);
   		 
   		 // Log time stamp and details
   		 pw.println(LocalDateTime.now() + " : " + message);
   		 
   		 pw.close();   	 
   	 }
   	 
   	 catch (IOException e) {
   		 System.out.println("Error logging details");
   	 }
    }

}
