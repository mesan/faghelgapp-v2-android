package no.mesan.faghelg.service.api;

import java.util.List;

import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.model.MessageOutput;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.model.Program;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FaghelgApi {

    @GET("/program")
    Call<Program> getProgram();

    @GET("/persons")
    Call<List<Person>> getPersons();

    @GET("/messages")
    Call<List<Message>> getMessages();

    @POST("/messages")
    Call<Void> postMessage(@Header("Authorization") String token,
                           @Body MessageOutput message);
}
