package no.mesan.faghelg.service.api;

import java.util.List;

import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.model.Program;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FaghelgApi {

    @GET("/program")
    Call<Program> getProgram();

    @GET("/persons")
    Call<List<Person>> getPersons();
}
