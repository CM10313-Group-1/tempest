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
    public String moduleName;
    public LinkedList<StudySession> studySessionsList = new LinkedList<StudySession>();

    public Module() {
    }

    public Module(String id, String moduleName) {
        this.id = id;
        this.moduleName = moduleName;
    }

    public Module(String id, String moduleName, LinkedList<StudySession> studySessionsList) {
        this.id = id;
        this.moduleName = moduleName;
        this.studySessionsList = studySessionsList;
    }

    /**
     * This method adds a study session to the sessions list of the module. It
     * creates a StudySession using the date and duration arguments.
     * 
     * @param date     : Date of session
     * @param duration : Duration of session
     */
    public void addSession(Date date, Duration duration) {
        studySessionsList.add(new StudySession(date, duration));
    }

    /**
     * This method adds a study session to the sessions list of the module.
     * 
     * @param session : StudySession created before calling this method
     */
    public void addSession(StudySession session) {
        studySessionsList.add(session);
    }
}
