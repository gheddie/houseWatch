package de.gravitex.rp;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import de.gravitex.rp.entity.RpUser;

@Named
@RequestScoped
public class RpUserListProducer {

	private List<RpUser> users = new ArrayList<RpUser>();
	
	@Inject
	private EntityManager em;
	
	@PostConstruct
	private void init() {		
		users = (List<RpUser>) em.createNamedQuery(RpUser.FIND_ALL).getResultList();
	}
	
	public List<RpUser> getUsers() {
		return users;
	}
}
