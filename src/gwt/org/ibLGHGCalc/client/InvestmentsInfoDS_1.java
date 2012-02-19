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
public class InvestmentsInfoDS_1 extends RestDataSource {
  private static InvestmentsInfoDS_1 instance;

  public static InvestmentsInfoDS_1 getInstance() {
    if (instance == null) {
      instance = new InvestmentsInfoDS_1();
    }
    return instance;
  }

  private InvestmentsInfoDS_1() {
    //set id + general stuff
    setID("investmentsInfoDS_1");
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
    DataSourceTextField investmentType = new DataSourceTextField("investmentType", "Investment Type");
        
    IblDataSourceFloatField scope1Emissions = new IblDataSourceFloatField("scope1Emissions", "Scope 1 Emissions");
    DataSourceTextField scope1Emissions_Unit = new DataSourceTextField("scope1Emissions_Unit", "Unit");
    IblDataSourceFloatField scope2Emissions = new IblDataSourceFloatField("scope2Emissions", "Scope 2 Emissions");
    DataSourceTextField scope2Emissions_Unit = new DataSourceTextField("scope2Emissions_Unit", "Unit");
                
    IblDataSourceFloatField percentShareOfInvestment = new IblDataSourceFloatField("percentShareOfInvestment", "Percent Share Of Investment");
    IblDataSourceFloatField anticipatedLifetimeScope1Emissions = new IblDataSourceFloatField("anticipatedLifetimeScope1Emissions", "Anticipated Lifetime Scope 1 Emissions");
    DataSourceTextField anticipatedLifetimeScope1Emissions_Unit = new DataSourceTextField("anticipatedLifetimeScope1Emissions_Unit", "Unit");
    IblDataSourceFloatField anticipatedLifetimeScope2Emissions = new IblDataSourceFloatField("anticipatedLifetimeScope2Emissions", "Anticipated Lifetime Scope 2 Emissions");
    DataSourceTextField anticipatedLifetimeScope2Emissions_Unit = new DataSourceTextField("anticipatedLifetimeScope2Emissions_Unit", "Unit");
    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");    
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, investmentType,
                scope1Emissions,scope1Emissions_Unit,
                scope2Emissions,scope2Emissions_Unit,
                percentShareOfInvestment,
                anticipatedLifetimeScope1Emissions,anticipatedLifetimeScope1Emissions_Unit,
                anticipatedLifetimeScope2Emissions,anticipatedLifetimeScope2Emissions_Unit,
                userNotesOnDataField, methodTypeField, dataBeginDateField, dataEndDateField );
    
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"investmentsInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"investmentsInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"investmentsInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"investmentsInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setAutoDeriveTitles(Boolean.TRUE);
    //setClientOnly(true);
  }
}