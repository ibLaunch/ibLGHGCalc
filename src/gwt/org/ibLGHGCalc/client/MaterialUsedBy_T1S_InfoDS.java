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
 * Date: Jan 2, 2012
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class MaterialUsedBy_T1S_InfoDS extends RestDataSource {
  private static MaterialUsedBy_T1S_InfoDS instance;

  public static MaterialUsedBy_T1S_InfoDS getInstance() {
    if (instance == null) {
      instance = new MaterialUsedBy_T1S_InfoDS();
    }
    return instance;
  }

  private MaterialUsedBy_T1S_InfoDS() {
    //set id + general stuff
    setID("materialUsedBy_T1S_InfoDS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);
    idField.setHidden(Boolean.TRUE);

    DataSourceIntegerField purchasedProductInfoIdField =
        new DataSourceIntegerField("purchasedProductInfoId", "Purchased Product Info Id");
    IntegerItem purchasedProductInfoIdItem = new IntegerItem();
    purchasedProductInfoIdField.setCanEdit(false);
    purchasedProductInfoIdField.setForeignKey("purchasedProductInfoDS_2.id");
    purchasedProductInfoIdField.setEditorType(purchasedProductInfoIdItem);
    purchasedProductInfoIdField.setHidden(Boolean.TRUE);

    DataSourceTextField materialUsedBy_T1S_ForPurchasedProduct = new DataSourceTextField("materialUsedBy_T1S_ForPurchasedProduct", "Material Used");    
    IblDataSourceFloatField amountOfMaterialUsedBy_T1S_ForPurchasedProduct = new IblDataSourceFloatField("amountOfMaterialUsedBy_T1S_ForPurchasedProduct", "Material Quantity Used");
    DataSourceTextField amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit = new DataSourceTextField("amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit", "Unit");
    IblDataSourceFloatField EF_Material = new IblDataSourceFloatField("EF_Material", "Emission Factor for Material");
    DataSourceTextField EF_Material_Unit = new DataSourceTextField("EF_Material_Unit", "Unit");
   
    //--General fields
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");    
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, purchasedProductInfoIdField,
                materialUsedBy_T1S_ForPurchasedProduct,
                amountOfMaterialUsedBy_T1S_ForPurchasedProduct,amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit,
                EF_Material,EF_Material_Unit,
        	userNotesOnDataField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"materialUsedBy_T1S_Info/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"materialUsedBy_T1S_Info/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"materialUsedBy_T1S_Info/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"materialUsedBy_T1S_Info/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}