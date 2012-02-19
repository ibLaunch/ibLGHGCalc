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
public class FranchisesInfoDS_2 extends RestDataSource {
  private static FranchisesInfoDS_2 instance;

  public static FranchisesInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new FranchisesInfoDS_2();
    }
    return instance;
  }

  private FranchisesInfoDS_2() {
    //set id + general stuff
    setID("franchisesInfoDS_2");
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
    DataSourceTextField franchiseName = new DataSourceTextField("franchiseName", "Franchise Name");
        
    DataSourceBooleanField flag_floorSpaceData =
        new DataSourceBooleanField("flag_floorSpaceData", "Floor Space data available?");
    
    //IblDataSourceFloatField emissionsFromCommercialAsset = new IblDataSourceFloatField("emissionsFromCommercialAssets", "Emissions From Commercial Asset");
    IblDataSourceFloatField floorSpace = new IblDataSourceFloatField("floorSpace", "Floor Space");
    DataSourceTextField floorSpace_Unit = new DataSourceTextField("floorSpace_Unit", "Unit");
    IblDataSourceFloatField average_EF = new IblDataSourceFloatField("average_EF", "Average Emission Factor");
    DataSourceTextField average_EF_Unit = new DataSourceTextField("average_EF_Unit", "Unit");

    //IblDataSourceFloatField emissionsFromOtherAsset = new IblDataSourceFloatField("emissionsFromOtherAsset", "Emissions From Other Asset");
    DataSourceTextField buildingOrAssetName = new DataSourceTextField("buildingOrAssetName", "Building/Asset Name");
    IblDataSourceFloatField numberOfbuildingsOrAssetTypes = new IblDataSourceFloatField("numberOfbuildingsOrAssetTypes", "Number of Building/Asset Type");        
    IblDataSourceFloatField averageEmissionsPerBuildingOrAssetType = new IblDataSourceFloatField("averageEmissionsPerBuildingOrAssetType", "Average Emissions Per Building/Asset Type");
    DataSourceTextField averageEmissionsPerBuildingOrAssetType_Unit = new DataSourceTextField("averageEmissionsPerBuildingOrAssetType_Unit", "Unit");
    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, franchiseName,                
                flag_floorSpaceData,
                floorSpace,floorSpace_Unit,
                average_EF,average_EF_Unit,
                buildingOrAssetName,numberOfbuildingsOrAssetTypes,
                averageEmissionsPerBuildingOrAssetType,averageEmissionsPerBuildingOrAssetType_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"franchisesInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"franchisesInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"franchisesInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"franchisesInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}