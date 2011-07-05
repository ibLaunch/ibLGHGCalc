<head>
<!--<div id="bannerLeft" style="float:left;" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'launch_yourself.png')}" alt="ibL" border="0"></a></div> -->
<!--<div id="bannerLeft" align="center" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'sun.gif')}" alt="ibL" border="0" height="60" width="1000"> </a></div>-->
<!--<div id="bannerRight" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" /></a></div>-->
<!--<title><g:message code='spring.security.ui.login.title'/></title>-->

<style type="text/css" media="screen">
  #whyDiv{
      float:left; width:45%; padding-left: 4em; 
  }

  #loginDiv{
      margin-left:45%; width:55%;
  }
</style>

<meta name='layout' content='register'/>
</head>

<body style='background-color:PowderBlue;' >

<p/>
<div>
<div id="whyDiv" align="center">
  </br>
  <!--<table border="1" cellpadding="5" cellspacing="5" width="50%" style="background-color:#FFF;border:2px solid orange;">-->
  <table border="0" cellpadding="5" cellspacing="0" width="100%" style="color: #606060 ;">
    <tr>
      <th align ="left" style="font-size: medium;">Why use iGreenLaunch.com for your organization?</th>
    </tr>
    <tr>
      <td>
        <li>Know the impact of your organization on the environment</li>
        <li>Start tracking your Greenhouse Gas emission sources</li>
        <li>Create environment friendly image for your organization</li>
        <li>Get your Greenhouse Gas emissions report</li>
        <li>Provide your Greenhouse Gas emissions report to different stake holders</li>
        <li>and many more....</li>
      </td>
    </tr>
  </table>
  </br>
  <p style="color: #A8A8A8 ;">This is a beta release. Also please note that currently iGreenLaunch.com only supports organizations based in the United States, we are currently working to expand that.</p>
  
</div>
</div>
<div id="loginDiv" class="login s2ui_center ui-corner-all" style='background-color:PowderBlue;'   >
        </br>
        <div class="login-inner" align="center">
	<form action='${postUrl}' method='POST' id="loginForm" name="loginForm" autocomplete='off' >
	<div class="sign-in" >

	<!--<h1><g:message code='spring.security.ui.login.signin'/></h1>-->

        <table>
		<tr>
			<td><label for="username"><g:message code='spring.security.ui.login.username'/></label></td>
			<td><input name="j_username" id="username" size="20" /></td>
		</tr>
		<tr>
			<td><label for="password"><g:message code='spring.security.ui.login.password'/></label></td>
			<td><input type="password" name="j_password" id="password" size="20" /></td>
		</tr>
		<tr>
			<td colspan='2'>
				<input type="checkbox" class="checkbox" name="${rememberMeParameter}" id="remember_me" checked="checked" />
				<label for='remember_me'><g:message code='spring.security.ui.login.rememberme'/></label> |
				<span class="forgot-link">
					<g:link controller='register' action='forgotPassword'><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
				</span>
			</td>
		</tr>
		<tr>
			<td colspan='2'>
				<s2ui:linkButton elementId='register' controller='register' messageCode='spring.security.ui.login.register'/>
				<s2ui:submitButton elementId='loginButton' form='loginForm' messageCode='spring.security.ui.login.login'/>
			</td>
		</tr>
	</table>

	</div>
	</form>
	</div>

</div>

<script>
$(document).ready(function() {
	$('#username').focus();
});

<s2ui:initCheckboxes/>

</script>

</body>
