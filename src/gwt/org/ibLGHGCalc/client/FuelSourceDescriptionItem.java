package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.TextItem;

public class FuelSourceDescriptionItem extends TextItem {

	public FuelSourceDescriptionItem() {
	    super();
            //this.setWidth("300");
	    this.setTitle("Fuel Source");
	    this.setSelectOnFocus(true);
	    this.setWrapTitle(false);
	    this.setDefaultValue("[Enter Source]");
	    this.setRequired(Boolean.TRUE);
	}
}
