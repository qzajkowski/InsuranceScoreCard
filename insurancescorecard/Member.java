package insurancescorecard;

class Member {
    private String firstName;
    private String lastName;
    private String age;
    private String weight;
    private String height;
    private String bpSyst;
    private String bpDias;
    private String cancer;
    private String diabetes;
    private String alzheimers;

    Member() {

    }

    Member(String firstName, String lastName, String age, String height, String weight, String bpSyst, String bpDias, String cancer, String diabetes, String alzheimers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bpSyst = bpSyst;
        this.bpDias = bpDias;
        this.cancer = cancer;
        this.diabetes = diabetes;
        this.alzheimers = alzheimers;
    }
    // sets and gets
    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getAge() {
        return age;
    }

    void setAge(String age) {
        this.age = age;
    }

    String getWeight() {
        return weight;
    }

    void setWeight(String weight) {
        this.weight = weight;
    }

    String getHeight() {
        return height;
    }

    void setHeight(String height) {
        this.height = height;
    }

    String getBpSyst() {
        return bpSyst;
    }

    void setBpSyst(String bpSyst) {
        this.bpSyst = bpSyst;
    }

    String getBpDias() {
        return bpDias;
    }

    void setBpDias(String bpDias) {
        this.bpDias = bpDias;
    }

    String getCancer() {
        return cancer;
    }

    void setCancer(String cancer) {
        this.cancer = cancer;
    }

    String getDiabetes() {
        return diabetes;
    }

    void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    String getAlzheimers() {
        return alzheimers;
    }

    void setAlzheimers(String alzheimers) {
        this.alzheimers = alzheimers;
    }

    @Override
    public String toString() {
        return firstName + "\t" +
                lastName + "\t" +
                age + "\t" +
                height + "\t" +
                weight + "\t" +
                bpSyst + "\t" +
                bpDias + "\t" +
                cancer + "\t" +
                diabetes + "\t" +
                alzheimers + "\t" + "\n";
    }
}
