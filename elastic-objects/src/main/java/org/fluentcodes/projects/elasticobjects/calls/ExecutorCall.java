package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.PathElement.MATCHER;
import static org.fluentcodes.projects.elasticobjects.PathElement.MATCHER_ALL;
import static org.fluentcodes.projects.elasticobjects.calls.Call.F_DURATION;
import static org.fluentcodes.projects.elasticobjects.calls.Call.TARGET_AS_STRING;

/**
 * Class for call executions.
 *
 * @author Werner Diwischek
 * @version 0.01
 * @date 2.5.2018
 */
public class ExecutorCall {
    private ExecutorCall() {

    }

    public static Object execute(final IEOScalar eo, final Call call) {
        if (eo == null) {
            return "eo is null!";
        }
        if (call == null) {
            return "call eo is null!";
        }
        if (call.getDuration() != null) {
            eo.warn("Already executed within " + call.getDuration() + " ms. ");
            return "Already executed within " + call.getDuration() + " ms. ";
        }
        List<IEOScalar> sourceList = createSourceList(call, eo);
        StringBuilder templateResult = new StringBuilder();
        templateResult.append(call.getPrepend());

        if (PathElement.SAME.equals(call.getTargetPath())) {
            call.setTargetPath(TARGET_AS_STRING);
        }
        for (IEOScalar source : sourceList) {
            try {
                templateResult.append(call.execute(source));
            } catch (EoException e) {
                StringBuilder message = new StringBuilder("In '" + call.getClass().getSimpleName() + "' ");
                message.append(": " + e.getMessage());
                if (call.isTargetAsString()) {
                    templateResult.append(message);
                }
                throw new EoException(message.toString());
            }
        }
        templateResult.append(call.getPostpend());
        eo.info("Successfully executed within " + call.getDuration() + " ms.");
        return templateResult.toString();
    }

    public static String executeEo(final EO eo) {
        if (eo == null) {
            throw new EoInternalException("Null adapter!");
        }
        Set<String> keys = eo.getCallKeys();
        if (keys.isEmpty()) {
            eo.info("No calls");
            return "";
        }
        StringBuilder stringResult = new StringBuilder();
        int counter = 0;
        for (String key : keys) {
            long startTime = System.currentTimeMillis();
            EO callEo = eo.getCallEo(key);
            if (callEo == null) {
                continue;
            }
            Call call = (Call) callEo.get();
            try {
                stringResult.append(execute(eo, call));
                Long duration = System.currentTimeMillis() - startTime;
                call.setDuration(duration);
                callEo.set(duration, F_DURATION);
            } catch (Exception e) {
                e.printStackTrace();
                if (!call.hasLogLevel() || !call.getLogLevel().equals(LogLevel.NONE)) {
                    eo.error("Problem executing call '" + call.getClass().getSimpleName() + "' for " + counter + ". call(" + call.getLogLevel() + "): " + e.getMessage());
                }
            }
            counter++;
        }
        if (stringResult.length() > 0) {
            eo.set(stringResult.toString(), PathElement.TEMPLATE);
        }
        return stringResult.toString();
    }

    static List<IEOScalar> createSourceList(Call call, IEOScalar eo) {
        if (!call.hasSourcePath()) {
            return Arrays.asList(eo);
        }
        String source = new Parser(TemplateMarker.SQUARE, call.getSourcePath()).parse(eo);
        if (!(source.contains(MATCHER) || source.contains(MATCHER_ALL))) {
            IEOScalar sourceEo = eo.getEo(source);
            if (!((CallImpl) call).evalStartCondition(sourceEo)) {
                return new ArrayList<>();
            }
            return Arrays.asList(eo.getEo(call.getSourcePath()));
        }

        Path sourcePath = new Path(eo.getPathAsString(), source);
        String matcher = sourcePath.getLastEntry();
        Path sourceParentPath = sourcePath.getParentPath();

        EoChild sourceParentEo = (EoChild) ((EoChild) eo).getEo(sourceParentPath);
        List<String> keys = sourceParentEo.filterPaths(matcher);
        if (keys.isEmpty()) {
            return new ArrayList<>();
        }
        List<IEOScalar> sourceList = new ArrayList<>();
        for (String key : keys) {
            IEOScalar sourceEntry = sourceParentEo.getEo(new PathElement(key));
            sourceList.add(sourceEntry);
        }
        return sourceList;
    }
}
