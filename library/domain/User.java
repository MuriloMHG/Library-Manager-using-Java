package library.domain;

public class User{
    private long id;
    private String name;
    private String email;
    private UserSituation situation;

    public User(){}

    public User(long id, String name, String email, UserSituation situation){
        this.id = id;
        this.name = name;
        this.email = email;
        this.situation = situation;
    }

    public boolean isActive(){return situation == UserSituation.ACTIVE;}
    public long getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public UserSituation getSituation(){return situation;}

    public void setEmail(String newEmail){this.email = newEmail;}

    @Override
    public String toString(){
        return "User{"+
                "id=" + id +
                "name=" + name +
                "email=" + email +
                "situation=" + situation +
                "}";
    }
}
