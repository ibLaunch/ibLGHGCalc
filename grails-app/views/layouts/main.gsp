<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
       
        <link rel="shortcut icon" href="${resource(dir:'images',file:'logo2.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="grailsLogo"><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" /></a></div>
       
        <g:layoutBody />
    </body>
</html>