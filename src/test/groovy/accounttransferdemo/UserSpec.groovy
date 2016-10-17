package accounttransferdemo

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test User without email"() {

        when:
        def user = new accounttransferdemo.User(name:"John")
        user.validate()
        then:
        user.hasErrors()
    }


    void "test User without name"() {

        when:
        def user = new accounttransferdemo.User(email:"denis@test.com")
        user.validate()
        then:
        user.hasErrors()
    }


    void "test invalid email"() {

        when:
        def user = new accounttransferdemo.User(name:"John", email:"denis.com")
        user.validate()
        then:
        user.hasErrors()
    }


    void "too long email adsress"() {

        when:
        def user = new accounttransferdemo.User(name:"John",
                email:"denisssssssssssssssssssssssssssssssssssssssssssssssssssssssssss@gmail.commmmmmmmmm")
        user.validate()
        then:
        user.hasErrors()
    }


    void "test valid user"() {

        when:
        def user = new accounttransferdemo.User(name:"John", email:"john@test.com")
        user.validate()
        then:
        !user.hasErrors()
    }
}
