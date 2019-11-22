package insurancescorecard;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class MemberReader {
    private String filepath;

    MemberReader(String filepath) {
        this.filepath = filepath;
    }

    //Read member data from the text file
    List<Member> readTextFile() {
        BufferedReader reader;
        List<Member> members = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(new File(".").getCanonicalPath() + "/" + this.filepath));
            String line = reader.readLine();
            while(line != null) {
                String[] memberData = line.split("\\s+");
                Member member = new Member(
                        memberData[0],
                        memberData[1],
                        memberData[2],
                        memberData[3],
                        memberData[4],
                        memberData[5],
                        memberData[6],
                        memberData[7],
                        memberData[8],
                        memberData[9]
                        );
                members.add(member);
                line =reader.readLine();
            }
            return members;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Member> readBinFile() {
        List<Member> members = new ArrayList<>();
        //splitting up data
        try {
            File file = new File(new File(".").getCanonicalPath() + "/" + this.filepath);
            Stream linesStream = Files.lines(file.toPath());
            linesStream.forEach(line -> {
                String[] memberData = line.toString().split("\\s+");
                Member member = new Member(
                        memberData[0],
                        memberData[1],
                        memberData[2],
                        memberData[3],
                        memberData[4],
                        memberData[5],
                        memberData[6],
                        memberData[7],
                        memberData[8],
                        memberData[9]
                );
                members.add(member);
            });
            return members;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Member> readXMLFile() {
        List<Member> members = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactor = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactor.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(".").getCanonicalPath() + "/" + this.filepath);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("member");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Member member = new Member();
                    member.setFirstName(element.getElementsByTagName("firstname").item(0).getTextContent());
                    member.setLastName(element.getElementsByTagName("lastname").item(0).getTextContent());
                    member.setAge(element.getElementsByTagName("age").item(0).getTextContent());
                    member.setHeight(element.getElementsByTagName("height").item(0).getTextContent());
                    member.setWeight(element.getElementsByTagName("weight").item(0).getTextContent());
                    member.setBpSyst(element.getElementsByTagName("bpSys").item(0).getTextContent());
                    member.setBpDias(element.getElementsByTagName("bpDia").item(0).getTextContent());
                    member.setCancer(element.getElementsByTagName("cancer").item(0).getTextContent());
                    member.setDiabetes(element.getElementsByTagName("diabetes").item(0).getTextContent());
                    member.setAlzheimers(element.getElementsByTagName("alzheimers").item(0).getTextContent());
                    members.add(member);
                }
            }
            return members;
          //Using SAX Exception here which is like IO Exception but for XMLs
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
