package org.openmrs.module.appointmentscheduling.reporting.data.definition;

import org.openmrs.module.appointmentscheduling.Appointment;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

@Caching(strategy=ConfigurationPropertyCachingStrategy.class)
@Localized("appointmentscheduling.AppointmentUuidDataDefinition")
public class AppointmentUuidDataDefinition extends BaseDataDefinition implements AppointmentDataDefinition {

    public static final long serialVersionUID = 1L;

    /**
     * Default Constructor
     */
    public AppointmentUuidDataDefinition() {
        super();
    }

    /**
     * Constructor to populate name only
     */
    public AppointmentUuidDataDefinition(String name) {
        super(name);
    }

    //***** INSTANCE METHODS *****

    /**
     * @see org.openmrs.module.reporting.data.DataDefinition#getDataType()
     */
    public Class<?> getDataType() {
        return String.class;
    }

}
