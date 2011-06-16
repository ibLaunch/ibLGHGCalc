package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
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
  private int FIELD_WIDTH = 150;

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

    DataSourceTextField organizationNameField = new DataSourceTextField("organizationName", "Organization", FIELD_WIDTH, true);
    StaticTextItem organizationNameItem = new StaticTextItem();
    organizationNameItem.setWidth("100%");
    organizationNameField.setEditorType(organizationNameItem);

    
    DataSourceDateTimeField currentInventoryBeginDateField =
        new DataSourceDateTimeField("currentInventoryBeginDate", "Current Inventory Begin Date");
    DateItem currentInventoryBeginDateItem = new DateItem();
    currentInventoryBeginDateItem.setWidth("100%");
    //currentInventoryBeginDateItem.setUseTextField(true);
    //currentInventoryBeginDateItem.setDisplayFormat(DateDisplayFormat.TOUSSHORTDATE);
    currentInventoryBeginDateField.setEditorType(currentInventoryBeginDateItem);
        
    DataSourceDateTimeField currentInventoryEndDateField =
        new DataSourceDateTimeField("currentInventoryEndDate", "Current Inventory End Date");
    DateItem currentInventoryEndDateItem = new DateItem();
    currentInventoryEndDateItem.setWidth("100%");
    //currentInventoryEndDateItem.setUseTextField(true);
    //currentInventoryEndDateItem.setDisplayFormat(DateDisplayFormat.TOUSSHORTDATE);
    currentInventoryEndDateField.setEditorType(currentInventoryEndDateItem);

    DataSourceTextField organizationStreetAddress1Field = new DataSourceTextField("organizationStreetAddress1", "Street Address", FIELD_WIDTH, true);
    TextItem organizationStreetAddress1Item = new TextItem();
    organizationStreetAddress1Item.setWidth("100%");
    organizationStreetAddress1Field.setEditorType(organizationStreetAddress1Item);

    DataSourceTextField organizationStreetAddress2Field = new DataSourceTextField("organizationStreetAddress2", "Street Address", FIELD_WIDTH, true);
    TextItem organizationStreetAddress2Item = new TextItem();
    organizationStreetAddress2Item.setWidth("100%");
    organizationStreetAddress2Field.setEditorType(organizationStreetAddress2Item);

    DataSourceTextField organizationCityField = new DataSourceTextField("organizationCity", "City", FIELD_WIDTH, true);
    TextItem organizationCityItem = new TextItem();
    organizationCityItem.setWidth("100%");
    organizationCityField.setEditorType(organizationCityItem);

    DataSourceTextField organizationStateField = new DataSourceTextField("organizationState", "State", FIELD_WIDTH, true);
    TextItem organizationStateItem = new TextItem();
    organizationStateItem.setWidth("100%");
    organizationStateField.setEditorType(organizationStateItem);

    DataSourceTextField organizationZipCodeField = new DataSourceTextField("organizationZipCode", "Zip Code", FIELD_WIDTH, true);
    TextItem organizationZipCodeItem = new TextItem();
    organizationZipCodeItem.setWidth("100%");
    organizationZipCodeField.setEditorType(organizationZipCodeItem);

    DataSourceTextField organizationCountryField = new DataSourceTextField("organizationCountry", "Country", FIELD_WIDTH, true);
    TextItem organizationCountryItem = new TextItem();
    organizationCountryItem.setWidth("100%");
    organizationCountryField.setEditorType(organizationCountryItem);

    DataSourceTextField organizationWebsiteField = new DataSourceTextField("organizationWebsite", "Website", FIELD_WIDTH, true);
    TextItem organizationWebsiteItem = new TextItem();
    organizationWebsiteItem.setWidth("100%");
    organizationWebsiteField.setEditorType(organizationWebsiteItem);

    DataSourceTextField organizationHQField = new DataSourceTextField("organizationHQ", "Head Quarter?", FIELD_WIDTH, true);
    TextItem organizationHQItem = new TextItem();
    organizationHQItem.setWidth("100%");
    organizationHQField.setEditorType(organizationHQItem);

    DataSourceTextField pointOfContactField = new DataSourceTextField("pointOfContact", "Point Of Contact", FIELD_WIDTH, true);
    TextItem pointOfContactItem = new TextItem();
    pointOfContactItem.setWidth("100%");
    pointOfContactField.setEditorType(pointOfContactItem);
    
    setFields(idField, organizationNameField,currentInventoryBeginDateField, currentInventoryEndDateField,
                organizationStreetAddress1Field,organizationStreetAddress2Field,organizationCityField,
                organizationStateField,organizationZipCodeField,organizationCountryField,organizationWebsiteField,
                organizationHQField,pointOfContactField);
    //setup operations
    //1. fetch

    //SC.say("GWT.getHostPageBaseURL() : " + GWT.getHostPageBaseURL());

    OperationBinding fetch =
            new OperationBinding(DSOperationType.FETCH, "organization/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"organization/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"organization/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"organization/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
  }

}