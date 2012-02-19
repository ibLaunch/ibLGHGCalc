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
public class PurchasedEnergyInfoDS_4 extends RestDataSource {
  private static PurchasedEnergyInfoDS_4 instance;

  public static PurchasedEnergyInfoDS_4 getInstance() {
    if (instance == null) {
      instance = new PurchasedEnergyInfoDS_4();
    }
    return instance;
  }

  private PurchasedEnergyInfoDS_4() {
    //set id + general stuff
    setID("purchasedEnergyInfoDS_4");
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
    //DataSourceTextField fuelType = new DataSourceTextField("fuelType", "Fuel Type");    
    
    DataSourceTextField energyType = new DataSourceTextField("energyType", "Energy Type");    
	
    DataSourceTextField supplierNameField = new DataSourceTextField("supplierName", "Supplier Name");
    DataSourceTextField supplierContactField = new DataSourceTextField("supplierContact", "Supplier Contact");
    DataSourceTextField supplierRegionCountry = new DataSourceTextField("supplierRegionCountry", "Supplier Region/Country");
	
    IblDataSourceFloatField energyPurchased = new IblDataSourceFloatField("energyPurchased", "Energy Purchased");
    DataSourceTextField energyPurchased_Unit = new DataSourceTextField("energyPurchased_Unit", "Unit");


    DataSourceBooleanField flag_upstreamEF =
        new DataSourceBooleanField("flag_upstreamEF", "Upstream Emission Factor?");
    
    IblDataSourceFloatField EF_UpstreamEnergy = new IblDataSourceFloatField("EF_UpstreamEnergy", "Upstream Emission Factor");
    DataSourceTextField EF_UpstreamEnergy_Unit = new DataSourceTextField("EF_UpstreamEnergy_Unit", "Unit");

    IblDataSourceFloatField cradleToGate_EF_Energy = new IblDataSourceFloatField("cradleToGate_EF_Energy", "Cradel To Gate Emission Factor");
    DataSourceTextField cradleToGate_EF_Energy_Unit = new DataSourceTextField("cradleToGate_EF_Energy_Unit", "Unit");

    IblDataSourceFloatField combustion_EF_Energy = new IblDataSourceFloatField("combustion_EF_Energy", "Combustion Emission Factor");
    DataSourceTextField combustion_EF_Energy_Unit = new DataSourceTextField("combustion_EF_Energy_Unit", "Unit");
/*
    IblDataSourceFloatField transAndDistLossRate = new IblDataSourceFloatField("transAndDistLossRate", "T & D Loss rate");    

    DataSourceBooleanField flag_transAndDistDataType =
        new DataSourceBooleanField("flag_transAndDistDataType", "What type of transmission and distribution data you have?");

    IblDataSourceFloatField scope2EmissionsOfEnergyUse = new IblDataSourceFloatField("scope2EmissionsOfEnergyUse", "Scope 2 Emissions");
    DataSourceTextField scope2EmissionsOfEnergyUse_Unit = new DataSourceTextField("scope2EmissionsOfEnergyUse_Unit", "Unit");
*/
    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, supplierNameField, supplierContactField, 
                activityType, energyType, supplierRegionCountry,
                energyPurchased,energyPurchased_Unit,
                flag_upstreamEF,
                EF_UpstreamEnergy, EF_UpstreamEnergy_Unit,
                cradleToGate_EF_Energy, cradleToGate_EF_Energy_Unit,
                combustion_EF_Energy, combustion_EF_Energy_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"purchasedEnergyInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"purchasedEnergyInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"purchasedEnergyInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"purchasedEnergyInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}