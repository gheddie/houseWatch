package de.gravitex.rp.logparser;


public class TestLogFileParser {

	public static void main(String[] args) {
		new FhemLogParser("CUL_HM_HM_SEC_SC_219D3D").writeMessage();
	}
}
