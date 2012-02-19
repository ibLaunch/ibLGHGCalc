package org.ibLGHGCalc.client;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

public class TextListGridField extends ListGridField {

	public TextListGridField(String fieldName) {	    
            super(fieldName);
            this.setType(ListGridFieldType.TEXT);
	}
}