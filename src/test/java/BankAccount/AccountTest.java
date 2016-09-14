package BankAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static org.junit.Assert.*;
/**
 * Created by evanhitchings on 9/13/16.
 */
public class AccountTest {

//    Account lunaCheckingAccount;
//    Account lunaSavingsAccount;
//    Account ariaAccount;
//    @Before
//    public void setUp(){
//        lunaCheckingAccount = new Account(Account.Type.CHECKING, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
//        lunaSavingsAccount = new Account(Account.Type.SAVINGS, 63214, 2323.00, "Bear", 5.0, Account.Overdraft.DISABLED);
//        ariaAccount = new Account(Account.Type.INVESTMENT, 666, 666.00, "Aria", 4.0, Account.Overdraft.ENABLED);
//
//    }



    @Test
    public void getBalanceOfAccount(){
        Account lunaCheckingAccount = new Account(Account.Type.CHECKING, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        lunaCheckingAccount.setStatus(Account.Status.OFAC_FREEZE);
        Assert.assertEquals(0.00, lunaCheckingAccount.getBalanceOfAccount(), 0.00);

    }

    @Test
    public void setStatusTest(){
        Account ariaAccount = new Account(Account.Type.INVESTMENT, 666, 666.00, "Aria", 4.0, Account.Overdraft.ENABLED);
        ariaAccount.setStatus(Account.Status.OFAC_FREEZE);
        Assert.assertEquals("Status did not change from Open to Frozen", Account.Status.OFAC_FREEZE, ariaAccount.getStatus());
        ariaAccount.setStatus(Account.Status.CLOSED);
    }

    @Test
    public void setBalanceOfAccount(){
        Account lunaCheckingAccount = new Account(Account.Type.CHECKING, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        lunaCheckingAccount.setStatus(Account.Status.OFAC_FREEZE);
        lunaCheckingAccount.setBalanceOfAccount(500.00);
        lunaCheckingAccount.setStatus(Account.Status.OPEN);
        Assert.assertEquals(9001.00, lunaCheckingAccount.getBalanceOfAccount(), 0.00);
    }

    @Test
    public void creditTest(){
        Account lunaCheckingAccount = new Account(Account.Type.CHECKING, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        lunaCheckingAccount.credit(50.00);
        Assert.assertEquals(9051.00, lunaCheckingAccount.getBalanceOfAccount(), 0.00);

    }

    @Test
    public void debitTest(){
        Account lunaCheckingAccount = new Account(Account.Type.CHECKING, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        Account lunaSavingAccount = new Account(Account.Type.SAVINGS, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        lunaCheckingAccount.debit(10000.00);
        Assert.assertEquals(0.00, lunaCheckingAccount.getBalanceOfAccount(), 0.00);
        Assert.assertEquals(999.00, lunaSavingAccount.getBalanceOfAccount(), 0.00);


    }

    @Test
    public void transferTest(){
        Account lunaCheckingAccount = new Account(Account.Type.CHECKING, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        Account lunaSavingAccount = new Account(Account.Type.SAVINGS, 6079, 9001.00, "Luna", 0.00, Account.Overdraft.AUTOTRANSFER);
        lunaCheckingAccount.transfer(lunaSavingAccount, 100.00);
        Assert.assertEquals(9101.00, lunaSavingAccount.getBalanceOfAccount(), 0.00);
        Assert.assertEquals(8901.00, lunaCheckingAccount.getBalanceOfAccount(), 0.00);





    }





}
