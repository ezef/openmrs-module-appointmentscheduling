package org.openmrs.module.appointment.api.db.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointment.Appointment;
import org.openmrs.module.appointment.AppointmentBlock;
import org.openmrs.module.appointment.AppointmentType;
import org.openmrs.module.appointment.TimeSlot;
import org.openmrs.module.appointment.api.db.TimeSlotDAO;
import org.springframework.transaction.annotation.Transactional;

public class HibernateTimeSlotDAO extends HibernateSingleClassDAO implements TimeSlotDAO {
	
	public HibernateTimeSlotDAO() {
		super(TimeSlot.class);
	}
	
	@Override
	public List<Appointment> getAppointmentsInTimeSlot(TimeSlot timeSlot) {
		return super.sessionFactory.getCurrentSession().createCriteria(Appointment.class).add(
		    Restrictions.eq("timeSlot", timeSlot)).list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TimeSlot> getTimeSlotsByConstraints(AppointmentType appointmentType, Date fromDate, Date toDate,
	        Provider provider) throws APIException {
		if (appointmentType == null)
			throw new APIException("Appointment Type can not be null.");
		else if (fromDate != null && toDate != null && !fromDate.before(toDate))
			throw new APIException("fromDate can not be later than toDate");
		else {
			Date startDate = (fromDate == null) ? new Date() : fromDate;
			
			String stringQuery = "SELECT timeSlot FROM TimeSlot AS timeSlot WHERE timeSlot.appointmentBlock IN("
			        + " FROM AppointmentBlock WHERE :appointmentType IN elements(types)) AND voided = 0 AND startDate >= :startDate";
			
			if (toDate != null)
				stringQuery += " AND endDate <= :endDate";
			if (provider != null)
				stringQuery += " AND timeSlot.appointmentBlock.provider = :provider";
			
			Query query = super.sessionFactory.getCurrentSession().createQuery(stringQuery).setParameter("appointmentType",
			    appointmentType).setParameter("startDate", startDate);
			
			if (toDate != null)
				query.setParameter("endDate", toDate);
			if (provider != null)
				query.setParameter("provider", provider);
			
			return query.list();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TimeSlot> getTimeSlotsByAppointmentBlock(AppointmentBlock appointmentBlock) {
		if (appointmentBlock == null)
			return new Vector<TimeSlot>();
		return super.sessionFactory.getCurrentSession().createCriteria(TimeSlot.class).add(
		    Restrictions.eq("appointmentBlock", appointmentBlock)).list();
	}
	
}
