package accounttransferdemo

class User {

    String name
    String email

    static hasMany = [accounts:Account]

    User(){}
    User(String name, String email ){
        this.name = name
        this.email = email
    }

    String toString(){
        return "${name}"
    }

    static constraints = {
        name(blank:false, maxSize:50)
        email(blank:false,email:true)
    }
}
