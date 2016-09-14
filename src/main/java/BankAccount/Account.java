package BankAccount;
import java.util.ArrayList;

/**
 * Created by evanhitchings on 9/13/16.
 */
public class Account {


    private Type typeOfAccount;
    private int numberOfAccount;
    private double balanceOfAccount;
    private String nameOfAccountHolder;
    private double interestRate;
    private Status status;
    private Overdraft overdraftProtectionStatus;
    private ArrayList<String> transactionLog;

    //for use in later lab. will likely be replaced by seperate class
    //private static ArrayList<Account> accountList = new ArrayList<Account>();




    public Account(Type typeOfAccount, int numberOfAccount, double balanceOfAccount, String nameOfAccountHolder, double interestRate, Overdraft overdraftProtectionStatus) {
        this.typeOfAccount = typeOfAccount;
        this.numberOfAccount = numberOfAccount;
        this.balanceOfAccount = balanceOfAccount;
        this.nameOfAccountHolder = nameOfAccountHolder;
        this.interestRate = interestRate;
        this.status = Status.OPEN;
        this.overdraftProtectionStatus = overdraftProtectionStatus;
        this.transactionLog = new ArrayList<String>();
        //accountList.add(this);


    }

//    public ArrayList<Account> getAccountList(){
//        return Account.accountList;
//    }

    private enum Status {OPEN, CLOSED, OFAC_FREEZE}
    private enum Type {CHECKING, SAVINGS, INVESTMENT}
    private enum Overdraft {ENABLED, DISABLED, AUTOTRANSFER}


    public Type getTypeOfAccount() {
        return typeOfAccount;
    }

    public int getNumberOfAccount() {
        return numberOfAccount;
    }

    public Double getBalanceOfAccount() throws NullPointerException {
        if(this.getStatus().name().equalsIgnoreCase("OFAC_FREEZE")){
            System.out.println("Account is currently under OFAC Freeze");
            return null;
        }
            return balanceOfAccount;
    }


    protected void setBalanceOfAccount(double balanceOfAccount) {
        if(this.getStatus().equals(Status.OFAC_FREEZE)){
            System.out.println("Account is Frozen");
            return;
        }

        if(this.getStatus().equals(Status.CLOSED)){
            System.out.println("Account is Closed");
            return;
        }
        this.addToTransactionLog("Changed Account Balance from " + this.getBalanceOfAccount() + " to " + balanceOfAccount);
        this.balanceOfAccount = balanceOfAccount;
    }

    public String getNameOfAccountHolder() {
        return nameOfAccountHolder;
    }

    public void setNameOfAccountHolder(String nameOfAccountHolder) {
        //CLOSED accounts cannot change owners
        if(!(this.getStatus().equals(Status.CLOSED))){
            this.addToTransactionLog("Changed Account Name from " + this.getNameOfAccountHolder() + " to " + nameOfAccountHolder);

            this.nameOfAccountHolder = nameOfAccountHolder;

        }

    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.addToTransactionLog("Changed Interest Rate from " + this.getInterestRate() + " to " + interestRate);
        this.interestRate = interestRate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if(status.equals(Status.CLOSED)){
            System.out.println("CLOSED accounts cannot be reopened");
            return;
        }
        try {
            if((this.getBalanceOfAccount() != 0.00) && (status.equals(Status.CLOSED))){
                System.out.println("Account Balance must be 0 before it can be closed");
                return;
            }
        } catch (NullPointerException e){

        }
        this.addToTransactionLog("Changed Account Status from " + this.getStatus().name() + " to " + status.name());
        this.status = status;
    }

    public Overdraft getOverdraftProtectionStatus() {
        return overdraftProtectionStatus;
    }

    public void setOverdraftProtectionStatus(Overdraft overdraftProtectionStatus) {
        this.overdraftProtectionStatus = overdraftProtectionStatus;
    }

    public ArrayList<String> getTransactionLog() {
        return transactionLog;
    }

    private void addToTransactionLog(String transaction){
        this.getTransactionLog().add(transaction);

    }

    public boolean credit(double money){
        if(this.getStatus().equals(Status.OPEN)){
            this.setBalanceOfAccount((this.getBalanceOfAccount() + money));
            System.out.println("Credited $" + money + "to account");
            return true;
        }
        //Accounts must be open to credit money!
        System.out.println("Account is not open. Unable to credit money");
        return false;
    }

    public boolean debit(double money){
        if(!(this.getStatus().equals(Status.OPEN))){
            return false;
        }

        if(this.getBalanceOfAccount() >= money || this.getOverdraftProtectionStatus().equals(Overdraft.DISABLED)){
            this.setBalanceOfAccount((this.getBalanceOfAccount() - money));
            return true;
        }

        if(this.getOverdraftProtectionStatus().equals(Overdraft.ENABLED)) {
            System.out.println("Declined. Debit would result in a negative balance");
            return false;
//        }
//        if(this.getOverdraftProtectionStatus().equals(Overdraft.ENABLED)){
//            return this.overdraftTransfer(money);
        }
        return false;
    }

//    public boolean overdraftTransfer(double money){
//        for(Account account : accountList){
//            //same name, different account numbers
//            if(this.getNameOfAccountHolder().equalsIgnoreCase(account.getNameOfAccountHolder()) && (this.getNumberOfAccount() != account.getNumberOfAccount())){
//                double moneyRemaining = money - this.getBalanceOfAccount();
//                if(account.getBalanceOfAccount() >= money){
//                    this.setBalanceOfAccount(0.00);
//                    account.debit(moneyRemaining);
//                    return true;
//                }
//            }
//        }
//        return false;
//
//    }

    public boolean transfer(Account transferTo, double money){
        if(this.getStatus().equals(Status.OPEN) && transferTo.getStatus().equals(Status.OPEN)){
           if(this.getBalanceOfAccount() >= money){
               this.debit(money);
               transferTo.credit(money);
               return true;
           } else {
               System.out.println("Unable to tranfer because " + this.getNumberOfAccount() + " does not have enough funds");
               return false;
           }
        } else {
            System.out.println("Both accounts must be OPEN and Unfrozen");
            return false;
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getNameOfAccountHolder() + "\n");
        sb.append(this.getNumberOfAccount() + "\n");
        sb.append(this.getBalanceOfAccount() + "\n");
        return sb.toString();
    }





}

