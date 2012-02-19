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
public class DirectUsePhaseEmissionsInfoDS_1 extends RestDataSource {
  private static DirectUsePhaseEmissionsInfoDS_1 instance;

  public static DirectUsePhaseEmissionsInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new DirectUsePhaseEmissionsInfoDS_1();
    }
    return instance;
  }

  private DirectUsePhaseEmissionsInfoDS_1() {
    //set id + general stuff
    setID("directUsePhaseEmissionsInfoDS_1");
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

    DataSourceTextField productType = new DataSourceTextField("productType", "Product Type");    	
    DataSourceTextField productName = new DataSourceTextField("productName", "Product Name");
    IblDataSourceFloatField totalLifetimeExpectedUsesOfProduct = new IblDataSourceFloatField("totalLifetimeExpectedUsesOfProduct", "Total Lifetime Expected Uses Of Product");
    IblDataSourceFloatField numberSoldInReportingPeriod = new IblDataSourceFloatField("numberSoldInReportingPeriod", "Number Sold In Reporting Period");
    
    DataSourceTextField fuelType = new DataSourceTextField("fuelType", "Fuel Type");     
    IblDataSourceFloatField fuelConsumedPerUse = new IblDataSourceFloatField("fuelConsumedPerUse", "Fuel Consumed Per Use");
    DataSourceTextField fuelConsumedPerUse_Unit = new DataSourceTextField("fuelConsumedPerUse_Unit", "Unit");
    IblDataSourceFloatField EF_Fuel = new IblDataSourceFloatField("EF_Fuel", "Fuel Emission Factor");
    DataSourceTextField EF_Fuel_Unit = new DataSourceTextField("EF_Fuel_Unit", "Unit");

    IblDataSourceFloatField electricityConsumedPerUse = new IblDataSourceFloatField("electricityConsumedPerUse", "Electricity Consumed Per Use");
    DataSourceTextField electricityConsumedPerUse_Unit = new DataSourceTextField("electricityConsumedPerUse_Unit", "Unit");
    IblDataSourceFloatField EF_Electricity = new IblDataSourceFloatField("EF_Electricity", "Electricity Emission Factor");
    DataSourceTextField EF_Electricity_Unit = new DataSourceTextField("EF_Electricity_Unit", "Unit");
    
    DataSourceTextField refrigerantType = new DataSourceTextField("refrigerantType", "Refrigerant Type");     
    IblDataSourceFloatField refrigerantLeakagePerUse = new IblDataSourceFloatField("refrigerantLeakagePerUse", "Refrigerant Leakage Quantity Per Use");
    DataSourceTextField refrigerantLeakagePerUse_Unit = new DataSourceTextField("refrigerantLeakagePerUse_Unit", "Unit");
    IblDataSourceFloatField EF_Refrigerant = new IblDataSourceFloatField("EF_Refrigerant", "Refrigerant Emission Factor");
    DataSourceTextField EF_Refrigerant_Unit = new DataSourceTextField("EF_Refrigerant_Unit", "Unit");
                
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                productType, productName,
                totalLifetimeExpectedUsesOfProduct,numberSoldInReportingPeriod,
                fuelType,fuelConsumedPerUse,fuelConsumedPerUse_Unit, EF_Fuel, EF_Fuel_Unit,
                electricityConsumedPerUse,electricityConsumedPerUse_Unit,EF_Electricity,EF_Electricity_Unit,
                refrigerantType,refrigerantLeakagePerUse,refrigerantLeakagePerUse_Unit, EF_Refrigerant, EF_Refrigerant_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"directUsePhaseEmissionsForSoldProductsInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"directUsePhaseEmissionsForSoldProductsInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"directUsePhaseEmissionsForSoldProductsInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"directUsePhaseEmissionsForSoldProductsInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}