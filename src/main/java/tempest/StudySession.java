package tempest;

import java.time.Duration;
import java.util.Date;

public class StudySession {
	public Date date;
	public Duration duration;

	public StudySession(Date date, Duration duration) {
		this.date = date;
		this.duration = duration;
	}
}
