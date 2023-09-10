import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car
{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay)
    {
        this.carId=carId;
        this.brand=brand;
        this.model=model;
        this.basePricePerDay=basePricePerDay;
        this.isAvailable=true;
    }

    public String getCarId()
    {
        return carId;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getModel()
    {
        return model;
    }
    public double calculatePrice(int rentalDays)
    {
        return basePricePerDay*rentalDays;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }
    public void rent()
    {
        isAvailable=false;
    }
    public void returnCar()
    {
        isAvailable=true;
    }
}
class Customer
{
    private String customerId;
    private String name;

    public Customer(String customerId,String name)
    {
        this.customerId=customerId;
        this.name=name;
    }

    public String getCustomerId()
    {
        return customerId;
    }
    public String getName()
    {
        return name;
    }
}
class Rental
{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer, int days)
    {
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public Car getCar()
    {
        return car;
    }

    public Customer getCustomer()
    {
        return customer;
    }
    public int getDays()
    {
        return days;
    }
}
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car Not available for Rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned");
        } else {
            System.out.println("car was not rented");
        }
    }

    public void menu() {
        Scanner sc1 = new Scanner(System.in);

        while (true) {
            System.out.println("***** Chaudhari Car Rental Services *****");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter tour choice : ");

            int choice = sc1.nextInt();
            sc1.nextLine();

            if (choice == 1) {
                System.out.println("== Rent a Car ==");
                System.out.println("Enter your Name ");
                String customerName = sc1.nextLine();

                System.out.println("Available Cars : ");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }
                System.out.println("Enter Car ID [rent] : ");
                String carId = sc1.nextLine();

                System.out.println("Enter number of days [rent] : ");
                int rentalDays = sc1.nextInt();
                sc1.nextLine();

                Customer newCustomer = new Customer("C01ST" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("** Rental Information");
                    System.out.println("Customer ID : " + newCustomer.getCustomerId());
                    System.out.println("Customer Name : " + newCustomer.getName());
                    System.out.println("Car : " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days : " + rentalDays);
                    System.out.println("Total Price : " + totalPrice);

                    System.out.println("confirm rental (Y/N) : ");
                    String confirm = sc1.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("car rented successfully");
                    } else {
                        System.out.println("Rental cancelled");
                    }
                } else {
                    System.out.println("car not available for rent");
                }
            }
                else if (choice == 2)
                {
                    System.out.println("** Return a Car **");
                    System.out.println("Enter Car ID to return : ");
                    String carId = sc1.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getCar() == carToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }
                        if (customer != null) {
                            returnCar(carToReturn);
                            System.out.println("Car returned Succesfully by " + customer.getName());
                        } else
                        {
                            System.out.println("Car was not returned");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid Car Id");
                    }
                } else if (choice == 3)
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid Choice");
                }
            }
            System.out.println("Thank You !! ");

        }
    }

public class RentalSystem {
    public static void main(String[] args) {
        CarRentalSystem rs1=new CarRentalSystem();
        Car c1=new Car("101","Suzuki","Swift",45.3);
        Car c2=new Car("102","Skoda","Olavia",52.1);
        Car c3=new Car("103","Honda","City",47.12);
        Car c4=new Car("104","Mahindra","Thar",60.2);
        rs1.addCar(c1);
        rs1.addCar(c2);
        rs1.addCar(c3);
        rs1.menu();
    }
}