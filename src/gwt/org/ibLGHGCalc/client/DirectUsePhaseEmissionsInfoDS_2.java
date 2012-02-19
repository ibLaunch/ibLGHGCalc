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
public class DirectUsePhaseEmissionsInfoDS_2 extends RestDataSource {
  private static DirectUsePhaseEmissionsInfoDS_2 instance;

  public static DirectUsePhaseEmissionsInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new DirectUsePhaseEmissionsInfoDS_2();
    }
    return instance;
  }

  private DirectUsePhaseEmissionsInfoDS_2() {
    //set id + general stuff
    setID("directUsePhaseEmissionsInfoDS_2");
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
    IblDataSourceFloatField totalQuantityOfFuelOrFeedstockSold = new IblDataSourceFloatField("totalQuantityOfFuelOrFeedstockSold", "Total Quantity Of Fuel/Feedstock Sold");
    DataSourceTextField totalQuantityOfFuelOrfeedstockSold_Unit = new DataSourceTextField("totalQuantityOfFuelOrfeedstockSold_Unit", "Unit");
    
    IblDataSourceFloatField combustion_EF_ForFuelOrFeedstock = new IblDataSourceFloatField("combustion_EF_ForFuelOrFeedstock", "Combustion Emission Factor ForFuel/Feedstock");
    DataSourceTextField combustion_EF_ForFuelOrFeedstock_Unit = new DataSourceTextField("combustion_EF_ForFuelOrFeedstock_Unit", "Unit");
                
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                productType, productName,
                totalQuantityOfFuelOrFeedstockSold,totalQuantityOfFuelOrfeedstockSold_Unit,
                combustion_EF_ForFuelOrFeedstock,combustion_EF_ForFuelOrFeedstock_Unit,
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