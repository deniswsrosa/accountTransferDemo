package accounttransferdemo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugins.mail.MailService

@Transactional(readOnly = true)
class TransactionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MailService mailService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Transaction.list(params), model:[transactionCount: Transaction.count()]
    }

    def show(Transaction transaction) {
        respond transaction
    }

    def create() {
        respond new Transaction(params)
    }

    @Transactional
    def save(Transaction transaction) {
        if (transaction == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transaction.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transaction.errors, view:'create'
            return
        }

        def actFrom = transaction.actFrom
        actFrom.transferMoney(transaction.actTo, transaction.ammount)

        actFrom.validate()
        if(actFrom.hasErrors()) {
            transactionStatus.setRollbackOnly()
            def accountList = Account.list()- transaction.actFrom;
            transaction.errors = actFrom.errors
            respond transaction.errors, model: [transaction: transaction, accountList:accountList], view: 'newPayment'
            return
        }

        transaction.save flush:true

        //emails are sent asynchronously
        mailService.sendMail {
            async true
            from "donotreply@bank.com"
            to Account.get(transaction.actFrom.id).owner.email
            subject "Transaction Confirmation"
            text "You successfully sent "+transaction.ammount+" to "+transaction.to.owner.name
        }

        mailService.sendMail {
            async true
            from "donotreply@bank.com"
            to Account.get(transaction.actTo.id).owner.email
            subject "Transaction Confirmation"
            text "You successfully received "+transaction.ammount+" from "+transaction.from.owner.name
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transaction.label', default: 'Transaction'), transaction.id])
                redirect transaction
            }
            '*' { respond transaction, [status: CREATED] }
        }
    }

    def listAccounts(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Account.list(params), model:[accountCount: Account.count()]
    }

    def listTransactions(Integer id) {
        println "**********************"
        def selectedAccount = Account.get(id)
        def query = Transaction.where{actFrom == selectedAccount || actTo == selectedAccount }
        respond query.list(), model:[transactionCount: Transaction.count(), account:selectedAccount]
    }

    def newPayment(Integer accountFrom) {

        def transaction = new Transaction()
        transaction.actFrom = Account.get(accountFrom)
        def accountList = Account.list()- transaction.actFrom;

        respond  accountList, model:[transaction: transaction]
    }


    @Transactional
    def update(Transaction transaction) {
        if (transaction == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transaction.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transaction.errors, view:'edit'
            return
        }

        transaction.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'transaction.label', default: 'Transaction'), transaction.id])
                redirect transaction
            }
            '*'{ respond transaction, [status: OK] }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transaction.label', default: 'Transaction'), params.id])
                redirect action: "listAccounts", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
