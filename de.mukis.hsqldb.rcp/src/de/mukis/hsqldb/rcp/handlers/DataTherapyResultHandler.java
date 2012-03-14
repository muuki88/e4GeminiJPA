package de.mukis.hsqldb.rcp.handlers;

import java.util.List;

import javax.persistence.EntityManager;

import de.mukis.hsqldb.entity.Data;
import de.mukis.hsqldb.entity.Patient;
import de.mukis.hsqldb.entity.Sensor;
import de.mukis.hsqldb.entity.Therapy;
import de.mukis.hsqldb.entity.TherapyResult;

public class DataTherapyResultHandler extends AbstractTestHandler {

	@Override
	public void transaction(EntityManager em) throws Exception {
		trace("[CREATING] Sensor");
		em.getTransaction().begin();
		Sensor sensor = new Sensor("MySensor", "123:Sensor:" + Math.random(), "1.0A");
		em.persist(sensor);
		em.getTransaction().commit();
		
		trace("[CREATING] Patient");
		em.getTransaction().begin();
		Patient patient = new Patient("Max", "Mustermann", "ABC123#" + Math.random());
		em.persist(patient);
		em.getTransaction().commit();
		
		
		trace("[CREATING] Therapy");
		em.getTransaction().begin();
		Therapy therapy = new Therapy(patient);
		em.persist(therapy);
		em.getTransaction().commit();
		
		printEntities(em);
		
		trace("[CREATING] TherapyResult");
		em.getTransaction().begin();
		TherapyResult therapyResult = new TherapyResult();
		Data data = new Data();

		sensor.addData(data);				
		therapy.addTherapyResult(therapyResult);
		therapyResult.setData(data);
		
		em.persist(therapyResult);
		em.getTransaction().commit();
		
		printEntities(em);
		
		trace("[Removing] Data");
		em.getTransaction().begin();
		em.remove(data);
		em.refresh(therapy);
		em.getTransaction().commit();
		
		printEntities(em);
		
		trace("[CREATING] TherapyResult2");
		em.getTransaction().begin();
		TherapyResult therapyResult2 = new TherapyResult();
		Data data2 = new Data();
		therapyResult2.setData(data2);
		therapyResult2.setTherapy(therapy); 
		data2.setSensor(sensor);
		
		em.persist(data2);
		em.persist(therapyResult2);
		em.getTransaction().commit();
		
		printEntities(em);
		
		trace("[REMOVING] TherapyResult2");
		em.getTransaction().begin();
		em.remove(therapyResult2);
		em.getTransaction().commit();
		printEntities(em);
		
		trace("[CREATING] TherapyResult3");
		em.getTransaction().begin();
		TherapyResult therapyResult3 = new TherapyResult();
		Data data3 = new Data();
		therapyResult3.setData(data3);
		therapyResult3.setTherapy(therapy); 
		data3.setSensor(sensor);
		
		em.persist(data3);
		em.persist(therapyResult3);
		em.getTransaction().commit();
		printEntities(em);
		
		trace("[REMOVING] Therapy");
		em.getTransaction().begin();
		therapy = em.find(Therapy.class, therapy.getId());
		em.remove(therapy);
		em.getTransaction().commit();
		printEntities(em);
		
	}
	

	private void printEntities(EntityManager em) {
		em.getTransaction().begin();
		
		List<Patient> patient = em.createNamedQuery("Patient.findAll").getResultList();
		trace("Patient: " + patient);
		
		List<Data> data = em.createNamedQuery("Data.findAll").getResultList();
		trace("DATA: " + data);
		
		List<TherapyResult> therapyResults = em.createNamedQuery("TherapyResult.findAll").getResultList();
		trace("THERAPYRESULTS: " + therapyResults);
		
		List<Sensor> sensors = em.createNamedQuery("Sensor.findAll").getResultList();
		trace("SENSORS: " + sensors);
		
		List<Therapy> therapies = em.createNamedQuery("Therapy.findAll").getResultList();
		trace("THERAPY: " + therapies);
		em.getTransaction().commit();
		
	}

}
