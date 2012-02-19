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
public class InDirectUsePhaseEmissionsInfoDS_1 extends RestDataSource {
  private static InDirectUsePhaseEmissionsInfoDS_1 instance;

  public static InDirectUsePhaseEmissionsInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new InDirectUsePhaseEmissionsInfoDS_1();
    }
    return instance;
  }

  private InDirectUsePhaseEmissionsInfoDS_1() {
    //set id + general stuff
    setID("inDirectUsePhaseEmissionsInfoDS_1");
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
    DataSourceTextField scenarioDescription = new DataSourceTextField("scenarioDescription", "Scenario Description");
    IblDataSourceFloatField percentOfTotalLifetimeUsesInThisScenario = new IblDataSourceFloatField("percentOfTotalLifetimeUsesInThisScenario", "Percent Of Total Lifetime Uses In This Scenario");
    IblDataSourceFloatField numberSoldInReportingPeriod = new IblDataSourceFloatField("numberSoldInReportingPeriod", "Number of Product Sold In Reporting Period");
    
    DataSourceTextField fuelType = new DataSourceTextField("fuelType", "Fuel Type");     
    IblDataSourceFloatField fuelConsumedPerUseInThisScenario = new IblDataSourceFloatField("fuelConsumedPerUseInThisScenario", "Fuel Consumed Per Use In This Scenario");
    DataSourceTextField fuelConsumedPerUseInThisScenario_Unit = new DataSourceTextField("fuelConsumedPerUseInThisScenario_Unit", "Unit");
    IblDataSourceFloatField EF_Fuel = new IblDataSourceFloatField("EF_Fuel", "Fuel Emission Factor");
    DataSourceTextField EF_Fuel_Unit = new DataSourceTextField("EF_Fuel_Unit", "Unit");

    IblDataSourceFloatField electricityConsumedPerUseInThisScenario = new IblDataSourceFloatField("electricityConsumedPerUseInThisScenario", "Electricity Consumed Per Use In This Scenario");
    DataSourceTextField electricityConsumedPerUseInThisScenario_Unit = new DataSourceTextField("electricityConsumedPerUseInThisScenario_Unit", "Unit");
    IblDataSourceFloatField EF_Electricity = new IblDataSourceFloatField("EF_Electricity", "Electricity Emission Factor");
    DataSourceTextField EF_Electricity_Unit = new DataSourceTextField("EF_Electricity_Unit", "Unit");
    
    DataSourceTextField refrigerantType = new DataSourceTextField("refrigerantType", "Refrigerant Type");     
    IblDataSourceFloatField refrigerantLeakagePerUseInThisScenario = new IblDataSourceFloatField("refrigerantLeakagePerUseInThisScenario", "Refrigerant Leakage Quantity Per Use In This Scenario");
    DataSourceTextField refrigerantLeakagePerUseInThisScenario_Unit = new DataSourceTextField("refrigerantLeakagePerUseInThisScenario_Unit", "Unit");
    IblDataSourceFloatField EF_Refrigerant = new IblDataSourceFloatField("EF_Refrigerant", "Refrigerant Emission Factor");
    DataSourceTextField EF_Refrigerant_Unit = new DataSourceTextField("EF_Refrigerant_Unit", "Unit");

    IblDataSourceFloatField GHG_EmittedIndirectly = new IblDataSourceFloatField("GHG_EmittedIndirectly", "GHG Emitted Indirectly");
    DataSourceTextField GHG_EmittedIndirectly_Unit = new DataSourceTextField("GHG_EmittedIndirectly_Unit", "Unit");
    
    DataSourceTextField GHG_Name = new DataSourceTextField("GHG_Name", "Greenhouse Gas Name");
    IblDataSourceFloatField GWP_GHG = new IblDataSourceFloatField("GWP_GHG", "Global Warming Potential for Greenhouse Gas");
    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, scenarioDescription,
                productType, productName,
                totalLifetimeExpectedUsesOfProduct,percentOfTotalLifetimeUsesInThisScenario,numberSoldInReportingPeriod,
                fuelType,fuelConsumedPerUseInThisScenario,fuelConsumedPerUseInThisScenario_Unit, EF_Fuel, EF_Fuel_Unit,
                electricityConsumedPerUseInThisScenario,electricityConsumedPerUseInThisScenario_Unit,EF_Electricity,EF_Electricity_Unit,
                refrigerantType,refrigerantLeakagePerUseInThisScenario,refrigerantLeakagePerUseInThisScenario_Unit, EF_Refrigerant, EF_Refrigerant_Unit,
                GHG_EmittedIndirectly,GHG_EmittedIndirectly_Unit,GHG_Name,GWP_GHG,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"inDirectUsePhaseEmissionsForSoldProductsInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"inDirectUsePhaseEmissionsForSoldProductsInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"inDirectUsePhaseEmissionsForSoldProductsInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"inDirectUsePhaseEmissionsForSoldProductsInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}