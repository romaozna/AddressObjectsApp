package service;

import model.AddressHierarchy;
import model.AddressObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class XmlParser {

    public static final String ADDRESS_FILE_PATH = "resources/AS_ADDR_OBJ.xml";
    public static final String HIERARCHY_FILE_PATH = "resources/AS_ADM_HIERARCHY.xml";
    private SAXParser parser;
    private AddressHandler addressHandler;
    private AddressHierarchyHandler addressHierarchyHandler;
    private static HashMap<Long, AddressObject> addresses;
    private static HashMap<Long, AddressHierarchy> addressesHierarchy;

    public XmlParser() {
        addresses = new HashMap<>();
        addressesHierarchy = new HashMap<>();

        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            this.parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }

        this.addressHandler = new AddressHandler();
        this.addressHierarchyHandler = new AddressHierarchyHandler();
    }

    public Map<Long, AddressObject> getAddresses() {
        try {
            parser.parse(new File(ADDRESS_FILE_PATH), addressHandler);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return addresses;
    }

    public Map<Long, AddressHierarchy> getAddressesHierarchy() {
        try {
            parser.parse(new File(HIERARCHY_FILE_PATH), addressHierarchyHandler);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return addressesHierarchy;
    }

    private static class AddressHandler extends DefaultHandler {

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("OBJECT")) {

                Long objectId = Long.valueOf(attributes.getValue("OBJECTID"));
                String name = attributes.getValue("NAME");
                String typename = attributes.getValue("TYPENAME");
                LocalDate startDate = LocalDate.parse(attributes.getValue("STARTDATE"));
                LocalDate endDate = LocalDate.parse(attributes.getValue("ENDDATE"));
                boolean isActual = attributes.getValue("ISACTUAL").equals("1");
                boolean isActive = attributes.getValue("ISACTIVE").equals("1");

                addresses.put(objectId, AddressObject.builder()
                                                     .objectId(objectId)
                                                     .name(name)
                                                     .typename(typename)
                                                     .startDate(startDate)
                                                     .endDate(endDate)
                                                     .isActual(isActual)
                                                     .isActive(isActive)
                                                     .build());
            }
        }
    }

    private static class AddressHierarchyHandler extends DefaultHandler {

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("ITEM")) {

                Long objectId = Long.valueOf(attributes.getValue("OBJECTID"));
                Long parentObjectId = Long.valueOf(attributes.getValue("PARENTOBJID"));
                LocalDate startDate = LocalDate.parse(attributes.getValue("STARTDATE"));
                LocalDate endDate = LocalDate.parse(attributes.getValue("ENDDATE"));
                boolean isActive = attributes.getValue("ISACTIVE").equals("1");

                addressesHierarchy.put(objectId, AddressHierarchy.builder()
                                                        .objectId(objectId)
                                                        .parentObjId(parentObjectId)
                                                        .startDate(startDate)
                                                        .endDate(endDate)
                                                        .isActive(isActive)
                                                        .build());
            }
        }
    }
}
