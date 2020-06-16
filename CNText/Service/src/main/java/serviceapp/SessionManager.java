package serviceapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SessionManager {
    static List<Session> activeSessions = Collections.synchronizedList(new ArrayList<>());

    public String newSession(boolean premium) {
        Session session = new Session(premium);
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

    class Session {
        private String ID;
        private boolean premium;

        private Session(boolean premium) {
            this.ID = String.valueOf((int) (Math.random() * 1_000_000));
            this.premium = premium;
        }

        String getID() {
            return this.ID;
        }

        boolean getPremium(){
            return this.premium;
        }
    }
}
