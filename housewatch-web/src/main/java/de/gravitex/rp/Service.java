package de.gravitex.rp;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "demoService")
@ApplicationScoped
public class Service {
	
//	@PersistenceContext(name="pgPersistenceUnit")
//	EntityManager entityManager;

	public String reverse(String name) {
//		List<RpUser> resultList = entityManager.createNamedQuery(RpUser.FIND_ALL).getResultList();
//		int size = resultList.size();
//		System.out.println("the service counted "+size+" users in database...");
		return new StringBuffer(name).reverse().toString().toLowerCase();
	}
}