package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Nov 16, 2010
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmissionsSummaryDS extends RestDataSource {
  private static EmissionsSummaryDS instance;

  public static EmissionsSummaryDS getInstance() {
    if (instance == null) {
      instance = new EmissionsSummaryDS();
    }
    return instance;
  }

  private EmissionsSummaryDS() {
    //set id + general stuff
    setID("emissionsSummaryDS");
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

    DataSourceFloatField totalDirectEmissionsField =
        new DataSourceFloatField("totalDirectEmissions", "Total Direct Emissions");
    //FloatItem directEmissionsItem = new FloatItem();
    //totlaDirectEmissionsField.setEditorType(directEmissionsItem);
    totalDirectEmissionsField.setCanEdit(false);
    totalDirectEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField totalInDirectEmissionsField =
        new DataSourceFloatField("totalInDirectEmissions", "Total InDirect Emissions");
    //FloatItem directEmissionsItem = new FloatItem();
    //directEmissionsField.setEditorType(directEmissionsItem);
    totalInDirectEmissionsField.setCanEdit(false);
    totalInDirectEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField stationaryCombustionEmissionsField =
        new DataSourceFloatField("stationaryCombustionEmissions", "Stationary Combustion Emissions");
    /*
    FloatItem stationaryCombustionEmissionsItem = new FloatItem();
    stationaryCombustionEmissionsField.setEditorType(stationaryCombustionEmissionsItem);    
     * 
     */
    stationaryCombustionEmissionsField.setType(ibLUsers.floatSimpleType);
    stationaryCombustionEmissionsField.setCanEdit(false);

    DataSourceFloatField mobileCombustionEmissionsField =
        new DataSourceFloatField("mobileCombustionEmissions", "Mobile Combustion Emissions");
    //FloatItem mobileCombustionEmissionsItem = new FloatItem();
    //mobileCombustionEmissionsField.setEditorType(mobileCombustionEmissionsItem);
    //mobileCombustionEmissionsField.set
    mobileCombustionEmissionsField.setCanEdit(false);
    mobileCombustionEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField biomassStationaryCombustionEmissionsField =
        new DataSourceFloatField("biomassStationaryCombustionEmissions", "Biomass Stationary Combustion Emissions");
    //FloatItem biomassStationaryCombustionEmissionsItem = new FloatItem();
    //biomassStationaryCombustionEmissionsField.setEditorType(biomassStationaryCombustionEmissionsItem);
    biomassStationaryCombustionEmissionsField.setCanEdit(false);
    biomassStationaryCombustionEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField biomassMobileCombustionEmissionsField =
        new DataSourceFloatField("biomassMobileCombustionEmissions", "Biomass Mobile Combustion Emissions");
    //FloatItem biomassMobileCombustionEmissionsItem = new FloatItem();
    //biomassMobileCombustionEmissionsField.setEditorType(biomassMobileCombustionEmissionsItem);
    biomassMobileCombustionEmissionsField.setCanEdit(false);
    biomassMobileCombustionEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField refridgerationAirConditioningEmissionsField =
        new DataSourceFloatField("refridgerationAirConditioningEmissions", "Refridgeration And Ac Emissions");
    //FloatItem refridgerationAirConditioningEmissionsItem = new FloatItem();
    //refridgerationAirConditioningEmissionsField.setEditorType(refridgerationAirConditioningEmissionsItem);
    refridgerationAirConditioningEmissionsField.setCanEdit(false);
    refridgerationAirConditioningEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField fireSuppressantEmissionsField =
        new DataSourceFloatField("fireSuppressantEmissions", "Fire Suppressant Emissions");
    //FloatItem fireSuppressantEmissionsItem = new FloatItem();
    //fireSuppressantEmissionsField.setEditorType(fireSuppressantEmissionsItem);
    fireSuppressantEmissionsField.setCanEdit(false);
    fireSuppressantEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField wasteStreamCombustionEmissionsField =
        new DataSourceFloatField("wasteStreamCombustionEmissions", "WasteStream Combustion Emissions");
    //FloatItem wasteStreamCombustionEmissionsItem = new FloatItem();
    //wasteStreamCombustionEmissionsField.setEditorType(wasteStreamCombustionEmissionsItem);
    wasteStreamCombustionEmissionsField.setCanEdit(false);
    wasteStreamCombustionEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField purchasedElectricityEmissionsField =
        new DataSourceFloatField("purchasedElectricityEmissions", "Purchased Electricity Emissions");
    //FloatItem purchasedElectricityEmissionsItem = new FloatItem();
    //purchasedElectricityEmissionsField.setEditorType(purchasedElectricityEmissionsItem);
    purchasedElectricityEmissionsField.setCanEdit(false);
    purchasedElectricityEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField employeeBusinessTravelByVehicleEmissionsField =
        new DataSourceFloatField("employeeBusinessTravelByVehicleEmissions", "Employee Business Travel By Vehicle Emissions");
    //FloatItem employeeBusinessTravelByVehicleEmissionsItem = new FloatItem();
    //employeeBusinessTravelByVehicleEmissionsField.setEditorType(employeeBusinessTravelByVehicleEmissionsItem);
    employeeBusinessTravelByVehicleEmissionsField.setCanEdit(false);
    employeeBusinessTravelByVehicleEmissionsField.setType(ibLUsers.floatSimpleType);

//==
    DataSourceFloatField employeeBusinessTravelByRailEmissionsField =
        new DataSourceFloatField("employeeBusinessTravelByRailEmissions", "Employee Business Travel By Rail Emissions");
    //FloatItem employeeBusinessTravelByRailEmissionsItem = new FloatItem();
    //employeeBusinessTravelByRailEmissionsField.setEditorType(employeeBusinessTravelByRailEmissionsItem);
    employeeBusinessTravelByRailEmissionsField.setCanEdit(false);
    employeeBusinessTravelByRailEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField employeeBusinessTravelByBusEmissionsField =
        new DataSourceFloatField("employeeBusinessTravelByBusEmissions", "Employee Business Travel By Bus Emissions");
    //FloatItem employeeBusinessTravelByBusEmissionsItem = new FloatItem();
    //employeeBusinessTravelByBusEmissionsField.setEditorType(employeeBusinessTravelByBusEmissionsItem);
    employeeBusinessTravelByBusEmissionsField.setCanEdit(false);
    employeeBusinessTravelByBusEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField employeeBusinessTravelByAirEmissionsField =
        new DataSourceFloatField("employeeBusinessTravelByAirEmissions", "Employee Business Travel By Air Emissions");
    //FloatItem employeeBusinessTravelByAirEmissionsItem = new FloatItem();
    //employeeBusinessTravelByAirEmissionsField.setEditorType(employeeBusinessTravelByAirEmissionsItem);
    employeeBusinessTravelByAirEmissionsField.setCanEdit(false);
    employeeBusinessTravelByAirEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField employeeCommutingByVehicleEmissionsField =
        new DataSourceFloatField("employeeCommutingByVehicleEmissions", "Employee Commuting By Vehicle Emissions");
    //FloatItem employeeCommutingByVehicleEmissionsItem = new FloatItem();
    //employeeCommutingByVehicleEmissionsField.setEditorType(employeeCommutingByVehicleEmissionsItem);
    employeeCommutingByVehicleEmissionsField.setCanEdit(false);
    employeeCommutingByVehicleEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField employeeCommutingByRailEmissionsField =
        new DataSourceFloatField("employeeCommutingByRailEmissions", "Employee Commuting By Rail Emissions");
    //FloatItem employeeCommutingByRailEmissionsItem = new FloatItem();
    //employeeCommutingByRailEmissionsField.setEditorType(employeeCommutingByRailEmissionsItem);
    employeeCommutingByRailEmissionsField.setCanEdit(false);
    employeeCommutingByRailEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField employeeCommutingByBusEmissionsField =
        new DataSourceFloatField("employeeCommutingByBusEmissions", "Employee Commuting By Bus Emissions");
    //FloatItem employeeCommutingByBusEmissionsItem = new FloatItem();
    //employeeCommutingByBusEmissionsField.setEditorType(employeeCommutingByBusEmissionsItem);
    employeeCommutingByBusEmissionsField.setCanEdit(false);
    employeeCommutingByBusEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField productTransportByVehicleEmissionsField =
        new DataSourceFloatField("productTransportByVehicleEmissions", "Product Transport By Vehicle Emissions");
    //FloatItem productTransportByVehicleEmissionsItem = new FloatItem();
    //productTransportByVehicleEmissionsField.setEditorType(productTransportByVehicleEmissionsItem);
    productTransportByVehicleEmissionsField.setCanEdit(false);
    productTransportByVehicleEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField productTransportByHeavyDutyTrucksEmissionsField =
        new DataSourceFloatField("productTransportByHeavyDutyTrucksEmissions", "Product Transport By Heavy Duty Trucks Emissions");
    //FloatItem productTransportByHeavyDutyTrucksEmissionsItem = new FloatItem();
    //productTransportByHeavyDutyTrucksEmissionsField.setEditorType(productTransportByHeavyDutyTrucksEmissionsItem);
    productTransportByHeavyDutyTrucksEmissionsField.setCanEdit(false);
    productTransportByHeavyDutyTrucksEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField productTransportByRailEmissionsField =
        new DataSourceFloatField("productTransportByRailEmissions", "Product Transport By Rail Emissions");
    //FloatItem productTransportByRailEmissionsItem = new FloatItem();
    //productTransportByRailEmissionsField.setEditorType(productTransportByRailEmissionsItem);
    productTransportByRailEmissionsField.setCanEdit(false);
    productTransportByRailEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField productTransportByWaterAirEmissionsField =
        new DataSourceFloatField("productTransportByWaterAirEmissions", "Product Transport By Water/Air Emissions");
    //FloatItem productTransportByWaterAirEmissionsItem = new FloatItem();
    //productTransportByWaterAirEmissionsField.setEditorType(productTransportByWaterAirEmissionsItem);
    productTransportByWaterAirEmissionsField.setCanEdit(false);
    productTransportByWaterAirEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField purchasedSteamEmissionsField =
        new DataSourceFloatField("purchasedSteamEmissions", "Purchased Steam Emissions");
    //FloatItem purchasedSteamEmissionsItem = new FloatItem();
    //purchasedSteamEmissionsField.setEditorType(purchasedSteamEmissionsItem);
    purchasedSteamEmissionsField.setCanEdit(false);
    purchasedSteamEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField totalEmissionsField =
        new DataSourceFloatField("totalEmissions", "Total Emissions");
    //FloatItem totalEmissionsItem = new FloatItem();
    //totalEmissionsField.setEditorType(totalEmissionsItem);
    totalEmissionsField.setCanEdit(false);
    totalEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceFloatField totalOptionalEmissionsField =
        new DataSourceFloatField("totalOptionalEmissions", "Total Optional Emissions");
    //FloatItem totalOptionalEmissionsItem = new FloatItem();
    //totalOptionalEmissionsField.setEditorType(totalOptionalEmissionsItem);
    totalOptionalEmissionsField.setCanEdit(false);
    totalOptionalEmissionsField.setType(ibLUsers.floatSimpleType);

    DataSourceIntegerField totalNumberOfSourcesField =
        new DataSourceIntegerField("totalNumberOfSources", "Number of Sources");
    IntegerItem totalNumberOfSourcesItem = new IntegerItem();
    totalNumberOfSourcesField.setEditorType(totalNumberOfSourcesItem);
    totalNumberOfSourcesField.setCanEdit(false);

    DataSourceTextField programTypeField = new DataSourceTextField("programType", "Program Type");
    TextItem programTypeItem = new TextItem();
    programTypeField.setEditorType(programTypeItem);
    programTypeField.setCanEdit(false);

    DataSourceDateField emissionsBeginDateField = new DataSourceDateField("emissionsBeginDate", "Emissions Begin Date");
    DateItem emissionsBeginDateItem = new DateItem();
    emissionsBeginDateField.setEditorType(emissionsBeginDateItem);
    emissionsBeginDateField.setCanEdit(false);

    DataSourceDateField emissionsEndDateField = new DataSourceDateField("emissionsEndDate", "Emissions End Date");
    DateItem emissionsEndDateItem = new DateItem();
    emissionsEndDateField.setEditorType(emissionsEndDateItem);
    emissionsEndDateField.setCanEdit(false);

    DataSourceTextField reportFileNameField = new DataSourceTextField("reportFileName", "Report File Name");
    TextItem reportFileNameItem = new TextItem();
    programTypeField.setEditorType(reportFileNameItem);
    reportFileNameField.setCanEdit(false);
    reportFileNameField.setHidden(Boolean.TRUE);

    DataSourceDateField reportGeneratedDateField = new DataSourceDateField("lastUpdated", "Report Generation Date");
    DateItem reportGeneratedDateItem = new DateItem();
    reportGeneratedDateField.setEditorType(reportGeneratedDateItem);
    reportGeneratedDateField.setCanEdit(false);

    setFields(idField, organizationIdField, stationaryCombustionEmissionsField, mobileCombustionEmissionsField,
            refridgerationAirConditioningEmissionsField, fireSuppressantEmissionsField, wasteStreamCombustionEmissionsField,
            purchasedElectricityEmissionsField, purchasedSteamEmissionsField,employeeBusinessTravelByVehicleEmissionsField,
            employeeBusinessTravelByRailEmissionsField,employeeBusinessTravelByBusEmissionsField,
            employeeBusinessTravelByAirEmissionsField,employeeCommutingByVehicleEmissionsField,
            employeeCommutingByBusEmissionsField,productTransportByVehicleEmissionsField,
            productTransportByHeavyDutyTrucksEmissionsField,productTransportByRailEmissionsField,
            productTransportByWaterAirEmissionsField,
            biomassStationaryCombustionEmissionsField,
            biomassMobileCombustionEmissionsField, totalEmissionsField, totalOptionalEmissionsField, totalDirectEmissionsField, totalInDirectEmissionsField, totalNumberOfSourcesField, programTypeField, emissionsBeginDateField, emissionsEndDateField, reportFileNameField,reportGeneratedDateField);
    //setup operations
    //1. fetch
    OperationBinding fetch =
                new OperationBinding(DSOperationType.FETCH,"emissionsSummary/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"emissionsSummary/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"emissionsSummary/calculateEmissionsSummary");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"emissionsSummary/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);

    //5. calculateEmissionsSummary
    OperationBinding calculateEmissionsSummary =
        new OperationBinding(DSOperationType.CUSTOM,"emissionsSummary/calculateEmissionsSummary");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);

    setOperationBindings(fetch, update, add, remove, calculateEmissionsSummary);
  }
}