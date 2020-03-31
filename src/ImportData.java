import java.time.Duration;
import java.time.LocalDateTime;
import java.io.FileOutputStream;  
import java.io.OutputStream;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.*;

public class ImportData {

	// attributes
	private Integer [] originAirportIDs;
	private Integer [] destAirportIDs;
	private LocalDateTime [] departureTimes;
	private LocalDateTime [] arrivalTimes;
	private double [] costs;
	private Duration [] durations;

	
	// constructor
	public ImportData(String filepath) {
		
		
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

}
