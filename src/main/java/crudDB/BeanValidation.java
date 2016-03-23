package crudDB;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class BeanValidation {

    private BeanValidation() {
    }

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> boolean isCorrectData (T data){
        Set<ConstraintViolation<T>> violations = validator.validate(data);
        return violations.isEmpty();
    }

    public static <T> String getViolationsText(T data){
        Set<ConstraintViolation<T>> violations = validator.validate(data);
        StringBuilder sb = new StringBuilder();
        if(!violations.isEmpty()){
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
