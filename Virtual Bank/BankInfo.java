public class BankInfo {
  private int acctNumber;
  private String acctType;
  private double balance;
  
  // Setters
  public void setAcctNumber(int integer) {
    acctNumber = integer;
  }
  
  public void setAcctType(String str) {
    acctType = str;
  }
  
  public void setBalance(double bal) {
    balance = bal;
  }
  
  // Getters
  public int getAcctNumber() {
    return acctNumber;
  }
  
  public String getAcctType() {
    return acctType;
  }
  
  public double getBalance() {
    return balance;
  }
} //end of bankInfo
