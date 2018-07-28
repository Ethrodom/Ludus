package com.apcsfinal.ludus;

import java.util.ArrayList;

/**
 * <code>Student.java</code>
 * 
 * will instantiate the Student object, which represents a Student who attends
 * sessions that are hosted by tutors.
 * 
 * @author Tyler Reinecke
 * @version 5/22/18
 */
public class Student extends User
{
    // Instance variables
    //private ArrayList<Session> attendingSessions; // the list of sessions that the Student will attend
    // private Subject[] neededSubjects; // the subjects and courses in which the Student needs assistance
    
    // Constructor
    /**
     * <code>Student()</code>
     * 
     * constructs the Student object with a given ID, name, email address, phone
     * number, password, and list of needed subjects.
     * 
     * @param id the Student's ID
     * @param name the Student's name
     * @param email the Student's email address
     * @param phone the Student's phone number
     * @param password the Student's password
     */
    String type = "Student";

    public Student(String id, String name, String email, String phone, String password)
    {
        super("Student", id, name, email, phone, password);
        //attendingSessions = new ArrayList<Session>();
    }
    public Student()
    {

    }


    public String getType()
    {
        return type;
    }
    
    // Public methods
    /**
     * <code>addSession()</code>
     * 
     * adds a session to the list of sessions the Student is attending.
     * 
     * @param session the session to be added
     */
    /*public void addSession(Session session)
    {
        attendingSessions.add(session);
    }*/
    
    /**
     * <code>removeSession()</code>
     * 
     * removes a session from the list of sessions the Student is attending.
     * 
     * @param session the session to be removed
     */
   /* public void removeSession(Session session)
    {
        attendingSessions.remove(session);
    }*/
    
    /**
     * <code>getSessions()</code>
     * 
     * @return the list of sessions that the Student will attend
     */
   /* public ArrayList<Session> getSessions()
    {
        return attendingSessions;
    }*/
}
