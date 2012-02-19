package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.SelectItem;

public class SelectItem_Unit extends SelectItem {

	public SelectItem_Unit(String fieldName) {
	    super(fieldName);
	    this.setName(fieldName);
            this.setValueMap("Kg","Piece","$");
            this.setDefaultValue("Kg");		
	    this.setTitle("Unit:");
	    this.setRequired(Boolean.TRUE);
            this.setEndRow(Boolean.TRUE);
	}
}
