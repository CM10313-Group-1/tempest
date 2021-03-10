package tempest;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import tempest.interfaces.CSVInterface;

/**
 * Module class represents a module and stores its moduleid, name and a list of
 * study sessions
 *
 */
public class Module {
    private UUID id;
    private String name;
    private LinkedList<StudySession> studySessions = new LinkedList<StudySession>();

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

    public String toBlankRow() {
        return getID() + CSVInterface.DELIMITER + getName() + CSVInterface.DELIMITER;
    }

    public StudySession[] getStudySessions() {
        return studySessions.toArray(new StudySession[studySessions.size()]);
    }

    public String getID() {
        return id.toString();
    }

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
            boolean matchingSessions = Arrays.deepEquals(this.getStudySessions(), other.getStudySessions());
            return matchingIDs && matchingNames && matchingSessions;
        } else {
            return false;
        }
    }
}
