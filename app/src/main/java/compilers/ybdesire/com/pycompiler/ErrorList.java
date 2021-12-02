package compilers.ybdesire.com.pycompiler;

import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ErrorList {

    public static String getErrorSuggestionText(String errorMessage) {
       Map<String, String> Error = new HashMap<String, String>();

        String suggestion_message[];


        if(errorMessage.contains("SyntaxError"))
        {
            suggestion_message=SyntaxErrorHandlerClass.syntaxErrorHandler(errorMessage);
            Error.put("Error",suggestion_message[1]);

        }
        else if (errorMessage.contains("NameError"))
        {
            suggestion_message=NameErrorHandlerClass.nameErrorHandlerMethod(errorMessage);
            Error.put("Error","Declare name "+suggestion_message[1]+" before using it at line number "+suggestion_message[0]);
        }
        else if (errorMessage.contains("IndexError"))
        {
            suggestion_message=IndexErrorHandler.indexErrorHandlerMethod(errorMessage);
            Error.put("Error","Index you are trying to access is out of range or element is not present at the specified index at line number "+suggestion_message[0]+ " check index before you specify");
        }
        else if(errorMessage.contains("KeyError"))
        {
            suggestion_message=KeyErrorHandler.keyErrorHandlerMethod(errorMessage);
            Error.put("Error","Key you are trying to access is not present in the list index at line number "+suggestion_message[0]+ ", check key before you access it");
        }

        return Error.get("Error");

}

}
