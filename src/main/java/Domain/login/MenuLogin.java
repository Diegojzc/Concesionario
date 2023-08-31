package Domain.login;

import Domain.Dto.LoginDao;
import Domain.Menu;
import Domain.User;
import Domain.Validations.UserValidations;
import Domain.mail.Mail;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuLogin {
    private final Scanner keyboard;
    int intentos = 3;
    private final UserValidations validations;

    public MenuLogin() {
        keyboard = new Scanner(System.in);
        validations = new UserValidations();
    }


    public void login() {

        switch (menu()) {
            case "1":
                register();

                break;
            case "2":
                start();
                break;
            default:
                System.out.println("Opción incorrecta");
                login();


        }


    }

    public void register() {
        String registerpassword;
        String correo;
        String registerName;

        System.out.print("Name: ");
        registerName = keyboard.next();


        do {

            System.out.print("Contraseña: ");
            registerpassword = keyboard.next();

            if (validations.passwordValidations(registerpassword))
                System.out.println("La contraseña tiene que tener al menos una letra en mayuscula, un numero y algun simbolo como un punto o una coma");


        } while (validations.passwordValidations(registerpassword));

        do {
            System.out.print("Correo electronico: ");
            correo = keyboard.next();
            if (validations.emailValidation(correo))
                System.out.println("El email es incorrecto, utilizar formato como el ejemplo:  [ejemplo@gmail.com] ");

        } while (validations.emailValidation(correo));


        User login = new User(registerName, registerpassword, correo);


        try {
            LoginDao loginDao = new LoginDao();
            loginDao.add(login);
            System.out.println("Registrado correctamente ");
            System.out.println();
            keyboard.nextLine();
            login();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() {

        String nameLogin;
        String passwordLogin;

        if (intentos == 0) {
            System.out.println("Usuario y contraseña incorrectos intenlo mas tarde");
            Menu menu = new Menu();
            menu.exit();


        } else {
            try {

                do {
                    System.out.print("Name: ");
                    nameLogin = keyboard.nextLine();
                    if (nameLogin.isEmpty()) {
                        System.out.println("Introduzca el nombre con caracteres");
                    }

                } while (nameLogin.isEmpty());
                do {
                    System.out.print("Contraseña: ");
                    passwordLogin = keyboard.nextLine();
                    if (passwordLogin.isEmpty()) {
                        System.out.println("La contraseña tiene que tener al menos una letra en mayuscula, un numero y algun simbolo como un punto o una coma");
                    }
                } while (passwordLogin.isEmpty());

                LoginDao loginDao = new LoginDao();

                User loggear = loginDao.loggear(nameLogin, passwordLogin);

                User user = new User(nameLogin, passwordLogin);


                if (!user.equals(loggear)) {
                    System.out.println("No hay usuarios en el user " + nameLogin + " y con el password " + passwordLogin);
                    passwordRecover();
                    intentos--;
                    start();


                } else {

                    System.out.println("Bienvenido " + user.getUser());
                    System.out.println();
                    Menu menu = new Menu();
                    menu.menu();


                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public String menu() {
        System.out.println("Aplicacion Concesionario");
        System.out.println("1- Sign up :");
        System.out.println("2- Login:");
        System.out.print("Elegir opcion: ");
        return keyboard.nextLine();


    }

    public void passwordRecover() {
        String password = null;
        String name = null;
        System.out.println("1- Recuperar usuario y contraseña?");
        String respuesta = keyboard.nextLine();
        if (respuesta.equals("si")) {
            System.out.println("Introduzca correo electronico: ");
            String correo = keyboard.nextLine();

            try {
                LoginDao loginDao = new LoginDao();
                String correoDB = loginDao.mailQuery(correo);
                if (!correo.equals(correoDB)) {
                    System.out.println("Correo no valido ");
                } else {
                    try {
                        LoginDao login = new LoginDao();
                        password = login.passwordQuery(correo);
                        name = login.nameQuery(correo);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    Mail mail = new Mail();
                    mail.createEmail(password, correo, name);
                    System.out.println("La contraseña a sido enviada al correo");
                }

            } catch (SQLException | MessagingException e) {
                e.printStackTrace();
            }
        }


    }

}

