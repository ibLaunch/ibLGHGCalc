package org.ibLGHGCalc.client;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

public class DateListGridField extends ListGridField {

	public DateListGridField(String fieldName) {
	    super(fieldName);
            this.setType(ListGridFieldType.DATE);
            this.setDateFormatter(DateDisplayFormat.valueOf("MMM d, yyyy")); //DateDisplayFormat.valueOf("MMM d, yyyy")
	}
}