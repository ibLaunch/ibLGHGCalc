package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.SelectItem;

public class SelectItem_DistanceUnit extends SelectItem {

	public SelectItem_DistanceUnit(String fieldName) {
	    super(fieldName);
	    this.setName(fieldName);
            this.setValueMap("Km","Mile");
            this.setDefaultValue("Km");		
            this.setTitle("Unit:");
	    this.setRequired(Boolean.TRUE);
            this.setEndRow(Boolean.TRUE);
	}
}
