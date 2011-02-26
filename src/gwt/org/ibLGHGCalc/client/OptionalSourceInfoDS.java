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
 * Created by NetBeans
 * User: ibL
 * Date: Jan 8, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class OptionalSourceInfoDS extends RestDataSource {
  private static OptionalSourceInfoDS instance;

  public static OptionalSourceInfoDS getInstance() {
    if (instance == null) {
      instance = new OptionalSourceInfoDS();
    }
    return instance;
  }

  private OptionalSourceInfoDS() {
    //set id + general stuff
    setID("optionalSourceInfoDS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);

    DataSourceIntegerField organizationIdField =
        new DataSourceIntegerField("organizationId", "Organization Id");
    IntegerItem organizationIdItem = new IntegerItem();
    organizationIdField.setCanEdit(false);
    organizationIdField.setForeignKey("organizationDS.id");
    organizationIdField.setEditorType(organizationIdItem);

    DataSourceTextField optionalSourceTypeField =
        new DataSourceTextField("optionalSourceType", "optionalSourceType");
    TextItem optionalSourceTypeItem = new TextItem();
    optionalSourceTypeItem.setWidth("100%");
    optionalSourceTypeField.setEditorType(optionalSourceTypeItem);

    DataSourceTextField sourceDescriptionField =
        new DataSourceTextField("sourceDescription", "Source Description");
    TextItem sourceDescriptionItem = new TextItem();
    sourceDescriptionItem.setWidth("100%");
    sourceDescriptionField.setEditorType(sourceDescriptionItem);

    DataSourceTextField vehicleTypeField =
        new DataSourceTextField("vehicleType", "Vehicle Type");
    TextItem vehicleTypeItem = new TextItem();
    sourceDescriptionItem.setWidth("100%");
    vehicleTypeField.setEditorType(vehicleTypeItem);

    DataSourceTextField railTypeField =
        new DataSourceTextField("railType", "Rail Type");
    TextItem railTypeItem = new TextItem();
    railTypeItem.setWidth("100%");
    railTypeField.setEditorType(railTypeItem);

    DataSourceTextField busTypeField =
        new DataSourceTextField("busType", "Bus Type");
    TextItem busTypeItem = new TextItem();
    busTypeItem.setWidth("100%");
    busTypeField.setEditorType(busTypeItem);

    DataSourceTextField airTravelTypeField =
        new DataSourceTextField("airTravelType", "Air Travel Type");
    TextItem airTravelTypeItem = new TextItem();
    airTravelTypeItem.setWidth("100%");
    airTravelTypeField.setEditorType(airTravelTypeItem);

    DataSourceFloatField passengerMilesField =
        new DataSourceFloatField("passengerMiles", "Passenger Miles");
    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesField.setEditorType(passengerMilesItem);

    DataSourceTextField transportTypeField =
        new DataSourceTextField("transportType", "Product transport Type");
    TextItem transportTypeItem = new TextItem();
    transportTypeItem.setWidth("100%");
    transportTypeField.setEditorType(transportTypeItem);

    DataSourceFloatField tonMilesField =
        new DataSourceFloatField("tonMiles", "Ton Miles");
    FloatItem tonMilesItem = new FloatItem();
    tonMilesField.setEditorType(tonMilesItem);
    
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

    setFields(idField,organizationIdField,optionalSourceTypeField,sourceDescriptionField,
                vehicleTypeField,railTypeField,busTypeField,airTravelTypeField,
                passengerMilesField,tonMilesField,fuelUsedBeginDateField,fuelUsedEndDateField);
    
//setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"optionalSourceInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"optionalSourceInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"optionalSourceInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"optionalSourceInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }
}