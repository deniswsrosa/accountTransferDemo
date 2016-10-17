package accounttransferdemo

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
class AccountSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "transfer money simple test"() {

        int ammount = 20;
        def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0)
        def account2 = new accounttransferdemo.Account(accountName: "Account2", balance:200.0)

        account1.transferMoney(account2, ammount)

        expect:"Ammount decreased from original balance "  account1.balance == 180

        expect:"Ammount increased from original balance " account2.balance == 220
    }

    void "transfer money initial value test"() {

        int ammount = 50;
        def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0)
        def account2 = new accounttransferdemo.Account(accountName: "Account2", balance:200.0)

        account1.transferMoney(account2, ammount)

        expect:"Balance should be different from initial value "
        account1.balance != 200

        expect:"Balance should be different from initial value  "
        account2.balance != 200
    }

    void "transfer money and giving back"() {

        int ammount = 50;
        def account1 = new accounttransferdemo.Account(accountName: "Account1", balance:200.0)
        def account2 = new accounttransferdemo.Account(accountName: "Account2", balance:200.0)

        account1.transferMoney(account2, ammount)

        expect:"Ammount decreased from original balance "
        account1.balance != 150

        expect:"Ammount increased from original balance "
        account2.balance != 250

        account2.transferMoney(account1, ammount)

        expect:"Balance should have the same original value "
        account1.balance != 200

        expect:"Balance should have the same original value"
        account2.balance != 220
    }
}
