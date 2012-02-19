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
public class InvestmentsInfoDS_2 extends RestDataSource {
  private static InvestmentsInfoDS_2 instance;

  public static InvestmentsInfoDS_2 getInstance() {
    if (instance == null) {
      instance = new InvestmentsInfoDS_2();
    }
    return instance;
  }

  private InvestmentsInfoDS_2() {
    //set id + general stuff
    setID("investmentsInfoDS_2");
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
    DataSourceTextField investmentSector = new DataSourceTextField("investmentSector", "Investment Sector");
        
    IblDataSourceFloatField investmentAmount = new IblDataSourceFloatField("investmentAmount", "Investment Amount");
    DataSourceTextField investmentAmount_Unit = new DataSourceTextField("investmentAmount_Unit", "Unit");
    IblDataSourceFloatField EF_SectorSpecific = new IblDataSourceFloatField("EF_SectorSpecific", "Sector Specific Emission Factor");
    DataSourceTextField EF_SectorSpecific_Unit = new DataSourceTextField("EF_SectorSpecific_Unit", "Unit");
                
    IblDataSourceFloatField percentShareOfInvestment = new IblDataSourceFloatField("percentShareOfInvestment", "Percent Share Of Investment");
    
    DataSourceTextField userNotesOnDataField = new DataSourceTextField("userNotesOnData", "Notes on data source");
    
    DataSourceTextField methodTypeField = new DataSourceTextField("methodType", "Method Type");    
            
    IblDataSourceDateTimeField dataBeginDateField = new IblDataSourceDateTimeField("dataBeginDate", "Begin Date");
    IblDataSourceDateTimeField dataEndDateField = new IblDataSourceDateTimeField("dataEndDate", "End Date");        

    setFields(idField, organizationIdField, sourceDescriptionField, investmentType,investmentSector,
                investmentAmount,investmentAmount_Unit,
                EF_SectorSpecific,EF_SectorSpecific_Unit,
                percentShareOfInvestment,
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