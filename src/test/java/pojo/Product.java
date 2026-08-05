package pojo;

public class Product {

	public String title;
	public double price ;
	public String description;
	public String categoery;
	public String image;
	
	public Product(String title , double price , String description , String categoery , String image)
	{
		this.title = title;
		this.categoery = categoery;
		this.price = price;
		this.description = description;
		this.image = image;
		
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle()
	{
		return this.title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoery() {
		return categoery;
	}

	public void setCategoery(String categoery) {
		this.categoery = categoery;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
