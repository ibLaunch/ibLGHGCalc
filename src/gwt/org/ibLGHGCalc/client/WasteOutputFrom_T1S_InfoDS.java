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
 * Date: Jan 12, 2012
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class WasteOutputFrom_T1S_InfoDS extends RestDataSource {
  private static WasteOutputFrom_T1S_InfoDS instance;

  public static WasteOutputFrom_T1S_InfoDS getInstance() {
    if (instance == null) {
      instance = new WasteOutputFrom_T1S_InfoDS();
    }
    return instance;
  }

  private WasteOutputFrom_T1S_InfoDS() {
    //set id + general stuff
    setID("wasteOutputFrom_T1S_InfoDS");
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
    
    DataSourceTextField sourceDescriptionField = new DataSourceTextField("sourceDescription", "Source Description");
		
    IblDataSourceFloatField massOfWasteFrom_T1S_ForPurchasedProduct = new IblDataSourceFloatField("massOfWasteFrom_T1S_ForPurchasedProduct", "Waste Quantity");
    DataSourceTextField massOfWasteFrom_T1S_ForPurchasedProduct_Unit = new DataSourceTextField("massOfWasteFrom_T1S_ForPurchasedProduct_Unit", "Unit");
    IblDataSourceFloatField EF_WasteActivity = new IblDataSourceFloatField("EF_WasteActivity", "Emission Factor for Waste Activity");
    DataSourceTextField EF_WasteActivity_Unit = new DataSourceTextField("EF_WasteActivity_Unit", "Unit");
   
    //--General fields
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");    
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, purchasedProductInfoIdField,
                sourceDescriptionField,
				massOfWasteFrom_T1S_ForPurchasedProduct,massOfWasteFrom_T1S_ForPurchasedProduct_Unit,
				EF_WasteActivity,EF_WasteActivity_Unit,
        	userNotesOnDataField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"wasteOutputFrom_T1S_Info/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"wasteOutputFrom_T1S_Info/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"wasteOutputFrom_T1S_Info/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"wasteOutputFrom_T1S_Info/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}