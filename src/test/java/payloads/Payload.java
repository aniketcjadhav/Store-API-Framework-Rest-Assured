package payloads;

import java.util.Random;

import com.github.javafaker.Faker;

import pojo.Product;

public class Payload {
	
	private static final Faker faker = new Faker();
	private static final String categories[] = {"electronics", "furniture", "clothing", "books", "beauty"};
	private static final Random random = new Random();
	
	
	//Product
	public static Product productPayload()
	{
		String title = faker.commerce().productName();
		double price = Double.parseDouble(faker.commerce().price());
		String description=faker.lorem().sentence();
		String imageUrl="https://i.pravatar.cc/100";
		String categoery = categories[random.nextInt(categories.length)];
		
		return new Product(title,price, description, categoery, imageUrl);
	}
	
	//Cart
	
	//User
	
	//Login
}
