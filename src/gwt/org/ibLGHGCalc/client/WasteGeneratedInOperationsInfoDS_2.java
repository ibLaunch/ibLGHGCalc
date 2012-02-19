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
public class WasteGeneratedInOperationsInfoDS_2 extends RestDataSource {
  private static WasteGeneratedInOperationsInfoDS_2 instance;

  public static WasteGeneratedInOperationsInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new WasteGeneratedInOperationsInfoDS_2();
    }
    return instance;
  }

  private WasteGeneratedInOperationsInfoDS_2() {
    //set id + general stuff
    setID("wasteGeneratedInOperationsInfoDS_2");
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
    DataSourceTextField serviceProviderName = new DataSourceTextField("serviceProviderName", "Service Provider Name");
    DataSourceTextField serviceProviderContact = new DataSourceTextField("serviceProviderContact", "Service Provider Contact");

    DataSourceTextField wasteType = new DataSourceTextField("wasteType", "Waste Type");    	
    DataSourceTextField wasteTreatmentType = new DataSourceTextField("wasteTreatmentType", "Waste Treatment Type");
    
    IblDataSourceFloatField wasteProduced = new IblDataSourceFloatField("wasteProduced", "Waste Produced");
    DataSourceTextField wasteProduced_Unit = new DataSourceTextField("wasteProduced_Unit", "Unit");
    
    IblDataSourceFloatField percentOfWasteTreatedByWasteTreatmentMethod = new IblDataSourceFloatField("percentOfWasteTreatedByWasteTreatmentMethod", "Proporation Of Waste Treated");
    
    IblDataSourceFloatField EF_WasteTreatmentMethod = new IblDataSourceFloatField("EF_WasteTreatmentMethod", "Waste Treatment Emission Factor");
    DataSourceTextField EF_WasteTreatmentMethod_Unit = new DataSourceTextField("EF_WasteTreatmentMethod_Unit", "Unit");
                    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                serviceProviderName,serviceProviderContact,
                wasteType,wasteTreatmentType,wasteProduced,wasteProduced_Unit,
                percentOfWasteTreatedByWasteTreatmentMethod,
                EF_WasteTreatmentMethod,EF_WasteTreatmentMethod_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"wasteGeneratedInOperationsInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"wasteGeneratedInOperationsInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"wasteGeneratedInOperationsInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"wasteGeneratedInOperationsInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}