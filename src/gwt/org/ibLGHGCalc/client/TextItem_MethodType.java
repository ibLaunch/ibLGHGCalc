package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.TextItem;

public class TextItem_MethodType extends TextItem {
	public TextItem_MethodType(String fieldName, String methodType) {
	    super(fieldName);
	    this.setName(fieldName);
            this.setLength(255);
	    this.setDefaultValue(methodType);
	    this.setRequired(Boolean.TRUE);
	}
}
