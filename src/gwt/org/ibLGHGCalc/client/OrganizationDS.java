package org.ibLGHGCalc.client;

import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Nov 16, 2010
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrganizationDS extends RestDataSource {
  private static OrganizationDS instance;

  public static OrganizationDS getInstance() {
    if (instance == null) {
      instance = new OrganizationDS();
    }
    return instance;
  }

  private OrganizationDS() {
    //set id + general stuff
    setID("organizationDS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);

    //DataSourceIntegerField versionField =
    //    new DataSourceIntegerField("version", "Version");
    //versionField.setCanEdit(false);

    DataSourceTextField organizationNameField = new DataSourceTextField("organizationName", "organization Name", 50, true);
    TextItem orgznizationNameItem = new TextItem();
    orgznizationNameItem.setWidth("100%");
    organizationNameField.setEditorType(orgznizationNameItem);

    setFields(idField, organizationNameField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
            new OperationBinding(DSOperationType.FETCH, "/ibLGHGCalc/organization/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE, "/ibLGHGCalc/organization/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD, "/ibLGHGCalc/organization/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE, "/ibLGHGCalc/organization/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
  }

}