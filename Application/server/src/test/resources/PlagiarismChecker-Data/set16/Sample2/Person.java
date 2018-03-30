package File2;

public class Person {

  private int identifier;
  private String fName;
  private String lName;

  /**
   * Creates an instance of customer.
   */
  public Person(final int identifier, final String fName, final String lName) {
    this.identifier = identifier;
    this.fName = fName;
    this.lName = lName;
  }

  public int getIdentifier() {
    return identifier;
  }

  public void setIdentifier(final int identifier) {
    this.identifier = identifier;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(final String fName) {
    this.fName = fName;
  }

  public String getlName() {
    return lName;
  }

  public void setlName(final String lName) {
    this.lName = lName;
  }

  @Override
  public String toString() {
    return "Customer{" + "identifier=" + getIdentifier() + ", fName='" + getfName() + '\'' + ", lName='"
        + getlName() + '\'' + '}';
  }

  @Override
  public boolean equals(final Object that) {
    boolean isEqual = false;
    if (this == that) {
      isEqual = true;
    } else if (that != null && getClass() == that.getClass()) {
      final Person customer = (Person) that;
      if (getIdentifier() == customer.getIdentifier()) {
        isEqual = true;
      }
    }
    return isEqual;
  }

  @Override
  public int hashCode() {
    return getIdentifier();
  }
}
