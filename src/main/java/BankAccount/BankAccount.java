package BankAccount;

/**
 * Created by evanhitchings on 9/13/16.
 */
public class BankAccount {
    public static void main(String[] args) {

        Account lunaCheckingAccount = new Account(Account.Type.CHECKING, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        Account lunaSavingAccount = new Account(Account.Type.SAVINGS, 60700, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        lunaCheckingAccount.debit(10000.00);
        System.out.println(lunaCheckingAccount);



    }

}
