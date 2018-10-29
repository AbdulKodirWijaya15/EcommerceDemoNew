package manish.ecommerce.com.ecommercedemo.Models;

public class SubCategoryGetSet {

    String id;
    String sub_name;

    public SubCategoryGetSet(String sub_id, String sub_cat_name){
        this.id = sub_id;
        this.sub_name = sub_cat_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
}
