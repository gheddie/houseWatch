package de.gravitex.rp;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.gravitex.rp.entity.RpUser;
import de.gravitex.rp.entity.WindowState;
import de.gravitex.rp.logic.WindowStateInfo;

@ManagedBean
@SessionScoped
public class HelloWorldBean implements Serializable {

	@PersistenceContext(name = "pgPersistenceUnit")
	EntityManager entityManager;

	private String name = "";

	@ManagedProperty(value = "#{demoService}")
	private Service service;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getReverseName() {
		return service.reverse(name);
	}

	@SuppressWarnings("unchecked")
	public List<RpUser> getUsers() {
		return entityManager.createNamedQuery(RpUser.FIND_ALL).getResultList();
	}
	
	public String getW1Opened() {if (windowOpen("w_1")) {return "windowOpened";} else {return "windowClosed";}}
	public String getW2Opened() {if (windowOpen("w_2")) {return "windowOpened";} else {return "windowClosed";}}
	public String getW3Opened() {if (windowOpen("w_3")) {return "windowOpened";} else {return "windowClosed";}}
	public String getW4Opened() {if (windowOpen("w_4")) {return "windowOpened";} else {return "windowClosed";}}
	
	public String getN1Opened() {if (windowOpen("n_1")) {return "windowOpened";} else {return "windowClosed";}}
	public String getN2Opened() {if (windowOpen("n_2")) {return "windowOpened";} else {return "windowClosed";}}
	public String getN3Opened() {if (windowOpen("n_3")) {return "windowOpened";} else {return "windowClosed";}}
	
	public String getE1Opened() {if (windowOpen("e_1")) {return "windowOpened";} else {return "windowClosed";}}
	public String getE2Opened() {if (windowOpen("e_2")) {return "windowOpened";} else {return "windowClosed";}}
	public String getE3Opened() {if (windowOpen("e_3")) {return "windowOpened";} else {return "windowClosed";}}

	private boolean windowOpen(String windowIdentifier) {
		Query qry = entityManager.createNamedQuery(WindowState.FIND_BY_IDENTIFIER);
		qry.setParameter("windowIdentifier", windowIdentifier);
		List resultList = qry.getResultList();
		if ((resultList == null) || (resultList.size() == 0)) {
			return false;
		}
		return ((WindowState) resultList.get(0)).getWindowState().equals(WindowStateInfo.OPEN);
	}
}