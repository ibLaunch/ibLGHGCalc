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
public class InDirectUsePhaseEmissionsInfoDS_2 extends RestDataSource {
  private static InDirectUsePhaseEmissionsInfoDS_2 instance;

  public static InDirectUsePhaseEmissionsInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new InDirectUsePhaseEmissionsInfoDS_2();
    }
    return instance;
  }

  private InDirectUsePhaseEmissionsInfoDS_2() {
    //set id + general stuff
    setID("inDirectUsePhaseEmissionsInfoDS_2");
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
    DataSourceTextField scenarioDescription = new DataSourceTextField("scenarioDescription", "Scenario Description");

    DataSourceTextField productType = new DataSourceTextField("productType", "Product Type");    	
    DataSourceTextField productName = new DataSourceTextField("productName", "Product Name");
    
    IblDataSourceFloatField functionalUnitsPerformedPerProduct = new IblDataSourceFloatField("functionalUnitsPerformedPerProduct", "Functional Units Performed Per Product");
    IblDataSourceFloatField numberSoldInReportingPeriod = new IblDataSourceFloatField("numberSoldInReportingPeriod", "Number of Product Sold In Reporting Period");
    
    //DataSourceTextField fuelType = new DataSourceTextField("fuelType", "Fuel Type");     
    IblDataSourceFloatField emissionsPerFunctionalUnitOfProduct = new IblDataSourceFloatField("emissionsPerFunctionalUnitOfProduct", "Emissions Per Functional Unit Of Product");
    DataSourceTextField emissionsPerFunctionalUnitOfProduct_Unit = new DataSourceTextField("emissionsPerFunctionalUnitOfProduct_Unit", "Unit");
    IblDataSourceFloatField totalLifetimeEmissions = new IblDataSourceFloatField("totalLifetimeEmissions", "Total Lifetime Emissions");
    DataSourceTextField totalLifetimeEmissions_Unit = new DataSourceTextField("totalLifetimeEmissions_Unit", "Unit");
        
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");  
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, scenarioDescription,
                productType, productName,
                functionalUnitsPerformedPerProduct,numberSoldInReportingPeriod,
                emissionsPerFunctionalUnitOfProduct,emissionsPerFunctionalUnitOfProduct_Unit,
                totalLifetimeEmissions,totalLifetimeEmissions_Unit,
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