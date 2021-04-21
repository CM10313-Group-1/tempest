package tempest;

import java.awt.Color;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import tempest.interfaces.CSVInterface;

/**
 * Module class represents a module and stores its moduleid, name and a list of
 * study sessions
 *
 */
public class Module implements Serializable {
    private static final long serialVersionUID = 4088145156876883901L;
    private final UUID id;
    private final String name;
    private LinkedList<StudySession> studySessions = new LinkedList<>();
    private int weeklyGoal;
    private final Color defaultColor;
    private Color color;

    public Module(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.defaultColor = generateDefaultColor(name);
        this.color = defaultColor;
    }

    public Module(String id, String name) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.defaultColor = generateDefaultColor(name);
        this.color = defaultColor;
    }

    public Module(String id, String name, LinkedList<StudySession> studySessions) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.studySessions = studySessions;
        this.defaultColor = generateDefaultColor(name);
        this.color = defaultColor;
    }

    private Color generateDefaultColor(String name) {
        Random rand = new Random(hash(name));
        float generated = rand.nextFloat();
        float minHue = 0 / 360f;
        float maxHue = 360 / 360f;
        float hue = generated * maxHue + (1 - generated) * minHue;
        return new Color(Color.HSBtoRGB(hue, 1, 0.8f));
    }

    /**
     * This method sets the weekly goal of the module.
     *
     * @param minutes The length of the weekly goal entered
     */
    public void setGoal(int minutes) {
        weeklyGoal = minutes;
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
     * Removes the passed in session from the studySessions list for this module
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
     * The returned array only contains one session per day - achieved by adding
     * together the duration of sessions with the same day
     *
     * @return An array containing study sessions
     */
    public StudySession[] getSessionsPerDay() {
        ArrayList<StudySession> sessions = new ArrayList<>();

        ArrayList<StudySession> moduleSessions = new ArrayList<>(Arrays.asList(this.getStudySessions()));

        // Holds study sessions that have been dealt with so they aren't dealt with
        // twice
        ArrayList<StudySession> completed = new ArrayList<>();

        // Used to compare just the day of sessions
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Comparing all sessions to each other - adding durations of sessions on the
        // same day
        for (StudySession studySession : moduleSessions) {
            Duration duration = studySession.duration;

            for (StudySession s : moduleSessions) {
                if (dateFormat.format(s.date).equals(dateFormat.format(studySession.date))
                        && !completed.contains(studySession) && !s.equals(studySession)) {

                    // Added this sessions time to another session w/ the same date -> don't want to
                    // look at this again
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

    public Color getDefaultColor() {
        return this.defaultColor;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void resetToDefaultColor() {
        setColor(getDefaultColor());
    }

    /**
     * Gets the weekly goal of the module.
     *
     * @return The weekly goal for the module
     */
    public int getWeeklyGoal() {
        return weeklyGoal;
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

    /**
     * Uses the FNV-1a hash function to convert the module name to a long.
     *
     * @return Hash of the module name.
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Fowler%E2%80%93Noll%E2%80%93Vo_hash_function#FNV-1_hash">FNV-1a</a>
     */
    private long hash(String name) {
        byte[] data = name.getBytes();
        long seed = 0xcbf29ce484222325L;
        for (byte b : data) {
            seed ^= (b & 0xff);
            seed *= 1099511628211L;
        }

        return seed;
    }

    public long hash() {
        return this.hash(this.name);
    }
}
