package MiniCompiler;

import java.util.regex.*;

public class LexicalAnalysis {

    public static boolean performLexicalAnalysis(String code) {
        String[] patterns = {
                "\\bint\\b",
                "\\bString\\b",
                "\\bclass\\b",
                "\\bpublic\\b",
                "\\bprivate\\b",
                "[a-zA-Z_][a-zA-Z0-9_]*",
                "\"[^\"]*\"",
                "\\d+",
                "\\+",
                "-",
                "\\*",
                "/",
                ";",
                "\\{",
                "\\}",
                "\\(",
                "\\)",
                "=",
                "\\[",
                "\\]"
        };

        boolean isValid = true;
        int position = 0;

        while (position < code.length()) {
            if (Character.isWhitespace(code.charAt(position))) {
                position++;
                continue;
            }

            boolean matched = false;

            for (String pattern : patterns) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(code.substring(position));

                if (m.lookingAt()) {
                    System.out.println("Found token: " + m.group());
                    position += m.end();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                System.out.println("Invalid token at position " + position + ": " + code.charAt(position));
                isValid = false;
                position++;
            }
        }

        return isValid;
    }
}
