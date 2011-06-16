package org.ibLGHGCalc.client;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.google.gwt.i18n.client.NumberFormat;

public class FloatListGridField extends ListGridField {

  private final String NUMBER_FORMAT_1 = "#,##0.00";

  public FloatListGridField(String fieldName, String FieldTitle) {
    super();
    this.setName(fieldName);
    this.setTitle(FieldTitle);
    this.setType(ListGridFieldType.FLOAT);
    this.setCellFormatter(new CellFormatter() {
    public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
	if (value == null) return null;
	try {
	    NumberFormat nf = NumberFormat.getFormat(NUMBER_FORMAT_1);
	    return nf.format(((Number) value).doubleValue());
	} catch (Exception e) {
	    return value.toString();
	}
       }
     });
  }
}