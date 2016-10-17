package accounttransferdemo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugins.mail.MailService

@Transactional(readOnly = true)
class TransactionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MailService mailService

    def index(Integer max) {
        redirect(action: "listAccounts")
    }

    def show(Transaction transaction) {
        respond transaction
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
            def accountList = Account.list();
            respond transaction.errors, model: [transaction: transaction, accountList:accountList], view: 'newPayment'
            return
        }

        //execute the money transfer
        transaction.transferMoney()

        def actFrom = transaction.actFrom
        actFrom.validate()

        if(actFrom.hasErrors()) {
            transactionStatus.setRollbackOnly()
            def accountList = Account.list();
            transaction.errors = actFrom.errors
            respond transaction.errors, model: [transaction: transaction, accountList:accountList], view: 'newPayment'
            return
        }

        transaction.save flush:true

        def userFrom = Account.get(transaction.actFrom.id).owner
        def userTo = Account.get(transaction.actTo.id).owner
        //emails are sent asynchronously
        mailService.sendMail {
            async true
            from "donotreply@bank.com"
            to userFrom.email
            subject "Transaction Confirmation"
            text "You successfully sent "+transaction.ammount+" to "+userTo.name
        }

        mailService.sendMail {
            async true
            from "donotreply@bank.com"
            to userTo.email
            subject "Transaction Confirmation"
            text "You successfully received "+transaction.ammount+" from "+userFrom.name
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
        def selectedAccount = Account.get(id)
        def query = Transaction.where{actFrom == selectedAccount || actTo == selectedAccount }
        respond query.list(), model:[transactionCount: Transaction.count(), account:selectedAccount]
    }

    def newPayment(Integer accountFrom) {

        def transaction = new Transaction()
        transaction.actFrom = Account.get(accountFrom)
        def accountList = Account.list();
        respond  accountList, model:[transaction: transaction]
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
