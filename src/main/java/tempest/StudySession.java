package tempest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import tempest.interfaces.CSVInterface;

public class StudySession {
	public Date date;
	public Duration duration;

	public StudySession(Date date, Duration duration) {
		this.date = date;
		this.duration = duration;
	}

	/**
	 * Generates a string representing the session that can be stored in the CSV
	 * file.
	 * 
	 * @param parentModule The module the session belongs to.
	 * @return A string representing the session that can be stored in the CSV file.
	 */
	public String toRow(Module parentModule) {
		DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");
		return parentModule.getID() + CSVInterface.DELIMITER + parentModule.getName() + CSVInterface.DELIMITER
				+ isoDate.format(date) + CSVInterface.DELIMITER + String.valueOf(duration.toMinutes());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;

		if (obj instanceof StudySession) {
			StudySession other = (StudySession) obj;
			boolean matchingDates = this.date.equals(other.date);
			boolean matchingDurations = this.duration.equals(other.duration);
			return matchingDates && matchingDurations;
		} else {
			return false;
		}
	}
}
