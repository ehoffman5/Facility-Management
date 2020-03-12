package interfaces;

import facilityBase.Facility;

import java.util.List;

public interface FacilityBaseDataInterface {

    public void addFacility(Facility facility);
    public void removeFacility(int facilityNumber);
    public List<Facility> listFacilities();
    public void addFacilityDetails(int facilityNumber, String facilityPhoneNo, String facilityName);
    public Facility getFacilityInformation(int facility_number);

}
