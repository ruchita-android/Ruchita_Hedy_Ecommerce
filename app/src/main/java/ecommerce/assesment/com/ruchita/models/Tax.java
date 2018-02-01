
package ecommerce.assesment.com.ruchita.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tax implements Serializable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


}
