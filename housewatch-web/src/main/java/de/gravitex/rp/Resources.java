package de.gravitex.rp;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

	@PersistenceContext
	private EntityManager em;
	
	@Produces
	private EntityManager getEntityManager() {
		return em;
	}
	
}
