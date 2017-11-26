package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by HILDA on 11/5/2017.
 */
@Entity
@Table(name="tbl_items")
public class Items extends Model {
    @Id

    public Long id;

    public String itemname;

    public String itemdetails;

    public Long categoryid;

    public static Finder<Long,Items> item = new Finder<Long, Items>(Long.class,Items.class);

    public static Items findByItemName(String itemname){

        return item.where().eq("username", itemname).findUnique();
    }
    public static List<Items> findAllItems(){
        return item.where().findList();
    }
    public static Items findItemById(Long id){
        return item.where().eq("id", id).findUnique();
    }

   /* public static Items findItemByCategoryId(long categoryid) {
        return item.where().eq("categoryid", categoryid).findUnique();
    }*/
}
