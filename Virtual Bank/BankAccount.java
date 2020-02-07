public class BankAccount {
  private PersonalInfo personal;
  private BankInfo info;
  
  // setters
  public void setPersonalInfo(PersonalInfo p) {
    personal = p;
  }
  
  public void setBankInfo(BankInfo b) {
    info = b;
  }
  
  // getters
  public PersonalInfo getPersonalInfo() {
    return personal;
  }
  
  public BankInfo getBankInfo() {
    return info;
  }
}