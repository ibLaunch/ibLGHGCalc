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
 * Date: Dec 28, 2010
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class VehicleType_EPADS extends RestDataSource {
  private static VehicleType_EPADS instance;

  public static VehicleType_EPADS getInstance() {
    if (instance == null) {
      instance = new VehicleType_EPADS();
    }
    return instance;
  }

  private VehicleType_EPADS() {
    //set id + general stuff
    setID("vehicleType_EPADS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);


    DataSourceTextField vehicleTypeField =
        new DataSourceTextField("vehicleType", "Vehicle Type");
    TextItem vehicleTypeItem = new TextItem();
    vehicleTypeItem.setWidth("100%");
    vehicleTypeField.setEditorType(vehicleTypeItem);

    DataSourceTextField fuelUnitField =
        new DataSourceTextField("fuelUnit", "Fuel Unit");
    TextItem fuelUnitItem = new TextItem();
    fuelUnitItem.setWidth("100%");
    fuelUnitField.setEditorType(fuelUnitItem);

    DataSourceTextField fuelTypeField =
        new DataSourceTextField("fuelType", "Fuel Type");
    TextItem fuelTypeItem = new TextItem();
    fuelTypeItem.setWidth("100%");
    fuelTypeField.setEditorType(fuelTypeItem);


    setFields(idField, vehicleTypeField, fuelUnitField, fuelTypeField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"vehicleType_EPA/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"vehicleType_EPA/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"vehicleType_EPA/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"vehicleType_EPA/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}