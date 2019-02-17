package google.com.fabquiz.database;
/**
 * Created by pratik on 23-12-2017.
 */
public class CategoryC {
    String categoryid,category_name,image,noofsub;
    public CategoryC() {
    }
    public CategoryC(String categoryid, String category_name, String image, String noofsub) {
        this.categoryid = categoryid;
        this.category_name = category_name;
        this.image = image;
        this.noofsub = noofsub;
    }
    public String getCategoryid() {
        return categoryid;
    }
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getNoofsub() {
        return noofsub;
    }
    public void setNoofsub(String noofsub) {
        this.noofsub = noofsub;
    }
}
