package Domain.Validations;

import Domain.Car;
import Domain.Menu;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CarValidations {
    public Menu menu;

    public CarValidations() {
        menu = new Menu();
    }

    public String validationBrand(Scanner keyboard) {
        String marca = null;
        do {
            try {
                System.out.print("Marca:");
                marca = keyboard.nextLine();

            } catch (Exception e) {
                System.out.println("el nombre es incorrecto");
            }
        } while (Objects.requireNonNull(marca).isEmpty());

        return  menu.changeUpperCase(marca);
    }

    public String validationModel(Scanner keyboard) {

        String model = null;

        do {
            try {
                System.out.print("Model:");
                model = keyboard.nextLine();

            } catch (Exception e) {
                System.out.println("el modelo es incorrecto");
            }
        } while (Objects.requireNonNull(model).isEmpty());
        return menu.changeUpperCase(model);
    }


    public int validationKm(Scanner keyboars) {
        String kilometros = null;
        String regex = "\\d{1,6}";
        int nuevokm = 0;
        boolean matches = false;
        do {

            try {
                System.out.print("kilometros:");
                kilometros = keyboars.nextLine();

                matches = Pattern.matches(regex, kilometros);
                nuevokm = Integer.parseInt(kilometros);
                if (!matches) {
                    System.out.println("Formato es incorrecto, introducir dato nuevamente ");
                }

            } catch (Exception e) {
                System.out.println("Dato incorrecto, introducir solo numeros");
            }


        } while (Objects.requireNonNull(kilometros).isEmpty() || !matches);


        return nuevokm;
    }

    public Double validationPrice(Scanner keyboard) {
        String precio = null;
        double newPrice = 0;
        String regex = "\\d{1,6}";
        boolean matches = false;

        do {
            try {
                System.out.print("Precio:");
                precio = keyboard.nextLine();

                matches = Pattern.matches(regex, precio);
                newPrice = Double.parseDouble(precio);

                if (!matches) {
                    System.out.println("Formato es incorrecto, introducir dato nuevamente");
                }
            } catch (Exception e) {
                System.out.println("Dato incorrecto, introducir solo numeros");
            }

        } while (Objects.requireNonNull(precio).isEmpty() || !matches);


        return newPrice;
    }

    public boolean questionRent(Scanner keyboard) {

        boolean verdadero = false;
        String alquiler = null;

        do {
            try {
                System.out.print("Alquilar? si o no?: ");
                alquiler = keyboard.nextLine();
                if (alquiler.isEmpty()) {
                    System.out.println("Dato incorrecto, vuelva a intentarlo");
                }
            } catch (Exception e) {

                System.out.println("Dato incorrecto, vuelva a intentarlo");
            }

        } while (Objects.requireNonNull(alquiler).isEmpty());

        if (alquiler.contains("si")) {
            return Boolean.parseBoolean(alquiler);

        } else {
            return false;
        }
    }

    public Car confirmValidation(String marca, String modelo, int kilometros, Double precio, boolean alquiler, Scanner keyboard) {
        System.out.println();
        System.out.println("Son correctos los datos del vehiculo?: ");
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("kilometros:" + kilometros);
        System.out.println("Precio: " + precio);
        System.out.println("Alquiler: " + alquiler);

        System.out.println("1= Guardar          2-Modificar");
        String respuesta = keyboard.nextLine();


      Car car = null;

        switch (respuesta) {
            case "1":
               car = new Car(marca, modelo, kilometros, precio, alquiler);
                break;

            case "2":
                String brand = validationBrand(keyboard);
                String model=validationModel(keyboard);
                int km=validationKm(keyboard);
                double price=validationPrice(keyboard);
                boolean rent=questionRent(keyboard);
                return new Car(brand,model,km,price,rent);

            default:
                System.out.println("Respuesta no valida");
                break;
        }
        return car;
    }
}