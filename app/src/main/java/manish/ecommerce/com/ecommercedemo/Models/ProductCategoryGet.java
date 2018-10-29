package manish.ecommerce.com.ecommercedemo.Models;

public class ProductCategoryGet {


    String categoryId;
    String categoryName;
    String branchId ;
    String  status;
    String image;
    String serial_no;

    public ProductCategoryGet(){

    }



    public ProductCategoryGet(String category_Id, String category_name, String branch_id){
        this.categoryId = category_Id;
        this.categoryName = category_name;
        this.branchId = branch_id;
    }

    public ProductCategoryGet(String category_Id, String category_name, String image, String status){
        this.categoryId = category_Id;
        this.categoryName = category_name;
        this.image = image;
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

}
