package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.Test;

public class ConfigurationInputdata {

	static Properties prop = new Properties();
	static InputStream input = null;
	public static String URL = "";
	public static String Developer_Login_URL = "";
	public static String RegistrationURL = "";
	public static String TempMail_URL = "";
	public static String Karomi24_7 = "";
	public static String Path_TestData = "";
	public static String File_TestData_dev = "";
	public static String File_TestData = "";
	public static String Viewer_File_TestData = "";
	public static String CHROME_DRIVER = "";
	public static String Edge_DRIVER = "";
	public static String Firefox_DRIVER = "";
	public static String default_Application = "";
	public static String default_Application_administrator = "";
	public static String default_Application_workflow = "";
	public static String DBconnection_URL = "";
	public static String DB_username = "";
	public static String DB_password = "";
	public static String DBconnection_URL_tlog = "";
	public static String Filedirectory = "";
	public static String downloadpath = "";
	public static String Database_connection_string_main = "";
	public static String Database_connection_string_tlog = "";
	public static String Database_connection_string_main_workflow = "";
	public static String Database_connection_string_tlog_workflow = "";
	public static String RelativePath = "";
	public static String Relativepath_shortexcel = "";
	public static String branch_name = "";
	public static String Relativepath_file = "";
	public static String flag_value = "";
	public static String Relativepath_fullexcel = "";
	public static String Filepath_folder;
	public static String Workflow_URL = "";
	public static String DBconnection_URL_workflow = "";
	public static String DBconnection_URL_workflow_tlog = "";
	public static String Tenant_Name = "";
	public static String branch_name_get = "";
	public static String Filedirectory_import = "";
	public static String dbname_tlog = "";
	public static String dbname = "";
	public static String flagexcel_value = "";
	public static String Excel_Relative_Path = "";
	
	
	 public static Map<String, String> configData = new HashMap<>();
	 public static List<String> metadataKeys = new ArrayList<>();
	 public static String environment;

	@Test
	public static void Setup() throws IOException {
//		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//		capabilities.setCapability("logLevel", "DEBUG");
		input = new FileInputStream("config.properties");
		// load a properties file
		prop.load(input);

		Path_TestData = prop.getProperty("excelfilepath");
		File_TestData_dev = prop.getProperty("excelinputdata_dev");
		// File_TestData=prop.getProperty("excelinputdata");

		URL = prop.getProperty("URL");
	
		DBconnection_URL = prop.getProperty("DBconnection_URL");
		DBconnection_URL_workflow = prop.getProperty("DBconnection_URL_workflow");
		DB_username = prop.getProperty("DB_username");
		DB_password = prop.getProperty("DB_password");
		DBconnection_URL_tlog = prop.getProperty("DBconnection_URL_tlog");
		DBconnection_URL_workflow_tlog = prop.getProperty("DBconnection_URL_workflow_tlog");
		Database_connection_string_main = prop.getProperty("Database_connection_string_main");
		Database_connection_string_tlog = prop.getProperty("Database_connection_string_tlog");
		Database_connection_string_main_workflow = prop.getProperty("Database_connection_string_main_workflow");
		Database_connection_string_tlog_workflow = prop.getProperty("Database_connection_string_tlog_workflow");
		Filepath_folder = prop.getProperty("filedirctory");
		flag_value = prop.getProperty("Flag");
		//flagexcel_value = prop.getProperty("flag_excel");
		dbname_tlog = prop.getProperty("DB_name_tlog");
		dbname = prop.getProperty("DB_name");
		Relativepath_shortexcel = prop.getProperty("Relativepath");
		Relativepath_fullexcel = prop.getProperty("Relativepath_full");
		Relativepath_file = prop.getProperty("Relativepath");
		
		String Dirc = System.getProperty("user.dir");
		downloadpath = prop.getProperty("downloadpath");
		Excel_Relative_Path = Dirc + prop.getProperty("Relativepath");

		// RelativePath=Dirc+;
		Relativepath_file = Dirc + Relativepath_file;

		Filedirectory = Relativepath_file + Filepath_folder;
		System.out.println(Filedirectory);

		if (branch_name.equals("1")) {
			branch_name_get = "PM\\";
			Filedirectory_import = Relativepath_file + Filepath_folder + branch_name_get;
			System.out.println(Filedirectory_import);

		} else {
			Filedirectory_import = Relativepath_file + Filepath_folder;
			System.out.println(Filedirectory_import);
		}

		if (flag_value.equals("0"))
		{
			Excel_Relative_Path = Dirc + Relativepath_shortexcel;
			System.out.println(Excel_Relative_Path);
		} else {
			Excel_Relative_Path = Dirc + Relativepath_fullexcel;
			System.out.println(Excel_Relative_Path);
		}

		// RelativePath=Relativepath_excel;
		System.out.println(Dirc);
		// For chrome driver
		if (flagexcel_value.equals("0")) {
			File_TestData = Automation.xlsx";
			System.out.println(File_TestData);
		} else {
			File_TestData = "Automation.xlsx";
			System.out.println(File_TestData);
		}

		CHROME_DRIVER = prop.getProperty("chromedriver");
		// driver access//
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		System.setProperty("webdriver.chrome.driver", Excel_Relative_Path + CHROME_DRIVER);
		Log.info("Get the path where eclipse is installed");

		// For IE driver
		Edge_DRIVER = prop.getProperty("Edgedriver");
		// driver access//
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		System.setProperty("webdriver.edge.driver",Excel_Relative_Path +  Edge_DRIVER);
		Log.info("Get the path where eclipse is installed");

		// For Fire fox driver
		Firefox_DRIVER = prop.getProperty("firefoxdriver");
		// driver access//
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		System.setProperty("webdriver.firefor.driver", Firefox_DRIVER);
		Log.info("Get the path where eclipse is installed");
		String ss = System.getProperty("OS.name");
		System.out.println(ss);
		
		

	}
	@Test
	public static  void clean() {
        if (configData != null) {
            configData.clear();
            configData = null;
        }
        if (metadataKeys != null) {
            metadataKeys.clear();
            metadataKeys = null;
        }
        environment = null;
    }
	
}
