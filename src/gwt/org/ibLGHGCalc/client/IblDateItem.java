package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.validator.DateRangeValidator;
import java.util.Date;

public class IblDateItem extends DateItem {

	public IblDateItem(String name, Date beginDate, Date endDate, DateRangeValidator validateDateRange ) {
            this.setName(name);         
            this.setStartDate(beginDate);
            this.setEndDate(endDate);
            //this.setDefaultValue(beginDate);
            //this.setDefaultValue(endDate);            
            this.setValidators(validateDateRange);            
            this.setValidateOnExit(Boolean.TRUE);
            this.setValidateOnChange(Boolean.TRUE);	
	}
}