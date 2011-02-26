package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
                                                                                  
/**
 * Created by HB
 * User: ibL
 * Date: Jan 3, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EquipmentCapacityRange_EPADS extends RestDataSource {
  private static EquipmentCapacityRange_EPADS instance;

  public static EquipmentCapacityRange_EPADS getInstance() {
    if (instance == null) {
      instance = new EquipmentCapacityRange_EPADS();
    }
    return instance;
  }

  private EquipmentCapacityRange_EPADS() {
    //set id + general stuff
    setID("eEquipmentCapacityRange_EPADS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);
    
    DataSourceTextField gasTypeField =
        new DataSourceTextField("gasType", "Gas Type");
    TextItem gasTypeItem = new TextItem();
    gasTypeItem.setWidth("100%");
    gasTypeField.setEditorType(gasTypeItem);

    DataSourceTextField typeOfEquipmentField =
        new DataSourceTextField("typeOfEquipment", "type Of Equipment");
    TextItem typeOfEquipmentItem = new TextItem();
    typeOfEquipmentItem.setWidth("100%");
    typeOfEquipmentField.setEditorType(typeOfEquipmentItem);


    DataSourceTextField capacityRangeField =
        new DataSourceTextField("capacityRange", "Capacity Range");
    TextItem capacityRangeItem = new TextItem();
    capacityRangeItem.setWidth("100%");
    capacityRangeField.setEditorType(capacityRangeItem);

    DataSourceTextField capacityRangeUnitField =
        new DataSourceTextField("capacityRangeUnit", "Capacity Range Unit");
    TextItem capacityRangeUnitItem = new TextItem();
    capacityRangeUnitItem.setWidth("100%");
    capacityRangeUnitField.setEditorType(capacityRangeUnitItem);


    DataSourceFloatField kFactorField =
        new DataSourceFloatField("kFactor", "k Factor");
    FloatItem kFactorItem = new FloatItem();
    kFactorField.setEditorType(kFactorItem);
    
    DataSourceFloatField xFactorField =
        new DataSourceFloatField("xFactor", "x Factor");
    FloatItem xFactorItem = new FloatItem();
    xFactorField.setEditorType(xFactorItem);

    DataSourceFloatField yFactorField =
        new DataSourceFloatField("yFactor", "y Factor");
    FloatItem yFactorItem = new FloatItem();
    yFactorField.setEditorType(yFactorItem);

    DataSourceFloatField zFactorField =
        new DataSourceFloatField("zFactor", "z Factor");
    FloatItem zFactorItem = new FloatItem();
    zFactorField.setEditorType(zFactorItem);


    setFields(idField, gasTypeField, typeOfEquipmentField, capacityRangeField, capacityRangeUnitField, kFactorField, xFactorField,yFactorField, zFactorField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"equipmentCapacityRange_EPA/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"equipmentCapacityRange_EPA/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);

    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"equipmentCapacityRange_EPA/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"equipmentCapacityRange_EPA/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}