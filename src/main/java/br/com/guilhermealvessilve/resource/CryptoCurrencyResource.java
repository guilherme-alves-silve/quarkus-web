package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.client.CurrencyClient;
import br.com.guilhermealvessilve.data.Currency;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

@Path("/crypto")
public class CryptoCurrencyResource {

    private static final Logger LOGGER = Logger.getLogger(CryptoCurrencyResource.class.getSimpleName());

    @Inject
    @RestClient
    private CurrencyClient currencyClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Currency> getCrypto(@QueryParam("id") String id) {
        return currencyClient.getCurrency(id);
    }

    @GET
    @Path("/async")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Collection<Currency>> getCryptoAsync(@QueryParam("id") String id) {
        return currencyClient.getCurrencyAsync(id);
    }

    @GET
    @Path("/fault/tolerance")
    @Retry(maxRetries = 3, delay = 1000L)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Currency> getCryptoWithFaultTolerance(@QueryParam("id") String id) {

        final boolean failed = new Random().nextBoolean();
        if (failed) {
            LOGGER.info("A random problem has occurred!");
            throw new RuntimeException("A random problem has occurred!");
        }

        return currencyClient.getCurrency(id);
    }

    @GET
    @Timeout
    @Path("/fault/tolerance/timeout")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Currency> getCryptoTimeout(@QueryParam("id") String id) {

        final boolean failed = new Random().nextBoolean();
        if (failed) {
            try {
                LOGGER.info("Server too slow!");
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                LOGGER.info(e.getMessage());
            }
        }

        return currencyClient.getCurrency(id);
    }

    @GET
    @Fallback(fallbackMethod = "getCryptoFallback")
    @Path("/fault/tolerance/fallback")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Currency> getCryptoThatMayUseFallback(@QueryParam("id") String id) {

        final boolean failed = new Random().nextBoolean();
        if (failed) {
            LOGGER.info("Using the fallback method!");
            throw new RuntimeException("Using the fallback method!");
        }

        return currencyClient.getCurrency(id);
    }

    @GET
    @Path("/fault/tolerance/circuitbreaker")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(failureRatio = 0.4, requestVolumeThreshold = 5, failOn = RuntimeException.class, delay = 1000L)
    public Collection<Currency> getCryptoCircuitBreaker(@QueryParam("id") String id) {

        final boolean failed = new Random().nextBoolean();
        if (failed) {
            LOGGER.info("Circuit Breaker failed in RuntimeException!");
            throw new RuntimeException("Circuit Breaker failed in RuntimeException!");
        }

        return currencyClient.getCurrency(id);
    }

    Collection<Currency> getCryptoFallback(@QueryParam("id") String id) {
        return List.of(new Currency());
    }
}