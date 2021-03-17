package com.nkvl.app.classes.expressions;

public final class TripleExpression {
    private final String text;
    private final byte[] allAnswers;
    private final byte rightAnswer;

    public TripleExpression() {
        text = genExpression();
        rightAnswer = solveExpression();
        allAnswers = genAnswers();
    }

    public String getText() { return text; }
    public byte[] getAnswers() { return allAnswers; }
    public byte getRightAnswer() { return rightAnswer; }

    private String genExpression() { return null; }
    private byte solveExpression() { return 0; }
    private byte[] genAnswers() { return new byte[] {0}; }
}
