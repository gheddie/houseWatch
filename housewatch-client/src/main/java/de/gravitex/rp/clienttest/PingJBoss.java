package de.gravitex.rp.clienttest;

import java.util.Date;

import javax.naming.NamingException;

import de.gravitex.rp.core.util.RPLookupHelper;
import de.gravitex.rp.logic.RPRemote;
import de.gravitex.rp.logic.WindowStateInfo;

public class PingJBoss {
	
	private static final String EJB_MODULE_NAME = "rp-server";

	public static void main(String[] args) {
		
		try {
			RPRemote testRemote = RPLookupHelper.lookup(EJB_MODULE_NAME, "RPBean", RPRemote.class);
			testRemote.ping("client time is now : "+new Date()+".");
			testRemote.createComponentMessage("123", WindowStateInfo.CLOSE);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
