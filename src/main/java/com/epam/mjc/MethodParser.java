package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String accessModifier;
        String returnType;
        String methodName;

        //split signature to not-arguments and arguments parts
        String[] splitBeforeArguments = signatureString.split("\\(");
        String[] notArguments = splitBeforeArguments[0].split(" ");

        String argumentsString = splitBeforeArguments[1].substring(0, splitBeforeArguments[1].length() - 1);
        String[] argumentsPairs = argumentsString.split(", ");

        //get access modified, return type, method name
        methodName = notArguments[notArguments.length - 1];
        returnType = notArguments[notArguments.length - 2];
        if (notArguments.length == 3) {
            accessModifier = notArguments[0];
        } else {
            accessModifier = null;
        }
        //get arguments
        List<MethodSignature.Argument> argumentsList = new ArrayList<>();
        if (argumentsPairs.length > 1) {
            for (String pair : argumentsPairs) {
                StringTokenizer stringTokenizer = new StringTokenizer(pair, " ");
                argumentsList.add(new MethodSignature.Argument(
                        stringTokenizer.nextToken(), stringTokenizer.nextToken())
                );
            }
        }
        MethodSignature methodSignature = new MethodSignature(methodName, argumentsList);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);
        return methodSignature;
    }
}
