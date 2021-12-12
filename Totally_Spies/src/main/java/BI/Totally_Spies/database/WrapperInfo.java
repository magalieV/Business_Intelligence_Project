package BI.Totally_Spies.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperInfo {
    public String personName;
    public String password;
    public String firstName;
    public List<String> interests;
    public String lastName;
    public String globalName;

    public List<String> getInterest() {return interests;}
    public String getPassword() {return password;}
    public String getPersonName() {return personName;}
    public String getLastName() {return lastName;}
    public String getGlobalName() {return globalName;}
    public String getFirstName() {return firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setInterest(List<String> interest) {this.interests = interest;}
    public void setPassword(String password) {this.password = password;}
    public void setPersonName(String personName) {this.personName = personName;}
    public void setGlobalName(String globalName) {this.globalName = globalName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
}
