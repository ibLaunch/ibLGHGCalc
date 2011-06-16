package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Created by NetBeans
 * User: ibL
 * Date: Jan 6, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PurchasedSteamInfoDS extends RestDataSource {
  private static PurchasedSteamInfoDS instance;

  public static PurchasedSteamInfoDS getInstance() {
    if (instance == null) {
      instance = new PurchasedSteamInfoDS();
    }
    return instance;
  }

  private PurchasedSteamInfoDS() {
    //set id + general stuff
    setID("purchasedSteamInfoDS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);
    idField.setHidden(Boolean.TRUE);

    DataSourceIntegerField organizationIdField =
        new DataSourceIntegerField("organizationId", "Organization Id");
    IntegerItem organizationIdItem = new IntegerItem();
    organizationIdField.setCanEdit(false);
    organizationIdField.setForeignKey("organizationDS.id");
    organizationIdField.setEditorType(organizationIdItem);
    organizationIdField.setHidden(Boolean.TRUE);

    DataSourceTextField sourceDescriptionField =
        new DataSourceTextField("sourceDescription", "Source Description");
    TextItem sourceDescriptionItem = new TextItem();
    sourceDescriptionItem.setWidth("100%");
    sourceDescriptionField.setEditorType(sourceDescriptionItem);

    DataSourceTextField fuelTypeField =
        new DataSourceTextField("fuelType", "Fuel Type");
    TextItem fuelTypeItem = new TextItem();
    fuelTypeItem.setWidth("100%");
    fuelTypeField.setEditorType(fuelTypeItem);

    DataSourceFloatField boilerEfficiencyPercentField =
        new DataSourceFloatField("boilerEfficiencyPercent", "Boiler Efficiency Percent");
    //FloatItem boilerEfficiencyPercentItem = new FloatItem();
    //boilerEfficiencyPercentField.setEditorType(boilerEfficiencyPercentItem);
    boilerEfficiencyPercentField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField purchasedSteamField =
        new DataSourceFloatField("purchasedSteam", "Purchased Steam");
    //FloatItem purchasedSteamItem = new FloatItem();
    //purchasedSteamField.setEditorType(purchasedSteamItem);
    purchasedSteamField.setType(ibLUsers.floatSimpleType);

    DataSourceTextField purchasedSteamUnitField =
        new DataSourceTextField("purchasedSteamUnit", "Purchased Steam Unit");
    TextItem purchasedSteamUnitItem = new TextItem();
    purchasedSteamUnitItem.setWidth("100%");
    purchasedSteamUnitField.setEditorType(purchasedSteamUnitItem);

    DataSourceFloatField supplierCO2MultiplierField =
        new DataSourceFloatField("supplierCO2Multiplier", "supplier CO2 Emission Factor");
    //FloatItem supplierCO2MultiplierItem = new FloatItem();
    //supplierCO2MultiplierField.setEditorType(supplierCO2MultiplierItem);
    supplierCO2MultiplierField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField supplierCH4MultiplierField =
        new DataSourceFloatField("supplierCH4Multiplier", "supplier CH4 Emission Factor");
    //FloatItem supplierCH4MultiplierItem = new FloatItem();
    //supplierCH4MultiplierField.setEditorType(supplierCH4MultiplierItem);
    supplierCH4MultiplierField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField supplierN2OMultiplierField =
        new DataSourceFloatField("supplierN2OMultiplier", "supplier N2O Emission Factor");
    //FloatItem supplierN2OMultiplierItem = new FloatItem();
    //supplierN2OMultiplierField.setEditorType(supplierN2OMultiplierItem);
    supplierN2OMultiplierField.setType(ibLUsers.floatSimpleType);

    DataSourceTextField supplierCO2MultiplierUnitField =
        new DataSourceTextField("supplierCO2MultiplierUnit", "supplierCO2MultiplierUnit");
    TextItem supplierCO2MultiplierUnitItem = new TextItem();
    supplierCO2MultiplierUnitItem.setWidth("100%");
    supplierCO2MultiplierUnitField.setEditorType(supplierCO2MultiplierUnitItem);

    DataSourceTextField supplierCH4MultiplierUnitField =
        new DataSourceTextField("supplierCH4MultiplierUnit", "supplierCH4MultiplierUnit");
    TextItem supplierCH4MultiplierUnitItem = new TextItem();
    supplierCH4MultiplierUnitItem.setWidth("100%");
    supplierCH4MultiplierUnitField.setEditorType(supplierCH4MultiplierUnitItem);

    DataSourceTextField supplierN2OMultiplierUnitField =
        new DataSourceTextField("supplierN2OMultiplierUnit", "supplierN2OMultiplierUnit");
    TextItem supplierN2OMultiplierUnitItem = new TextItem();
    supplierN2OMultiplierUnitItem.setWidth("100%");
    supplierN2OMultiplierUnitField.setEditorType(supplierN2OMultiplierUnitItem);

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
    

    setFields(idField, organizationIdField, sourceDescriptionField, fuelTypeField,boilerEfficiencyPercentField, purchasedSteamField,
    			purchasedSteamUnitField, supplierCO2MultiplierField, supplierCO2MultiplierUnitField, supplierCH4MultiplierField,
    			supplierCH4MultiplierUnitField, supplierN2OMultiplierField, supplierN2OMultiplierUnitField, fuelUsedBeginDateField, fuelUsedEndDateField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"purchasedSteamInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"purchasedSteamInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"purchasedSteamInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"purchasedSteamInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}