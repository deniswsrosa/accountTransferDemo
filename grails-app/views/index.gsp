<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <content tag="nav">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Application Status <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Environment: ${grails.util.Environment.current.name}</a></li>
                <li><a href="#">App profile: ${grailsApplication.config.grails?.profile}</a></li>
                <li><a href="#">App version:
                    <g:meta name="info.app.version"/></a>
                </li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Grails version:
                    <g:meta name="info.app.grailsVersion"/></a>
                </li>
                <li><a href="#">Groovy version: ${GroovySystem.getVersion()}</a></li>
                <li><a href="#">JVM version: ${System.getProperty('java.version')}</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Artefacts <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Controllers: ${grailsApplication.controllerClasses.size()}</a></li>
                <li><a href="#">Domains: ${grailsApplication.domainClasses.size()}</a></li>
                <li><a href="#">Services: ${grailsApplication.serviceClasses.size()}</a></li>
                <li><a href="#">Tag Libraries: ${grailsApplication.tagLibClasses.size()}</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Installed Plugins <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
                    <li><a href="#">${plugin.name} - ${plugin.version}</a></li>
                </g:each>
            </ul>
        </li>
    </content>


    <div id="content" role="main">
        <section class="row colset-2-its">
            <h1>About</h1>

            <p>
                This is a web demo application using Grails 3.2 and Java 8, tested on Ubuntu 16.04.1 LTS.
            </p>
            <p>
                The database has been populated with some initial accounts, but you can add more at "Manage Accounts" Page.
            </p>

            <p>
                To test emails, please download and run <a href="https://nilhcem.github.io/FakeSMTP/"> FakeSMTP </a>
            </p>

            <p>
                If you have any questions, please contatc me by email or at <a href="https://www.linkedin.com/in/deniswsrosa"> deniswsrosa </a>
             </p>
            <br/>
            <hr/>
            <br/>
            <h1>Let's Start!</h1>
            <p>Choose bellow what you would like to do </p>

            <ul>
                <li><a href="/user/index">Manage Users</a></li>
                <li><a href="/account/index">Manage Accounts</a></li>
                <li><a href="/transaction/index">View Transaction</a></li>
            </ul>


        </section>
    </div>

</body>
</html>
