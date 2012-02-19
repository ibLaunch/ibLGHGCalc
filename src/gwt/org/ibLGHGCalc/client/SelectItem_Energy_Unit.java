package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.SelectItem;

public class SelectItem_Energy_Unit extends SelectItem {

	public SelectItem_Energy_Unit(String fieldName) {
	    super(fieldName);
	    this.setName(fieldName);
            this.setValueMap("Kwh");
            this.setDefaultValue("Kwh");
	    this.setTitle("Unit:");
	    this.setRequired(Boolean.TRUE);
            this.setEndRow(Boolean.TRUE);
	}
}
