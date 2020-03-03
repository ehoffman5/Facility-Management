package clientView;

public class RunClient {
    /***
     * Important! Can only run one client at a time due to connection limitations on Heroku database.
     * Must comment out the other two unused clients in this main method in Client class.
     * @param args args
     * @throws Exception if there is an error creating the particular client
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

        FacilityBaseUI facilityClient = new FacilityBaseUI();
        //FacilityUseUI useClient = new FacilityUseUI();
        //FacilityMaintenanceUI maintenanceClient = new FacilityMaintenanceUI();

    }
}
