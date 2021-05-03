package modi1.dummy11.models;

import java.util.List;

public class VaccineSession {
    String name;
    String timings;
    String fees;
    String availablity;
    String age_limit;
    List<String> slots;

    public VaccineSession(){}
    public VaccineSession(String name, String timings, String fees, String availablity, String age_limit, List<String> slots) {
        this.name = name;
        this.timings = timings;
        this.fees = fees;
        this.availablity = availablity;
        this.age_limit = age_limit;
        this.slots = slots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getAvailablity() {
        return availablity;
    }

    public void setAvailablity(String availablity) {
        this.availablity= availablity;
    }

    public String getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(String age_limit) {
        this.age_limit = age_limit;
    }

    public List<String> getSlots() {
        return slots;
    }

    public void setSlots(List<String> slots) {
        this.slots = slots;
    }
}
