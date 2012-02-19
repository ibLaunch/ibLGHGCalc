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
public class TransportationInfoDS_2 extends RestDataSource {
  private static TransportationInfoDS_2 instance;

  public static TransportationInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new TransportationInfoDS_2();
    }
    return instance;
  }

  private TransportationInfoDS_2() {
    //set id + general stuff
    setID("transportationInfoDS_2");
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
    
    DataSourceTextField tansportModeOrVehicleType = new DataSourceTextField("transportModeOrVehicleType", "Transport Mode/Vehicle Type");    	

    IblDataSourceFloatField massOfGoodsPurchased = new IblDataSourceFloatField("massOfGoodsPurchased", "Goods Purchased");
    DataSourceTextField massOfGoodsPurchased_Unit = new DataSourceTextField("massOfGoodsPurchased_Unit", "Unit");

    IblDataSourceFloatField distanceTraveledInTransportLeg = new IblDataSourceFloatField("distanceTraveledInTransportLeg", "Distance Transported");
    DataSourceTextField distanceTraveledInTransportLeg_Unit = new DataSourceTextField("distanceTraveledInTransportLeg_Unit", "Unit");
    
    IblDataSourceFloatField EF_TransportModeOrVehicleType = new IblDataSourceFloatField("EF_TransportModeOrVehicleType", "Emission Factor");
    DataSourceTextField EF_TransportModeOrVehicleType_Unit = new DataSourceTextField("EF_TransportModeOrVehicleType_Unit", "Unit");
    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                streamType, serviceProviderName, serviceProviderContact,
                tansportModeOrVehicleType,
                massOfGoodsPurchased,massOfGoodsPurchased_Unit,
                distanceTraveledInTransportLeg,distanceTraveledInTransportLeg_Unit,
                EF_TransportModeOrVehicleType, EF_TransportModeOrVehicleType_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"transportationInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"transportationInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"transportationInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"transportationInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}