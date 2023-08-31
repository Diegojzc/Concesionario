package Domain.Validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidations {

    public boolean passwordValidations(String password){

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_+=])(?=.{8,})[A-Za-z\\d!@#$%^&*()-_+=]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher =pattern.matcher(password);

        return !matcher.matches();

    }
    public boolean emailValidation(String email){
        String regex ="^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
}
