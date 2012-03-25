package de.mukis.hsqldb.rcp.handlers;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;

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
		String sensorId = "123:Sensor:" + Math.random();
		Sensor sensor = new Sensor("MySensor", sensorId, "1.0A");
		em.persist(sensor);
		em.getTransaction().commit();
		
		trace("[CREATING] Patient");
		em.getTransaction().begin();
		Patient patient = new Patient("Max", "Mustermann", "ABC123#" + Math.random());
		em.persist(patient);
		em.getTransaction().commit();
		
		List<Sensor> sensors = em.createNamedQuery("Sensor.findBySensorId")
		.setParameter("sensorId", sensorId)
		.getResultList();
		
		
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
		
		trace("[CREATING] TherapyResult4/5");
		em.getTransaction().begin();
		Therapy therapy2 = new Therapy(patient);
		em.persist(therapy2);
		
		
		TherapyResult therapyResult4 = new TherapyResult();
		TherapyResult therapyResult5 = new TherapyResult();
		
		em.persist(therapyResult4);
		em.persist(therapyResult5);
		
		therapyResult4.setData(data3);
		therapyResult4.setTherapy(therapy2); 

		therapyResult5.setData(data2);
		therapyResult5.setTherapy(therapy2); 
		
		em.getTransaction().commit();
		printEntities(em);
		
		trace("[REMOVE] Patient");
		em.getTransaction().begin();
		em.remove(patient);
		em.getTransaction().commit();
		printEntities(em);
	}
	
	private void transaction2(EntityManager em) throws Exception{
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
		
		trace("[CREATING] TherapyResult4/5");
		em.getTransaction().begin();
		TherapyResult therapyResult4 = new TherapyResult();
		TherapyResult therapyResult5 = new TherapyResult();
		Data data1 = new Data();
		Data data2 = new Data();
		data1.setSensor(sensor);
		data2.setSensor(sensor);
		
		em.persist(data1);
		em.persist(data2);
		
		em.persist(therapyResult4);
		em.persist(therapyResult5);
		
		therapyResult4.setData(data1);
		therapyResult4.setTherapy(therapy); 

		therapyResult5.setData(data2);
		therapyResult5.setTherapy(therapy); 
		
		em.getTransaction().commit();
		printEntities(em);
		
		trace("[REMOVING] Patient");
		em.getTransaction().begin();
		em.remove(patient);
		em.getTransaction().commit();
		printEntities(em);
	}
	
//	@Inject
//	private void createPreferences(@Preference IEclipsePreferences node) {
//		System.err.println("Create Preferences: " + node.absolutePath());
//		node.put("application.appfolder", System.getProperty("user.home"));
//	}

	@SuppressWarnings("unchecked")
	private void printEntities(EntityManager em) throws Exception {
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
