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
 * Date: Dec 26, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PurchasedProductInfoDS_1 extends RestDataSource {
  private static PurchasedProductInfoDS_1 instance;

  public static PurchasedProductInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new PurchasedProductInfoDS_1();
    }
    return instance;
  }

  private PurchasedProductInfoDS_1() {
    //set id + general stuff
    setID("purchasedProductInfoDS_1");
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
    
    DataSourceTextField supplierNameField = new DataSourceTextField("supplierName", "Supplier Name");
    DataSourceTextField supplierContactField = new DataSourceTextField("supplierContact", "Supplier Contact");
    DataSourceTextField purchasedProductTypeField = new DataSourceTextField("purchasedProductType", "Product Type");
    DataSourceTextField purchasedProductNameField = new DataSourceTextField("purchasedProductName", "Product Name");

    IblDataSourceFloatField quantityOfPurchasedProductField = new IblDataSourceFloatField("quantityOfPurchasedProduct", "Quantity");
    DataSourceTextField quantityOfPurchasedProduct_UnitField = new DataSourceTextField("quantityOfPurchasedProduct_Unit", "Unit");

    IblDataSourceFloatField supplierSpecific_EF_ForPurchasedProductField = new IblDataSourceFloatField("supplierSpecific_EF_ForPurchasedProduct", "Supplier Specific Emission Factor");
    DataSourceTextField supplierSpecific_EF_ForPurchasedProduct_UnitField = new DataSourceTextField("supplierSpecific_EF_ForPurchasedProduct_Unit", "Unit");

    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, supplierNameField, supplierContactField, purchasedProductTypeField, 
				purchasedProductNameField, quantityOfPurchasedProductField, quantityOfPurchasedProduct_UnitField,
				supplierSpecific_EF_ForPurchasedProductField, supplierSpecific_EF_ForPurchasedProduct_UnitField, 
				userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"purchasedProductInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"purchasedProductInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"purchasedProductInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"purchasedProductInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}