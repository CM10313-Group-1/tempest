package tempest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import tempest.interfaces.CSVInterface;

/**
 * Module class represents a module and stores its moduleid, name and a list of
 * study sessions
 *
 */
public class Module {
    private final UUID id;
    private final String name;
    private LinkedList<StudySession> studySessions = new LinkedList<>();

    public Module(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Module(String id, String name) {
        this.id = UUID.fromString(id);
        this.name = name;
    }

    public Module(String id, String name, LinkedList<StudySession> studySessions) {
        this.id = UUID.fromString(id);
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
     * Removes the passed in session from the studySessions list
     * for this module
     *
     * @param session The StudySession to be removed
     */
    public void removeSession(StudySession session) {
        studySessions.remove(session);
    }

    /**
     * Converts a module to an array corresponding to rows that can be stored in the
     * CSV file.
     * 
     * @return An array of rows.
     */
    public String[] toRows() {
        if (studySessions.size() > 0) {
            String[] output = new String[studySessions.size()];
            for (int i = 0; i < studySessions.size(); i++) {
                output[i] = studySessions.get(i).toRow(this);
            }
            return output;
        } else {
            return new String[] { this.toBlankRow() };
        }
    }

    /**
     * Generates a string representing the module if it has no sessions.
     * 
     * @return A string representing the module if it has no sessions.
     */
    public String toBlankRow() {
        return getID() + CSVInterface.DELIMITER + getName() + CSVInterface.DELIMITER;
    }

    /**
     * Gets the StudySessions related to this module.
     * 
     * @return The StudySessions related to this module.
     */
    public StudySession[] getStudySessions() {
        return studySessions.toArray(new StudySession[0]);
    }

    /**
     * The returned array only contains one session per day - achieved by adding together the
     * duration of sessions with the same day
     *
     * @return An array containing study sessions
     */
    public StudySession[] getSessionsPerDay() {
        ArrayList<StudySession> sessions = new ArrayList<>();

        ArrayList<StudySession> moduleSessions = new ArrayList<>(Arrays.asList(this.getStudySessions()));

        // Holds study sessions that have been dealt with so they aren't dealt with twice
        ArrayList<StudySession> completed = new ArrayList<>();

        // Used to compare just the day of sessions
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Comparing all sessions to each other - adding durations of sessions on the same day
        for (StudySession studySession : moduleSessions) {
            Duration duration = studySession.duration;

            for (StudySession s : moduleSessions) {
                if (dateFormat.format(s.date)
                        .equals(dateFormat.format(studySession.date))
                        && !completed.contains(studySession)
                        && !s.equals(studySession)) {

                    // Added this sessions time to another session w/ the same date -> don't want to look at this again
                    completed.add(s);
                    duration = duration.plus(s.duration);
                }
            }

            if (!completed.contains(studySession)) {
                completed.add(studySession);

                // Creating a new session holding the combined duration of sessions for a day
                sessions.add(new StudySession(studySession.date, duration));
            }
        }

        return sessions.toArray(new StudySession[0]);
    }

    /**
     * Gets the id of the module.
     * 
     * @return A string representing the id of the module.
     */
    public String getID() {
        return id.toString();
    }

    /**
     * Gets the name of the module.
     * 
     * @return The name of the module.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof Module) {
            Module other = (Module) obj;
            boolean matchingIDs = this.id.equals(other.id);
            boolean matchingNames = this.name.equals(other.name);
            return matchingIDs || matchingNames;
        } else {
            return false;
        }
    }
}
