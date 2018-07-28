package com.apcsfinal.ludus;

import java.util.Arrays;

/**
 * <code>Subject.java</code>
 * 
 * will outline the subjects available for tutors and tutees.
 * 
 * @author Sunwoo Kim
 * @version 5/18/18
 */
public abstract class Subject
{
    // Instance variables
    private String name; // the name of the subject
    private String[] courses; // 1st row lists courses, 2nd row lists "Y/N"
    
    // Constructor
    /**
     * <code>Subject()</code>
     * 
     * constructs the Subject object with a given name and list of courses
     * for the subject.
     * 
     * @param subjectName the given name
     * @param courseList the list of courses for the subject
     */
    public Subject(String subjectName, String[] courseList)
    {
        name = subjectName;
        courses = courseList;
    }
    
    // Public methods
    /**
     * <code>setCourses()</code>
     * 
     * sets the courses within the subject that the tutor/tutee has selected.
     * 
     * @param input the input from the user (true -> "Y", false -> "N")
     */
    private void findCourses(String search)
    {

    }
}
