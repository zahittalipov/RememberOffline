package zahittalipov.com.remember.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class SignUpError {

    @SerializedName("errors")
    private String[] errors;


    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
