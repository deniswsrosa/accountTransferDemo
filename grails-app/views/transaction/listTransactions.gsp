<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'transaction.label', default: 'Transaction')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-transaction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link action="newPayment" params="${[accountFrom:account.id]}"><img src="https://cdn3.iconfinder.com/data/icons/money-and-finance-1/32/transfer-funds-16.png"/> New payment</g:link></li>
            </ul>
        </div>
        <div id="list-transaction" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <f:table collection="${transactionList}" />
            <hr/>

            <g:if test="${transactionList== null || transactionList.isEmpty()}">
                <br>
                <p>   This account does not have any transactions yet.</p>
             </g:if>

            <g:if test="${transactionList!= null && !transactionList.isEmpty()}">
                <div class="pagination">
                    <g:paginate total="${transactionCount ?: 0}" />
                </div>
            </g:if>

        </div>
    </body>
</html>