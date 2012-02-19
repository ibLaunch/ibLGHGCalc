package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.TextItem;

public class IblTextItem extends TextItem {

	public IblTextItem(String fieldName, String defaultValue) {
            super(fieldName);
	    this.setName(fieldName);
            this.setLength(255);
	    this.setDefaultValue(defaultValue);
	    //this.setRequired(Boolean.TRUE);
	}
}
