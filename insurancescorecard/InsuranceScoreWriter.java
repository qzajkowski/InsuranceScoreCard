package insurancescorecard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InsuranceScoreWriter {
    List<InsuranceScore> assessments;

    public InsuranceScoreWriter(List<Member> members) {
        this.assessments = new ArrayList<>();
        for (Member member : members) {
            this.assessments.add(new Assessor(member).getInsuranceScore());
        }
    }

    //Prints the insurance scores
    public void printAssessments() {
        System.out.println("Here are the insurance assessments: ");
        for (InsuranceScore assessment : this.assessments) {
            System.out.println("Name:               " + assessment.getName());
            System.out.println("Score:              " + assessment.getScore());
            System.out.println("Verdict:            " + assessment.getRiskLevel());
            System.out.println("\n");
        }
    }
    
    public void writeAssessmentsToJSON() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the name of JSON file: ");
        String filename = in.nextLine();
        if (!filename.contains(".json")) {
            throw new Exception("\nFile name must be in the form of - name.json, try again...\n");
        }
        String jsonString = buildJsonObject();
        try {
            FileWriter fileWriter = new FileWriter(new File(".").getCanonicalFile() + "/" + filename);
            fileWriter.write(jsonString);
            fileWriter.close();
            System.out.println("\nThe scores were successfully written\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String buildJsonObject() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t\"assessments\": [\n");
        for (int i = 0; i < this.assessments.size(); i++) {
            if (i+1 == this.assessments.size()) {
                sb.append("\t\t{\n");
                sb.append("\t\t\t\"name\":\"").append(this.assessments.get(i).getName()).append("\",\n");
                sb.append("\t\t\t\"score\":\"").append(this.assessments.get(i).getScore()).append("\",\n");
                sb.append("\t\t\t\"riskLevel\":\"").append(this.assessments.get(i).getRiskLevel()).append("\"\n");
                sb.append("\t\t}\n");
            } else {
                sb.append("\t\t{\n");
                sb.append("\t\t\t\"name\":\"").append(this.assessments.get(i).getName()).append("\",\n");
                sb.append("\t\t\t\"score\":\"").append(this.assessments.get(i).getScore()).append("\",\n");
                sb.append("\t\t\t\"riskLevel\":\"").append(this.assessments.get(i).getRiskLevel()).append("\"\n");
                sb.append("\t\t},\n");
            }
        }
        sb.append("\t]\n}");

        return sb.toString();
    }
}
