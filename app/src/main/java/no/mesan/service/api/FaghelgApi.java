package no.mesan.service.api;

import no.mesan.model.Program;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FaghelgApi {

    @GET("/program")
    Call<Program> getProgram();
}
