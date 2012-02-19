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
public class FranchisesInfoDS_1 extends RestDataSource {
  private static FranchisesInfoDS_1 instance;

  public static FranchisesInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new FranchisesInfoDS_1();
    }
    return instance;
  }

  private FranchisesInfoDS_1() {
    //set id + general stuff
    setID("franchisesInfoDS_1");
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
    
    IblDataSourceFloatField scope1EmissionsOfFranchise = new IblDataSourceFloatField("scope1EmissionsOfFranchise", "Scope1 Emissions Of Franchise");
    DataSourceTextField scope1EmissionsOfFranchise_Unit = new DataSourceTextField("scope1EmissionsOfFranchise_Unit", "Unit");
    IblDataSourceFloatField scope2EmissionsOfFranchise = new IblDataSourceFloatField("scope2EmissionsOfFranchise", "Scope2 Emissions Of Franchise");
    DataSourceTextField scope2EmissionsOfFranchise_Unit = new DataSourceTextField("scope2EmissionsOfFranchise_Unit", "Unit");
                
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");    
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, franchiseName,
                scope1EmissionsOfFranchise,scope1EmissionsOfFranchise_Unit,
                scope2EmissionsOfFranchise,scope2EmissionsOfFranchise_Unit,
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