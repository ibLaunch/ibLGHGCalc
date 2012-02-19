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
public class BusinessTravelInfoDS extends RestDataSource {
  private static BusinessTravelInfoDS instance;

  public static BusinessTravelInfoDS getInstance() {
    if (instance == null) {
      instance = new BusinessTravelInfoDS();
    }
    return instance;
  }

  private BusinessTravelInfoDS() {
    //set id + general stuff
    setID("businessTravelInfoDS");
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

    DataSourceTextField activityType = new DataSourceTextField("activityType", "Activity Type");    	
    DataSourceTextField vehicleType = new DataSourceTextField("vehicleType", "Vehicle Type");
    
    IblDataSourceFloatField distanceTravelledByVehicleType = new IblDataSourceFloatField("distanceTravelledByVehicleType", "Distance");
    DataSourceTextField distanceTravelledByVehicleType_Unit = new DataSourceTextField("distanceTravelledByVehicleType_Unit", "Unit");
    IblDataSourceFloatField EF_VehicleType = new IblDataSourceFloatField("EF_VehicleType", "Vehicle Type Emission Factor");
    DataSourceTextField EF_VehicleType_Unit = new DataSourceTextField("EF_VehicleType_Unit", "Unit");

    IblDataSourceFloatField annualNumberOfHotelNights = new IblDataSourceFloatField("annualNumberOfHotelNights", "Hotel Nights");
    DataSourceTextField EF_Hotel = new DataSourceTextField("EF_Hotel", "Emission Factor for Hotel");
    DataSourceTextField EF_Hotel_Unit = new DataSourceTextField("EF_Hotel_Unit", "Unit");
                    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                activityType, vehicleType, 
                distanceTravelledByVehicleType,distanceTravelledByVehicleType_Unit,
                EF_VehicleType, EF_VehicleType_Unit,
                annualNumberOfHotelNights,EF_Hotel,EF_Hotel_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"businessTravelInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"businessTravelInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"businessTravelInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"businessTravelInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}