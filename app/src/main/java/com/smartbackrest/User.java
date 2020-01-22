package com.smartbackrest;

public class User {
    private String firstName, lastName;
    private int dob;
    private int gender;
    private boolean hasDiabetes;
    private boolean hasAsthama;
    private boolean hasParkinsons;
    private boolean hasScleroderma;
    private boolean hasMultipleSclerosis;
    private boolean onDigestiveMedication;
    private boolean hasAnxietyIssues;
    private boolean hasStomachSurgeries;
    private boolean hasPain;

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getDob() {
        return dob;
    }

    public User setDob(int dob) {
        this.dob = dob;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public User setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public boolean isHasDiabetes() {
        return hasDiabetes;
    }

    public User setHasDiabetes(boolean hasDiabetes) {
        this.hasDiabetes = hasDiabetes;
        return this;
    }

    public boolean isHasAsthama() {
        return hasAsthama;
    }

    public User setHasAsthama(boolean hasAsthama) {
        this.hasAsthama = hasAsthama;
        return this;
    }

    public boolean isHasParkinsons() {
        return hasParkinsons;
    }

    public User setHasParkinsons(boolean hasParkinsons) {
        this.hasParkinsons = hasParkinsons;
        return this;
    }

    public boolean isHasScleroderma() {
        return hasScleroderma;
    }

    public User setHasScleroderma(boolean hasScleroderma) {
        this.hasScleroderma = hasScleroderma;
        return this;
    }

    public boolean isHasMultipleSclerosis() {
        return hasMultipleSclerosis;
    }

    public User setHasMultipleSclerosis(boolean hasMultipleSclerosis) {
        this.hasMultipleSclerosis = hasMultipleSclerosis;
        return this;
    }

    public boolean isOnDigestiveMedication() {
        return onDigestiveMedication;
    }

    public User setOnDigestiveMedication(boolean onDigestiveMedication) {
        this.onDigestiveMedication = onDigestiveMedication;
        return this;
    }

    public boolean isHasAnxietyIssues() {
        return hasAnxietyIssues;
    }

    public User setHasAnxietyIssues(boolean hasAnxietyIssues) {
        this.hasAnxietyIssues = hasAnxietyIssues;
        return this;
    }

    public boolean isHasStomachSurgeries() {
        return hasStomachSurgeries;
    }

    public User setHasStomachSurgeries(boolean hasStomachSurgeries) {
        this.hasStomachSurgeries = hasStomachSurgeries;
        return this;
    }

    public boolean isHasPain() {
        return hasPain;
    }

    public User setHasPain(boolean hasPain) {
        this.hasPain = hasPain;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", hasDiabetes=" + hasDiabetes +
                ", hasAsthama=" + hasAsthama +
                ", hasParkinsons=" + hasParkinsons +
                ", hasScleroderma=" + hasScleroderma +
                ", hasMultipleSclerosis=" + hasMultipleSclerosis +
                ", onDigestiveMedication=" + onDigestiveMedication +
                ", hasAnxietyIssues=" + hasAnxietyIssues +
                ", hasStomachSurgeries=" + hasStomachSurgeries +
                ", hasPain=" + hasPain +
                '}';
    }
}
