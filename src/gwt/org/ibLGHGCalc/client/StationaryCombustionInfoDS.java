package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
                                                                                  
/**
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Nov 16, 2010
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class StationaryCombustionInfoDS extends RestDataSource {
  private static StationaryCombustionInfoDS instance;

  public static StationaryCombustionInfoDS getInstance() {
    if (instance == null) {
      instance = new StationaryCombustionInfoDS();
    }
    return instance;
  }

  private StationaryCombustionInfoDS() {
    //set id + general stuff
    setID("stationaryCombustionInfoDS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);
    idField.setHidden(Boolean.TRUE);

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
    
    DataSourceTextField fuelUnitField =
        new DataSourceTextField("fuelUnit", "Fuel Unit");
    TextItem fuelUnitItem = new TextItem();
    fuelUnitItem.setWidth("100%");
    fuelUnitField.setEditorType(fuelUnitItem);

    DataSourceDateTimeField fuelUsedBeginDateField =
        new DataSourceDateTimeField("fuelUsedBeginDate", "Begin Date");
    //DateItem fuelUsedBeginDateItem = new DateItem();
    //fuelUsedBeginDateItem.setWidth("100%");
    ///fuelUsedBeginDateField.setEditorType(fuelUsedBeginDateItem);
    fuelUsedBeginDateField.setType(FieldType.DATE);    

    DataSourceDateTimeField fuelUsedEndDateField =
        new DataSourceDateTimeField("fuelUsedEndDate", "End Date");
    //DateItem fuelUsedEndDateItem = new DateItem();
    //fuelUsedEndDateItem.setWidth("100%");
    //fuelUsedEndDateField.setEditorType(fuelUsedEndDateItem);
    fuelUsedEndDateField.setType(FieldType.DATE);

/*
    DataSourceBooleanField isPublicField =
        new DataSourceBooleanField("isPublic", "Public");
 *
 */
    setFields(idField, organizationIdField, fuelSourceDescriptionField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"stationaryCombustionInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"stationaryCombustionInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"stationaryCombustionInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"stationaryCombustionInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}