package manish.ecommerce.com.ecommercedemo.Models;

public class ProductItemGetSet {

    String id,branch,unitName,status,CatId,ProductName,price,tax,unitid,image,Branch,uid,proid,productId, discription;


  /*  public ProductItemGetSet(String id,String branch,String unitName,String status,String CatId,String ProductName,String price,String tax,String unitid,String image,String Branch,String uid,String proid,String productId){
        this.id = id;
        this.branch = id;
        this.unitName = id;
        this.status = id;
        this.CatId = id;
        this.ProductName = id;
        this.price = id;
        this.tax = id;
        this.image = image;
        this.Branch = Branch;
        this.uid = uid;
        this.proid = proid;
        this.productId = productId;
    }*/


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        CatId = catId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
