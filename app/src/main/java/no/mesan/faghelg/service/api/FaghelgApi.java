package no.mesan.faghelg.service.api;

import java.util.List;

import no.mesan.faghelg.model.AuthToken;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.model.MessageOutput;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.model.Program;
import no.mesan.faghelg.model.PushDevice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FaghelgApi {

    @POST("/auth")
    Call<AuthToken> authenticate(@Header("Authorization") String idToken, @Query("phone") String phoneNumber);

    @GET("/program")
    Call<Program> getProgram();

    @GET("/persons")
    Call<List<Person>> getPersons();

    @POST("/push/register")
    Call<Void> registerForPush(@Header("Authorization") String authToken,
                               @Body PushDevice pushDevice);

    @GET("/messages")
    Call<List<Message>> getMessages();

    @POST("/push")
    Call<Void> postMessage(@Header("Authorization") String token,
                           @Body MessageOutput message);
}
