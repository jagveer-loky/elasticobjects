package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    public static Object execute(final EO eo, final Call call) {
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
        if (!call.hasSourcePath()) {
            call.setSourcePath(PathElement.SAME);
        }
        String sourcePathString = new Parser(TemplateMarker.SQUARE, call.getSourcePath()).parse(eo);
        Path sourcePath = new Path(eo.getPathAsString(), sourcePathString);
        EO sourceParent = sourcePath.moveToParent(eo);
        boolean isFilter = sourcePath.isEmpty()? false : sourcePath.isFilter();
        if (!((CallImpl)call).evalStartCondition(sourceParent)) {
            return "";
        }
        List<String> loopPaths = sourcePath.isEmpty()? Arrays.asList(new String[]{"."}):sourceParent.keys(sourcePath.getParentKey());

        // get targetParent
        String targetPath;
        if (call.hasTargetPath()) {
            targetPath = call.getTargetPath();
        }
        else {
            if (isFilter) {
                call.setTargetPath(sourceParent.getPathAsString());
                targetPath = new Path(eo.getPathAsString(), call.getTargetPath()).toString();
            }
            else {
                targetPath = sourcePath.toString();
            }
        }
        StringBuilder templateResult = new StringBuilder();
        templateResult.append(call.getPrepend());

        if (PathElement.SAME.equals(call.getTargetPath())) {
            call.setTargetPath(TARGET_AS_STRING);
        }
        for (String entry : loopPaths) {
            EO sourceEntry = sourceParent.getEo(entry);

            if (isFilter) {
                call.setTargetPath(targetPath + Path.DELIMITER + entry);
            }
            else {
                call.setTargetPath(targetPath);
            }
            try {
                templateResult.append(call.execute(sourceEntry));
            }
            catch (EoException e) {
                StringBuilder message = new StringBuilder("In '" + call.getClass().getSimpleName() + "' ");
                message.append(": " + e.getMessage());
                if (call.isTargetAsString()) {
                    templateResult.append(message);
                }
                throw new EoException( message.toString());
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
        for (String key : eo.getCallKeys()) {
            long startTime = System.currentTimeMillis();
            EO callEo = eo.getCallEo(key);
            if (callEo == null) {
                continue;
            }
            Call call =  (Call)callEo.get();
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
        if (stringResult.length()>0) {
            eo.set(stringResult.toString(), PathElement.TEMPLATE);
        }
        return stringResult.toString();
    }
}
