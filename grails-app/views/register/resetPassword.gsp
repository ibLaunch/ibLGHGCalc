<head>
<div id="bannerLeft" style="float:left;" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'launch_yourself.png')}" alt="ibL" border="0"></a></div>
<div id="bannerRight" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" /></a></div>
<title><g:message code='spring.security.ui.resetPassword.title'/></title>
<meta name='layout' content='register'/>
</head>

<body>

<p/>

<s2ui:form width='475' height='250' elementId='resetPasswordFormContainer'
           titleCode='spring.security.ui.resetPassword.header' center='true'>

	<g:form action='resetPassword' name='resetPasswordForm' autocomplete='off'>
	<g:hiddenField name='t' value='${token}'/>
	<div class="sign-in">

	<br/>
	<h4><g:message code='spring.security.ui.resetPassword.description'/></h4>

	<table>
		<s2ui:passwordFieldRow name='password' labelCode='resetPasswordCommand.password.label' bean="${command}"
                             labelCodeDefault='Password' value="${command?.password}"/>

		<s2ui:passwordFieldRow name='password2' labelCode='resetPasswordCommand.password2.label' bean="${command}"
                             labelCodeDefault='Password (again)' value="${command?.password2}"/>
	</table>

	<s2ui:submitButton elementId='reset' form='resetPasswordForm' messageCode='spring.security.ui.resetPassword.submit'/>

	</div>
	</g:form>

	</div>
</s2ui:form>

<script>
$(document).ready(function() {
	$('#password').focus();
});
</script>

</body>
