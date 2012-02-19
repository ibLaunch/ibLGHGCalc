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
public class DirectUsePhaseEmissionsInfoDS_3 extends RestDataSource {
  private static DirectUsePhaseEmissionsInfoDS_3 instance;

  public static DirectUsePhaseEmissionsInfoDS_3 getInstance() {
    if (instance == null) {
      instance = new DirectUsePhaseEmissionsInfoDS_3();
    }
    return instance;
  }

  private DirectUsePhaseEmissionsInfoDS_3() {
    //set id + general stuff
    setID("directUsePhaseEmissionsInfoDS_3");
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
    
    DataSourceTextField GHG_Name = new DataSourceTextField("GHG_Name", "Greenhouse Gas Name");
    IblDataSourceFloatField numberSoldInReportingPeriod = new IblDataSourceFloatField("numberSoldInReportingPeriod", "Number Sold In Reporting Period");
            
    IblDataSourceFloatField GHG_PerProduct = new IblDataSourceFloatField("GHG_PerProduct", "Greenhouse Gas Per Product");
    IblDataSourceFloatField percentOfGHGReleasedDuringLifetimeUseOfProduct = new IblDataSourceFloatField("percentOfGHGReleasedDuringLifetimeUseOfProduct", "% Of GHG Released During Lifetime Use Of Product");    
    IblDataSourceFloatField GWP_GHG = new IblDataSourceFloatField("GWP_GHG", "Global Warming Potential for Greenhouse Gas");
                    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                productType, productName,
                GHG_Name,numberSoldInReportingPeriod,
                GHG_PerProduct,percentOfGHGReleasedDuringLifetimeUseOfProduct,GWP_GHG,
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