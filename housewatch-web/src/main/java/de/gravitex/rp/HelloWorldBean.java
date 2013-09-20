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
	
	public String windowOpen(String windowIdentifier) {
		if (checkWindowState(windowIdentifier)) {
			return "windowOpened";} else {return "windowClosed";
		}
	}
	
	private boolean checkWindowState(String windowIdentifier) {
		Query qry = entityManager.createNamedQuery(WindowState.FIND_BY_IDENTIFIER);
		qry.setParameter("windowIdentifier", windowIdentifier);
		List resultList = qry.getResultList();
		if ((resultList == null) || (resultList.size() == 0)) {
			return false;
		}
		return ((WindowState) resultList.get(0)).getWindowState().equals(WindowStateInfo.OPEN);
	}
}