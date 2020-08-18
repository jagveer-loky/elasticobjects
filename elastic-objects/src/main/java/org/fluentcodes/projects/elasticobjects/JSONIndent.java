package org.fluentcodes.projects.elasticobjects;

/**
 * Created by Werner on 24.02.2017.
 */
public class JSONIndent {
    public static final String SPACER = "\t";
    private final String indent;
    private final String nextIndent;
    private final String newLine;
    private final int increase;

    public JSONIndent(int increase) {
        if (increase == 0) {
            this.indent = "";
            this.nextIndent = "";
            this.newLine = "";
            this.increase = 0;
        } else {
            StringBuilder indentBuilder = new StringBuilder(SPACER);
            for (int i = 1; i < increase; i++) {
                indentBuilder.append(SPACER);
            }
            this.indent = indentBuilder.toString();
            this.nextIndent = this.indent + SPACER;
            this.newLine = "\n";
            this.increase = increase;
        }
    }

    public JSONIndent(JSONIndent indent) {
        if (indent.getIncrease() == 0) {
            this.indent = "";
            this.nextIndent = "";
            this.newLine = "";
            this.increase = 0;
        } else {
            this.indent = indent.getNextIndent();
            this.nextIndent = this.indent + SPACER;
            this.newLine = "\n";
            this.increase = indent.getIncrease();
        }
    }

    public int getIncrease() {
        return this.increase;
    }

    public String getIndent() {
        return indent;
    }

    public String getNewLine() {
        return newLine;
    }

    public String getNextIndent() {
        return nextIndent;
    }
}
