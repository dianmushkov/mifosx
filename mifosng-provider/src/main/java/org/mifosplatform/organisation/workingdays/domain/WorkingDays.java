/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.mifosplatform.organisation.workingdays.domain;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.organisation.workingdays.api.WorkingDaysApiConstants;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "m_working_days")
public class WorkingDays extends AbstractPersistable<Long> {

    @Column(name = "recurrence", length = 100, nullable = true)
    private String recurrence;

    @Column(name = "repayment_rescheduling_enum", nullable = false)
    private Integer repaymentReschedulingType;

    @Column(name = "extend_term_daily_repayments", nullable = false)
    private Boolean extendTermForDailyRepayments;
    
    protected WorkingDays() {

    }

    protected WorkingDays(final String recurrence, final Integer repaymentReschedulingType, final Boolean extendTermForDailyRepayments ) {
        this.recurrence = recurrence;
        this.repaymentReschedulingType = repaymentReschedulingType;
        this.extendTermForDailyRepayments = extendTermForDailyRepayments;
    }

    /**
     * @return the recurrence
     */
    public String getRecurrence() {
        return this.recurrence;
    }

    /**
     * @return the repaymentReschedulingType
     */
    public Integer getRepaymentReschedulingType() {
        return this.repaymentReschedulingType;
    }

    public void setRepaymentReschedulingType(Integer repaymentReschedulingType) {
        this.repaymentReschedulingType = repaymentReschedulingType;
    }
    
    public Boolean getExtendTermForDailyRepayments(){
        return this.extendTermForDailyRepayments;
    }

    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);

        final String recurrenceParamName = "recurrence";
        if (command.isChangeInStringParameterNamed(recurrenceParamName, this.recurrence)) {
            final String newValue = command.stringValueOfParameterNamed(recurrenceParamName);
            actualChanges.put(recurrenceParamName, newValue);
            this.recurrence = newValue;
        }

        final String repaymentRescheduleTypeParamName = "repaymentRescheduleType";
        if (command.isChangeInIntegerParameterNamed(repaymentRescheduleTypeParamName, this.repaymentReschedulingType)) {
            final Integer newValue =command.integerValueOfParameterNamed(repaymentRescheduleTypeParamName);
            actualChanges.put(repaymentRescheduleTypeParamName,  WorkingDaysEnumerations.workingDaysStatusType(newValue));
            this.repaymentReschedulingType = RepaymentRescheduleType.fromInt(newValue).getValue();
        }
        
        if(command.isChangeInBooleanParameterNamed(WorkingDaysApiConstants.extendTermForDailyRepayments, this.extendTermForDailyRepayments)){
            final Boolean newValue = command.booleanPrimitiveValueOfParameterNamed(WorkingDaysApiConstants.extendTermForDailyRepayments);
            actualChanges.put(WorkingDaysApiConstants.extendTermForDailyRepayments, newValue);
            this.extendTermForDailyRepayments = newValue;
        }
        return actualChanges;
    }

}
