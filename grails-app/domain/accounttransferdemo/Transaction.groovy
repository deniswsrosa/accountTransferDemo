package accounttransferdemo

class Transaction {

    BigDecimal ammount;
    Date dateCreated
    static belongsTo = [actFrom:Account,actTo:Account]

    static constraints = {
        ammount(min:0.01) // you cannot transfer less than one cent
    }
}
