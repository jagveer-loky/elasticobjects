package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 26.08.2020.
 */
public class TheGreetingCall extends SimpleValueFromEoCall {
    private String greeting;
    public TheGreetingCall() {
        super();
    }
    public TheGreetingCall(final String greeting) {
        super();
        this.greeting = greeting;
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        if (eo.get() == null) {
            throw new EoException("Value " + eo.getPathAsString() + " is null ");
        }
        if (greeting == null) {
            this.greeting = "Hi";
        }

        String name = "Stranger";
        if ((eo.get() instanceof String)) {
            name = (String)eo.get();
        }
        String value = this.greeting + " " + name + "!";
        eo.set(value, getTargetPath());
        return (value);
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
