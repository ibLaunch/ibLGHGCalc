package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.TextItem;

public class SourceDescriptionItem extends TextItem {

	public SourceDescriptionItem(String fieldName) {
	    super(fieldName);
            //this.setWidth("100%");
	    this.setName(fieldName);
            this.setLength(255);
	    this.setTitle("Description");
	    this.setSelectOnFocus(true);
	    this.setWrapTitle(false);
	    this.setDefaultValue("[Enter Description]");
	    this.setRequired(Boolean.TRUE);
            this.setEndRow(Boolean.TRUE);
	}
}
