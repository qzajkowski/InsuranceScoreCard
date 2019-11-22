package insurancescorecard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

     // Prints the welcome message
    private static void printIntroMessage(){
        System.out.println("******************************************");
        System.out.println("         Insurance Score Card             ");
        System.out.println("This app scores a potential customer on   ");
        System.out.println("various health attributes: blood pressure,");
        System.out.println("age, height, weight, and family history of");
        System.out.println("disease. It writes each member's insurance");
        System.out.println("grade to a JSON file so that they can be  ");
        System.out.println("easily shared on a web-based data exchange");
        System.out.println("******************************************");
        System.out.println("\n");
    }

     // Prints the goodbye message
    private static void printGoodbyeMessage(){
        System.out.println("\n");
        System.out.println("******************************************");
        System.out.println("           Insurance Score Card           ");
        System.out.println("                 Thank You                ");
        System.out.println("******************************************");
    }

    // Reads the initial data file to be used with the program
    private static List<Member> initialFileRead() {
        System.out.print("Enter name of member file: ");
        Scanner in = new Scanner(System.in);
        String filepath = in.nextLine();
        List<Member> membersList = new MemberReader(filepath).readTextFile();
        System.out.println("\n" + membersList.size() + " members were read\n");
        return membersList;
    }

    //Menu
    private static String chooseOptions() {
        System.out.println("Here are your choices: ");
        System.out.println("    1. List members");
        System.out.println("    2. Add a new member");
        System.out.println("    3. Save members");
        System.out.println("    4. Load members");
        System.out.println("    5. Assess members");
        System.out.println("    6. Save assessments as JSON");
        System.out.println("    7. Exit");
        System.out.print("Please enter your choice: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    // Prints all the members
    private static void printMembers(List<Member> memberList){
        System.out.println("\nHere are the members:");
        for (Member member : memberList) {
            System.out.println(member.getLastName() + ", " + member.getFirstName());
            System.out.println("Age             " + member.getAge());
            System.out.println("Height          " + member.getHeight());
            System.out.println("Weight          " + member.getWeight());
            System.out.println("BP Syst         " + member.getBpSyst());
            System.out.println("BP Dias         " + member.getBpDias());
            System.out.println("Cancer          " + member.getCancer());
            System.out.println("Diabetes        " + member.getDiabetes());
            System.out.println("Alzheimers      " + member.getAlzheimers());
            System.out.println("---------------------------");
        }
    }

    // Creates new member
    private static Member createNewMember(){
        Scanner in = new Scanner(System.in);
        Member newMember = new Member();

        System.out.print("Enter the first and last name (first last): ");
        String[] name = in.nextLine().split(" ");
        newMember.setFirstName(name[0]);
        newMember.setLastName(name[1]);

        System.out.print("Enter age: ");
        newMember.setAge(in.nextLine());

        System.out.print("Enter height in inches: ");
        newMember.setHeight(in.nextLine());

        System.out.print("Enter weight in pounds: ");
        newMember.setWeight(in.nextLine());

        System.out.print("Enter blood pressure (sys and dia): ");
        String[] bloodPressure = in.nextLine().split(" ");
        newMember.setBpSyst(bloodPressure[0]);
        newMember.setBpDias(bloodPressure[1]);

        System.out.println("Has a family member had (y/n)... ");
        System.out.print("Cancer?: ");
        newMember.setCancer(in.nextLine());
        System.out.print("Diabetes?: ");
        newMember.setDiabetes(in.nextLine());
        System.out.print("Alzheimers?: ");
        newMember.setAlzheimers(in.nextLine());
        System.out.println("\n");
        return newMember;
    }

    //Asks the user where to save data
    private static void saveMembers(List<Member> memberList) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("(T)ext, (B)inary, or (X)ML? ");
        String saveType = in.nextLine().toUpperCase();
        if (!saveType.equals("T") && !saveType.equals("B") && !saveType.equals("X")) {
            throw new Exception("\nInvalid file type, try again...\n");
        }

        System.out.print("Enter name of output file: ");
        String filename = in.nextLine();

        MemberWriter memberWriter = new MemberWriter(filename, memberList);
        switch (saveType) {
            case "T":
                if (!filename.contains(".txt")) {
                    throw new Exception("\nFile name must be in the form of - name.txt, try again...\n");
                }
                memberWriter.saveAsTextFile();
                break;
            case "B":
                if (!filename.contains(".bin")) {
                    throw new Exception("\nFile name must be in the form of - name.bin, try again...\n");
                }
                memberWriter.saveAsBinFile();
                break;
            case "X":
                if (!filename.contains(".xml")) {
                    throw new Exception("\nFile name must be in the form of - name.xml, try again...\n");
                }
                memberWriter.saveAsXMLFile();
                break;
        }
        System.out.println("\nMembers were written successfully\n");
    }

    //Ask the user to choose where to load data from
    private static List<Member> loadMembers() throws Exception {
        List<Member> memberList = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        System.out.print("(T)ext, (B)inary, or (X)ML? ");
        String loadType = in.nextLine().toUpperCase();
        if (!loadType.equals("T") && !loadType.equals("B") && !loadType.equals("X")) {
            throw new Exception("\nInvalid file type, try again...\n");
        }
        System.out.print("Enter name of output file: ");
        String filename = in.nextLine();
        MemberReader memberReader = new MemberReader(filename);
        switch (loadType) {
            case "T":
                if (!filename.contains(".txt")) {
                    throw new Exception("\nFile name must be in the form of - name.txt, try again...\n");
                }
                memberList = memberReader.readTextFile();
                break;
            case "B":
                if (!filename.contains(".bin")) {
                    throw new Exception("\nFile name must be in the form of - name.bin, try again...\n");
                }
                memberList = memberReader.readBinFile();
                break;
            case "X":
                if (!filename.contains(".xml")) {
                    throw new Exception("\nFile name must be in the form of - name.xml, try again...\n");
                }
                memberList = memberReader.readXMLFile();
                break;
        }
        System.out.println("\n" + memberList.size() + " members were read\n");
        return memberList;
    }

    public static void main(String[] args) throws IOException {
        List<Member> memberList;
        String choice = "";

        printIntroMessage();
        memberList = initialFileRead();
        while (!choice.equals("7")) {
            choice = chooseOptions();
            switch (choice) {
                case "1":
                    printMembers(memberList);
                    break;
                case "2":
                    memberList.add(createNewMember());
                    System.out.println("The new member has been added!\n");
                    break;
                case "3":
                    try {
                        saveMembers(memberList);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    try {
                        memberList = loadMembers();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    new InsuranceScoreWriter(memberList).printAssessments();
                    break;
                case "6":
                    try {
                        new InsuranceScoreWriter(memberList).writeAssessmentsToJSON();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());

                    }
                    break;
                case "7":
                    break;
                default:
                    System.out.println("Invalid input, please try again.");
            }
        }

        printGoodbyeMessage();
    }
}
