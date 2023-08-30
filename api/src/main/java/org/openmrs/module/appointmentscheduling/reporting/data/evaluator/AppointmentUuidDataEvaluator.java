package org.openmrs.module.appointmentscheduling.reporting.data.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.appointmentscheduling.reporting.data.definition.AppointmentUuidDataDefinition;

@Handler(supports = AppointmentUuidDataDefinition.class)
public class AppointmentUuidDataEvaluator extends AppointmentPropertyDataEvaluator {

    @Override
    public String getPropertyName() {
        return "uuid";
    }

}
