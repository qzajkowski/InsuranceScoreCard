package insurancescorecard;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class MemberWriter {
    private String filename;
    private List<Member> members;
    private String text;

    MemberWriter(String filename, List<Member> members) {
        this.filename = filename;
        this.members = members;
        this.text = this.turnMemberToString(members);
    }

    //Takes the list of members and turns them into string for the writer methods
    private String turnMemberToString(List<Member> members) {
        StringBuilder builder = new StringBuilder();
        for (Member member : members) {
            builder.append(member.toString());
        }
        return builder.toString();
    }

    //Saves the string of members to a text file
    void saveAsTextFile() {
        try {
            FileWriter fileWriter = new FileWriter(new File(".").getCanonicalFile() + "/" + this.filename);
            fileWriter.write(this.text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Saves the string of members to a binary file
    void saveAsBinFile() {
        try {
            String filePath = new File(".").getCanonicalFile() + "/" + this.filename;
            Files.write(Paths.get(filePath), this.text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Saves list of members to an xml file
    void saveAsXMLFile() {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Creating the root element
            Element root = document.createElement("members");
            document.appendChild(root);

            for (int i = 0; i <  this.members.size(); i++) {

                // Creating the element that will hold member data
                Element member = document.createElement("member");
                root.appendChild(member);
                Attr memberId = document.createAttribute("id");
                memberId.setValue("member" + (i+1));
                member.setAttributeNode(memberId);

                Element firstName = document.createElement("firstname");
                firstName.appendChild(document.createTextNode(this.members.get(i).getFirstName()));
                member.appendChild(firstName);

                Element lastnName = document.createElement("lastname");
                lastnName.appendChild(document.createTextNode(this.members.get(i).getLastName()));
                member.appendChild(lastnName);

                Element age = document.createElement("age");
                age.appendChild(document.createTextNode(this.members.get(i).getAge()));
                member.appendChild(age);

                Element height = document.createElement("height");
                height.appendChild(document.createTextNode(this.members.get(i).getHeight()));
                member.appendChild(height);

                Element weight = document.createElement("weight");
                weight.appendChild(document.createTextNode(this.members.get(i).getWeight()));
                member.appendChild(weight);

                Element bpSys = document.createElement("bpSys");
                bpSys.appendChild(document.createTextNode(this.members.get(i).getBpSyst()));
                member.appendChild(bpSys);

                Element bpDia = document.createElement("bpDia");
                bpDia.appendChild(document.createTextNode(this.members.get(i).getBpDias()));
                member.appendChild(bpDia);

                Element cancer = document.createElement("cancer");
                cancer.appendChild(document.createTextNode(this.members.get(i).getCancer()));
                member.appendChild(cancer);

                Element diabetes = document.createElement("diabetes");
                diabetes.appendChild(document.createTextNode(this.members.get(i).getDiabetes()));
                member.appendChild(diabetes);

                Element alzheimers = document.createElement("alzheimers");
                alzheimers.appendChild(document.createTextNode(this.members.get(i).getAlzheimers()));
                member.appendChild(alzheimers);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File(".").getCanonicalFile() + "/" + this.filename);
                transformer.transform(domSource, streamResult);
            }

        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
