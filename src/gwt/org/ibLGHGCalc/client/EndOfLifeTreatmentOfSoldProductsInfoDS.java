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
public class EndOfLifeTreatmentOfSoldProductsInfoDS extends RestDataSource {
  private static EndOfLifeTreatmentOfSoldProductsInfoDS instance;

  public static EndOfLifeTreatmentOfSoldProductsInfoDS getInstance() {
    if (instance == null) {
      instance = new EndOfLifeTreatmentOfSoldProductsInfoDS();
    }
    return instance;
  }

  private EndOfLifeTreatmentOfSoldProductsInfoDS() {
    //set id + general stuff
    setID("endOfLifeTreatmentOfSoldProductsInfoDS");
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

    IblDataSourceFloatField massOfSoldProductsAfterConsumerUse = new IblDataSourceFloatField("massOfSoldProductsAfterConsumerUse", "Mass Of Sold Products After Consumer Use");
    DataSourceTextField massOfSoldProductsAfterConsumerUse_Unit = new DataSourceTextField("massOfSoldProductsAfterConsumerUse_Unit", "Unit");
    
    IblDataSourceFloatField percentOfWasteTreatedByWasteTreatmentMethod = new IblDataSourceFloatField("percentOfWasteTreatedByWasteTreatmentMethod", "Proporation Of Waste Treated");
    
    DataSourceTextField wasteType = new DataSourceTextField("wasteType", "Waste Type");    	
    DataSourceTextField wasteTreatmentType = new DataSourceTextField("wasteTreatmentType", "Waste Treatment Type");
           
    IblDataSourceFloatField EF_WasteTreatmentMethod = new IblDataSourceFloatField("EF_WasteTreatmentMethod", "Waste Treatment Emission Factor");
    DataSourceTextField EF_WasteTreatmentMethod_Unit = new DataSourceTextField("EF_WasteTreatmentMethod_Unit", "Unit");
                    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                soldProductName,
                massOfSoldProductsAfterConsumerUse,massOfSoldProductsAfterConsumerUse_Unit,
                percentOfWasteTreatedByWasteTreatmentMethod,
                wasteType,wasteTreatmentType,
                EF_WasteTreatmentMethod,EF_WasteTreatmentMethod_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"endOfLifeTreatmentOfSoldProductsInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"endOfLifeTreatmentOfSoldProductsInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"endOfLifeTreatmentOfSoldProductsInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"endOfLifeTreatmentOfSoldProductsInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);    
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}