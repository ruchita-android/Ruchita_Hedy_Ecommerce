
package ecommerce.assesment.com.ruchita.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product_  implements Serializable{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("view_count")
    @Expose
    private Long viewCount;
    @SerializedName("order_count")
    @Expose
    private Long orderCount;
    @SerializedName("shares")
    @Expose
    private Long shares;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Long getShares() {
        return shares;
    }

    public void setShares(Long shares) {
        this.shares = shares;
    }

}
