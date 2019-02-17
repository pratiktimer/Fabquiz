package google.com.fabquiz.database;
/**
 * Created by pratik on 28-01-2018.
 */
public class SubjectsC {
    String categoryid,subid,subname,subimage;
    public SubjectsC() {
    }
    public SubjectsC(String categoryid, String subid, String subname, String subimage) {
        this.categoryid = categoryid;
        this.subid = subid;
        this.subname = subname;
        this.subimage = subimage;
    }
    public String getCategoryid() {
        return categoryid;
    }
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
    public String getSubid() {
        return subid;
    }
    public void setSubid(String subid) {
        this.subid = subid;
    }
    public String getSubname() {
        return subname;
    }
    public void setSubname(String subname) {
        this.subname = subname;
    }
    public String getSubimage() {
        return subimage;
    }
    public void setSubimage(String subimage) {
        this.subimage = subimage;
    }
}
