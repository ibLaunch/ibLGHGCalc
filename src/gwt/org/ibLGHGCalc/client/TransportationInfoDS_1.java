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
public class TransportationInfoDS_1 extends RestDataSource {
  private static TransportationInfoDS_1 instance;

  public static TransportationInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new TransportationInfoDS_1();
    }
    return instance;
  }

  private TransportationInfoDS_1() {
    //set id + general stuff
    setID("transportationInfoDS_1");
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
    DataSourceTextField serviceProviderName = new DataSourceTextField("serviceProviderName", "Service Provider");
    DataSourceTextField serviceProviderContact = new DataSourceTextField("serviceProviderContact", "Service Provider Contact");
    
    DataSourceTextField fuelDataType = new DataSourceTextField("fuelDataType", "What type of fuel related data you have?");     
    DataSourceTextField fuelType = new DataSourceTextField("fuelType", "Fuel Type");     
    IblDataSourceFloatField fuelConsumed = new IblDataSourceFloatField("fuelConsumed", "Fuel Consumed");
    DataSourceTextField fuelConsumed_Unit = new DataSourceTextField("fuelConsumed_Unit", "Unit");
    IblDataSourceFloatField EF_Fuel = new IblDataSourceFloatField("EF_Fuel", "Fuel Emission Factor");
    DataSourceTextField EF_Fuel_Unit = new DataSourceTextField("EF_Fuel_Unit", "Unit");

    DataSourceTextField refrigerantType = new DataSourceTextField("refrigerantType", "Refrigerant Type");     
    IblDataSourceFloatField refrigerantLeakage = new IblDataSourceFloatField("refrigerantLeakage", "Refrigerant Leakage Quantity");
    DataSourceTextField refrigerantLeakage_Unit = new DataSourceTextField("refrigerantLeakage_Unit", "Unit");
    IblDataSourceFloatField EF_Refrigerant = new IblDataSourceFloatField("EF_Refrigerant", "Refrigerant Emission Factor");
    DataSourceTextField EF_Refrigerant_Unit = new DataSourceTextField("EF_Refrigerant_Unit", "Unit");

    DataSourceBooleanField flag_calculateFuelConsumed =
        new DataSourceBooleanField("flag_calculateFuelConsumed", "Calculate Fuel consumed from distance travelled and vehicle used?");
    
    IblDataSourceFloatField totalDistanceTravelled = new IblDataSourceFloatField("totalDistanceTravelled", "Distance Travelled");
    DataSourceTextField totalDistanceTravelled_Unit = new DataSourceTextField("totalDistanceTravelled_Unit", "Unit");
    DataSourceTextField vehicleType = new DataSourceTextField("vehicleType", "Vehicle Type");
    //DataSourceTextField vehicleName = new DataSourceTextField("vehicleName", "Vehicle Name");    
    IblDataSourceFloatField fuelEfficiencyOfVehicle = new IblDataSourceFloatField("fuelEfficiencyOfVehicle", "Fuel Efficiency");
    DataSourceTextField fuelEfficiencyOfVehicle_Unit = new DataSourceTextField("fuelEfficiencyOfVehicle_Unit", "Unit");
    
    DataSourceBooleanField flag_calculateAllocatedFuelUse =
        new DataSourceBooleanField("flag_calculateAllocatedFuelUse", "Calculate Allocated Fuel Use?");
    
    IblDataSourceFloatField allocatedFuelUse = new IblDataSourceFloatField("allocatedFuelUse", "Allocated Fuel Use");
    DataSourceTextField allocatedFuelUse_Unit = new DataSourceTextField("allocatedFuelUse_Unit", "Unit");

    IblDataSourceFloatField totalFuelConsumed = new IblDataSourceFloatField("totalFuelConsumed", "Total Fuel Consumed");
    DataSourceTextField totalFuelConsumed_Unit = new DataSourceTextField("totalFuelConsumed_Unit", "Unit");

    IblDataSourceFloatField companyGoodsTransported = new IblDataSourceFloatField("companyGoodsTransported", "Company Goods Transported");
    DataSourceTextField companyGoodsTransported_Unit = new DataSourceTextField("companyGoodsTransported_Unit", "Unit");
    DataSourceTextField companyGoodsTransported_MassType = new DataSourceTextField("companyGoodsTransported_MassType", "Mass Type");

    IblDataSourceFloatField totalGoodsTransportedByCarrier = new IblDataSourceFloatField("totalGoodsTransportedByCarrier", "Total Goods Transported By Carrier");
    DataSourceTextField totalGoodsTransportedByCarrier_Unit = new DataSourceTextField("totalGoodsTransportedByCarrier_Unit", "Unit");
    DataSourceTextField totalGoodsTransportedByCarrier_MassType = new DataSourceTextField("totalGoodsTransportedByCarrier_MassType", "Mass Type");
    
    DataSourceTextField massType =
        new DataSourceTextField("massType", "Type of Mass");

    DataSourceBooleanField flag_unladenBackhaul =
        new DataSourceBooleanField("flag_unladenBackhaul", "Unladen Backhaul Information?");
        
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, 
                streamType, serviceProviderName, serviceProviderContact,
                fuelDataType,fuelType,fuelConsumed,fuelConsumed_Unit, EF_Fuel, EF_Fuel_Unit,
                refrigerantType,refrigerantLeakage,refrigerantLeakage_Unit, EF_Refrigerant, EF_Refrigerant_Unit,
                flag_calculateFuelConsumed,
                totalDistanceTravelled, totalDistanceTravelled_Unit,
                vehicleType,fuelEfficiencyOfVehicle,fuelEfficiencyOfVehicle_Unit,
                flag_calculateAllocatedFuelUse, 
                allocatedFuelUse,allocatedFuelUse_Unit,totalFuelConsumed,totalFuelConsumed_Unit,
                companyGoodsTransported, companyGoodsTransported_Unit,companyGoodsTransported_MassType,
                totalGoodsTransportedByCarrier,totalGoodsTransportedByCarrier_Unit,massType,totalGoodsTransportedByCarrier_MassType,
                flag_unladenBackhaul,
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