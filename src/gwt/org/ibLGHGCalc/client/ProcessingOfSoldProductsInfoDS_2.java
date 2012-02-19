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
public class ProcessingOfSoldProductsInfoDS_2 extends RestDataSource {
  private static ProcessingOfSoldProductsInfoDS_2 instance;

  public static ProcessingOfSoldProductsInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new ProcessingOfSoldProductsInfoDS_2();
    }
    return instance;
  }

  private ProcessingOfSoldProductsInfoDS_2() {
    //set id + general stuff
    setID("processingOfSoldProductsInfoDS_2");
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

    IblDataSourceFloatField massOfSoldIntermediateProduct = new IblDataSourceFloatField("massOfSoldIntermediateProduct", "Mass Of Sold Product");
    DataSourceTextField massOfSoldIntermediateProduct_Unit = new DataSourceTextField("massOfSoldIntermediateProduct_Unit", "Unit");
    IblDataSourceFloatField EF_ProcessingOfSoldProducts = new IblDataSourceFloatField("EF_ProcessingOfSoldProducts", "Emission Factor for Processing Of Sold Products");
    DataSourceTextField EF_ProcessingOfSoldProducts_Unit = new DataSourceTextField("EF_ProcessingOfSoldProducts_Unit", "Unit");
                
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                soldProductName,productSoldTo,productSoldToContact,
                massOfSoldIntermediateProduct,massOfSoldIntermediateProduct_Unit,
                EF_ProcessingOfSoldProducts,EF_ProcessingOfSoldProducts_Unit,
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