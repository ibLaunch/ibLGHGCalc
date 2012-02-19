package org.ibLGHGCalc.client;

import com.smartgwt.client.data.fields.DataSourceFloatField;

    public class IblDataSourceFloatField extends DataSourceFloatField {

	public IblDataSourceFloatField(String fieldName, String fieldTitle) {
	     super(fieldName,fieldTitle);
            this.setType(ibLUsers.floatSimpleType);
	}

}