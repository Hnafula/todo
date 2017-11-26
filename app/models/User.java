package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Constraint;


/**
 * Created by HILDA on 11/1/2017.
 */
@Entity
@Table(name = "tbl_users")

public class User extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String username;

    public String email;

    public String password;

    public String pin;

    public static Finder<Long,User> find=new Finder<Long, User>(Long.class,User.class);

    public static User findByUsername(String username){
        return find.where().eq("username", username).findUnique();

    }
    public static User findByEmail(String email){
        return find.where().eq("email",email).findUnique();

    }
    public static User FindByName(String pin){
        return find.where().eq("pin", pin).findUnique();
    }
    public static User displayPassword(String password){
        return find.where().eq("password",password).findUnique();
    }

}
