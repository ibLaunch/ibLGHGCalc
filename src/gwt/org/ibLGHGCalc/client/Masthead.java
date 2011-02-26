package org.ibLGHGCalc.client;


import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;

public class Masthead extends HLayout {

  private static final int MASTHEAD_HEIGHT = 60;

  Label label;

  public Masthead() {
    super();

    GWT.log("init Masthead()...", null);

    // initialise the layout container
    //this.setHeight(MASTHEAD_HEIGHT);
    //this.setBackgroundColor("#C3D9FF");
    /*
    // initialise the masthead label
    label = new Label();
    label.setContents("Masthead");
    label.setAlign(Alignment.CENTER);
    label.setOverflow(Overflow.HIDDEN);

    // add the label to the layout container
    this.addMember(label);
    */
    //Img launchImg = new Img("/ibLGHGCalc/images/launch_yourself.png");
    //Img logoImg = new Img("/ibLGHGCalc/images/logo.gif");
    //Img headerImg = new Img("/ibLGHGCalc/images/sun.gif", 1000,60);
    Img headerImg = new Img(GWT.getHostPageBaseURL()+"images/small_banner.JPG", 1000,60);
    //Img logoImg = new Img("/ibLGHGCalc/images/logo_alone.png");

    //Img headerImg = new Img("/ibLGHGCalc/images/sun.gif");
    headerImg.setImageType(ImageStyle.TILE);
    headerImg.setHeight100();
    headerImg.setWidth100();
    //headerImg.setBorder("2px solid black");
    headerImg.setShowTitle(Boolean.TRUE);
    headerImg.setTitle("eFootprint !");
    //headerImg.setTitleStyle("")
    
    // initialise the Name label
    //Label welcomeLabel = new Label();
    //welcomeLabel.setContents("Welcome to eFootprint!");

    // initialise the West layout container
    HLayout westLayout = new HLayout();
    //westLayout.setAutoHeight();
    westLayout.setHeight(MASTHEAD_HEIGHT);
    westLayout.setWidth100();
    westLayout.setHeight100();
    //westLayout.addMember(logoImg);
    westLayout.addMember(headerImg);

    
    //westLayout.addMember(welcomeLabel);

   // initialise Signed In User label
/*
    // initialise the East layout container
    HLayout eastLayout = new HLayout();
    eastLayout.setAlign(Alignment.RIGHT);
    eastLayout.setHeight(MASTHEAD_HEIGHT);
    eastLayout.setWidth("50%");
    eastLayout.addMember(logoImg);
 *
 */
    // add the West and East layout containers to the Masthead layout container
    //this.setAutoHeight();
    this.addMember(westLayout);
    //this.addMember(eastLayout);
  }
}