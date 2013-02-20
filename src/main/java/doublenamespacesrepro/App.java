package doublenamespacesrepro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xalan.xsltc.trax.TransformerFactoryImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            repro(System.out);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void repro(Appendable out) throws IOException, TransformerException, SAXException {

        Transformer transformer = createTransformer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        
        xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        
        Source definitionSource = new SAXSource(xmlReader, creaInputSourceFromResource());

        StreamResult responseResult = new StreamResult(baos);
        transformer.transform(definitionSource, responseResult);
        out.append(new String(baos.toByteArray(), "UTF-8"));
        
    }

    static Transformer createTransformer() {
        try {
            return new TransformerFactoryImpl().newTransformer();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static InputSource creaInputSourceFromResource() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("sampleWSDL.txt");
        return new InputSource(is);
    }
}
