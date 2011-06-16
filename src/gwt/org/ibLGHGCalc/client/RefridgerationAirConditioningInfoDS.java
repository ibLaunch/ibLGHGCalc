            
package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
                                                                                  
/**
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Jan 3, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class RefridgerationAirConditioningInfoDS extends RestDataSource {
  private static RefridgerationAirConditioningInfoDS instance;

  public static RefridgerationAirConditioningInfoDS getInstance() {
    if (instance == null) {
      instance = new RefridgerationAirConditioningInfoDS();
    }
    return instance;
  }

  private RefridgerationAirConditioningInfoDS() {
    //set id + general stuff
    setID("refridgerationAirConditioningInfoDS");
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

//-Source level screening method fields
    DataSourceTextField sourceDescriptionField =
        new DataSourceTextField("sourceDescription", "Source Description");
    TextItem sourceDescriptionItem = new TextItem();
    sourceDescriptionItem.setWidth("100%");
    sourceDescriptionField.setEditorType(sourceDescriptionItem);

    DataSourceTextField gasTypeField =
        new DataSourceTextField("gasType", "Gas Type");
    TextItem gasTypeItem = new TextItem();
    gasTypeItem.setWidth("100%");
    gasTypeField.setEditorType(gasTypeItem);

//-company-wide material balance fields
    DataSourceFloatField inventoryChangeField =
        new DataSourceFloatField("inventoryChange", "Inventory Change");
    //FloatItem inventoryChangeItem = new FloatItem();
    //inventoryChangeField.setEditorType(inventoryChangeItem);
    inventoryChangeField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField transferredAmountField =
        new DataSourceFloatField("transferredAmount", "Transferred Amount");
    //FloatItem transferredAmountItem = new FloatItem();
    //transferredAmountField.setEditorType(transferredAmountItem);
    transferredAmountField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField capacityChangeField =
        new DataSourceFloatField("capacityChange", "Capacity Change");
    //FloatItem capacityChangeItem = new FloatItem();
    //capacityChangeField.setEditorType(capacityChangeItem);
    capacityChangeField.setType(ibLUsers.floatSimpleType);

//-company-wide simplified material balance fields
    DataSourceFloatField newUnitsChargeField =
        new DataSourceFloatField("newUnitsCharge", "New Units Charge");
    //FloatItem newUnitsChargeItem = new FloatItem();
    //newUnitsChargeField.setEditorType(newUnitsChargeItem);
    newUnitsChargeField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField newUnitsCapacityField =
        new DataSourceFloatField("newUnitsCapacity", "New Units Capacity");
    //FloatItem newUnitsCapacityItem = new FloatItem();
    //newUnitsCapacityField.setEditorType(newUnitsCapacityItem);
    newUnitsCapacityField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField existingUnitsRechargeField =
        new DataSourceFloatField("existingUnitsRecharge", "Existing Units Recharge");
    //FloatItem existingUnitsRechargeItem = new FloatItem();
    //existingUnitsRechargeField.setEditorType(existingUnitsRechargeItem);
    existingUnitsRechargeField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField disposedUnitsCapacityField =
        new DataSourceFloatField("disposedUnitsCapacity", "Disposed Units Capacity");
    //FloatItem disposedUnitsCapacityItem = new FloatItem();
    //disposedUnitsCapacityField.setEditorType(disposedUnitsCapacityItem);
    disposedUnitsCapacityField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField disposedUnitsRecoveredField =
        new DataSourceFloatField("disposedUnitsRecovered", "Disposed Units Recovered");
    //FloatItem disposedUnitsRecoveredItem = new FloatItem();
    //disposedUnitsRecoveredField.setEditorType(disposedUnitsRecoveredItem);
    //disposedUnitsRecoveredField.setType(ibLUsers.floatSimpleType);
    disposedUnitsRecoveredField.setType(ibLUsers.floatSimpleType);

    DataSourceTextField typeOfEquipmentField =
        new DataSourceTextField("typeOfEquipment", "Type Of Equipment");
    TextItem typeOfEquipmentItem = new TextItem();
    typeOfEquipmentItem.setWidth("100%");
    typeOfEquipmentField.setEditorType(gasTypeItem);

    DataSourceFloatField sourceNewUnitsChargeField =
        new DataSourceFloatField("sourceNewUnitsCharge", "Source New Units Charge");
    //FloatItem sourceNewUnitsChargeItem = new FloatItem();
    //sourceNewUnitsChargeField.setEditorType(sourceNewUnitsChargeItem);
    sourceNewUnitsChargeField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField operatingUnitsCapacityField =
        new DataSourceFloatField("operatingUnitsCapacity", "Operating Units Capacity");
    //FloatItem operatingUnitsCapacityItem = new FloatItem();
    //voperatingUnitsCapacityField.setEditorType(operatingUnitsCapacityItem);
    operatingUnitsCapacityField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField sourceDisposedUnitsCapacityField =
        new DataSourceFloatField("sourceDisposedUnitsCapacity", "Source Disposed Units Capacity");
    //FloatItem sourceDisposedUnitsCapacityItem = new FloatItem();
    //sourceDisposedUnitsCapacityField.setEditorType(sourceDisposedUnitsCapacityItem);
    sourceDisposedUnitsCapacityField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField timeInYearsUsedField =
        new DataSourceFloatField("timeInYearsUsed", "Time In Years Used");
    //FloatItem timeInYearsUsedItem = new FloatItem();
    //timeInYearsUsedField.setEditorType(timeInYearsUsedItem);
    timeInYearsUsedField.setType(ibLUsers.floatSimpleType);

//--method type etc..

    DataSourceTextField methodTypeField =
        new DataSourceTextField("methodType", "Method Type");
    TextItem methodTypeItem = new TextItem();
    methodTypeItem.setWidth("100%");
    methodTypeField.setEditorType(methodTypeItem);

/*
    DataSourceTextField fuelUnitField =
        new DataSourceTextField("fuelUnit", "Fuel Unit");
    TextItem fuelUnitItem = new TextItem();
    fuelUnitItem.setWidth("100%");
    fuelUnitField.setEditorType(fuelUnitItem);
*/

    DataSourceDateTimeField fuelUsedBeginDateField =
        new DataSourceDateTimeField("fuelUsedBeginDate", "Begin Date");
    //DateItem fuelUsedBeginDateItem = new DateItem();
    //fuelUsedBeginDateItem.setWidth("100%");
    //fuelUsedBeginDateField.setEditorType(fuelUsedBeginDateItem);
    fuelUsedBeginDateField.setType(FieldType.DATE);

    DataSourceDateTimeField fuelUsedEndDateField =
        new DataSourceDateTimeField("fuelUsedEndDate", "End Date");
    //DateItem fuelUsedEndDateItem = new DateItem();
    //fuelUsedEndDateItem.setWidth("100%");
    //fuelUsedEndDateField.setEditorType(fuelUsedEndDateItem);
    fuelUsedEndDateField.setType(FieldType.DATE);

    setFields(idField, organizationIdField, gasTypeField, inventoryChangeField, transferredAmountField, capacityChangeField, newUnitsChargeField,
            newUnitsCapacityField, existingUnitsRechargeField, disposedUnitsCapacityField, disposedUnitsRecoveredField, sourceDescriptionField, 
            typeOfEquipmentField, sourceNewUnitsChargeField, operatingUnitsCapacityField,sourceDisposedUnitsCapacityField, timeInYearsUsedField, methodTypeField,
            fuelUsedBeginDateField, fuelUsedEndDateField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"refridgerationAirConditioningInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"refridgerationAirConditioningInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"refridgerationAirConditioningInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"refridgerationAirConditioningInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}