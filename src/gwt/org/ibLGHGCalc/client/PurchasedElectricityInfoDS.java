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
public class PurchasedElectricityInfoDS extends RestDataSource {
  private static PurchasedElectricityInfoDS instance;

  public static PurchasedElectricityInfoDS getInstance() {
    if (instance == null) {
      instance = new PurchasedElectricityInfoDS();
    }
    return instance;
  }

  private PurchasedElectricityInfoDS() {
    //set id + general stuff
    setID("purchasedElectricityInfoDS");
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

    DataSourceTextField eGRIDSubregionField =
        new DataSourceTextField("eGRIDSubregion", "eGRID Subregion");
    TextItem eGRIDSubregionItem = new TextItem();
    eGRIDSubregionItem.setWidth("100%");
    eGRIDSubregionField.setEditorType(eGRIDSubregionItem);

    DataSourceFloatField purchasedElectricityField =
        new DataSourceFloatField("purchasedElectricity", "Purchased Electricity");
    //FloatItem purchasedElectricityItem = new FloatItem();
    //purchasedElectricityField.setEditorType(purchasedElectricityItem);
    purchasedElectricityField.setType(ibLUsers.floatSimpleType);
    
    DataSourceTextField purchasedElectricityUnitField =
        new DataSourceTextField("purchasedElectricityUnit", "Purchased Electricity Unit");
    TextItem purchasedElectricityUnitItem = new TextItem();
    purchasedElectricityUnitItem.setWidth("100%");
    purchasedElectricityUnitField.setEditorType(purchasedElectricityUnitItem);

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

    setFields(idField, organizationIdField, sourceDescriptionField, eGRIDSubregionField, purchasedElectricityField, purchasedElectricityUnitField, fuelUsedBeginDateField, fuelUsedEndDateField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"purchasedElectricityInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"purchasedElectricityInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"purchasedElectricityInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"purchasedElectricityInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}