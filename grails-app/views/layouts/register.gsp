<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title><g:layoutTitle default='User Registration'/></title>


<!--<div id="tagLine" align="center" style="background-color: #6cf;height: 60px;">-->
<div id="tagLine" align="center" style="background-color: #FFFFFF;height: 60px; bottom: 20px">
  <!--<div style="margin-top: 30px; vertical-align: middle;">-->
    <img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" align="left"/>
    <h2 style="margin-top: 25px">
      <!--<img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" />-->
      Manage your impact on environment, save your bottom line!
    </h2>
  <!--</div>-->
</div>
<!--<div id="bannerLeft" align="center" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'sun.gif')}" alt="ibL" border="0" height="60"  width="1000"> </a></div>-->
<!--<link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon"/>-->
<link rel="shortcut icon" href="${resource(dir:'images',file:'logo2.ico')}" type="image/x-icon"/>

<g:javascript library='jquery' plugin='jquery' />

<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'reset.css')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'spring-security-ui.css')}"/>
<jqui:resources />
<link rel="stylesheet" media="screen" href="${resource(dir:'css/smoothness',file:'jquery-ui-1.8.2.custom.css',plugin:'spring-security-ui')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.jgrowl.css')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'jquery.safari-checkbox.css')}"/>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'auth.css')}"/>

<g:layoutHead/>
<!--<h3 style="text-align:center;">Welcome! Know your impact on environment, save your bottom line!</h3>-->

<style type="text/css" media="screen">

* {
  margin: 0;
}

html, body {
   height: 100%;
}

.mainContainer{
  min-height: 100%;
  height: auto !important;
  height: 100%;
  margin: 0 auto -1em;
}

.clearfooter {
  height: 1em;
}

.footer {
  height: 1em;
  background:#6cf;
}
</style>

</head>

<body style='background-color:PowderBlue; text-align:center; height:100%;' >
  <div class="mainContainer">
    <g:javascript src='jquery/jquery.jgrowl.js'/>
    <g:javascript src='jquery/jquery.checkbox.js'/>
    <g:javascript src='spring-security-ui.js'/>
    <g:layoutBody/>

    <s2ui:showFlash/>
    <div class="clearfooter"></div>
  </div>
<!--<div id="footer" style="padding-top: 20px">-->
<div class="footer">
  <h4><pre>Copyright © 2011 ibLaunch Energy, Inc. All rights reserved.;  Contact: support@ibLaunchEnergy.com </pre></h4>
</div>
</body>
</html>

