package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.SelectItem;

public class SelectItem_EFUnit extends SelectItem {

	public SelectItem_EFUnit(String fieldName) {
	    super(fieldName);
	    this.setName(fieldName);
            this.setValueMap("Kg CO2e/Kg", "Kg CO2e/Piece", "Kg CO2e/$");
            this.setDefaultValue("Kg CO2e/Kg");
	    this.setTitle("Unit:");
	    this.setRequired(Boolean.TRUE);
            this.setEndRow(Boolean.TRUE);
	}
}
