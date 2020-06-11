package br.com.guilhermealvessilve.socket;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
@ServerEndpoint(value = "/chat/{username}")
public class ChatSocket {

    private final ConcurrentMap<String, Session> sessionMap
            = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username
    ) {
        sessionMap.put(username, session);
        sendMessage(String.format("User %s logged in", username));
    }

    @OnMessage
    public void onMessage(
            String message,
            @PathParam("username") String username
    ) {
        sendMessage(String.format(">> %s: %s", username, message));
    }

    @OnClose
    public void onClose(
            Session session,
            @PathParam("username") String username
    ) {
        sessionMap.remove(username);
        sendMessage(String.format("User %s logged out", username));
    }

    @OnError
    public void onError(
            Session session,
            @PathParam("username") String username,
            Throwable throwable
    ) {
        sessionMap.remove(username);
        throwable.printStackTrace();
        sendMessage(String.format("User %s logged out because of an error", username));
    }

    public void sendMessage(String message) {
        sessionMap.forEach((username, session) -> {
            final var remote = session.getAsyncRemote();
            remote.sendObject(message, result -> {
               if (result.getException() != null) {
                   result.getException().printStackTrace();
               }
            });
        });
    }
}
