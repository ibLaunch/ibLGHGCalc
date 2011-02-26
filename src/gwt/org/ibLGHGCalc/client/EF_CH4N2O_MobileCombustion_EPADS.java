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
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Nov 16, 2010
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EF_CH4N2O_MobileCombustion_EPADS extends RestDataSource {
  private static EF_CH4N2O_MobileCombustion_EPADS instance;

  public static EF_CH4N2O_MobileCombustion_EPADS getInstance() {
    if (instance == null) {
      instance = new EF_CH4N2O_MobileCombustion_EPADS();
    }
    return instance;
  }

  private EF_CH4N2O_MobileCombustion_EPADS() {
    //set id + general stuff
    setID("eF_CH4N2O_MobileCombustion_EPADS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);
    
    DataSourceTextField vehicleTypeField =
        new DataSourceTextField("vehicleType", "Vehicle Type");
    TextItem vehicleTypeItem = new TextItem();
    vehicleTypeItem.setWidth("100%");
    vehicleTypeField.setEditorType(vehicleTypeItem);

    DataSourceTextField vehicleYearField =
        new DataSourceTextField("vehicleYear", "Vehicle Year");
    TextItem vehicleYearItem = new TextItem();
    vehicleYearItem.setWidth("100%");
    vehicleYearField.setEditorType(vehicleYearItem);
    
    DataSourceFloatField CH4MultiplierInGramField =
        new DataSourceFloatField("CH4MultiplierInGram", "CH4 Multiplier In Gram");
    FloatItem CH4MultiplierInGramItem = new FloatItem();
    CH4MultiplierInGramField.setEditorType(CH4MultiplierInGramItem);

    DataSourceFloatField N2OMultiplierInGramField =
        new DataSourceFloatField("N2OMultiplierInGram", "N2O Multiplier In Gram");
    FloatItem N2OMultiplierInGramItem = new FloatItem();
    N2OMultiplierInGramField.setEditorType(N2OMultiplierInGramItem);

    setFields(idField,vehicleTypeField, vehicleYearField, CH4MultiplierInGramField, N2OMultiplierInGramField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"EF_CH4N2O_MobileCombustion_EPA/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"EF_CH4N2O_MobileCombustion_EPA/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"EF_CH4N2O_MobileCombustion_EPA/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"EF_CH4N2O_MobileCombustion_EPA/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}