package de.gravitex.rp.logic;

public interface RPRemote {

	public void debugUsers();
	
	public void createRpUser(String firstName, String lastName);
	
	public void toggleWindowState(String windowsIdentifier, WindowStateInfo state);

	public void ping(String pingMessage);
	
	public void createComponentMessage(String windowsIdentifier, WindowStateInfo state);
}
