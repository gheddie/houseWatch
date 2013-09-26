package de.gravitex.rp.logparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import de.gravitex.rp.logic.WindowStateInfo;

public class FhemLogParser {
	
	public static final String LOGFILE = "C:\\fhemlog\\CUL_HM_HM_SEC_SC_219D3D-2013.log";
	
	public static final String LAN_ADAPTER = "HMLAN1";
	
	public static final String COMPONENT_NAME = "CUL_HM_HM_SEC_SC_219D3D";
	
//	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public WindowStateInfo analyze(Date from) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(LOGFILE));
			String row = null;
			WindowStateInfo info = null;
			while ((row = reader.readLine()) != null) {
				if ((row != null) && (row.length() > 0)) {
					//String dateStr = row.substring(0, 19).replace("_", " ");
					String infoStr = row.substring(21, row.length());
					Date date = null;
					//date = df.parse(dateStr);
					if (infoStr.contains("contact")) {
						if (infoStr.contains("open")) {
//							System.out.println("opened at "+date+".");
							info = WindowStateInfo.OPEN;
						} else if (infoStr.contains("closed")) {
//							System.out.println("closed at "+date+".");
							info = WindowStateInfo.CLOSE;
						} 							
					}
				}				
			}
			return info;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	//------------------------------------------------------------------------------------

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2013, 9, 20, 12, 0);
		WindowStateInfo result = new FhemLogParser().analyze(calendar.getTime());
//		System.out.println(result);
		StringBuffer s = new StringBuffer();
		s.append("<?xml version=\"1.0\"?>");
		s.append("\n");
		s.append("<message identifier=\"w_3\" state=\""+result+"\" />");
		System.out.println(s.toString());
	}
}
