package compilers.ybdesire.com.pycompiler;

public class NameErrorHandlerClass {

    public static String[] nameErrorHandlerMethod(String name)
    {

        String[] lines=name.split("\n");
        for(String s : lines)
            System.out.println(s);
        String line_number=lines[1];
        String sugg[]=new String[2];

        String lineNumber=line_number.substring(line_number.indexOf(", line ")+6, line_number.indexOf(", in"));

        String error_msg=lines[3];
        sugg[0]=lineNumber;
        sugg[1]=error_msg.substring(error_msg.indexOf("NameError: name ")+15, error_msg.indexOf(" is not"));

         return sugg;

    }

}
