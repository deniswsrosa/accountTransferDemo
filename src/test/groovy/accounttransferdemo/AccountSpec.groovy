package accounttransferdemo

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
@Mock([accounttransferdemo.User])
class AccountSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }


    void "test negative balance"() {

        when:
        def user = new accounttransferdemo.User(name:"John", email:"john@test.com")
        def account = new accounttransferdemo.Account(accountName:"Account1", balance:-20, owner:user)
        account.validate()
        then:
        account.hasErrors()
    }


    void "test account without name"() {

        when:
        def user = new accounttransferdemo.User(name:"John", email:"john@test.com")
        def account = new accounttransferdemo.Account( balance:20, owner:user)
        account.validate()
        then:
        account.hasErrors()
    }

    void "test valid account"() {

        when:
        def user = new accounttransferdemo.User(name:"John", email:"john@test.com")
        def account = new accounttransferdemo.Account( accountName:"Account1", balance:20, owner:user)
        account.validate()
        then:
        !account.hasErrors()
    }

}
