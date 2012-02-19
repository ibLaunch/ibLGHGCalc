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
public class MaterialTransportTo_T1S_InfoDS extends RestDataSource {
  private static MaterialTransportTo_T1S_InfoDS instance;

  public static MaterialTransportTo_T1S_InfoDS getInstance() {
    if (instance == null) {
      instance = new MaterialTransportTo_T1S_InfoDS();
    }
    return instance;
  }

  private MaterialTransportTo_T1S_InfoDS() {
    //set id + general stuff
    setID("materialTransportTo_T1S_InfoDS");
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
    IblDataSourceFloatField distanceOfTransportOfMaterialInputsTo_T1S = new IblDataSourceFloatField("distanceOfTransportOfMaterialInputsTo_T1S", "Distance of Transport");
    DataSourceTextField distanceOfTransportOfMaterialInputsTo_T1S_Unit = new DataSourceTextField("distanceOfTransportOfMaterialInputsTo_T1S_Unit", "Unit");
    IblDataSourceFloatField massOfMateriaInput = new IblDataSourceFloatField("massOfMateriaInput", "Mass of Material");
    DataSourceTextField massOfMateriaInput_Unit = new DataSourceTextField("massOfMateriaInput_Unit", "Unit");
    DataSourceTextField vehicleType = new DataSourceTextField("vehicleType", "Vehicle Type");
    IblDataSourceFloatField EF_VehicleType = new IblDataSourceFloatField("EF_VehicleType", "Emission Factor for Vehicle");
    DataSourceTextField EF_VehicleType_Unit = new DataSourceTextField("EF_VehicleType_Unit", "Unit");
   
    //--General fields
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");    
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, purchasedProductInfoIdField,
                materialUsedBy_T1S_ForPurchasedProduct,
				distanceOfTransportOfMaterialInputsTo_T1S,distanceOfTransportOfMaterialInputsTo_T1S_Unit,
				massOfMateriaInput,massOfMateriaInput_Unit,
				vehicleType,EF_VehicleType,EF_VehicleType_Unit,				
        	userNotesOnDataField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"materialTransportTo_T1S_Info/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"materialTransportTo_T1S_Info/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"materialTransportTo_T1S_Info/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"materialTransportTo_T1S_Info/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}