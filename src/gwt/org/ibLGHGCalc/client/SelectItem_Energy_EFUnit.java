package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.SelectItem;

public class SelectItem_Energy_EFUnit extends SelectItem {

	public SelectItem_Energy_EFUnit(String fieldName) {
	    super(fieldName);
	    this.setName(fieldName);
            this.setValueMap("Kg CO2e/Kwh");
            this.setDefaultValue("Kg CO2e/Kwh");
	    this.setTitle("Unit:");
	    this.setRequired(Boolean.TRUE);
            this.setEndRow(Boolean.TRUE);
	}
}
