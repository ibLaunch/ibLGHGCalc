package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.TextAreaItem;

public class IblTextAreaItem extends TextAreaItem {

	public IblTextAreaItem(String fieldName) {
	    super(fieldName);
	    this.setName(fieldName);
            this.setLength(255);
            this.setStartRow(Boolean.TRUE);
            //this.setRowSpan(2);		
	}
}
