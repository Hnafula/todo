package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;


/**
 * Created by HILDA on 11/7/2017.
 */
@Entity
@Table(name = "category_details")
public class Categories extends Model {
    @Id

    public Long id;

    public String categoryname;

    public String categorydetails;

    public static Finder<Long,Categories> category = new Finder<Long, Categories>(Long.class,Categories.class);

    public static Categories findByCategoryName(String categoryname){
        return category.where().eq("username", categoryname).findUnique();
    }

    public static List<Categories> findAllCategories(){
        return category.where().findList();
    }

    public static Categories findCategoryById(Long id){
        return category.where().eq("id", id).findUnique();
    }
}
