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
 * Date: Jan 10, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EF_ProductTransportType_EPADS extends RestDataSource {
  private static EF_ProductTransportType_EPADS instance;

  public static EF_ProductTransportType_EPADS getInstance() {
    if (instance == null) {
      instance = new EF_ProductTransportType_EPADS();
    }
    return instance;
  }

  private EF_ProductTransportType_EPADS() {
    //set id + general stuff
    setID("eF_ProductTransportType_EPADS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);

    DataSourceTextField transportTypeField =
        new DataSourceTextField("transportType", "Transport Type");
    TextItem transportTypeItem = new TextItem();
    transportTypeItem.setWidth("100%");
    transportTypeField.setEditorType(transportTypeItem);

    DataSourceFloatField cO2MultiplierField =
        new DataSourceFloatField("CO2Multiplier", "CO2Multiplier");
    FloatItem cO2MultiplierItem = new FloatItem();
    cO2MultiplierField.setEditorType(cO2MultiplierItem);

    DataSourceTextField cO2MultiplierUnitField =
        new DataSourceTextField("CO2MultiplierUnit", "CO2MultiplierUnit");
    TextItem cO2MultiplierUnitItem = new TextItem();
    cO2MultiplierUnitItem.setWidth("100%");
    cO2MultiplierUnitField.setEditorType(cO2MultiplierUnitItem);

    DataSourceFloatField cH4MultiplierField =
        new DataSourceFloatField("CH4Multiplier", "CH4Multiplier");
    FloatItem cH4MultiplierItem = new FloatItem();
    cH4MultiplierField.setEditorType(cH4MultiplierItem);

    DataSourceTextField cH4MultiplierUnitField =
        new DataSourceTextField("CH4MultiplierUnit", "CH4MultiplierUnit");
    TextItem cH4MultiplierUnitItem = new TextItem();
    cH4MultiplierUnitItem.setWidth("100%");
    cH4MultiplierUnitField.setEditorType(cH4MultiplierUnitItem);

    DataSourceFloatField n2OMultiplierField =
        new DataSourceFloatField("N2OMultiplier", "N2OMultiplier");
    FloatItem n2OMultiplierItem = new FloatItem();
    n2OMultiplierField.setEditorType(n2OMultiplierItem);

    DataSourceTextField n2OMultiplierUnitField =
        new DataSourceTextField("N2OMultiplierUnit", "N2OMultiplierUnit");
    TextItem n2OMultiplierUnitItem = new TextItem();
    n2OMultiplierUnitItem.setWidth("100%");
    n2OMultiplierUnitField.setEditorType(n2OMultiplierUnitItem);

    setFields(idField, transportTypeField, cO2MultiplierField,cO2MultiplierUnitField, cH4MultiplierField,cH4MultiplierUnitField, n2OMultiplierField, n2OMultiplierUnitField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"EF_ProductTransportType_EPA/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"EF_ProductTransportType_EPA/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"EF_ProductTransportType_EPA/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"EF_ProductTransportType_EPA/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}