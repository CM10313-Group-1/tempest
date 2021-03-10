package tempest;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedList;

/**
 * Module class represents a module and stores its moduleid, name and a list of
 * study sessions
 *
 */
public class Module {
    public String id;
    public String name;
    public LinkedList<StudySession> studySessions = new LinkedList<StudySession>();

    public Module() {
    }

    public Module(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Module(String id, String name, LinkedList<StudySession> studySessions) {
        this.id = id;
        this.name = name;
        this.studySessions = studySessions;
    }

    /**
     * This method adds a study session to the sessions list of the module. It
     * creates a StudySession using the date and duration arguments.
     * 
     * @param date     : Date of session
     * @param duration : Duration of session
     */
    public void addSession(Date date, Duration duration) {
        studySessions.add(new StudySession(date, duration));
    }

    /**
     * This method adds a study session to the sessions list of the module.
     * 
     * @param session : StudySession created before calling this method
     */
    public void addSession(StudySession session) {
        studySessions.add(session);
    }

    /**
     * Converts a module to an array corresponding to rows that can be stored in the
     * CSV file.
     * 
     * @return An array of rows.
     */
    public String[] toRows() {
        String[] output = new String[studySessions.size()];
        for (int i = 0; i < studySessions.size(); i++) {
            output[i] = studySessions.get(i).toRow(this);
        }
        return output;
    }
}
