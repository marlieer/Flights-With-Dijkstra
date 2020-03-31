import java.time.Duration;
import java.time.LocalDateTime; 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
//import org.apache.poi.ss.usermodel.*;

public class ImportData {

	// attributes
	private Integer [] originAirportIDs;
	private Integer [] destAirportIDs;
	private LocalDateTime [] departureTimes;
	private LocalDateTime [] arrivalTimes;
	private double [] costs;
	private Duration [] durations;
	private String[] originAirportCity;
	private String[] destAirportCity;
	
	/*
	Resources referenced: 
	Reading from csv file: https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	Using Durations: https://www.dariawan.com/tutorials/java/java-time-duration-tutorial-examples/?fbclid=IwAR2d1wpay0a6z_T6E2qSWJF6-Pq1x_kajq6ke1nln2-FO-MoQ-9JSR8hLn8
	*/
	
	public static void main(String[] args) {
		ImportData data = new ImportData("flight_data.csv");
		for (int j = 0; j < 10; j++) {
			
    		System.out.printf("originAirportID %d, destAirportID %d, departureTimes %s, arrivalTimes %s, costs %f, duration %s, originAirportCity %s, destAirportCity %s \n", 
    				data.originAirportIDs[j], data.destAirportIDs[j], data.departureTimes[j], data.arrivalTimes[j], 
    				data.costs[j], data.durations[j], data.originAirportCity[j], data.destAirportCity[j]);
			
		}
	}
	
	// constructor
	public ImportData(String filepath) {
		 String csvFile = filepath;
		 BufferedReader br = null;
	     String line = "";
	     String cvsSplitBy = ",";
	     
	     try {

	            br = new BufferedReader(new FileReader(csvFile));
	            System.out.println(br.readLine()); // Skip the first line because it's just headers
	            int i = 0;
	            while ((line = br.readLine()) != null) {
	            		
            		// use comma as separator
            		String[] values = line.split(cvsSplitBy);
            		/*
            		 * values[0] is the year
            		 * values[1] is the month
            		 * values[2] is the day
            		 * values[3] is the originAirportID
            		 * values[4] and values[5] are the originAirportCity and State
            		 * values[6] is the destAirportID
            		 * values[7] and values[8] are the destAirportCity and State
            		 * values[9] and values[10] are the departure hour and minute
            		 * values[11] and values[12] are the arrival hour and minute
            		 * values[13] is the distance
            		 * values[14] is the cost
            		 */
            		
            		
            		System.out.printf("year %s, month %s, day %2s, originAirportID %s, originAirportCity %-10s, originAirportState %s" 
            		+ " destAirportID %s, destAirportCity %-10s, destAirportState %s, dep_hour %s, dep_min %s, arr_hour %s, arr_min %s, distance %s, cost %s\n", 
            		values[0], values[1], values[2], values[3], values[4].substring(1), values[5].substring(0,values[5].length()-1), values[6], 
            		values[7].substring(1), values[8].substring(0,values[8].length()-1), values[9], values[10], values[11], values[12], values[13], values[14]);
					
            		
            		originAirportIDs[i] = Integer.parseInt(values[3].trim());
            		destAirportIDs[i] = Integer.parseInt(values[6]);
            		departureTimes[i] = LocalDateTime.of(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
            				Integer.parseInt(values[2]), Integer.parseInt(values[9]), Integer.parseInt(values[10]));
            		arrivalTimes[i] = departureTimes[i].withHour(Integer.parseInt(values[11])); // arrival Date is the same as departure date, so just change the hour and minute
            		arrivalTimes[i] = arrivalTimes[i].withMinute(Integer.parseInt(values[12]));
            		costs[i] = Double.parseDouble(values[14]);
            		durations[i] = Duration.between(departureTimes[i], arrivalTimes[i]);
            		originAirportCity[i] = values[4].substring(1) + ", " + values[5].substring(0,values[5].length()-1); // There were some extra " " in the csv file, so had to get rid of those
            		destAirportCity[i] = values[7].substring(1) + ", " + values[8].substring(0,values[8].length()-1); 
            		/*
            		System.out.printf("year %s, month %s, day %2s, originAirportID %s, originAirportCity %-10s, originAirportState %s" 
            		+ " destAirportID %s, destAirportCity %-10s, destAirportState %s, dep_hour %s, dep_min %s, arr_hour %s, arr_min %s, distance %s, cost %s\n", 
            		values[0], values[1], values[2], values[3], values[4].substring(1), values[5].substring(0,values[5].length()-1), values[6], 
            		values[7].substring(1), values[8].substring(0,values[8].length()-1), values[9], values[10], values[11], values[12], values[13], values[14]);
					*/
            		i++;
            		if (i % 10000 == 0) System.out.println("reading line " + i);
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		
		
		// just testing out some small data values
//		this.originAirportIDs = new Integer [] {31953, 31650, 31295, 35249,30397,31267,32448,31703,31136};
//		this.destAirportIDs = new Integer [] {30397,33105,33105,30397,31778,31650,30397,33105,31703};
//		this.costs = new double [] {300,774.8,297.7,289.9,694.8,688.8,443.3,760.5,1082.9};
//		
//		String [] deptTimes = new String [] {"600","1404","1220","1527","1902","900","1558","1555","1045"};
//		String [] arrTimes = new String [] {723,1709,1345,1639,2005,1012,1823,1821,1301};
//		
//		String [] deptDates = new String [] {"2019-01-01","2019-01-01","2019-01-01","2019-01-01","2019-01-01","2019-01-01","2019-01-01","2019-01-01","2019-01-01"};
	}
	
	
	
	// getters and setters
	public double [] getCosts() {
		return this.costs;
	}


	public Duration [] getDurations() {
		return durations;
	}


	public LocalDateTime [] getArrivalTimes() {
		return arrivalTimes;
	}


	public Integer [] getOriginAirportIDs() {
		return originAirportIDs;
	}


	public Integer [] getDestAirportIDs() {
		return destAirportIDs;
	}


	public LocalDateTime [] getDepartureTimes() {
		return departureTimes;
	}

	public String[] getOriginAirportCity() {
		return originAirportCity;
	}

	public void setOriginAirportCity(String[] originAirportCity) {
		this.originAirportCity = originAirportCity;
	}

	public String[] getDestAirportCity() {
		return destAirportCity;
	}

	public void setDestAirportCity(String[] destAirportCity) {
		this.destAirportCity = destAirportCity;
	}


}