package sg.edu.rp.c346.petboardingapp;

import java.io.Serializable;

public class Pet implements Serializable {
    private String name;
    private Integer days;
    private String pet;
    private String date;
    private Boolean vaccinated;


    public Pet(String name, Integer days, String pet, String date, Boolean vaccinated) {
        this.name = name;
        this.days = days;
        this.pet = pet;
        this.date = date;
        this.vaccinated = vaccinated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}
