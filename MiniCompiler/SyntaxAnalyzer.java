package MiniCompiler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxAnalyzer {

    private static final String DATA_TYPE_REGEX = "\\b(int|float|double|char|boolean|String)\\b";
    private static final String VARIABLE_NAME_REGEX = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String ASSIGNMENT_REGEX = "(\\s*=\\s*.+)?";

    public static boolean performSyntaxAnalysis(List<String> codeLines) {
        for (String line : codeLines) {
            line = line.replaceAll("//.*", "").trim();
            if (line.isEmpty()) continue;
            if (!isValidSyntax(line)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidSyntax(String line) {
        String regex = DATA_TYPE_REGEX + "\\s+" + VARIABLE_NAME_REGEX + ASSIGNMENT_REGEX + "\\s*;?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }
}
