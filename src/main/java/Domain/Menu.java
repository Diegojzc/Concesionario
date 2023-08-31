package Domain;

import Domain.Dto.CarDao;
import Domain.Validations.CarValidations;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    Scanner keyboard;
    String choice;
    boolean exit;

    public void menu() {
        keyboard = new Scanner(System.in);
        exit = false;


        do {
            System.out.println("APP CONCESIONARIO");

            System.out.println("1- Ver todos los coches");
            System.out.println("2- Ver modelos por marca");
            System.out.println("3- Ver coches nuevos");
            System.out.println("4- Precios");
            System.out.println("5- Alquilar");
            System.out.println("6- Salir");
            System.out.print("Elegir opcion: ");
            choice = keyboard.nextLine();


            switch (choice) {
                case "1":
                    seeCars();
                    waitt();
                    break;
                case "2":
                    seeBrandsModels();
                    waitt();
                    break;
                case "3":
                    seeNewCars();
                    waitt();
                    break;
                case "4":
                    price();
                    break;
                case "5":
                    rent();
                    waitt();
                    break;
                case "6":
                    exit();
                    break;
                default:
                    System.out.println("Opcion incorrecta");
                    waitt();
            }

        } while (!exit);


    }

    public void seeCars() {

        try {
            CarDao cocheDto = new CarDao();
            List<Car> lista = cocheDto.seeCars();
            if (lista.isEmpty()) {
                System.out.println("El concecionario no tiene coches disponibles");

            } else {
                System.out.println("COCHES DISPONIBLES");
                listaCars(lista);

            }
            menuAddExitSeeCarssold();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void seeBrandsModels() {
        System.out.print("Introduzca la marca: ");
        String marca1 = "";
        String brand = null;

        marca1 = keyboard.nextLine();

        if (marca1.isEmpty()) {
            System.out.println("Dato no valido (Es importante introducir nombre de alguna marca disponible)");

            waitt();
        } else {
            brand = changeUpperCase(marca1);
        }

        try {
            CarDao cocheDto = new CarDao();
            List<Car> coches = cocheDto.modelsByBrand(brand);
            if (coches.isEmpty()) {
                System.out.println("La marca " + marca1.toUpperCase() + " no se encuentra o ya no estan disponibles en la tienda");
            } else
                System.out.println("Los modelos con la marca " + marca1.toUpperCase() + " son: ");
            coches.forEach(n -> System.out.println(n.getSeeAll() + " " + n.getModels()));


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void seeNewCars() {
        try {
            Car coche = new Car(0);
            CarDao cocheDto = new CarDao();
            List<Car> listaCochesNuevos = cocheDto.newCars(coche);
            System.out.println("Los coches nuevos son: ");
            listaCochesNuevos.forEach(n -> System.out.println(n.getSeeAll() + " " + n.getModels()));
            if (listaCochesNuevos.isEmpty()) {
                System.err.println("No hay coches nuevos disponibles");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void price() {
        try {
            Car coche = new Car();
            CarDao cocheDto = new CarDao();
            List<Car> precio = cocheDto.seeCars();
            System.out.println("Los precios son: ");

            precio.forEach(n -> System.out.println(n.getSeeAll() + " " + n.getModels() + " " + n.getPrice() + "€"));
            buy();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void buy() {
        String model = null;
        String modelo = null;
        System.out.println();

        System.out.println("Pulse opcion: ");
        System.out.println("1- Comprar" + "         " + "2- salir");
        String respuesta = keyboard.nextLine();

        if (respuesta.isEmpty() || !(respuesta.equals("1") || respuesta.equals("2"))) {
            System.out.println("Dato no valido");
            waitt();
        }
       else if (respuesta.equals("1")) {

            System.out.print("Introduzca el modelo del coche a comprar: ");
            modelo = keyboard.nextLine();

        } else {
            menu();
        }

        if (Objects.requireNonNull(modelo).isEmpty()) {
            System.out.println("dato no valido");

        } else {
            model = changeUpperCase(modelo);
        }


        if (checkModels().contains(model)) {
            try {

                CarDao cocheDto = new CarDao();
                System.out.println(model);
                cocheDto.buy(model);
                System.out.println("Coche vendido");

                List<String> nuevo = null;
                PrintWriter soldList = null;

                try {
                    soldList = new PrintWriter(new FileWriter("soldList.txt", true));
                    soldList.println(model);


                    soldList.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }


        } else {

            System.out.println("El modelo introducido no existe, mirar los coches disponibles");
        }
    }


    public void rent() {

        try {
            Car coche = new Car(true);
            CarDao cocheDto = new CarDao();
            List<Car> listaAlquiler = cocheDto.isRent(coche);
            System.out.println("Los coche en alquiler son: ");
            listaAlquiler.forEach(n -> System.out.println(n.getSeeAll() + " " + n.getModels()));
            carRent();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void exit() {
        System.err.println("Programa finalizado");
        exit = true;
    }


    public Car dates() {


        CarValidations validations = new CarValidations();
        System.out.println("INTRODUCIR DATOS DEL COCHE");
        String marca = validations.validationBrand(keyboard);
        String  marcaUppercase = changeUpperCase(marca);
        String model = validations.validationModel(keyboard);
        String modeloUppercase = changeUpperCase(model);
        int kilometros = validations.validationKm(keyboard);
        double precio = validations.validationPrice(keyboard);
        boolean questionRent = validations.questionRent(keyboard);

        return validations.confirmValidation(changeUpperCase(marca), changeUpperCase(model), kilometros, precio, questionRent, keyboard);



    }


    public void waitt() {
        System.out.println("Pulse enter para ir a menu");
        keyboard.nextLine();
        menu();
    }

    public void addCar() {
        try {
            CarDao carDto = new CarDao();

            carDto.add(dates());
            System.out.println("Coche almacenado");
            waitt();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void menuAddExitSeeCarssold() {
        System.out.println("Pulse opcion: ");
        System.out.println("1- Añadir" + "           " + "2- salir" + "       " + "3- Ver coche vendidos");
        String respuesta = keyboard.nextLine();

        switch (respuesta) {
            case "1":
                addCar();
            case "2":
                menu();
                break;
            case "3":
                addSold();
                break;
            default:
                System.err.println("Opcion incorrecta");
                waitt();
        }
    }


    public List<Double> prices(String model) {

        List<Double> price = null;
        try {
            CarDao carDao = new CarDao();
            List<Car> car = carDao.seeCars();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return price;
    }


    public void listaCars(List<Car> lista) {
        int i;
        int j;
        for (i = 0; i < lista.size(); i++) {
            for (j = 0; j < lista.size(); j++) {
                i++;
                System.out.println((i) + "- " + lista.get(j));

            }
        }
    }

    public List<String> checkModels() {
        List<String> models = null;

        try {
            CarDao carDao = new CarDao();
            List<Car> car = carDao.seeCars();
            models = car.stream()
                    .map(Car::getModels)
                    .collect(Collectors.toList());

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return models;
    }

    public List<String> checkBrand() {
        List<String> brand = null;
        try {
            CarDao carDao = new CarDao();
            List<Car> brand1 = carDao.seeCars();
            brand = brand1.stream().map(Car::getSeeAll)
                    .collect(Collectors.toList());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


        return brand;
    }


    public String changeUpperCase(String name) {

        return name.toUpperCase().charAt(0) + name.substring(1, name.length()).toLowerCase();
    }


    public void carRent() {

        System.out.println("Elegir opcion: ");
        System.out.println("1-Alquilar      2- Salir");
        String respuesta = keyboard.nextLine();
        if (respuesta.isEmpty()) {
            System.err.println("Opcion incorrecta");
            waitt();
        }
        if (respuesta.equals("1")) {
            System.out.print("Introduzca el modelo del coche que desea alquilar: ");
            String model = keyboard.nextLine();
            try {
                CarDao carDao = new CarDao();
                carDao.rentCar(model);
                System.err.println("coche alquilado");
                listRent(model);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (respuesta.equals("2")) {
            menu();
        }
    }

    public void addSold() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("soldList.txt"));
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.isEmpty()) {
                    System.err.println("No hay coches vendidos");
                }
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listRent(String model) {
        try {
            PrintWriter rentCar = new PrintWriter(new FileWriter("rentList.txt", true));
            rentCar.println(model);
            rentCar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


