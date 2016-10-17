package accounttransferdemo

class Transaction {

    BigDecimal ammount;
    Date dateCreated
    static belongsTo = [actFrom:Account,actTo:Account]

    static constraints = {
        actFrom(nullable:false)
        actTo(nullable:false, validator: {val, obj  ->
            if ( val.id == obj.actFrom.id) {
                return 'transaction.validation.destiny.same.origin'
            } })
        ammount(min:0.01) // you cannot transfer less than one cent
    }


    void transferMoney() {
        actFrom.balance = actFrom.balance-ammount;
        actTo.balance = actTo.balance + ammount;
    }
}
