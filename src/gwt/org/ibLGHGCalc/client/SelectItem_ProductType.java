package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.SelectItem;

public class SelectItem_ProductType extends SelectItem {

	public SelectItem_ProductType(String fieldName) {
	    super(fieldName);
	    this.setName(fieldName);
		this.setValueMap("Goods","Services","Capital Goods");
		this.setDefaultValue("Goods");   
	    this.setRequired(Boolean.TRUE);
	}
}
