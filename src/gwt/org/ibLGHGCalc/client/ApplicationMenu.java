package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.*;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
//import org.ibLGHGCalc.client.ibLUsers;

public class ApplicationMenu extends HLayout {

  private static final int APPLICATION_MENU_HEIGHT = 42;
  private static final int MEMBERS_MARGIN = 5;
  private static final int PADDING_SIZE = 3;
  private static final int IMENU_BUTTON_HEIGHT = 23;
  private static final int IMENU_BUTTON_WIDTH = 120;
  private static final int SHADOW_DEPTH = 5;
  private static final Menu emissionSourcesMenu = new Menu();;
  public static final Menu directEmissionsSourcesSubMenu = new Menu();;
  public static final Menu inDirectEmissionsSourcesSubMenu = new Menu();;
  public static final Menu optionalEmissionsSourcesSubMenu = new Menu();;
  public static final Menu organizationSubMenu = new Menu();;
  public static final Menu reportSubMenu = new Menu();;
  public static final Menu fileUploadSubMenu = new Menu();;

  public ApplicationMenu() {
    super();

    GWT.log("init Application Menu()...", null);

    //this.setAlign(VerticalAlignment.BOTTOM);
    //this.setAlign(Alignment.LEFT);

    // initialise the layout container
    this.setHeight(APPLICATION_MENU_HEIGHT);
    //this.setBackgroundColor("#00FFFF");
    this.setBackgroundColor("#A9F5F2");
    //this.setDefaultLayoutAlign(Alignment.CENTER);
    //this.setAlign(VerticalAlignment.CENTER);
    //---
    
    /*
    Label emissionSourceLabel =new Label();
    emissionSourceLabel.setContents("Emission Sources:");
    emissionSourceLabel.setAlign(Alignment.CENTER);
    emissionSourceLabel.setOverflow(Overflow.HIDDEN);
    */
    
//-- Top menu
   //emissionSourcesMenu =
   emissionSourcesMenu.setShowShadow(true);
   emissionSourcesMenu.setShadowDepth(10);

   MenuItem directEmissionSourcesItem = new MenuItem("Direct Emission Sources", "/ibLGHGCalc/images/logo.gif", "Ctrl+N");
   MenuItem inDirectEmissionSourcesItem = new MenuItem("In-Direct Emission Sources", "/ibLGHGCalc/images/logo.gif", "Ctrl+N");
   MenuItem optionalEmissionSourcesItem = new MenuItem("Optional Emission Sources", "/ibLGHGCalc/images/logo.gif", "Ctrl+N");

   MenuItemSeparator separator = new MenuItemSeparator();

    //Menu directEmissionsSourcesSubMenu = new Menu();
    directEmissionsSourcesSubMenu.setItems(
            new MenuItem("Stationary Combustions Sources"),
            new MenuItem("Mobile Combustions Sources"),
            new MenuItem("Refridgeration and Air Conditioning Sources"),
            new MenuItem("Fire Suppression Sources"),
            new MenuItem("Waste Stream Combustions Sources"));
    directEmissionSourcesItem.setSubmenu(directEmissionsSourcesSubMenu);

    //Menu inDirectEmissionsSourcesSubMenu = new Menu();
    inDirectEmissionsSourcesSubMenu.setItems(
            new MenuItem("Purchased Electricity"),
            new MenuItem("Purchased Steam"));
    inDirectEmissionSourcesItem.setSubmenu(inDirectEmissionsSourcesSubMenu);

    //Menu optionalEmissionsSourcesSubMenu = new Menu();
    optionalEmissionsSourcesSubMenu.setItems(
            new MenuItem("Employee Business Travel"),
            new MenuItem("Employee Commuting"),
            new MenuItem("Product Transport"));
    optionalEmissionSourcesItem.setSubmenu(optionalEmissionsSourcesSubMenu);


   organizationSubMenu.setItems(
           new MenuItem("Organization Profile"),
           new MenuItem("Change Inventory Year"),
           new MenuItem("Dashboard")
           );

   reportSubMenu.setItems(
           new MenuItem("Calculate Emissions"),
           new MenuItem("Emissions Summary"),
           new MenuItem("Emissions Report")
           );

   fileUploadSubMenu.setItems(
           new MenuItem("Load Data"),
           new MenuItem("Load Emission Factors")
           );

   emissionSourcesMenu.setItems(directEmissionSourcesItem, inDirectEmissionSourcesItem,separator, optionalEmissionSourcesItem);
   emissionSourcesMenu.setShowSubmenus(Boolean.TRUE);
   //emissionSourcesMenu.setShowMenuButtonImag
   //emissionSourcesMenu.

   IMenuButton emissionSourceMenuButton = new IMenuButton("Emission Sources", emissionSourcesMenu);
   emissionSourceMenuButton.setLayoutAlign(VerticalAlignment.CENTER);
   emissionSourceMenuButton.setWidth100();
   //emissionSourceMenuButton.setMenuAnimationEffect("fade");
   emissionSourceMenuButton.setShowShadow(true);
   emissionSourceMenuButton.setShadowDepth(SHADOW_DEPTH);
   emissionSourceMenuButton.setShowMenuButtonImage(true);
   //emissionSourceMenuButton.setPadding(PADDING_SIZE);

//--
   IMenuButton directEmissionSourceMenuButton = new IMenuButton("Direct Emissions", directEmissionsSourcesSubMenu);
   //IMenuButton directEmissionSourceMenuButton = new IMenuButton("Scope 1", directEmissionsSourcesSubMenu);
   //directEmissionSourceMenuButton.setWidth100();
   directEmissionSourceMenuButton.setLayoutAlign(VerticalAlignment.CENTER);
   directEmissionSourceMenuButton.setWidth(IMENU_BUTTON_WIDTH);
   directEmissionSourceMenuButton.setHeight(IMENU_BUTTON_HEIGHT);
   //directEmissionSourceMenuButton.setMenuAnimationEffect("fade");
   directEmissionSourceMenuButton.setShowShadow(true);
   directEmissionSourceMenuButton.setShadowDepth(SHADOW_DEPTH);
   directEmissionSourceMenuButton.setShowMenuButtonImage(true);
   //directEmissionSourceMenuButton.setLayoutAlign(VerticalAlignment.CENTER);
   //directEmissionSourceMenuButton.setPadding(PADDING_SIZE);
//--
   IMenuButton inDirectEmissionSourceMenuButton = new IMenuButton("In-Direct Emissions", inDirectEmissionsSourcesSubMenu);
   inDirectEmissionSourceMenuButton.setLayoutAlign(VerticalAlignment.CENTER);

   //IMenuButton inDirectEmissionSourceMenuButton = new IMenuButton("Scope 2", inDirectEmissionsSourcesSubMenu);
   //inDirectEmissionSourceMenuButton.setWidth100();
   inDirectEmissionSourceMenuButton.setWidth(IMENU_BUTTON_WIDTH);
   inDirectEmissionSourceMenuButton.setHeight(IMENU_BUTTON_HEIGHT);
   //inDirectEmissionSourceMenuButton.setMenuAnimationEffect("fade");
   inDirectEmissionSourceMenuButton.setShowShadow(true);
   inDirectEmissionSourceMenuButton.setShadowDepth(SHADOW_DEPTH);
   inDirectEmissionSourceMenuButton.setShowMenuButtonImage(true);
   //inDirectEmissionSourceMenuButton.setPadding(PADDING_SIZE);

//--
   IMenuButton optionalEmissionSourceMenuButton = new IMenuButton("Optional Emissions", optionalEmissionsSourcesSubMenu);
   optionalEmissionSourceMenuButton.setLayoutAlign(VerticalAlignment.CENTER);

   //IMenuButton optionalEmissionSourceMenuButton = new IMenuButton("Scope 3", optionalEmissionsSourcesSubMenu);
   //optionalEmissionSourceMenuButton.setWidth100();
   optionalEmissionSourceMenuButton.setWidth(IMENU_BUTTON_WIDTH);
   optionalEmissionSourceMenuButton.setHeight(IMENU_BUTTON_HEIGHT);
   //optionalEmissionSourceMenuButton.setMenuAnimationEffect("fade");
   optionalEmissionSourceMenuButton.setShowShadow(true);
   optionalEmissionSourceMenuButton.setShadowDepth(SHADOW_DEPTH);
   optionalEmissionSourceMenuButton.setShowMenuButtonImage(true);
   //optionalEmissionSourceMenuButton.setPadding(PADDING_SIZE);

//-----
   IMenuButton organizationMenuButton = new IMenuButton("Organization", organizationSubMenu);
   organizationMenuButton.setLayoutAlign(VerticalAlignment.CENTER);
   //organizationMenuButton.setWidth100();
   organizationMenuButton.setWidth(IMENU_BUTTON_WIDTH);
   organizationMenuButton.setHeight(IMENU_BUTTON_HEIGHT);
   //optionalEmissionSourceMenuButton.setMenuAnimationEffect("fade");
   organizationMenuButton.setShowShadow(true);
   organizationMenuButton.setShadowDepth(SHADOW_DEPTH);
   organizationMenuButton.setShowMenuButtonImage(true);
   //organizationMenuButton.setPadding(PADDING_SIZE);

//-----
   IMenuButton reportMenuButton = new IMenuButton("Report", reportSubMenu);
   reportMenuButton.setLayoutAlign(VerticalAlignment.CENTER);
   //reportMenuButton.setWidth100();
   reportMenuButton.setWidth(IMENU_BUTTON_WIDTH);
   reportMenuButton.setHeight(IMENU_BUTTON_HEIGHT);
   //optionalEmissionSourceMenuButton.setMenuAnimationEffect("fade");
   reportMenuButton.setShowShadow(true);
   reportMenuButton.setShadowDepth(SHADOW_DEPTH);
   reportMenuButton.setShowMenuButtonImage(true);
   //reportMenuButton.setPadding(PADDING_SIZE);

//-----
   IMenuButton dataUploadMenuButton = new IMenuButton("Upload Data", fileUploadSubMenu);
   dataUploadMenuButton.setLayoutAlign(VerticalAlignment.CENTER);
   //dataUploadMenuButton.setWidth100();
   dataUploadMenuButton.setWidth(IMENU_BUTTON_WIDTH);
   dataUploadMenuButton.setHeight(IMENU_BUTTON_HEIGHT);
   //optionalEmissionSourceMenuButton.setMenuAnimationEffect("fade");
   dataUploadMenuButton.setShowShadow(true);
   dataUploadMenuButton.setShadowDepth(SHADOW_DEPTH);
   dataUploadMenuButton.setShowMenuButtonImage(true);
   //dataUploadMenuButton.setPadding(PADDING_SIZE);

/*
//-- Top Menu click handler

    directEmissionsSourcesSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            ibLUsers.displayEmissionSourceInfo(item.getTitle());
        }
    });

    inDirectEmissionsSourcesSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            ibLUsers.displayEmissionSourceInfo(item.getTitle());
        }
    });

    optionalEmissionsSourcesSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            ibLUsers.displayEmissionSourceInfo(item.getTitle());
        }
    });
*/
    // add the label to the layout container
    //HLayout emissionSourcesHLayout = new HLayout();
   
    this.addMember(organizationMenuButton);
    //this.addMember(emissionSourceMenuButton);
    this.addMember(directEmissionSourceMenuButton);
    this.addMember(inDirectEmissionSourceMenuButton);
    this.addMember(optionalEmissionSourceMenuButton);
    this.addMember(reportMenuButton);
    this.addMember(dataUploadMenuButton);
    //this.setIsGroup(Boolean.TRUE);
    //this.setGroupTitle("Application Menu");
    this.setMembersMargin(MEMBERS_MARGIN);
    
    //this.setWidth("80%");
    
    //emissionSourcesHLayout.setBorder("1px double orange");
    //emissionSourcesHLayout.setTitle("Edit Emission Sources");
    //emissionSourcesHLayout.setShowEdges(true);
    //this.addMember(emissionSourcesHLayout);
  }

  public Menu getDirectEmissionsSourcesSubMenu() {
    return directEmissionsSourcesSubMenu;
  }

  public Menu getInDirectEmissionsSourcesSubMenu() {
     return inDirectEmissionsSourcesSubMenu;
  }

  public Menu getOptionalEmissionsSourcesSubMenu() {
      return optionalEmissionsSourcesSubMenu;
  }

}