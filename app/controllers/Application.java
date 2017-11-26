package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Categories;
import models.Items;
import models.User;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public static Result index() {
        return ok(login.render("Your new application is ready."));
    }

    public static Result registerPage(){
        return ok(register.render("register here"));
    }

    public static Result toDoCategories(){
        Logger.info("LoggedInuser$$"+session().get("username"));
        if(session().get("username")!=null)
            return ok(todocategories.render("to do categories"));

        return index();
    }

    public static Result logOut(){
        session().clear();
        return index();
    }

    public static Result travelToDoItems(){
        return ok(traveltodoItems.render("to do travel items"));
    }

  /* public static Result editingItem(){
        return ok(editingItem.render("edit item"));
    }
    */
  public static Result findingPin(User user){
      return ok(pinreset.render("pin is here",user));
  }


    public static Result addItem(){
        return ok(addItem.render("add Item"));
    }

    public static Result addCategory(){
        return ok(addCategory.render());
    }

    public static Result resetPassword(){
        return ok(pin.render("Reset password"));
    }

    public static Result register(){
        ObjectNode majibu = Json.newObject();

        DynamicForm frm= Form.form().bindFromRequest();
        String username = frm.get("firstname");
        String password = frm.get("pass");
        String email = frm.get("email");
        String pin = frm.get("pin");

        User user = User.findByUsername(username);

            if(user != null){
                majibu.put("message","User taken");
                majibu.put("code",203);
                return ok(majibu);
            }
        User users = User.findByEmail(email);
        if(users !=null ){
            majibu.put("message","Email taken");
            majibu.put("code",204);
            return ok(majibu);
        }
        User use =new User();
        use.username=username;
        use.password=password;
        use.email=email;
        use.pin = pin;
        use.save();

        majibu.put("message","user created successfully");
        majibu.put("code",200);
        return ok(majibu);
    }

    public static Result login() {
        ObjectNode result = Json.newObject();

        DynamicForm lg = Form.form().bindFromRequest();
        String username = lg.get("name1");
        String password = lg.get("pass1");

        Logger.info("Request imeingia");
        Logger.info("username:" + username);
        Logger.info("password:" + password);

        User userFromDb = User.findByUsername(username);
        if (userFromDb != null) {

            if (!userFromDb.password.equals(password)) {
                result.put("message", "invalid password");
                result.put("code", 201);
                return ok(result);
            }

            session("username",userFromDb.username);
            result.put("message", "logged in successfully");
            result.put("code", 200);
            return ok(result);
        } else {
            result.put("message", "user not found");
            result.put("code", 201);
            return ok(result);
        }
    }
        public static Result resettingOfPasword() {
            ObjectNode reset = Json.newObject();
            DynamicForm setpassword = Form.form().bindFromRequest();
            String username = setpassword.get("username");
            String pin = setpassword.get("pin");

            Logger.info("username" + username);

            User userindb = User.findByUsername(username);
            if (userindb != null) {
                if (!userindb.pin.equals(pin)) {
                    reset.put("message", "invalid pin");
                    reset.put("code", 300);
                    return ok(reset);
                } else {
                    return findingPin(userindb);
                }
            } else {
                reset.put("message", "user not found");
                reset.put("code", 201);

            }
            return ok(reset);
        }

    public static Result displayYourPassword(User user){
        //User user = User.displayPassword(String password);
        return ok(pinreset.render("viewpassword",user));
    }

    public static Result deleteItem(Long id){
        //ObjectNode delete = Json.newObject();
        System.out.println("reached");
        Items del= Items.findItemById(id);

        if(del !=null){
            Logger.info("##"+id);
            del.delete();
        }

        return travelToDoItems();
    }

    public static Result deleteCategory(Long id){

        Categories delcat = Categories.findCategoryById(id);

        if(delcat !=null){
           delcat.delete();
        }
        return toDoCategories();
    }


    public static Result editCategory(Long id){
        Categories editcat = Categories.findCategoryById(id);
        return  ok(editCategory.render("Edit Cat",id,editcat));
    }



    public static Result editingCategory(){

        DynamicForm editingcategory = Form.form().bindFromRequest();
        String categoryname = editingcategory.get("name");
        String categorydetails = editingcategory.get("description");
        Long id = Long.valueOf(editingcategory.get("categoryid"));

        Categories editcat = Categories.findCategoryById(id);

        if(editcat != null){
            editcat.categoryname = categoryname;
            editcat.categorydetails=categorydetails;
            Logger.info("edited");
            editcat.update();

        }

        return toDoCategories();

    }

    public static Result editingItem(Long id){
        Items edititem = Items.findItemById(id);
        return ok(editingItem.render("edit the item",id,edititem));
    }

    public static Result editingOfItem(){
        DynamicForm edit =Form.form().bindFromRequest();
        String itemname = edit.get("itemname");
        String itemdetails = edit.get("itemdetails");
        Long id = Long.valueOf(edit.get("id"));
        //Long categoryid=Long.valueOf(edit.get("categoryID"));

        Logger.info("if posting itemname: "+itemname);

        Items items = Items.findItemById(id);
        if(items !=null){

            items.itemname = itemname;
            items.itemdetails = itemdetails;
            items.id = id;
           // items.categoryid = categoryid;

            Logger.info("iko sawa");

            items.update();
        }

        return  travelToDoItems();
    }

    public static Result add_item(){
        ObjectNode additem = Json.newObject();

        DynamicForm  addI = Form.form().bindFromRequest();
        String itemname = addI.get("name");
        String itemdetails = addI.get("textarea");
        Long categoryid = Long.valueOf(addI.get("categoryid"));

        Items item = new Items();
        item.itemname =itemname;
        item.itemdetails =itemdetails;
        item.categoryid = categoryid;
        item.save();

        additem.put("message","item added");
        additem.put("message",300);

        return travelToDoItems();

    }

    public static Result add_category(){
        ObjectNode addcategory = Json.newObject();

        DynamicForm addC = Form.form().bindFromRequest();
        String categoryname = addC.get("name");
        String categorydetails = addC.get("description");

        Categories category = new Categories();
        category.categoryname = categoryname;
        category.categorydetails = categorydetails;
        category.save();

        addcategory.put("Message","category added");
        addcategory.put("Message",400);
        return toDoCategories();
    }


}
