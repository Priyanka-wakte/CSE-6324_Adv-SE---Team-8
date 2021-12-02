package compilers.ybdesire.com.pycompiler;

import android.support.v7.widget.AppCompatEditText;

public class SyntaxErrorHandlerClass {
    public static String[] syntaxErrorHandler(String name )
    {
        String[] lines=name.split("\n");
        System.out.println("Length is "+lines.length);
        for(String s : lines)
            System.out.println(s);
        String line_number=lines[0];
        String sugg[]=new String[2];
        String lineNumber=line_number.substring(line_number.indexOf(", line ")+6);

        if(lines[3].contains("literal"))
        {
            sugg[1]="String Literal is not positioned properly or is not closed at line number "+lineNumber;
        }
        else if(lines[3].contains("unexpected EOF"))
        {
            sugg[1]="Parenthesis is not properly closed in the program at line number "+lineNumber ;
        }
        else if(lines[3].contains("invalid syntax"))
        {
            sugg[1]="Invalid syntax in program at line number "+lineNumber ;
        }
        return sugg;
        
    }

}
