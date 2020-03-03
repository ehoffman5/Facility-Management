package facilityMaintenance;

import facilityBase.Facility;

public class DownTime extends FacilityMaintenance {

    FacilityMaintenance maintObj = new FacilityMaintenance();

    // Function for calculating time not spent on maintenance requests
    public int calcDownTimeForFacility(Facility fac) {
        int daysOfDownTime = 0;
        try {
            int numberOfCompletedMaintItems = maintObj.listMaintenance(fac).size();
            daysOfDownTime = numberOfCompletedMaintItems * 7;  // each maintenance order counts for 7 days
        } catch (Exception se) {
            System.err.println("Maintenance: Threw an Exception calculating "
                    + "the down time for a facility.");
            System.err.println(se.getMessage());
        }

        return daysOfDownTime;
    }

}