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
 * Created by IntelliJ IDEA.
 * User: ibL
 * Date: Jan 11, 2011
 * Time: 3:14:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class WasteStreamCombustionInfoDS extends RestDataSource {
  private static WasteStreamCombustionInfoDS instance;

  public static WasteStreamCombustionInfoDS getInstance() {
    if (instance == null) {
      instance = new WasteStreamCombustionInfoDS();
    }
    return instance;
  }

  private WasteStreamCombustionInfoDS() {
    //set id + general stuff
    setID("wasteStreamCombustionInfoDS");
    setClientOnly(false);
    //setup fields
    DataSourceIntegerField idField =
        new DataSourceIntegerField("id", "ID");
    idField.setCanEdit(false);
    idField.setPrimaryKey(true);

    DataSourceIntegerField organizationIdField =
        new DataSourceIntegerField("organizationId", "Organization Id");
    IntegerItem organizationIdItem = new IntegerItem();
    organizationIdField.setCanEdit(false);
    organizationIdField.setForeignKey("organizationDS.id");
    organizationIdField.setEditorType(organizationIdItem);
    
    DataSourceTextField fuelSourceDescriptionField =
        new DataSourceTextField("fuelSourceDescription", "Fuel Source Description");
    TextItem fuelSourceDescriptionItem = new TextItem();
    fuelSourceDescriptionItem.setWidth("100%");
    fuelSourceDescriptionField.setEditorType(fuelSourceDescriptionItem);

    DataSourceFloatField amountOfWasterStreamGasCombustedField =
        new DataSourceFloatField("amountOfWasterStreamGasCombusted", "Amount Of Waster Stream Gas Combusted");
    FloatItem amountOfWasterStreamGasCombustedItem = new FloatItem();
    amountOfWasterStreamGasCombustedField.setEditorType(amountOfWasterStreamGasCombustedItem);

    DataSourceTextField amountOfWasterStreamGasCombustedUnitField =
        new DataSourceTextField("amountOfWasterStreamGasCombustedUnit", "Amount Of Waster Stream Gas Combusted Unit");
    TextItem amountOfWasterStreamGasCombustedUnitItem = new TextItem();
    amountOfWasterStreamGasCombustedUnitItem.setWidth("100%");
    amountOfWasterStreamGasCombustedUnitField.setEditorType(amountOfWasterStreamGasCombustedUnitItem);

    DataSourceFloatField totalNumberOfMolesPerUnitVolumentField =
        new DataSourceFloatField("totalNumberOfMolesPerUnitVolument", "Total Number Of Moles Per Unit Volument");
    FloatItem totalNumberOfMolesPerUnitVolumentItem = new FloatItem();
    totalNumberOfMolesPerUnitVolumentField.setEditorType(totalNumberOfMolesPerUnitVolumentItem);

    DataSourceTextField totalNumberOfMolesPerUnitVolumentUnitField =
        new DataSourceTextField("totalNumberOfMolesPerUnitVolumentUnit", "Amount Of Waster Stream Gas Combusted Unit");
    TextItem totalNumberOfMolesPerUnitVolumentUnitItem = new TextItem();
    totalNumberOfMolesPerUnitVolumentUnitItem.setWidth("100%");
    totalNumberOfMolesPerUnitVolumentUnitField.setEditorType(totalNumberOfMolesPerUnitVolumentUnitItem);

    DataSourceFloatField carbonMonoxideMolarFractionPercentField =
        new DataSourceFloatField("carbonMonoxideMolarFractionPercent", "Carbon Monoxide Molar Fraction Percent");
    FloatItem carbonMonoxideMolarFractionPercentItem = new FloatItem();
    carbonMonoxideMolarFractionPercentField.setEditorType(carbonMonoxideMolarFractionPercentItem);

    DataSourceFloatField carbonDioxideMolarFractionPercentField =
        new DataSourceFloatField("carbonDioxideMolarFractionPercent", "Carbon Dioxide Molar Fraction Percent");
    FloatItem carbonDioxideMolarFractionPercentItem = new FloatItem();
    carbonDioxideMolarFractionPercentField.setEditorType(carbonDioxideMolarFractionPercentItem);

    DataSourceFloatField methaneMolarFractionPercentField =
        new DataSourceFloatField("methaneMolarFractionPercent", "methaneMolarFractionPercent");
    FloatItem methaneMolarFractionPercentItem = new FloatItem();
    methaneMolarFractionPercentField.setEditorType(methaneMolarFractionPercentItem);

    DataSourceFloatField cetyleneMolarFractionPercentField =
        new DataSourceFloatField("cetyleneMolarFractionPercent", "Cetylene Molar Fraction Percent");
    FloatItem cetyleneMolarFractionPercentItem = new FloatItem();
    cetyleneMolarFractionPercentField.setEditorType(cetyleneMolarFractionPercentItem);

    DataSourceFloatField ethyleneMolarFractionPercentField =
        new DataSourceFloatField("ethyleneMolarFractionPercent", "Ethylene Molar Fraction Percent");
    FloatItem ethyleneMolarFractionPercentItem = new FloatItem();
    ethyleneMolarFractionPercentField.setEditorType(ethyleneMolarFractionPercentItem);
    
    DataSourceFloatField ethaneMolarFractionPercentField =
        new DataSourceFloatField("ethaneMolarFractionPercent", "Ethane Molar Fraction Percent");
    FloatItem ethaneMolarFractionPercentItem = new FloatItem();
    ethaneMolarFractionPercentField.setEditorType(ethaneMolarFractionPercentItem);

    DataSourceFloatField propyleneMolarFractionPercentField =
        new DataSourceFloatField("propyleneMolarFractionPercent", "Propylene Molar Fraction Percent");
    FloatItem propyleneMolarFractionPercentItem = new FloatItem();
    propyleneMolarFractionPercentField.setEditorType(propyleneMolarFractionPercentItem);

    DataSourceFloatField propaneMolarFractionPercentField =
        new DataSourceFloatField("propaneMolarFractionPercent", "Propane Molar Fraction Percent");
    FloatItem propaneMolarFractionPercentItem = new FloatItem();
    propaneMolarFractionPercentField.setEditorType(propaneMolarFractionPercentItem);

    DataSourceFloatField n_ButaneMolarFractionPercentField =
        new DataSourceFloatField("n_ButaneMolarFractionPercent", "n_Butane Molar Fraction Percent");
    FloatItem n_ButaneMolarFractionPercentItem = new FloatItem();
    n_ButaneMolarFractionPercentField.setEditorType(n_ButaneMolarFractionPercentItem);

    DataSourceFloatField benzeneMolarFractionPercentField =
        new DataSourceFloatField("benzeneMolarFractionPercent", "Benzene Molar Fraction Percent");
    FloatItem benzeneMolarFractionPercentItem = new FloatItem();
    benzeneMolarFractionPercentField.setEditorType(benzeneMolarFractionPercentItem);

    DataSourceFloatField bexaneMolarFractionPercentField =
        new DataSourceFloatField("bexaneMolarFractionPercent", "Bexane Molar Fraction Percent");
    FloatItem bexaneMolarFractionPercentItem = new FloatItem();
    bexaneMolarFractionPercentField.setEditorType(bexaneMolarFractionPercentItem);

    DataSourceFloatField tolueneMolarFractionPercentField =
        new DataSourceFloatField("tolueneMolarFractionPercent", "Toluene Molar Fraction Percent");
    FloatItem tolueneMolarFractionPercentItem = new FloatItem();
    tolueneMolarFractionPercentField.setEditorType(tolueneMolarFractionPercentItem);

    DataSourceFloatField octaneMolarFractionPercentField =
        new DataSourceFloatField("octaneMolarFractionPercent", "Octane Molar Fraction Percent");
    FloatItem octaneMolarFractionPercentItem = new FloatItem();
    octaneMolarFractionPercentField.setEditorType(octaneMolarFractionPercentItem);

    DataSourceFloatField ethanolMolarFractionPercentField =
        new DataSourceFloatField("ethanolMolarFractionPercent", "Ethanol Molar Fraction Percent");
    FloatItem ethanolMolarFractionPercentItem = new FloatItem();
    ethanolMolarFractionPercentField.setEditorType(ethanolMolarFractionPercentItem);

    DataSourceFloatField acetoneMolarFractionPercentField =
        new DataSourceFloatField("acetoneMolarFractionPercent", "Acetone Molar Fraction Percent");
    FloatItem acetoneMolarFractionPercentItem = new FloatItem();
    acetoneMolarFractionPercentField.setEditorType(acetoneMolarFractionPercentItem);

    DataSourceFloatField tetrahydrofuranMolarFractionPercentField =
        new DataSourceFloatField("tetrahydrofuranMolarFractionPercent", "Tetrahydrofuran Molar Fraction Percent");
    FloatItem tetrahydrofuranMolarFractionPercentItem = new FloatItem();
    tetrahydrofuranMolarFractionPercentField.setEditorType(tetrahydrofuranMolarFractionPercentItem);

    DataSourceFloatField otherNon_CMolarFractionPercentField =
        new DataSourceFloatField("otherNon_CMolarFractionPercent", "Other Non_C Molar Fraction Percent");
    FloatItem otherNon_CMolarFractionPercentItem = new FloatItem();
    otherNon_CMolarFractionPercentField.setEditorType(otherNon_CMolarFractionPercentItem);

    DataSourceFloatField oxidationFactorPercentField =
        new DataSourceFloatField("oxidationFactorPercent", "Oxidation Factor Percent");
    FloatItem oxidationFactorPercentItem = new FloatItem();
    oxidationFactorPercentField.setEditorType(oxidationFactorPercentItem);

    DataSourceDateTimeField fuelUsedBeginDateField =
        new DataSourceDateTimeField("fuelUsedBeginDate", "Begin Date");
    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setWidth("100%");
    fuelUsedBeginDateField.setEditorType(fuelUsedBeginDateItem);

    DataSourceDateTimeField fuelUsedEndDateField =
        new DataSourceDateTimeField("fuelUsedEndDate", "End Date");
    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setWidth("100%");
    fuelUsedEndDateField.setEditorType(fuelUsedEndDateItem);

    setFields(idField, organizationIdField, fuelSourceDescriptionField, 
            amountOfWasterStreamGasCombustedField,
            amountOfWasterStreamGasCombustedUnitField,
            totalNumberOfMolesPerUnitVolumentField,
            totalNumberOfMolesPerUnitVolumentUnitField,      
            carbonMonoxideMolarFractionPercentField,
            carbonDioxideMolarFractionPercentField,
            methaneMolarFractionPercentField,
            cetyleneMolarFractionPercentField,
            ethyleneMolarFractionPercentField,
            ethaneMolarFractionPercentField,
            propyleneMolarFractionPercentField,
            propaneMolarFractionPercentField,
            n_ButaneMolarFractionPercentField,
            benzeneMolarFractionPercentField,
            bexaneMolarFractionPercentField,
            tolueneMolarFractionPercentField,
            octaneMolarFractionPercentField,
            ethanolMolarFractionPercentField,
            acetoneMolarFractionPercentField,
            tetrahydrofuranMolarFractionPercentField,
            otherNon_CMolarFractionPercentField,
            fuelUsedBeginDateField, fuelUsedEndDateField
            );

    //setup operations
    //1. fetch
    OperationBinding fetch =
        new OperationBinding(DSOperationType.FETCH,"wasteStreamCombustionInfo/list");
    fetch.setDataProtocol(DSProtocol.POSTPARAMS);
    //2. update
    OperationBinding update =
        new OperationBinding(DSOperationType.UPDATE,"wasteStreamCombustionInfo/save");
    update.setDataProtocol(DSProtocol.POSTPARAMS);
    //3. add
    OperationBinding add =
        new OperationBinding(DSOperationType.ADD,"wasteStreamCombustionInfo/save");
    add.setDataProtocol(DSProtocol.POSTPARAMS);
    //4. remove
    OperationBinding remove =
        new OperationBinding(DSOperationType.REMOVE,"wasteStreamCombustionInfo/remove");
    remove.setDataProtocol(DSProtocol.POSTPARAMS);
    setOperationBindings(fetch, update, add, remove);
    //setClientOnly(true);
  }

}