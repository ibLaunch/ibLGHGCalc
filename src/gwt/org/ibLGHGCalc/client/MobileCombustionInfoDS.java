package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
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
    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityField.setEditorType(fuelQuantityItem);

    DataSourceTextField fuelUnitField =
        new DataSourceTextField("fuelUnit", "Fuel Unit");
    TextItem fuelUnitItem = new TextItem();
    fuelUnitItem.setWidth("100%");
    fuelUnitField.setEditorType(fuelUnitItem);

    DataSourceFloatField milesTravelledField =
        new DataSourceFloatField("milesTravelled", "Miles Travelled");
    FloatItem milesTravelledItem = new FloatItem();
    milesTravelledField.setEditorType(milesTravelledItem);


    DataSourceFloatField bioFuelPercentField =
        new DataSourceFloatField("bioFuelPercent", "Biofuel Percent");
    FloatItem bioFuelPercentItem = new FloatItem();
    bioFuelPercentField.setEditorType(bioFuelPercentItem);

    DataSourceFloatField ethanolPercentField =
        new DataSourceFloatField("ethanolPercent", "Ethanol Percent");
    FloatItem ethanolPercentItem = new FloatItem();
    ethanolPercentField.setEditorType(ethanolPercentItem);


    DataSourceDateTimeField fuelUsedBeginDateField =
        new DataSourceDateTimeField("fuelUsedBeginDate", "Begin Date");
    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setWidth("100%");
    fuelUsedBeginDateField.setEditorType(fuelUsedBeginDateItem);

    DataSourceDateTimeField fuelUsedEndDateField =
        new DataSourceDateTimeField("fuelUsedEndDate", "End Date");
    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setWidth("100%");
    fuelUsedEndDateField.setEditorType(fuelUsedEndDateItem);


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