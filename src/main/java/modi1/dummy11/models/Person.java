package modi1.dummy11.models;

import javax.persistence.*;

@Entity
@Table(name = "Person")

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer person_id;
    private String emailiD;
    private String pincode;
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getEmailiD() {
        return emailiD;
    }

    public void setEmailiD(String emailiD) {
        this.emailiD = emailiD;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Person(Integer person_id, String emailiD, String pincode, String age) {
        this.person_id = person_id;
        this.emailiD = emailiD;
        this.pincode = pincode;
        this.age = age;
    }

    public Person(){}
}
