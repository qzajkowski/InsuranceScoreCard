package insurancescorecard;

import java.util.HashMap;
import java.util.Map;

public class Assessor {
    private InsuranceScore insuranceScore;

    private Map<String, Integer> bmi_points;
    private Map<String, Integer> blood_pressure_points;

    Assessor(Member member) {
        bmi_points = new HashMap<>();
        bmi_points.put("Normal", 0);
        bmi_points.put("Overweight", 30);
        bmi_points.put("Else", 75);

        blood_pressure_points = new HashMap<>();
        blood_pressure_points.put("Normal", 0);
        blood_pressure_points.put("Elevated", 15);
        blood_pressure_points.put("Stage 1", 30);
        blood_pressure_points.put("Stage 2", 75);
        blood_pressure_points.put("Crisis", 100);

        insuranceScore = new InsuranceScore();
        this.insuranceScore.setName(member.getLastName() + ", " + member.getFirstName());
        this.insuranceScore.setScore(this.getTotalPoints(member).toString());
        this.insuranceScore.setRiskLevel(this.determineRisk(member));
    }

    public InsuranceScore getInsuranceScore() {
        return insuranceScore;
    }
    //Determines how bad their blood pressure is
    private String determineBloodPressureCategory(Integer systolic, Integer diastolic) {
        if (180 < systolic || 120 < diastolic) {
            return "Crisis";
        }

        if (140 <= systolic || 90 <= diastolic) {
            return "Stage 2";
        }

        if (130 <= systolic || 80 <= diastolic && diastolic < 89){
            return "Stage 1";
        }

        if (120 <= systolic && diastolic < 80) {
            return "Elevated";
        }

        if (systolic < 120 && diastolic < 80) {
            return "Normal";
        }

        return "Invalid blood pressure";
    }
    //Assigns a value to their given age
    private Integer getAgePoints(Integer age) {
        if (age < 30) {
            return 0;
        } else if (age < 45) {
            return 10;
        } else if (age < 60) {
            return 20;
        } else {
            return 30;
        }
    }
    //BMI calculator
    private Double calculateBMI(Integer height, Integer weight) {
        double h = height*2.54/100;
        double w = weight*0.45359237;
        return w/(h*h);
    }
    //Determines which category of BMI
    private Integer getBMIPoints(Integer height, Integer weight) {
        Double bmi = this.calculateBMI(height, weight);
        if (18.5 <= bmi && bmi <= 24.9) {
            return bmi_points.get("Normal");
        } else if (25.0 <= bmi && bmi <= 29.9) {
            return bmi_points.get("Overweight");
        } else {
            return bmi_points.get("Else");
        }
    }

    private Integer getBloodPressurePoints(Integer systolic, Integer diastolic) {
        return blood_pressure_points.get(determineBloodPressureCategory(systolic, diastolic));
    }
    //Adding points for family diseases
    private Integer getFamilyDiseasePoints(String diabetes, String cancer, String alzheimers) {
        int points = 0;
        if (diabetes.equals("y")) {
            points += 10;
        }
        if (cancer.equals("y")) {
            points += 10;
        }
        if (alzheimers.equals("y")) {
            points += 10;
        }

        return points;
    }

    private Integer getTotalPoints(Member member) {
        int agePoints = this.getAgePoints(Integer.parseInt(member.getAge()));
        int bmiPoints = this.getBMIPoints(Integer.parseInt(member.getHeight()), Integer.parseInt(member.getWeight()));
        int bpPoints = this.getBloodPressurePoints(Integer.parseInt(member.getBpSyst()), Integer.parseInt(member.getBpDias()));
        int familyDiseasePoints = this.getFamilyDiseasePoints(member.getDiabetes(), member.getCancer(), member.getAlzheimers());

        return agePoints + bmiPoints + bpPoints + familyDiseasePoints;
    }
    //Determines their risk
    private String determineRisk(Member member) {
        int points = this.getTotalPoints(member);
        if (points <= 20) {
            return "low risk";
        } else if (points <= 50) {
            return "moderate risk";
        } else if (points <= 75){
            return "high risk";
        } else {
            return "uninsurable";
        }
    }
}
