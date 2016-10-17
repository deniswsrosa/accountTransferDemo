package accounttransferdemo

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Transaction)
@Mock([accounttransferdemo.Account, accounttransferdemo.User])
class TransactionSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test transfer money from same account"() {

        when:
            def user = new accounttransferdemo.User(name:"John", email:"john@test.com")
            def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0, owner:user)
            account1.save();
            def trans = new accounttransferdemo.Transaction(ammount:100, actFrom:account1,actTo:account1)
            trans.validate()

        then:
        trans.hasErrors()
    }




    void "transfer money simple test"() {

        def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0)
        def account2 = new accounttransferdemo.Account(accountName: "Account2", balance:200.0)
        def transaction = new accounttransferdemo.Transaction(ammount:20,actFrom:account1,actTo:account2 )

        when:
        transaction.transferMoney()

        then:
        account1.balance == 180
        account2.balance == 220
    }

    void "transfer money initial value test"() {

        def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0)
        def account2 = new accounttransferdemo.Account(accountName: "Account2", balance:200.0)
        def transaction = new accounttransferdemo.Transaction(ammount:50,actFrom:account1,actTo:account2 )

        when:
        transaction.transferMoney()

        then:
        account1.balance != 200
        account2.balance != 200
    }

    void "transfer money and giving back"() {

        def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0)
        def account2 = new accounttransferdemo.Account(accountName: "Account2", balance:200.0)
        def transaction = new accounttransferdemo.Transaction(ammount:50,actFrom:account1,actTo:account2 )
        def givingBack = new accounttransferdemo.Transaction(ammount:50,actFrom:account2,actTo:account1 )

        when:
        transaction.transferMoney()
        givingBack.transferMoney()

        then:
        account1.balance == 200
        account2.balance == 200
    }

    void "overdraft money transfer"() {

        int ammount = 2000;
        def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0)
        def account2 = new accounttransferdemo.Account(accountName: "Account2", balance:200.0)
        def transaction = new accounttransferdemo.Transaction(ammount:50,actFrom:account1,actTo:account2 )

        when:
        transaction.transferMoney()
        account1.validate()
        then:
        account1.hasErrors()
    }
}
