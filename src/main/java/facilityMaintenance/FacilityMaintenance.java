package facilityMaintenance;

import database.DBConnector;
import facilityBase.Facility;
import java.util.*;
import java.sql.*;


public class FacilityMaintenance extends Facility {
    // Variable declarations
    private float cost;
    private String maintRequestDescription;

    // Constructor to initialize class attributes
    public FacilityMaintenance() { }


    // ---------- Getter and Setter Methods ---------- //
    public void setMaintRequestDescription(String maintRequestDescription) {
        this.maintRequestDescription = maintRequestDescription;
    }

    public String getMaintRequestDescription() {
        return maintRequestDescription;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }


    // ---------- Functionality Methods ---------- //
    // Method for submitting a new request
    public FacilityMaintenance makeFacilityMaintRequest(Facility fac, String maintenanceDescription, int cost) {
        try {

            FacilityMaintenance maint = new FacilityMaintenance();
            maint.setMaintRequestDescription(maintenanceDescription);
            maint.setCost(cost);
            maint.setFacilityNumber(fac.getFacilityNumber());

            Statement st = DBConnector.getConnection().createStatement();
            String makeMaintRequestQuery = "INSERT INTO MaintenanceRequests (facility_id, cost, description) VALUES (" +
                    fac.getFacilityNumber() + ", '" + cost + "', " + maintenanceDescription + ")";
            st.execute(makeMaintRequestQuery);
            System.out.println("Maintenance: *************** Query " + makeMaintRequestQuery + "\n");

            //close to manage resources
            st.close();

            return maint;

        }
        catch (SQLException se) {
            System.err.println("Maintenance: Threw a SQLException making a maintenance request.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return null;
    }

    // Print out a list of all current requests
    public List<FacilityMaintenance> listFacilityMaintRequests(Facility fac) {

        List<FacilityMaintenance> listOfMaintRequests = new ArrayList<FacilityMaintenance>();

        try {

            Statement st = DBConnector.getConnection().createStatement();
            String listMaintRequestsQuery = "SELECT * FROM MaintenanceRequests WHERE facility_number = '" +
                    fac.getFacilityNumber() + "' ORDER BY cost";

            ResultSet maintRS = st.executeQuery(listMaintRequestsQuery);
            System.out.println("Maintenance: *************** Query " + listMaintRequestsQuery + "\n");

            while ( maintRS.next() ) {
                FacilityMaintenance maintenanceRequest = new FacilityMaintenance();
                maintenanceRequest.setMaintRequestDescription(maintRS.getString("description"));
                maintenanceRequest.setCost(maintRS.getInt("cost"));
                maintenanceRequest.setFacilityNumber(fac.getFacilityNumber());
                listOfMaintRequests.add(maintenanceRequest);
            }

            //close to manage resources
            maintRS.close();
            st.close();

        }
        catch (SQLException se) {
            System.err.println("Maintenance: Threw a SQLException retrieving list of maintenance "
                    + "requests from MaintenanceRequest table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return listOfMaintRequests;
    }

    // Method for setting request to an active order
    public void scheduleMaintenance(FacilityMaintenance maintRequest) {

        try {  // add to ActiveMaintenance

            Statement st = DBConnector.getConnection().createStatement();
            String scheduleMaintenanceAddQuery = "INSERT INTO ActiveMaintenance (facility_number, cost, description) VALUES (" +
                    maintRequest.getFacilityNumber() + ", '" + maintRequest.getCost() +
                    "', " + maintRequest.getMaintRequestDescription() + ")";
            st.execute(scheduleMaintenanceAddQuery);
            System.out.println("Maintenance: *************** Query " + scheduleMaintenanceAddQuery + "\n");

            //close to manage resources
            st.close();

        }
        catch (SQLException se) {
            System.err.println("Maintenance: Threw a SQLException adding a ActiveMaintenance "
                    + "request to maintenance table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        try {  // remove from MaintenanceRequests

            Statement st = DBConnector.getConnection().createStatement();
            String scheduleMaintenanceRemoveQuery = "DELETE FROM MaintenanceRequests WHERE facility_number = " +
                    maintRequest.getFacilityNumber() + " AND description = '" + maintRequest.getMaintRequestDescription() +
                    "' AND cost = " + maintRequest.getCost();
            st.execute(scheduleMaintenanceRemoveQuery);
            System.out.println("Maintenance: *************** Query " + scheduleMaintenanceRemoveQuery + "\n");

            //close to manage resources
            st.close();

        }
        catch (SQLException se) {
            System.err.println("Maintenance: Threw a SQLException removing a "
                    + "maintenance request from MaintenanceRequests table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }

    // Prints out a list of all active maintenance orders
    public List<FacilityMaintenance> listMaintenance(Facility fac) {
        List<FacilityMaintenance> listOfCompletedMaintenance = new ArrayList<FacilityMaintenance>();

        try {

            Statement st = DBConnector.getConnection().createStatement();
            String listMaintenanceQuery = "SELECT * FROM ActiveMaintenance WHERE facility_number = '" +
                    fac.getFacilityNumber() + "' ORDER BY cost";

            ResultSet maintRS = st.executeQuery(listMaintenanceQuery);
            System.out.println("Maintenance: *************** Query " + listMaintenanceQuery + "\n");

            while ( maintRS.next() ) {
                FacilityMaintenance maintenance = new FacilityMaintenance();
                maintenance.setMaintRequestDescription(maintRS.getString("description"));
                maintenance.setCost(maintRS.getInt("cost"));
                maintenance.setFacilityNumber(fac.getFacilityNumber());
                listOfCompletedMaintenance.add(maintenance);
            }

            //close to manage resources
            maintRS.close();
            st.close();

        }
        catch (SQLException se) {
            System.err.println("Maintenance: Threw a SQLException retrieving list of maintenance "
                    + "from ActiveMaintenance table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }

        return listOfCompletedMaintenance;

    }
}