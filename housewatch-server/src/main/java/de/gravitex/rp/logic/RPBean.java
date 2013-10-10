package de.gravitex.rp.logic;

import java.awt.Window;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.gravitex.rp.entity.ComponentMessage;
import de.gravitex.rp.entity.RpUser;
import de.gravitex.rp.entity.WindowState;

@Stateless
@Remote(RPRemote.class)
public class RPBean implements RPRemote {
	
	@PersistenceContext(name="pgPersistenceUnit")
	EntityManager entityManager; 

	@SuppressWarnings("unchecked")
	public void debugUsers() {
		List<RpUser> resultList = entityManager.createNamedQuery(RpUser.FIND_ALL).getResultList();
		int size = resultList.size();
		System.out.println(size + " users found.");
		int i = 0;
		for (RpUser user : resultList) {
			System.out.println("["+i+"] :: ("+user.getLastName()+", "+user.getFirstName()+")");
			i++;
		}			
	}
	
	public void createRpUser(String firstName, String lastName) {
		System.out.println("creating user ("+lastName+", "+firstName+")...");
		RpUser user = new RpUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setCreationDate(new Date());
		entityManager.persist(user);
	}

	public void toggleWindowState(String windowsIdentifier, WindowStateInfo state) {
		switch (state) {
		case OPEN:
			//---------------------------------------------
			System.out.println("opening window '"+windowsIdentifier+"'.");
			//---------------------------------------------
			break;
		case CLOSE:
			//---------------------------------------------
			System.out.println("closing window '"+windowsIdentifier+"'.");
			//---------------------------------------------
			break;
		}
		
		//---
		
		ComponentMessage message = new ComponentMessage();
		message.setComponentidentifier(windowsIdentifier);
		message.setEventtimestamp(new Date());
		message.setComponentstate(state);
		entityManager.persist(message);
		
		//---
		
//		WindowState stateInfo = getWindowStateEntry(windowsIdentifier);
//		if (stateInfo == null) {
//			stateInfo = new WindowState();
//			stateInfo.setWindowIdentifier(windowsIdentifier);
//			stateInfo.setWindowState(state);			
//			entityManager.persist(stateInfo);
//		} else {
//			stateInfo.setWindowState(state);
//			entityManager.merge(stateInfo);
//		}		
	}

	private WindowState getWindowStateEntry(Object windowIdentifier) {
		Query qry = entityManager.createNamedQuery(WindowState.FIND_BY_IDENTIFIER);
		qry.setParameter("windowIdentifier", windowIdentifier);
		List resultList = qry.getResultList();
		if ((resultList == null) || (resultList.size() == 0)) {
			return null;
		}
		return (WindowState) resultList.get(0);
	}

	public void ping(String pingMessage) {
		System.out.println("im getting pinged with message : '"+pingMessage+"'.");
	}

	public void createComponentMessage(String windowsIdentifier, WindowStateInfo state) {
		ComponentMessage message = new ComponentMessage();
		message.setComponentidentifier(windowsIdentifier);
		message.setEventtimestamp(new Date());
		message.setComponentstate(state);
		entityManager.persist(message);		
	}
}
