<html>
    <head>        
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'logo2.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
       <!-- <div id="bannerLeft" style="float:left;" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'launch_yourself.png')}" alt="ibL" border="0"></a></div> -->
       <!-- <div id="bannerRight" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" /></a></div> -->        
        <sec:ifLoggedIn>
            Welcome <sec:username/>! <g:link controller="logout">sign out</g:link>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <g:link controller='login' action='auth'>Login</g:link>
        </sec:ifNotLoggedIn>
        <g:layoutBody />

    </body>
</html>