package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.data.SimpleTypeFormatter;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.DataBoundComponent;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
                                                                                  
/**
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Dec 27, 2010
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileCombustionInfoDS extends RestDataSource {
  private static MobileCombustionInfoDS instance;

  public static MobileCombustionInfoDS getInstance() {
    if (instance == null) {
      instance = new MobileCombustionInfoDS();
    }
    return instance;
  }

  private MobileCombustionInfoDS() {
    //set id + general stuff
    setID("mobileCombustionInfoDS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);
    idField.setHidden(Boolean.TRUE);
/*
    // Create a SimpleType for float fields and set the formatters.
    final NumberFormat floatSimpleFormat = NumberFormat.getFormat("#,##0.00");
    SimpleType floatSimpleType = new SimpleType("floatSimpleType", FieldType.FLOAT);
    floatSimpleType.setNormalDisplayFormatter(new SimpleTypeFormatter() {
            @Override
            public String format(Object value, DataClass field,
                            DataBoundComponent component, Record record) {
                    return floatSimpleFormat.format(Double.valueOf(value.toString()));
            }
    });
*/
/*
    DataSourceIntegerField organizationNameField =
        new DataSourceIntegerField("organizationName", "Organization Name");
    organizationNameField.setCanEdit(false);
    //organizationNameField.setPrimaryKey(true);
*/

    DataSourceIntegerField organizationIdField =
        new DataSourceIntegerField("organizationId", "Organization Id");
    IntegerItem organizationIdItem = new IntegerItem();
    organizationIdField.setCanEdit(false);
    organizationIdField.setForeignKey("organizationDS.id");
    organizationIdField.setEditorType(organizationIdItem);
    organizationIdField.setHidden(Boolean.TRUE);
    
    //DataSourceIntegerField versionField =
    //    new DataSourceIntegerField("version", "Version");
    //versionField.setCanEdit(false);

    DataSourceTextField fuelSourceDescriptionField =
        new DataSourceTextField("fuelSourceDescription", "Fuel Source Description");
    TextItem fuelSourceDescriptionItem = new TextItem();
    fuelSourceDescriptionItem.setWidth("100%");
    fuelSourceDescriptionField.setEditorType(fuelSourceDescriptionItem);

    DataSourceTextField vehicleTypeField =
        new DataSourceTextField("vehicleType", "Vehicle Type");
    TextItem vehicleTypeItem = new TextItem();
    vehicleTypeItem.setWidth("100%");
    vehicleTypeField.setEditorType(vehicleTypeItem);

    DataSourceTextField vehicleYearField =
        new DataSourceTextField("vehicleYear", "Vehicle Year");
    TextItem vehicleYearItem = new TextItem();
    vehicleYearItem.setWidth("100%");
    vehicleYearField.setEditorType(vehicleYearItem);
    
    DataSourceTextField fuelTypeField =
        new DataSourceTextField("fuelType", "Fuel Type");
    TextItem fuelTypeItem = new TextItem();
    fuelTypeItem.setWidth("100%");
    fuelTypeField.setEditorType(fuelTypeItem);

    DataSourceFloatField fuelQuantityField =
        new DataSourceFloatField("fuelQuantity", "Fuel Quantity");
    //FloatItem fuelQuantityItem = new FloatItem();
    //fuelQuantityField.setEditorType(fuelQuantityItem);
    fuelQuantityField.setType(ibLUsers.floatSimpleType);
    //fuelQuantityField.setType(new ibLUsers.FloatSimpleType());

    DataSourceTextField fuelUnitField =
        new DataSourceTextField("fuelUnit", "Fuel Unit");
    TextItem fuelUnitItem = new TextItem();
    fuelUnitItem.setWidth("100%");
    fuelUnitField.setEditorType(fuelUnitItem);

    DataSourceFloatField milesTravelledField =
        new DataSourceFloatField("milesTravelled", "Miles Travelled");
    //FloatItem milesTravelledItem = new FloatItem();
    //milesTravelledField.setEditorType(milesTravelledItem);
    milesTravelledField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField bioFuelPercentField =
        new DataSourceFloatField("bioFuelPercent", "Biofuel Percent");
    //FloatItem bioFuelPercentItem = new FloatItem();
    //bioFuelPercentField.setEditorType(bioFuelPercentItem);
    bioFuelPercentField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField ethanolPercentField =
        new DataSourceFloatField("ethanolPercent", "Ethanol Percent");
    //FloatItem ethanolPercentItem = new FloatItem();
    //ethanolPercentField.setEditorType(ethanolPercentItem);
    ethanolPercentField.setType(ibLUsers.floatSimpleType);


    DataSourceDateTimeField fuelUsedBeginDateField =
        new DataSourceDateTimeField("fuelUsedBeginDate", "Begin Date");
    //DateItem fuelUsedBeginDateItem = new DateItem();
    //fuelUsedBeginDateItem.setWidth("100%");
    //fuelUsedBeginDateField.setEditorType(fuelUsedBeginDateItem);
    fuelUsedBeginDateField.setType(FieldType.DATE);

    DataSourceDateTimeField fuelUsedEndDateField =
        new DataSourceDateTimeField("fuelUsedEndDate", "End Date");
    //DateItem fuelUsedEndDateItem = new DateItem();
    //fuelUsedEndDateItem.setWidth("100%");
    //fuelUsedEndDateField.setEditorType(fuelUsedEndDateItem);
    fuelUsedEndDateField.setType(FieldType.DATE);

    setFields(idField, organizationIdField, fuelSourceDescriptionField, vehicleTypeField, vehicleYearField, fuelTypeField, fuelQuantityField, fuelUnitField, milesTravelledField, bioFuelPercentField, ethanolPercentField, fuelUsedBeginDateField, fuelUsedEndDateField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"mobileCombustionInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"mobileCombustionInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"mobileCombustionInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"mobileCombustionInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }
}