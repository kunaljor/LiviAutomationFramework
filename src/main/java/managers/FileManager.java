package managers;
import dataProviders.ConfigFileReader;
import dataProviders.ExcelFileReader;

public class FileManager extends POManager{
	private static FileManager fileReaderManager;
	 private static ConfigFileReader configFileReader;
	 private static ExcelFileReader excelFileReader;
	 private FileManager() {
	 }
	 
	 public static FileManager getInstance( ) {
		
		  if(fileReaderManager==null) { 
			  synchronized(FileManager.class) {
		  if(fileReaderManager==null) { 
			  fileReaderManager = new FileManager(); 
		  } 
		  } 
			  }
		  return fileReaderManager;
	 }
	 
	 public ConfigFileReader getConfigReader() {
	 return (configFileReader == null) ? new ConfigFileReader() : configFileReader;	 
}
	 public ExcelFileReader getExcelFileReader() {
		 return (excelFileReader == null) ? new ExcelFileReader() : excelFileReader;	 
	}
}
