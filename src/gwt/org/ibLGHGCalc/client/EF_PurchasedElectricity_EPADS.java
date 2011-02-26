package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Created by NetBeans
 * User: ibL
 * Date: Jan 6, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EF_PurchasedElectricity_EPADS extends RestDataSource {
  private static EF_PurchasedElectricity_EPADS instance;

  public static EF_PurchasedElectricity_EPADS getInstance() {
    if (instance == null) {
      instance = new EF_PurchasedElectricity_EPADS();
    }
    return instance;
  }

  private EF_PurchasedElectricity_EPADS() {
    //set id + general stuff
    setID("eF_PurchasedElectricity_EPADS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);

    DataSourceTextField eGRIDSubregionField =
        new DataSourceTextField("eGRIDSubregion", "eGRID Subregion");
    TextItem eGRIDSubregionItem = new TextItem();
    eGRIDSubregionItem.setWidth("100%");
    eGRIDSubregionField.setEditorType(eGRIDSubregionItem);

    DataSourceFloatField cO2MultiplierField =
        new DataSourceFloatField("CO2Multiplier", "CO2Multiplier");
    FloatItem cO2MultiplierItem = new FloatItem();
    cO2MultiplierField.setEditorType(cO2MultiplierItem);

    DataSourceFloatField cH4MultiplierField =
        new DataSourceFloatField("CH4Multiplier", "CH4Multiplier");
    FloatItem cH4MultiplierItem = new FloatItem();
    cH4MultiplierField.setEditorType(cH4MultiplierItem);

    DataSourceFloatField n2OMultiplierField =
        new DataSourceFloatField("N2OMultiplier", "N2OMultiplier");
    FloatItem n2OMultiplierItem = new FloatItem();
    n2OMultiplierField.setEditorType(n2OMultiplierItem);


    setFields(idField, eGRIDSubregionField, cO2MultiplierField, cH4MultiplierField, n2OMultiplierField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"EF_PurchasedElectricity_EPA/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"EF_PurchasedElectricity_EPA/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"EF_PurchasedElectricity_EPA/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"EF_PurchasedElectricity_EPA/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}