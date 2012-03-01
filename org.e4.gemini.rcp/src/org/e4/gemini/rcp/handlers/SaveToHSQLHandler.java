package org.e4.gemini.rcp.handlers;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.e4.gemini.hsqldb.data.Car;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;

public class SaveToHSQLHandler {

	@Inject
	@Optional
	@GeminiPersistenceContext(unitName = "testHSQL")
	private EntityManager em;

	@Execute
	public void execute() {
		System.out.println(em);
		try {
			em.getTransaction().begin();
			Car car = new Car();
			car.setName("1er");
			car.setVendor("BMW");
			em.persist(car);
			em.getTransaction().commit();
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
