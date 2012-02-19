package org.ibLGHGCalc.client;

import com.smartgwt.client.data.DataSourceField;
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
public class PurchasedProductInfoDS_2 extends RestDataSource {
  private static PurchasedProductInfoDS_2 instance;

  public static PurchasedProductInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new PurchasedProductInfoDS_2();
    }
    return instance;
  }

  private PurchasedProductInfoDS_2() {
    //set id + general stuff
    setID("purchasedProductInfoDS_2");
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
   
    IblDataSourceFloatField scope1EmissionsOf_T1S_ForPurchasedProduct = new IblDataSourceFloatField("scope1EmissionsOf_T1S_ForPurchasedProduct", "Scope1 Emissions");
    DataSourceTextField scope1EmissionsOf_T1S_ForPurchasedProduct_Unit = new DataSourceTextField("scope1EmissionsOf_T1S_ForPurchasedProduct_Unit", "Unit");    
    IblDataSourceFloatField scope2EmissionsOf_T1S_ForPurchasedProduct = new IblDataSourceFloatField("scope2EmissionsOf_T1S_ForPurchasedProduct", "Scope2 Emissions");
    DataSourceTextField scope2EmissionsOf_T1S_ForPurchasedProduct_Unit = new DataSourceTextField("scope2EmissionsOf_T1S_ForPurchasedProduct_Unit", "Unit");

    MaterialUsedBy_T1S_InfoDS materialUsedBy_T1S_InfoDataSource = MaterialUsedBy_T1S_InfoDS.getInstance();        
    DataSourceField materialUsedBy_T1S_InfoField = new DataSourceField();
    materialUsedBy_T1S_InfoField.setName("materialUsedBy_T1S_Info");
    materialUsedBy_T1S_InfoField.setTypeAsDataSource(materialUsedBy_T1S_InfoDataSource);
    materialUsedBy_T1S_InfoField.setMultiple(true);

    IblDataSourceFloatField distanceOfTransportOfMaterialInputsTo_T1S = new IblDataSourceFloatField("distanceOfTransportOfMaterialInputsTo_T1S", "Distance of transport for Material");
    DataSourceTextField distanceOfTransportOfMaterialInputsTo_T1S_Unit = new DataSourceTextField("distanceOfTransportOfMaterialInputsTo_T1S_Unit", "Unit");
    IblDataSourceFloatField massOfMateriaInput = new IblDataSourceFloatField("massOfMateriaInput", "Mass of Material");
    DataSourceTextField massOfMateriaInput_Unit = new DataSourceTextField("massOfMateriaInput_Unit", "Unit");
    DataSourceTextField vehicleType = new DataSourceTextField("vehicleType", "Vehicle Type");
    DataSourceTextField EF_VehicleType = new DataSourceTextField("EF_VehicleType", "Emission Factor for Vehicle Type");
    DataSourceTextField EF_VehicleType_Unit = new DataSourceTextField("EF_VehicleType_Unit", "Unit");
    
    IblDataSourceFloatField massOfWasteFrom_T1S_ForPurchasedProduct = new IblDataSourceFloatField("massOfWasteFrom_T1S_ForPurchasedProduct", "Mass of Waste");
    DataSourceTextField massOfWasteFrom_T1S_ForPurchasedProduct_Unit = new DataSourceTextField("massOfWasteFrom_T1S_ForPurchasedProduct_Unit", "Unit");
    IblDataSourceFloatField EF_WasteActivity = new IblDataSourceFloatField("EF_WasteActivity", "Emission Factor for Waste Activity");
    DataSourceTextField EF_WasteActivity_Unit = new DataSourceTextField("EF_WasteActivity_Unit", "Unit");
    
    //--General fields
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, supplierNameField, supplierContactField, purchasedProductTypeField, 
		purchasedProductNameField, quantityOfPurchasedProductField, quantityOfPurchasedProduct_UnitField,
                scope1EmissionsOf_T1S_ForPurchasedProduct, scope1EmissionsOf_T1S_ForPurchasedProduct_Unit,
                scope2EmissionsOf_T1S_ForPurchasedProduct,scope2EmissionsOf_T1S_ForPurchasedProduct_Unit,
                //materialUsedBy_T1S_InfoField,
                //distanceOfTransportOfMaterialInputsTo_T1S,distanceOfTransportOfMaterialInputsTo_T1S_Unit,
                //massOfMateriaInput,massOfMateriaInput_Unit,
                //vehicleType,EF_VehicleType,EF_VehicleType_Unit,
                //massOfWasteFrom_T1S_ForPurchasedProduct,massOfWasteFrom_T1S_ForPurchasedProduct_Unit,
                //EF_WasteActivity,EF_WasteActivity_Unit,
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