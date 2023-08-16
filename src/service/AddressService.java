package service;

import model.AddressHierarchy;
import model.AddressObject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddressService {
    private Map<Long, AddressObject> addressObjects;
    private Map<Long, AddressHierarchy> addressHierarchy;

    public AddressService() {
        XmlParser xmlParser = new XmlParser();
        this.addressObjects = xmlParser.getAddresses();
        this.addressHierarchy = xmlParser.getAddressesHierarchy();
    }

    public void getAddressObjectsByDate(LocalDate date, List<Long> objectIds) {
        List<AddressObject> resultAddresses = addressObjects.values()
                .stream()
                .filter(addressObject ->
                        addressObject.getStartDate().isBefore(date) && addressObject.getEndDate().isAfter(date))
                .filter(addressObject ->
                        objectIds.contains(addressObject.getObjectId()))
                .collect(Collectors.toList());
        for (AddressObject address: resultAddresses) {
            System.out.println(address.getObjectId() + ": " + address.getTypename() + " " + address.getName());
        }
        System.out.println();
    }

    public void getAddressObjectsHierarchy(String text) {
        List<AddressObject> matchAddresses = addressObjects.values()
                .stream()
                .filter(addressObject -> addressObject.getTypename().trim().contains(text.trim()))
                .filter(AddressObject::isActual)
                .collect(Collectors.toList());
        for (AddressObject address : matchAddresses) {
            StringBuilder sb = new StringBuilder();
            sb.insert(0, address.getTypename() + " " + address.getName());
            AddressObject parent = getParent(address);
            while (parent != null) {
                sb.insert(0, parent.getTypename() + " " + parent.getName() + " ");
                parent = getParent(parent);
            }
            System.out.println(sb);
        }
        System.out.println();
    }

    private AddressObject getParent(AddressObject node) {
        return addressObjects.get(addressHierarchy.get(node.getObjectId()).getParentObjId());
    }
}
