package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.client.FileClient;
import br.com.guilhermealvessilve.data.MultipartBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;

@Path("/echo")
public class FileResource {

    @Inject
    @RestClient
    private FileClient fileClient;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String postEchoFile(String body) {
        return body;
    }

    @POST
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String postEchoFileCalled() {
        final var multipartBody = new MultipartBody(
                new ByteArrayInputStream("Hello World".getBytes()),
                "hello.txt"
        );
        return fileClient.sendFile(multipartBody);
    }
}