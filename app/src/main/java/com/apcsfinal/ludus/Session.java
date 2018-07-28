package com.apcsfinal.ludus;

import java.util.*;
import java.util.Date;
import com.apcsfinal.ludus.Tutor;

/**
 * <code>Session.java</code>
 * 
 * will instantiate the Session object, which is created by a tutor and keeps
 * track of the date, duration, and maximum capacity of a session as well as
 * the list of tutees that will attend.
 * 
 * @author Tyler Reinecke
 * @version 5/22/18
 */
public class Session
{
    // Instance variables
    String tutor;
    String location;
    String time;
    String date; // the date on which the session will take place
    int duration; // the duration of the session (in minutes)
    int tuteeCapacity; // the maximum tutee capacity of the session
    ArrayList<Student> students; // the list of tutees that will attend the session
    // private User[][] pairings;

    public Session()
    {

    }

    /**
     * <code>Session()</code>
     * 
     * constructs the Session object with the tutor creating it as well as the
     * given date, duration (in minutes), and maximum tutee capacity.
     * 
     * @param tutor the tutor creating the session
     * @param date the date on which the session will take place
     * @param duration the duration of the session (in minutes)
     * @param tuteeCapacity the maximum tutee capacity of the session
     */
    public Session(String tutor, String date, String tm, String Location, int duration, int tuteeCapacity)
    {
        this.tutor = tutor;
        this.date = date;
        this.time = tm;
        this.location = Location;
        this.duration = duration;
        this.tuteeCapacity = tuteeCapacity;
        students = new ArrayList<Student>(tuteeCapacity);
    }
    
    // Public methods
    /**
     * <code>addTutee()</code>
     * 
     * adds a tutee who will be attending the session to the list.
     * 
     * @param tutee the tutee to be added
     */
   /* public void addTutee(Student tutee)
    {
        students.add(tutee);
        //tutee.addSession(this);
    }*/
    
    /**
     * <code>removeTutee()</code>
     * 
     * removes a tutee who will no longer be attending the session from the
     * list.
     * 
     * the tutee to be removed
     */
    /*public void removeTutee(Student tutee)
    {
        students.remove(tutee);
        //tutee.removeSession(this);
    }*/

    public String getDesc() {
        String desc;
        desc = time + " on " + date + " at " + location;
        return desc;
    }

    /**
     * <code>removeAllTutees()</code>
     * 
     * removes all tutees from the list.
     */
    /*public void removeAllTutees()
    {
        for (Student tutee : getTutees())
        {
            students.remove(tutee);
            //tutee.removeSession(this);
        }
    }*/
    
    /**
     * <code>rateTutor()</code>
     * 
     * receives a new rating by a tutee to adjust the tutor's rating.
     * 
     * @param rating the tutee's rating
     */
    /*public void rateTutor(double rating)
    {
        tutor.adjustRating(rating);
    }*/
    
    /**
     * <code>getTutor()</code>
     * 
     * @return the tutor hosting the session
     */
    public String getTime(){return time;}
    public void setTime(String tm){time = tm;}

    public void setTutor(String tut){tutor = tut;}

    public String getTutor()
    {
        return tutor;
    }

    public void setLocation(String Location){location = Location;}

    public String getLocation() {
        return location;
    }

    public void setDate(String dt){date = dt;}

    /**
     * <code>getDate()</code>
     * 
     * @return the date on which the session will take place
     */
    public String getDate()
    {
        return date;
    }

    public void setDuration(int durt){duration = durt;}

    /**
     * <code>getDuration()</code>
     * 
     * @return the duration of the session (in minutes)
     */
    public int getDuration()
    {
        return duration;
    }

    public void setTuteeCapacity(int cap){tuteeCapacity = cap;}

    /**
     * <code>getCapacity()</code>
     * 
     * @return the maximum tutee capacity of the session
     */
    public int getCapacity()
    {
        return tuteeCapacity;
    }
    
    /**
     * <code>getTutees()</code>
     * 
     * @return the list of tutees that will attend the session
     */
    public ArrayList getTutees()
    {
        return students;
    }
}
