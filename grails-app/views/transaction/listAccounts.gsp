<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'Account.label', default: 'Account')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
            <a href="#list-account" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
            <div class="nav" role="navigation">
                <ul>
                    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                    <li><g:link action="newPayment"><img src="https://cdn3.iconfinder.com/data/icons/money-and-finance-1/32/transfer-funds-16.png"/> New payment</g:link></li>
                </ul>
            </div>
            <div id="list-account" class="content scaffold-list" role="main">
                <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>

                <table>
                    <th>Account name</th>
                    <th>Balance</th>


                <g:each var="account" in="${accountList}">
                    <tr>
                        <td><a href="${createLink(controller: 'transaction',action: 'listTransactions', id:account.id)}">${account.accountName}</a></td>
                        <td>${account.balance}</td>
                    </tr>
                </g:each>

                </table>

                <div class="pagination">
                    <g:paginate total="${accountCount ?: 0}" />
                </div>
            </div>
        </body>
</html>