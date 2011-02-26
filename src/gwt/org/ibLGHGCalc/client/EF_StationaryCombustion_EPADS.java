package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Nov 16, 2010
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EF_StationaryCombustion_EPADS extends RestDataSource {
  private static EF_StationaryCombustion_EPADS instance;

  public static EF_StationaryCombustion_EPADS getInstance() {
    if (instance == null) {
      instance = new EF_StationaryCombustion_EPADS();
    }
    return instance;
  }

  private EF_StationaryCombustion_EPADS() {
    //set id + general stuff
    setID("eF_StationaryCombustion_EPADS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);

    //DataSourceIntegerField versionField =
    //    new DataSourceIntegerField("version", "Version");
    //versionField.setCanEdit(false);

    DataSourceTextField fuelTypeField =
    new DataSourceTextField("fuelType", "Fuel Type", 50, true);
    TextItem fuelTypeItem = new TextItem();
    fuelTypeItem.setWidth("100%");
    fuelTypeField.setEditorType(fuelTypeItem);

    DataSourceTextField fuelUnitField =
        new DataSourceTextField("fuelUnit", "Fuel Unit", 20, true);
    TextItem fuelUnitItem = new TextItem();
    fuelUnitItem.setWidth("100%");
    fuelUnitField.setEditorType(fuelUnitItem);

    DataSourceFloatField CO2MultiplierInKgField =
        new DataSourceFloatField("CO2MultiplierInKg", "CO2 Multiplier In Kg", 15, true);
    FloatItem CO2MultiplierInKgItem = new FloatItem();
    CO2MultiplierInKgField.setEditorType(CO2MultiplierInKgItem);

    DataSourceFloatField CH4MultiplierInGramField =
        new DataSourceFloatField("CH4MultiplierInGram", "CH4 Multiplier In Gram", 15, true);
    FloatItem CH4MultiplierInGramItem = new FloatItem();
    CH4MultiplierInGramField.setEditorType(CH4MultiplierInGramItem);

    DataSourceFloatField N2OMultiplierInGramField =
        new DataSourceFloatField("N20MultiplierInGram", "N20 Multiplier In Gram", 15, true);
    FloatItem N2OMultiplierInGramItem = new FloatItem();
    N2OMultiplierInGramField.setEditorType(N2OMultiplierInGramItem);

    DataSourceBooleanField isPublicField =
        new DataSourceBooleanField("isPublic", "Public", 0, false);
    setFields(idField, fuelTypeField, fuelUnitField, CO2MultiplierInKgField, CH4MultiplierInGramField, N2OMultiplierInGramField, isPublicField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
            new OperationBinding(DSOperationType.FETCH,"EF_StationaryCombustion_EPA/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"EF_StationaryCombustion_EPA/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"EF_StationaryCombustion_EPA/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"EF_StationaryCombustion_EPA/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
  }

}