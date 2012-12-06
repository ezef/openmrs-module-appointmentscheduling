package org.openmrs.module.appointment.web;

import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointment.Appointment;
import org.openmrs.module.appointment.api.AppointmentService;

/**
 * DWR patient methods. The methods in here are used in the webapp to get data from the database via
 * javascript calls.
 * 
 * @see PatientService
 */
public class DWRAppointmentService {
	
	public PatientDescription getPatientDescription(Integer patientId) {
		Patient patient = Context.getPatientService().getPatient(patientId);
		if (patient == null)
			return null;
		PatientDescription patientDescription = new PatientDescription();
		//Get Patient's phone
		Integer phonePropertyId = Integer.parseInt(Context.getAdministrationService().getGlobalProperty(
		    "appointment.phoneNumberPersonAttributeTypeId"));
		PersonAttribute phoneAttribute = patient.getAttribute(phonePropertyId);
		if (phoneAttribute != null)
			patientDescription.setPhoneNumber(phoneAttribute.getValue());
		//Runs: check if patient missed his/her last appointment.
		Appointment lastAppointment = Context.getService(AppointmentService.class).getLastAppointment(patient);
		//TODO: change hard coded "MISSED" to correct enum value
		if (lastAppointment != null && lastAppointment.getStatus() == "MISSED")
			patientDescription.setDateMissed(Context.getDateFormat().format(lastAppointment.getTimeSlot().getStartDate()));
		
		return patientDescription;
	}
}