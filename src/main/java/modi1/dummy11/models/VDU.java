package modi1.dummy11.models;

public  class VDU {
    public static String VIRUS_DATA_URI="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin/?";
    public  VDU(){}
    public  static String getVirusDataUri() {
        return VIRUS_DATA_URI;
    }

    public  static void setVirusDataUri(String string) {
        VIRUS_DATA_URI += string;
    }

    public static void setToOriginalString()
    {
        VIRUS_DATA_URI="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin/?";
    }
}
