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
public class DistributionInfoDS_2 extends RestDataSource {
  private static DistributionInfoDS_2 instance;

  public static DistributionInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new DistributionInfoDS_2();
    }
    return instance;
  }

  private DistributionInfoDS_2() {
    //set id + general stuff
    setID("distributionInfoDS_2");
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

    DataSourceTextField streamType = new DataSourceTextField("streamType", "Upstream/Downstream");    	
    DataSourceTextField serviceProviderName = new DataSourceTextField("serviceProviderName", "Service Provider Name");
    DataSourceTextField serviceProviderContact = new DataSourceTextField("serviceProviderContact", "Service Provider Contact");
    DataSourceTextField storageFacility = new DataSourceTextField("storageFacility", "Storage Facility");
        
    IblDataSourceFloatField storedGoodsInReportingYear = new IblDataSourceFloatField("storedGoodsInReportingYear", "Stored Goods");
    DataSourceTextField storedGoodsInReportingYear_Unit = new DataSourceTextField("storedGoodsInReportingYear_Unit", "Unit");
    IblDataSourceFloatField EF_StorageFacility = new IblDataSourceFloatField("EF_StorageFacility", "Storage Facility Emission Factor");
    DataSourceTextField EF_StorageFacility_Unit = new DataSourceTextField("EF_StorageFacility_Unit", "Unit");
            
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                streamType, serviceProviderName, serviceProviderContact,storageFacility,
                storedGoodsInReportingYear,storedGoodsInReportingYear_Unit,
                EF_StorageFacility,EF_StorageFacility_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"distributionInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"distributionInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"distributionInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"distributionInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}