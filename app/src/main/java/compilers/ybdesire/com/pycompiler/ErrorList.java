package compilers.ybdesire.com.pycompiler;

import java.util.HashMap;
import java.util.Map;

public class ErrorList {

    public static String getErrorSuggestionText(String searchErrorText) {
        String flag = null;
        Map<String, String> errorsList = new HashMap<String, String>();
        errorsList.put("StringLiteral", "Add String Literal at proper place in the statement");

        for (String s : errorsList.keySet()) {
            if (s.equalsIgnoreCase(searchErrorText)) {
                flag= errorsList.get(s);
                break;
            }
        }
        return flag;
}

}
