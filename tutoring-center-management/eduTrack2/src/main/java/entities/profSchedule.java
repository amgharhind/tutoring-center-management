package entities;

import java.sql.Time;
import java.util.Date;

public class profSchedule {
	private int idProfSchedule;
    private int fkEnseignant;
    private Date specificDate;
    private Time startTime;
    private Time endTime;
    private boolean isWholeDay;

 
    public int getIdProfSchedule() {
        return idProfSchedule;
    }

    public void setIdProfSchedule(int idProfSchedule) {
        this.idProfSchedule = idProfSchedule;
    }

    public int getFkEnseignant() {
        return fkEnseignant;
    }

    public void setFkEnseignant(int fkEnseignant) {
        this.fkEnseignant = fkEnseignant;
    }

    public Date getSpecificDate() {
        return specificDate;
    }

    public void setSpecificDate(Date specificDate) {
        this.specificDate = specificDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public boolean isWholeDay() {
        return isWholeDay;
    }

    public void setWholeDay(boolean wholeDay) {
        isWholeDay = wholeDay;
    }
}
