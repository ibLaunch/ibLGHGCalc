package org.ibLGHGCalc.client;

import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.form.fields.IntegerItem;

/**
 * Created by NetBeans
 * User: ibL
 * Date: Jan 16, 2012
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessingOfSoldProductsInfoDS_1 extends RestDataSource {
  private static ProcessingOfSoldProductsInfoDS_1 instance;

  public static ProcessingOfSoldProductsInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new ProcessingOfSoldProductsInfoDS_1();
    }
    return instance;
  }

  private ProcessingOfSoldProductsInfoDS_1() {
    //set id + general stuff
    setID("processingOfSoldProductsInfoDS_1");
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
	
    DataSourceTextField sourceDescriptionField = new DataSourceTextField("sourceDescription", "Source Description");
    DataSourceTextField soldProductName = new DataSourceTextField("soldProductName", "Sold Product Name");
    DataSourceTextField productSoldTo = new DataSourceTextField("productSoldTo", "Product Sold To");
    DataSourceTextField productSoldToContact = new DataSourceTextField("productSoldToContact", "Product Sold To Contact");

    DataSourceTextField fuelType = new DataSourceTextField("fuelType", "Fuel Type");    	
    IblDataSourceFloatField fuelConsumed = new IblDataSourceFloatField("fuelConsumed", "Fuel Consumed");
    DataSourceTextField fuelConsumed_Unit = new DataSourceTextField("fuelConsumed_Unit", "Unit");
    IblDataSourceFloatField EF_Fuel = new IblDataSourceFloatField("EF_Fuel", "Fuel Emission Factor");
    DataSourceTextField EF_Fuel_Unit = new DataSourceTextField("EF_Fuel_Unit", "Unit");

    IblDataSourceFloatField electricityConsumed = new IblDataSourceFloatField("electricityConsumed", "Electricity Consumed");
    DataSourceTextField electricityConsumed_Unit = new DataSourceTextField("electricityConsumed_Unit", "Unit");
    IblDataSourceFloatField EF_Electricity = new IblDataSourceFloatField("EF_Electricity", "Electricity Emission Factor");
    DataSourceTextField EF_Electricity_Unit = new DataSourceTextField("EF_Electricity_Unit", "Unit");
    
    DataSourceTextField refrigerantType = new DataSourceTextField("refrigerantType", "Refrigerant Type");     
    IblDataSourceFloatField refrigerantLeakage = new IblDataSourceFloatField("refrigerantLeakage", "Refrigerant Leakage Quantity");
    DataSourceTextField refrigerantLeakage_Unit = new DataSourceTextField("refrigerantLeakage_Unit", "Unit");
    IblDataSourceFloatField EF_Refrigerant = new IblDataSourceFloatField("EF_Refrigerant", "Refrigerant Emission Factor");
    DataSourceTextField EF_Refrigerant_Unit = new DataSourceTextField("EF_Refrigerant_Unit", "Unit");

    IblDataSourceFloatField massOfWasteOutput = new IblDataSourceFloatField("massOfWasteOutput", "Mass Of Waste Output");
    DataSourceTextField massOfWasteOutput_Unit = new DataSourceTextField("massOfWasteOutput_Unit", "Unit");
    IblDataSourceFloatField EF_WasteActivity = new IblDataSourceFloatField("EF_WasteActivity", "Emission Factor for Waste Activity");
    DataSourceTextField EF_WasteActivity_Unit = new DataSourceTextField("EF_WasteActivity_Unit", "Unit");
                
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                soldProductName,productSoldTo,productSoldToContact,
                fuelType,fuelConsumed,fuelConsumed_Unit, EF_Fuel, EF_Fuel_Unit,
                electricityConsumed,electricityConsumed_Unit,EF_Electricity,EF_Electricity_Unit,
                refrigerantType,refrigerantLeakage,refrigerantLeakage_Unit, EF_Refrigerant, EF_Refrigerant_Unit,
                massOfWasteOutput,massOfWasteOutput_Unit,
                EF_WasteActivity,EF_WasteActivity_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"processingOfSoldProductsInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"processingOfSoldProductsInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"processingOfSoldProductsInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"processingOfSoldProductsInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}