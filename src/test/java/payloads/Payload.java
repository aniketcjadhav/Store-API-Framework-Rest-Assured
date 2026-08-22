package payloads;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import pojo.Address;
import pojo.Cart;
import pojo.CartProduct;
import pojo.Geolocation;
import pojo.Name;
import pojo.Product;
import pojo.User;

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
	
	//User
	
	public static User userPayload()
	{
		String email = faker.internet().emailAddress();
	    String username =faker.name().username();
		String password = faker.internet().password();
		
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		
		Name name = new Name(firstName, lastName);
		
		 String city = faker.address().cityName();
		 String street= faker.address().streetAddress();
		 int number = Integer.parseInt( faker.address().buildingNumber());
		 String zipcode = faker.address().zipCode();
		 
		 String lat = faker.address().latitude();
		 String lng = faker.address().longitude();
		 Geolocation geolocation = new Geolocation(lat, lng) ;
		
		 Address address = new Address(city, street, number, zipcode, geolocation);
		 String phone = faker.phoneNumber().phoneNumber();
		
		return new User(email,username,password,name,address,phone);
	}
	
	//Cart
	public static Cart cartPayload(int userId)
	{
		/*
		 *private int userId;
	private String date;
	List<CartProduct> products; 
		 * */
		
		
		List<CartProduct> products = new ArrayList<>();
		
		int productSize = random.nextInt(4);
		
		for(int i =1;i<=productSize;i++)
		{	
			int productId = random.nextInt(100);
			int quantity =random.nextInt(10)+1;
			products.add(new CartProduct(productId ,quantity ));
		}
		
		LocalDate localdate = LocalDate.now();
		DateTimeFormatter formate = DateTimeFormatter.ofPattern("yyyyMMdd");
		String date = localdate.format(formate);
		
		return new Cart(userId ,date ,products);
		
	}
	
	
	//Login
}
