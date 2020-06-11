package br.com.guilhermealvessilve.client;

import br.com.guilhermealvessilve.data.Currency;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.concurrent.CompletionStage;

@Path("ticker")
@RegisterRestClient(configKey = "config.api.crypto")
public interface CurrencyClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Collection<Currency> getCurrency(@QueryParam("id") String id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<Collection<Currency>> getCurrencyAsync(@QueryParam("id") String id);
}
