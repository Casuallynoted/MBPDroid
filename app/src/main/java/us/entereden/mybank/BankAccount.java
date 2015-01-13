package us.entereden.mybank;


class BankAccount {

    private double mbalance;
    public static final double OVERDRAFT_FEE = 30;

    public void withdraw(double amount){
       mbalance -= amount;
    }

    public void deposit(double amount){

    mbalance += amount;

    }
    public double getBalance(){
        return mbalance;
    }
}
