package TeleMed.TeleMed.Models;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicines") 
public class Medicine {
	@Id
	private String id;
private List<String> storeId;
	private String name;
    private int quantity;
    private double price; // Price of the medicine
    private Date expiryDate; // Expiry date of the medicine
    private String description; // A short description of the medicine
    private String imageUrl; // URL for the medicine image

    // Getters and Setters
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

	public List<String> getStoreId() {
		return storeId;
	}

	public void setStoreId(List<String> storeId) {
		this.storeId = storeId;
	}

}
