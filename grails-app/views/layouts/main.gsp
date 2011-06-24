<html>
    <head>        
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'logo2.ico')}" type="image/x-icon" />

        <!--<div id="heading"  align="left" style="background-color: #FFFFFF;height: 30px;margin-left: 5em;margin-right: 5em;"> -->
          <div align="left" style="vertical-align: middle;background-color: #FFFFFF;height: 30px;">
            <img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" height="35px" />
            <sec:ifLoggedIn>
            Logged in as <sec:username/> <g:link controller="logout">sign out</g:link>
            </sec:ifLoggedIn>
            <sec:ifSwitched>
            <a href='${request.contextPath}/j_spring_security_exit_user'>
               Resume as <sec:switchedUserOriginalUsername/>
            </a>
            </sec:ifSwitched>

            <sec:ifNotSwitched>
               <sec:ifAllGranted roles='ROLE_SWITCH_USER'>

               <form action='${request.contextPath}/j_spring_security_switch_user' method='POST'>
                  Switch to user: <input type='text' name='j_username'/><br/>
                  <input type='submit' value='Switch'/>
               </form>

               </sec:ifAllGranted>
            </sec:ifNotSwitched>
          </div>
        <!--</div>-->
    
        <g:layoutHead />
        <g:javascript library="application" />

        <style type="text/css" media="screen">

        * {          
          margin: 0;          
        }

        html, body {
           height: 100%;
           /*
           padding-left: 2em;
           padding-right: 2em;
           
           margin-left: 10%;
           margin-right: 10%;
           */
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
          background:#B0E0E6;
          /*
          margin-left: 5em;
          margin-right: 5em;
          */
        }
        </style>
    </head>

    <body bgcolor="#E0FFFF" >
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
       <div class="mainContainer">

            <g:layoutBody />

            <div class="clearfooter"></div>
        </div>
        <div class="footer">
          <h4><pre>Copyright © 2011 ibLaunch Energy, Inc. All rights reserved.;  Contact: support@ibLaunchEnergy.com </pre></h4>
        </div>
      
    </body>
</html>