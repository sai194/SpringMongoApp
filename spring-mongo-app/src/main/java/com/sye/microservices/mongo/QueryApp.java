package com.sye.microservices.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.sye.microservices.mongo.domain.Continent;
import com.sye.microservices.mongo.domain.Country;
import com.sye.microservices.mongo.domain.Employee;
import com.sye.microservices.mongo.repository.CountryRepository;
import com.sye.microservices.mongo.repository.EmployeeRepository;

@Component
public class QueryApp implements CommandLineRunner {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	MongoOperations mongoOperation;

	@Override
	public void run(String... args) throws Exception {

		testEmployeeData();
		
		testCountriesData();

	}

	private void testCountriesData() {
		 Country[] countriesArr = new Country[] {
		            new Country("Australia", 2966200, 21884000L, new Continent(6, "Australia")),
		            new Country("Gabon", 103347, 1475000L, new Continent(1, "Africa")),
		            new Country("Gambia", 4361, 1705000L, new Continent(1, "Africa")),
		            new Country("Georgia", 26900, 4382100L, new Continent(3, "Europe")),
		            new Country("Germany", 137847, 82046000L, new Continent(3, "Europe")),
		            new Country("Ghana", 92098, 23837000L, new Continent(1, "Africa")),
		            new Country("Greece", 50949, 11257285L, new Continent(3, "Europe")),
		            new Country("Japan", 145925, 126659683L, new Continent(2, "Asia")),
		            new Country("New Zealand", 104454, 4320300L, new Continent(6, "Australia")),
		            new Country("Serbia", 34116, 7120666L, new Continent(3, "Europe")),
		            new Country("USA", 3794101, 316637000L, new Continent(4, "North America")),
		            new Country("Vietnam", 128565, 90388000L, new Continent(2, "Asia")),
		            new Country("Iceland", 39770, 321857L, new Continent(3, "Europe"))
		        };
		 
		 countryRepository.deleteAll();
		 countryRepository.saveAll(Arrays.asList(countriesArr));
		 
		//------------------------------------------------- equality
	        
	        Continent asia = mongoOperation.findOne(new Query(Criteria.where("continent.name").is("Asia")), Country.class).getContinent();
	        
	        System.out.println("asia "+asia);
	        
	        Query europeanQuery = new Query (Criteria.where("continent.name").is("Europe"));
	        List<Country> europeanCountries = mongoOperation.find(europeanQuery, Country.class);
	        europeanCountries.forEach(System.out::println);
	        
 //------------------------------------------------- not equal to
	        
	        List<Continent> notAsia = mongoOperation.find(new Query(Criteria.where("name").ne("Asia")), Continent.class);
	        notAsia.forEach(System.out::println);
	        //------------------------------------------------- less than
	        
	        List<Country> smallCountries = mongoOperation.find(new Query(Criteria.where("area").lt(30000)), Country.class);
	        smallCountries.forEach(System.out::println);
	        //------------------------------------------------- between
	        
	        Criteria between = Criteria.where("population").gt(5000000).lt(30000000);
	        List<Country> popBetween = mongoOperation.find(new Query(between), Country.class);
	        popBetween.forEach(System.out::println);
	        //------------------------------------------------- in list
	        
	        List<Country> ghanaAndGambia = mongoOperation.find(new Query(Criteria.where("name").in("Ghana", "Gambia")), Country.class);
	        List<Country> notGhanaAndGambia = mongoOperation.find(new Query(Criteria.where("name").nin("Ghana", "Gambia")), Country.class);
	        
	        //------------------------------------------------- regular expression

	        List<Country> regex = mongoOperation.find(new Query(Criteria.where("name").regex("G[ae].*")), Country.class);
	        
	        //------------------------------------------------- not
	        
	        List<Country> notRegex = mongoOperation.find(new Query(Criteria.where("name").not().regex("G[ae].*")), Country.class);
	        
	        //------------------------------------------------- subdocument
	        
	        List<Country> asianCountries = mongoOperation.find(new Query(Criteria.where("continent.name").is("Asia")), Country.class);
	        
	        //------------------------------------------------- and
	        
	        Criteria smallAreaAndBigPop = Criteria.where("area").lt(500000).and("population").gt(30000000);
	        List<Country> densePop = mongoOperation.find(new Query(smallAreaAndBigPop), Country.class);
	        
	        //------------------------------------------------- or
	        
	        Criteria smallArea = Criteria.where("area").lt(50000);
	        Criteria smallPop = Criteria.where("population").lt(2000000);
	        Criteria smallAreaOrPop = new Criteria().orOperator(smallArea, smallPop);
	        List<Country> smallAreaOrSmallPop = mongoOperation.find(new Query(smallAreaOrPop), Country.class);
	        
	        //------------------------------------------------- and / or
	        
	        Criteria countries = Criteria.where("name").regex("G[ae].*");
	        Criteria andOr = new Criteria().andOperator(countries, smallAreaOrPop);
	        List<Country> countryList = mongoOperation.find(new Query(andOr), Country.class);
	        System.out.println("countryList - " + new Query(andOr).toString());
	        countryList.forEach(System.out::println);

	       
		
	}

	private void testEmployeeData() {
		employeeRepository.deleteAll();

		final Employee first = new Employee("emp1@xyz.com", "emp1", "mgr1@xyz.com", 100000d, 21);
		final Employee second = new Employee("emp2@xyz.com", "emp2", "mgr2@xyz.com", 200000d, 22);
		final Employee third = new Employee("emp3@xyz.com", "emp3", "mgr3@xyz.com", 300000d, 23);
		final Employee fourth = new Employee("emp4@xyz.com", "emp4", "mgr4@xyz.com", 400000d, 24);

		employeeRepository.save(first);
		employeeRepository.save(second);
		employeeRepository.save(third);
		employeeRepository.save(fourth);

		/*
		 * BasicQuery example
		 */
		BasicQuery query1 = new BasicQuery("{ age : { $lt : 40 }, fullName : 'emp2' }");
		Employee test1 = mongoOperation.findOne(query1, Employee.class);

		System.out.println("query1 - " + query1.toString());
		System.out.println("userTest1 - " + test1);

		/*
		 * find one example with criteria and
		 */
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("fullName").is("emp3").and("age").is(23));
		Employee test2 = mongoOperation.findOne(query2, Employee.class);
		System.out.println("query2 - " + query2.toString());
		System.out.println("userTest2 - " + test2);

		/*
		 * find example with criteria and $inc
		 */
		List<Integer> listOfAge = new ArrayList<Integer>();
		listOfAge.add(21);
		listOfAge.add(22);
		listOfAge.add(23);
		Query query3 = new Query();
		query3.addCriteria(Criteria.where("age").in(listOfAge));

		List<Employee> test3 = mongoOperation.find(query3, Employee.class);
		System.out.println("query3 - " + query3.toString());

		for (Employee emp : test3) {
			System.out.println("Test3 - " + emp);
		}

		/*
		 * find example with criteria and repeat an attribute
		 */
		Query query4 = new Query();
		query4.addCriteria(Criteria.where("age").exists(true).andOperator(Criteria.where("age").gt(10),
				Criteria.where("age").lt(40)));

		List<Employee> test4 = mongoOperation.find(query4, Employee.class);
		System.out.println("query4 - " + query4.toString());

		for (Employee emp : test4) {
			System.out.println("Test4 - " + emp);
		}

		/*
		 * find , paginate and sorting example
		 */
		Query query5 = new Query();
		query5.addCriteria(Criteria.where("age").gte(20));
		query5.with(new Sort(Sort.Direction.DESC, "age"));
		final Pageable pageableRequest = PageRequest.of(0, 2);
		query5.with(pageableRequest);

		List<Employee> test5 = mongoOperation.find(query5, Employee.class);
		System.out.println("query5 - " + query5.toString());

		for (Employee emp : test5) {
			System.out.println("userTest5 - " + emp);
		}

		/*
		 * find and $regex example
		 */
		Query query6 = new Query();
		query6.addCriteria(Criteria.where("name").regex("e.*p", "i"));

		List<Employee> test6 = mongoOperation.find(query6, Employee.class);
		System.out.println("query6 - " + query6.toString());

		for (Employee emp : test6) {
			System.out.println("userTest6 - " + emp);
		}

	}
}
