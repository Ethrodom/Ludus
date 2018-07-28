package com.apcsfinal.ludus;

/**
 * <code>User.java</code>
 * 
 * will outline the information stored on individual users.
 * 
 * @author Tyler Reinecke
 * @version 5/22/18
 */
public abstract class User
{
    // Instance variables
    private String type;
    private String id; // the user's login ID
    private String name; // the name of the user
    private String email; // the user's email address
    private String phone; // the user's phone number
    private String password; // the user's password
    // private Session[] sessionHistory;
    
    // Constructor
    /**
     * <code>User()</code>
     * 
     * constructs the User object with a given ID, name, email address, phone
     * number, and password.
     *
     * @param type the user's account type
     * @param id the user's ID
     * @param name the user's name
     * @param email the user's email address
     * @param phone the user's phone number
     * @param password the user's password
     */
    public User(String type, String id, String name, String email, String phone, String password)
    {
        this.type = type;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
    public User()
    {

    }
    
    // Public methods
    /**
     * <code>changeName()</code>
     * 
     * changes the user's name.
     * 
     * @param newName the user's new name
     */
    public void changeName(String newName)
    {
        name = newName;
    }
    
    /**
     * <code>changeEmail()</code>
     * 
     * changes the user's email address.
     * 
     * @param newEmail the user's new email address
     */
    public void changeEmail(String newEmail)
    {
        email = newEmail;;
    }
    
    /**
     * <code>changePhone()</code>
     * 
     * changes the user's phone number.
     * 
     * @param newPhone the user's new phone number
     */
    public void changePhone(String newPhone)
    {
        phone = newPhone;
    }
    
    /**
     * <code>getId()</code>
     * 
     * @return the user's ID
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * <code>getName()</code>
     * 
     * @return the user's name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * <code>getEmail()</code>
     * 
     * @return the user's email address
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     * <code>getPhone()</code>
     * 
     * @return the user's phone number
     */
    public String getPhone()
    {
        return phone;
    }
    
    /**
     * <code>getPassword()</code>
     * 
     * @return the user's password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * <code>getTyler()</code>
     *
     * @return the user's type
     */
    public String getType()
    {
        return type;
    };
}
