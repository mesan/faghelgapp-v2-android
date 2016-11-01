package no.mesan.faghelg.service.api;

import java.util.List;

import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.model.Program;
import no.mesan.faghelg.model.PushDevice;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FaghelgApi {

    @GET("/program")
    Call<Program> getProgram();

    @GET("/persons")
    Call<List<Person>> getPersons();

    @POST("/register")
    Call<Void> registerForPush(@Header("Authorization") String authToken, PushDevice pushDevice);
}
