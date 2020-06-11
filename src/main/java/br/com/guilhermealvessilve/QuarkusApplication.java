package br.com.guilhermealvessilve;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "Quarkus Web Study API",
                description = "This API has simple examples of " +
                        "the Quarkus Framework used for web applications",
                version = "1.0"
        ),
        servers = @Server(
                url = "localhost"
        )
)
public class QuarkusApplication extends Application {

}
