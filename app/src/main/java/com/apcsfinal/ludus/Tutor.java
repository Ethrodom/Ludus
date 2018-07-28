package com.apcsfinal.ludus;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

/**
 * <code>Tutor.java</code>
 * 
 * will instantiate the Tutor object, which represents a tutor who offers
 * assistance in certain subjects by creating and hosting sessions.
 * 
 * @author Tyler Reinecke
 * @version 5/22/18
 */
public class Tutor extends User
{
    // Instance variables
    private String classes;
    private String UID;
    private double hours; // the total time spent tutoring
    private float rating; // the average rating by tutees
    private int tuteeCount; // the number of tutees that have rated the tutor
    private HashMap offeredSubjects; // the subjects and courses that the tutor is offering
    
    // Constructor
    /**
     * <code>Tutor()</code>
     * 
     * constructs the Tutor object with a given ID, name, email address, phone
     * number, password, and list of offered subjects.
     * 
     //* @param id the tutor's ID
     //* @param name the tutor's name
     //* @param email the tutor's email address
     //* @param phone the tutor's phone number
     //* @param password the tutor's password
     //* @param subjects the tutor's list of offered subjects
     */

    public Tutor()
    {

    }

    public Tutor(String id, String name, String email, String phone, String password, HashMap subjects, String uid)
    {
        super("Tutor", id, name, email, phone, password);
        hours = 0;
        rating = 0;
        tuteeCount = 0;
        UID = uid;
        //hostingSessions = new ArrayList<Session>();
        offeredSubjects = subjects;
        SubToString();
    }

    // Public methods


    public String getUID()
    {
        return UID;
    }
    
    /**
     * <code>adjustRating()</code>
     * 
     * increments the tutee count and adjusts the overall rating of the tutor.
     * 
     * @param newRating the new rating to be considered
     */
    public void adjustRating(float newRating)
    {
        tuteeCount++;
        rating = (rating + newRating) / tuteeCount;
    }

    public void SubToString()
    {
        Map<String, List<String>> map = offeredSubjects;
        classes = this.getName() + " knows ";
        List<List<String>> subs = new ArrayList<List<String>>(offeredSubjects.size());
        int x = 0;

        for (HashMap.Entry<String, List<String>> ee: map.entrySet())
        {
            List<String> sub = ee.getValue();
            subs.add(sub);
        }
        for(int i = 0; i < subs.size() - 1; i++)
        {
            List<String> sub = subs.get(i);
            for(int p = 0; p < sub.size(); p++)
            {
                classes += sub.get(p) + ", ";

            }
        }
        List<String> sub = subs.get(subs.size()-1);
        for(int p = 0; p < sub.size() - 1; p++)
        {
            classes += sub.get(p) + ", ";

        }
        classes += "and " + sub.get(sub.size()-1);

    }

    public String getClasses()
    {
        return classes;
    }
    
    /**
     * <code>addHours()</code>
     * 
     * adds the given time (in hours) to the tutor's total time spent
     * tutoring.
     * 
     * @param hoursSpent the number of hours spent at a session
     */
    public void addHours(double hoursSpent)
    {
        hours += hoursSpent;
    }
    
    /**
     * <code>getHours()</code>
     * 
     * @return the tutor's total time spent tutoring
     */
    public double getHours()
    {
        return hours;
    }
    
    /**
     * <code>getRating()</code>
     * 
     * @return the tutor's average rating by tutees
     */
    public float getRating()
    {
        return rating;
    }
    
    /**
     * <code>getSubjects()</code>
     * 
     * @return the subjects and courses that the tutor is offering
     */
    public HashMap getSubjects()
    {
        return offeredSubjects;
    }
}
