package accounttransferdemo

class Account {

    String accountName
    BigDecimal balance
    Date dateCreated
    Date lastUpdated

    static belongsTo = [owner:User]

    static constraints = {
        accountName(blank:false, maxSize:50)
        balance(min:0.0)
    }

    String toString(){
        return "${accountName}"
    }
}
