package rest.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Administrator on 2017/7/28 0028.
 */
public class XML2Java {
    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(People.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File("src/people.xml");
        People people = (People)unmarshaller.unmarshal(file);
        System.out.println(people.id);
        System.out.println(people.name);
        System.out.println(people.age);
    }
}
