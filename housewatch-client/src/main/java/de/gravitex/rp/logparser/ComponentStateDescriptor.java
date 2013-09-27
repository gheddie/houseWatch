package de.gravitex.rp.logparser;

import java.util.Date;

import de.gravitex.rp.logic.WindowStateInfo;

public class ComponentStateDescriptor {

	private WindowStateInfo windowStateInfo;
	
	private Date timeStamp;

	public WindowStateInfo getWindowStateInfo() {
		return windowStateInfo;
	}

	public void setWindowStateInfo(WindowStateInfo windowStateInfo) {
		this.windowStateInfo = windowStateInfo;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
