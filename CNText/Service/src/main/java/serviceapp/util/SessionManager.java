package serviceapp.util;

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

        Logger.log(String.format("User '%s' logged in with session '%s'. Premium: %b.", session.getUsername(), session.getID(), session.getPremium()));
        return session.getID();
    }

    public boolean closeSession(String sessionID) {
        Optional<Session> optional = activeSessions.stream().filter(item -> item.getID().equals(sessionID)).findFirst();
        if(optional.isPresent()){
            Session session = optional.get();
            activeSessions.remove(session);

            Logger.log(String.format("User '%s' logged out with session '%s'.", session.getUsername(), session.getID()));
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
        return optional.map(Session::getPremium).orElse(false);
    }

    public int getFreeSessionsCount() {
        return (int) activeSessions.stream().filter(item -> !item.getPremium()).count();
    }

    public int getPremiumSessionsCount() {
        return (int) activeSessions.stream().filter(Session::getPremium).count();
    }

    public class Session {
        private String ID;
        private String username;
        private boolean premium;

        private Session(String username, boolean premium) {
            this.ID = String.valueOf(sessionId.incrementAndGet());
            this.username = username;
            this.premium = premium;
        }

        public String getID() {
            return this.ID;
        }

        String getUsername() {
            return this.username;
        }

        public boolean getPremium(){
            return this.premium;
        }
    }
}
