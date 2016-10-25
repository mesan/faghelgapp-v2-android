package no.mesan.faghelg.model;

public class Constants {

    // AAD PARAMETERS
    // https://login.windows.net/tenantInfo
    public static final String AUTHORITY_URL = "https://login.windows.net/common";

    // Clientid is given from AAD page when you register your Android app
    public static final String CLIENT_ID = "685ff077-c1ca-4d18-b364-7746b4560cea";

    // URI for the resource. You need to setup this resource at AAD
    public static final String RESOURCE_ID = "https://faghelg.herokuapp.com";

    // RedirectUri
    public static final String REDIRECT_URL = "https://faghelg.herokuapp.com";
}
