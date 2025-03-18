package com.cottagesystems.convert;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.type.Type;

import java.util.List;
import java.util.Optional;

final class ConversionUtils {

    private static final Modifier STATIC_MODIFIER = Modifier.staticModifier();

    public static boolean isStaticField(FieldDeclaration field) {
        if (field == null) {
            return false;
        }
        return field.getModifiers().contains(STATIC_MODIFIER);
    }

    // TODO: avoid duplication between the isStatic methods.
    public static boolean isStaticMethod(MethodDeclaration method) {
        if (method == null) {
            return false;
        }
        return method.getModifiers().contains(STATIC_MODIFIER);
    }

    public static Type getFieldType(FieldDeclaration field) {
        List<VariableDeclarator> variables = field.getVariables();
        if (variables.size() != 1) {
            throw new IllegalArgumentException("Expected exactly one variable for " + field.toString());
        }
        return variables.get(0).getType();
    }

    public static <T> T extractParseResult(ParseResult<T> result) {
        if (result.isSuccessful()) {
            return result.getResult().get();
        }
        throw new ParseProblemException(result.getProblems());
    }

    /**
     * Gets the first line represented by the node.
     *
     * @param node Node to examine.
     * @return The line number or a negative number if unavailable.
     */
    public static int getBeginLine(Node node) {
        Optional<Range> range = node.getRange();
        if (!range.isPresent()) {
            return -1;
        }
        return range.get().begin.line;
    }

    /**
     * Gets the last line represented by the node.
     *
     * @param node Node to examine.
     * @return The line number or a negative number if unavailable.
     */
    public static int getEndLine(Node node) {
        Optional<Range> range = node.getRange();
        if (!range.isPresent()) {
            return -1;
        }
        return range.get().end.line;
    }

}