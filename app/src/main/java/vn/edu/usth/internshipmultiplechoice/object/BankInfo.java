package vn.edu.usth.internshipmultiplechoice.object;

import com.google.gson.annotations.SerializedName;

public class BankInfo {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankInfo(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
