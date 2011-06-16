package org.ibLGHGCalc.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class FloatDetailViewerField extends DetailViewerField {

  private final String NUMBER_FORMAT_1 = "#,##0.00";

  public FloatDetailViewerField(String fieldName, String FieldTitle) {
    super();
    this.setName(fieldName);
    this.setTitle(FieldTitle);
    //this.setType(ListGridFieldType.FLOAT);
    this.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
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