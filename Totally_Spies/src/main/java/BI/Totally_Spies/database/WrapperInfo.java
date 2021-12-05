package BI.Totally_Spies.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperInfo {
    public String personName;
    public String password;
    public List<String> interest;

    public List<String> getInterest() {return interest;}
    public String getPassword() {return password;}
    public String getPersonName() {return personName;}

    public void setInterest(List<String> interest) {this.interest = interest;}
    public void setPassword(String password) {this.password = password;}
    public void setPersonName(String personName) {this.personName = personName;}
}
