import accounttransferdemo.Account
import accounttransferdemo.User

class BootStrap {

    def init = { servletContext ->

        //Generating initial users
        def names = ["John", "Yulya", "Denis", "Robert", "Pedro"]

        names.each{
           // def user = new User(it, it.toLowerCase() + "@email.com")
            def user = new User(it, "deniswsrosa@gmail.com")
            user.save();

            def account1 = new Account(accountName: it+" Account 1", balance: 200.0, owner: user)
            account1.save();

            def account2 = new Account(accountName: it+" Account 2", balance: 200.0, owner: user)
            account2.save();
        }

        def destroy = {
        }
    }
}
