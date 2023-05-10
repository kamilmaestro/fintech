package kamilmarnik.fintech.bank.domain


class AccountDeletionSpec extends BankBaseSpec{

    def "deleting account"(){
        given: "account exists"
            bankFacade.createAccount(FIRST_ACCOUNT_ID)
        expect: "deleting the account"
            bankFacade.deleteAccount(FIRST_ACCOUNT_ID)
    }
}
