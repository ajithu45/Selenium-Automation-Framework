package Utility;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Log {

	// Initialize Log4j logs
	 


		private static Logger Log = LogManager.getLogger(Log.class.getName());

	 // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

	 public static void startTestCase(String sTestCaseName){

		Log.info("****************************************************************************************");

		Log.info("****************************************************************************************");

		Log.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");

		Log.info("****************************************************************************************");

		Log.info("****************************************************************************************");

		}

		//This is to print log for the ending of the test case

	 public static void endTestCase(String sTestCaseName){

		Log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");

		Log.info("X");

		Log.info("X");

		Log.info("X");

		Log.info("X");

		}

		// Need to create these methods, so that they can be called  

	 public static void info(String message) {

			Log.info(message);

			}

	 public static void warn(String message) {

	    Log.warn(message);

		}

	 public static void error(Exception e) {

	    Log.error(e);

		}

	 

	 public static void debug(String message) {

	    Log.debug(message);

		}

	public static void error(String string) {
		 Log.error(string);
		// TODO Auto-generated method stub
		
	}

}
