package insurancescorecard;

public class InsuranceScore {
    private String name;
    private String score;
    private String riskLevel;
    public InsuranceScore() {

    }

    public InsuranceScore(String name, String score, String riskLevel) {
        this.name = name;
        this.score = score;
        this.riskLevel = riskLevel;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
