<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'transaction.label', default: 'Transaction')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-transaction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            </ul>
        </div>
        <div id="create-transaction" class="content scaffold-create" role="main">
            <h1>Create new payment</h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.transaction}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.transaction}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">
                <fieldset class="form">
                     <label>Account from: </label><br/>
                     <g:select name="actFrom.id" from="${accountList}"  value="${transaction?.actFrom?.id}" optionValue="accountName" optionKey="id" /><br/>
                     <label>Account to: </label><br/>
                     <g:select name="actTo.id" from="${accountList}"  value="${transaction?.actTo?.id}" optionValue="accountName" optionKey="id" /><br/>
                     <label>Ammount: </label><br/>
                     <g:textField name="ammount" value="${transaction.ammount ?: 0}" /><br/><br/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="Pay" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
