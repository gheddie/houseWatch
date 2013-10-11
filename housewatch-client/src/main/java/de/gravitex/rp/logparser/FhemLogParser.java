package de.gravitex.rp.logparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.gravitex.rp.core.util.SystemType;
import de.gravitex.rp.core.util.RpUrlProvider;
import de.gravitex.rp.logic.WindowStateInfo;

public class FhemLogParser {

	public static final String LAN_ADAPTER = "HMLAN1";

	public static final String COMPONENT_NAME = "CUL_HM_HM_SEC_SC_219D3D";
	
	SimpleDateFormat dfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat dfOut = new SimpleDateFormat("yyyyMMdd.HHmmss");

	private String componentName;

	private SystemType systemType;
	
	public FhemLogParser(String componentName, SystemType systemType) {
		super();
		this.componentName = componentName;
		this.systemType = systemType;
	}

	private ComponentStateDescriptor stateDescriptor() {
		try {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			BufferedReader reader = new BufferedReader(new FileReader(RpUrlProvider.gimmeLogDirectory(systemType) + componentName + "-"+year+".log"));
			String row = null;
			ComponentStateDescriptor descriptor = new ComponentStateDescriptor();
			while ((row = reader.readLine()) != null) {
				if ((row != null) && (row.length() > 0)) {
					String dateStr = row.substring(0, 19);
					String infoStr = row.substring(21, row.length());
					if (infoStr.contains("contact")) {
						if (infoStr.contains("open")) {
							descriptor.setWindowStateInfo(WindowStateInfo.OPEN);
						} else if (infoStr.contains("closed")) {
							descriptor.setWindowStateInfo(WindowStateInfo.CLOSE);
						}
						descriptor.setTimeStamp(dfIn.parse(dateStr.replace("_", " ")));
					}
				}
			}
			reader.close();
			return descriptor;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void writeMessage() {
		StringBuffer result = new StringBuffer();
		result.append("<?xml version=\"1.0\"?>");
		result.append("\n");
		ComponentStateDescriptor descriptor = stateDescriptor();
		result.append("<message identifier=\""+componentName+"\" state=\"" + descriptor.getWindowStateInfo() + "\" />");
//		System.out.println(result.toString());
		try {
			String resultFileName = RpUrlProvider.gimmeMessagesDirectory(systemType) + componentName+"_"+dfOut.format(descriptor.getTimeStamp())+".xml";
			FileWriter fileWriter = new FileWriter(resultFileName);
			BufferedWriter out = new BufferedWriter(fileWriter);
			String outText = result.toString();
			out.write(outText);
			out.close();
			System.out.println("file written to "+resultFileName+".");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
