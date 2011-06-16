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
public class GWP_RefridgerationAirConditioning_EPADS extends RestDataSource {
  private static GWP_RefridgerationAirConditioning_EPADS instance;

  public static GWP_RefridgerationAirConditioning_EPADS getInstance() {
    if (instance == null) {
      instance = new GWP_RefridgerationAirConditioning_EPADS();
    }
    return instance;
  }

  private GWP_RefridgerationAirConditioning_EPADS() {
    //set id + general stuff
    setID("gWP_RefridgerationAirConditioning_EPADS");
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
    
    DataSourceFloatField gasTypeGWPField =
        new DataSourceFloatField("gasTypeGWP", "Gas Type GWP");
    FloatItem gasTypeGWPItem = new FloatItem();
    gasTypeGWPField.setEditorType(gasTypeGWPItem);

    setFields(idField, gasTypeField, gasTypeGWPField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"GWP_RefridgerationAirConditioning_EPA/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"GWP_RefridgerationAirConditioning_EPA/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"GWP_RefridgerationAirConditioning_EPA/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"GWP_RefridgerationAirConditioning_EPA/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}