package org.ibLGHGCalc.client;

import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.types.FieldType;

    public class IblDataSourceDateTimeField extends DataSourceDateTimeField {

	public IblDataSourceDateTimeField(String fieldName, String fieldTitle) {
	    super(fieldName,fieldTitle);
            this.setType(FieldType.DATE);
	}

}