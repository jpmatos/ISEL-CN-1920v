package serviceapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionManager {
    private List<Session> activeSessions = Collections.synchronizedList(new ArrayList<>());
    private AtomicInteger sessionId = new AtomicInteger(1000);

    public String newSession(String username, boolean premium) {
        Session session = new Session(username, premium);
        activeSessions.add(session);
        return session.getID();
    }

    public boolean closeSession(String sessionID) {
        Optional<Session> optional = activeSessions.stream().filter(item -> item.getID().equals(sessionID)).findFirst();
        if(optional.isPresent()){
            activeSessions.remove(optional.get());
            return true;
        }
        else
            return false;
    }

    public List<Session> getActiveSessions() {
        return activeSessions;
    }

    public boolean isValid(String sessionId) {
        Optional<Session> optional = activeSessions.stream().filter(item -> item.getID().equals(sessionId)).findFirst();
        return optional.isPresent();
    }

    public String getUsername(String sessionId) {
        Optional<Session> optional = activeSessions.stream().filter(item -> item.getID().equals(sessionId)).findFirst();
        if(optional.isPresent()){
            return optional.get().getUsername();
        }
        else
            return "";
    }

    public boolean isPremium(String sessionId) {
        Optional<Session> optional = activeSessions.stream().filter(item -> item.getID().equals(sessionId)).findFirst();
        if(optional.isPresent()){
            return  optional.get().getPremium();
        }
        else
            return false;
        //TODO Throw some exception
    }

    class Session {
        private String ID;
        private String username;
        private boolean premium;

        private Session(String username, boolean premium) {
            this.ID = String.valueOf(sessionId.incrementAndGet());
            this.username = username;
            this.premium = premium;
        }

        String getID() {
            return this.ID;
        }

        String getUsername() {
            return this.username;
        }

        boolean getPremium(){
            return this.premium;
        }
    }
}
