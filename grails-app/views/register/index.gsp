<head>
        <!--<div id="bannerLeft" style="float:left;" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'launch_yourself.png')}" alt="ibL" border="0"></a></div>-->
        <!--<div id="bannerRight" ><a href="http://www.greenhousegas.org"><img src="${resource(dir:'images',file:'logo.gif')}" alt="ibL" border="0" /></a></div>-->
        <meta name='layout' content='register'/>
	<title><g:message code='spring.security.ui.register.title'/></title>
</head>

<body>

<p/>

<div id ="container" align="center" >
<s2ui:form width='650' height='300' elementId='loginFormContainer'
           titleCode='spring.security.ui.register.description' center='true'>

<g:form action='register' name='registerForm'>

	<g:if test='${emailSent}'>
	<br/>
	<g:message code='spring.security.ui.register.sent'/>
	</g:if>
	<g:else>

	<br/>
        
        <table>
	<tbody>

		<s2ui:textFieldRow name='firstName' labelCode='user.firstName.label' bean="${command}"
                         size='40' labelCodeDefault='First Name' value="${command.firstName}"/>

		<s2ui:textFieldRow name='lastName' labelCode='user.lastName.label' bean="${command}"
                         size='40' labelCodeDefault='Last Name' value="${command.lastName}"/>

		<s2ui:textFieldRow name='phoneNumber' labelCode='user.phoneNumber.label' bean="${command}"
                         size='40' labelCodeDefault='Phone Number' value="${command.phoneNumber}"/>

		<s2ui:textFieldRow name='organizationName' labelCode='user.organizationName.label' bean="${command}"
                         size='40' labelCodeDefault='Organization Name' value="${command.organizationName}"/>

		<s2ui:textFieldRow name='username' labelCode='user.username.label' bean="${command}"
                         size='40' labelCodeDefault='Username' value="${command.username}"/>

		<s2ui:textFieldRow name='email' bean="${command}" value="${command.email}"
		                   size='40' labelCode='user.email.label' labelCodeDefault='E-mail'/>

		<s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${command}"
                             size='40' labelCodeDefault='Password' value="${command.password}"/>

		<s2ui:passwordFieldRow name='password2' labelCode='user.password2.label' bean="${command}"
                                       size='40' labelCodeDefault='Password (again)' value="${command.password2}"/>                                                                                                                                                             
                
	</tbody>
	</table>
             <g:link controller='termsConditions' action='serveTermsConditionFile'>Terms and conditions:</g:link>
             <s2ui:checkboxRow name='acceptTerms' labelCode='user.acceptTerms.label' bean="${command}"
                   size='40' labelCodeDefault='I accept' value="${command.acceptTerms}"/>      
              <s2ui:submitButton elementId='create' form='registerForm' messageCode='spring.security.ui.register.submit'/>
	</g:else>

</g:form>

</s2ui:form>

</div>
<script>
$(document).ready(function() {
	$('#username').focus();
});
</script>

</body>
