package org.ibLGHGCalc.client;

//import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.DateItem;
//import com.smartgwt.client.widgets.form.validator.DateRangeValidator;
//import java.util.Date;
//import com.smartgwt.client.util.DateDisplayFormatter;
import com.google.gwt.i18n.client.DateTimeFormat;
//import com.smartgwt.client.util.SC;
//import com.google.gwt.i18n.client.TimeZone;

public class FuelUsedDateItem extends DateItem {
    private final DateTimeFormat displayDateFormatter = DateTimeFormat.getFormat("MMM d, yyyy");

	public FuelUsedDateItem() {
	    super();

            //this.setName("fuelUsedBeginDate");
/*
            DateUtil.setDefaultDisplayTimezone("+00:00");

            DateUtil.setShortDateDisplayFormatter(new DateDisplayFormatter() {
             public String format(Date date) {
                 if(date == null) {
                     return null;
                 }
                 return displayDateFormatter.format(date, TimeZone.createTimeZone(0));
              }
            });
            Date currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
            Date currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
            DateRangeValidator validateDateRange = new DateRangeValidator();
            validateDateRange.setMin(currentInventoryBeginDateMin);
            validateDateRange.setMax(currentInventoryEndDateMax);
            validateDateRange.setErrorMessage("Date is not within reporting period");
*/

            this.setWidth("*");
            //this.setValidators(validateDateRange);
            this.setValidateOnExit(Boolean.TRUE);
            this.setValidateOnChange(Boolean.TRUE);
            this.setRequired(Boolean.TRUE);
	}
}