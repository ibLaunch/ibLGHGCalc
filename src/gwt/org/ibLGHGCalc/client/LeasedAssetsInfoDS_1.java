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
public class LeasedAssetsInfoDS_1 extends RestDataSource {
  private static LeasedAssetsInfoDS_1 instance;

  public static LeasedAssetsInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new LeasedAssetsInfoDS_1();
    }
    return instance;
  }

  private LeasedAssetsInfoDS_1() {
    //set id + general stuff
    setID("leasedAssetsInfoDS_1");
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
    DataSourceTextField streamType = new DataSourceTextField("streamType", "Downstream/Upstream");
    
    DataSourceTextField typeOfLeasingArrangement = new DataSourceTextField("typeOfLeasingArrangement", "Type Of Leasing Arrangement");    	
    //DataSourceTextField consolidationApproach = new DataSourceTextField("consolidationApproach", "Consolidation Approach");
    DataSourceTextField lessorOrLessee = new DataSourceTextField("lessorOrLessee", "Lessor/Lessee");
    DataSourceTextField leasedAssetName = new DataSourceTextField("leasedAssetName", "Asset Name");
    
    IblDataSourceFloatField scope1EmissionsOfleasedAsset = new IblDataSourceFloatField("scope1EmissionsOfLeasedAsset", "Scope1 Emissions Of Leased Asset");
    DataSourceTextField scope1EmissionsOfLeasedAsset_Unit = new DataSourceTextField("scope1EmissionsOfLeasedAsset_Unit", "Unit");
    IblDataSourceFloatField scope2EmissionsOfLeasedAsset = new IblDataSourceFloatField("scope2EmissionsOfLeasedAsset", "Scope2 Emissions Of Leased Asset");
    DataSourceTextField scope2EmissionsOfLeasedAsset_Unit = new DataSourceTextField("scope2EmissionsOfLeasedAsset_Unit", "Unit");
                
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");
    //methodTypeField.setValueMap("Purchased Goods and Services - Product Level Method");
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, streamType,
                typeOfLeasingArrangement,lessorOrLessee,leasedAssetName,
                scope1EmissionsOfleasedAsset,scope1EmissionsOfLeasedAsset_Unit,
                scope2EmissionsOfLeasedAsset,scope2EmissionsOfLeasedAsset_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"leasedAssetsInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"leasedAssetsInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"leasedAssetsInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"leasedAssetsInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}