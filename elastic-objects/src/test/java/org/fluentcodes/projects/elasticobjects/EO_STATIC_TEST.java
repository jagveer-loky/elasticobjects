package org.fluentcodes.projects.elasticobjects;

/**
 * Created 11.6.2018
 */

public class EO_STATIC_TEST {

    public static final String PATH_TEMPLATE_CONTENT = "templatesContent";

    public static final String VC_INT_VALUE1 = "intValue1";
    public static final String VC_EMPTY = "empty";
    public static final String VC_CONTENT = "content";
    public static final String VC_TEST_ITEM = "testItem";

    public static final String SC_TEST = "test";


    //<call templateKey="StaticValuesLoop.tpl" config:="Model" prefix:="M_" keep="JAVA">
    public static final String M_SIMPLE = "Simple";
//</call> 
    //<call templateKey="StaticValuesLoop.tpl" config:="Field" prefix:="F_" keep="JAVA">

    public static final String F_I = "i";
    public static final String F_S = "s";
//</call> 

    //<call templateKey="StaticValuesLoop.tpl" config:="Host" prefix:="H_" keep="JAVA">

    public static final String H_TEST = "test";
    public static final String H_TEST_FTP = "testFtp";
//</call> 
    //<call templateKey="StaticValuesLoop.tpl" config:="File" prefix:="FILE_" keep="JAVA">

    public static final String FILE_TEST_TEST = "test:test";
    public static final String FILE_RESULT_WITH_MAP_PATH_STRING = "resultWithMapPath.string";

    public static final String FILE_RESULT_STRING = "result.string";
    public static final String FILE_TEST_SOURCE_TXT = "test_source.txt";
//</call>


    //<call templateKey="StaticValuesLoop.tpl" config:="Json" prefix:="J_" keep="JAVA">

    public static final String J_SIMPLE_INSERT_WITH_PATH = "SimpleInsertWithPath";
    public static final String J_CONTENT_EXAMPLE = "ContentExample";
    //</call>
    public static final String T_MODEL_WITH_LOOP_PATH = "modelWithLoopPath.tpl";
    public static final String T_MODEL_WITH_PATH_AND_LOOP_PATH = "modelWithPathAndLoopPath.tpl";
    public static final String T_MODEL_WITH_LOOP_PATH_AND_EMBEDDED_IF = "modelWithLoopPathAndEmbeddedIf.tpl";
    public static final String T_MODEL_WITH_LOOP_PATH_AND_EMBEDDED_IF_KEEP = "modelWithLoopPathAndEmbeddedIfKeep.tpl";

    public static final String T_EMPTY_NO_CALL = "EmptyNoCall.tpl";
    public static final String T_EMPTY_WITH_CALL_AT_START_STOP = "EmptyWithCallAtStartStop.tpl";
    public static final String T_EMPTY_WITH_CALL = "EmptyWithCall.tpl";
    public static final String T_EMPTY_WITH_EMBEDDED_CALL = "EmptyWithEmbeddedCall.tpl";
    public static final String T_ATTRIBUTE_WITH_CALL = "AttributeWithCall.tpl";
    public static final String T_ATTRIBUTE_WITH_EMBEDDED_CALL = "AttributeWithEmbeddedCall.tpl";
    public static final String T_ATTRIBUTE_GLOBAL = "AttributeGlobal.tpl";
    public static final String T_ATTRIBUTE_GLOBAL_PARALLEL = "AttributeGlobalParallel.tpl";
    public static final String T_VALUES_WITH_CALL_AND_PATH = "ValuesWithCallAndPath.tpl";
    public static final String T_VALUES_NO_CALL_NOT_EXISTING_VALUE = "ValuesNoCallNotExistingValue.tpl";
    public static final String T_VALUES_NO_CALL = "ValuesNoCall.tpl";
    public static final String T_VALUES_WITH_CALL_DEEPER_PATH = "ValuesWithCallDeeperPath.tpl";


    //<call templateKey="StaticValuesLoop.tpl" config:="Template" prefix:="T_" keep="JAVA" >

    public static final String T_VALUES_MATH_SIN_EXAMPLE = "ValuesMathSinExample";
    public static final String T_CONTENT_EXAMPLE = "ContentExample";
    public static final String T_VALUES_MISC_SET_EXAMPLE = "ValuesMiscSetExample";
    public static final String T_EMBEDDED_2_WITH_PLACE_HOLDER = "Embedded2WithPlaceHolder";
    public static final String T_SIMPLE_INSERT_WITH_PATH_AND_EMBEDDED_JSON = "SimpleInsertWithPathAndEmbeddedJson";
    public static final String T_EMBEDDED_2_WITH_VALUE_ACTION = "Embedded2WithValueAction";
    public static final String T_MODULE_TEST_TPL = "ModuleTest.tpl";
    public static final String T_CONTENT_EXAMPLE_ELEMENT_1 = "ContentExampleElement1";
    public static final String T_CONTENT_EXAMPLE_ELEMENT_2 = "ContentExampleElement2";
    public static final String T_EMBEDDED_1_WITH_PLACE_HOLDER = "Embedded1WithPlaceHolder";
    public static final String T_STATIC_VALUES_LOOP_TPL = "StaticValuesLoop.tpl";
    public static final String T_CONTENT_EXAMPLE_WITH_STATIC_TEMPLATE = "ContentExampleWithStaticTemplate";
    public static final String T_VALUES_MISC_REPLACE_EXAMPLE = "ValuesMiscReplaceExample";
    public static final String T_CONTENT_EXAMPLE_WITH_DYNAMIC_TEMPLATE = "ContentExampleWithDynamicTemplate";
    public static final String T_VALUES_MISC_REPEAT_EXAMPLE = "ValuesMiscRepeatExample";
    public static final String T_EMBEDDED_0_WITH_PLACE_HOLDER = "Embedded0WithPlaceHolder";
    public static final String T_CONFIG_TEST_LOOP_TPL = "ConfigTestLoop.tpl";
    public static final String T_STATIC_VALUES_EOSTATIC_TPL = "StaticValuesEOStatic.tpl";
    public static final String T_SIMPLE_INSERT_WITH_PATH = "SimpleInsertWithPath";
    public static final String T_SIMPLE_INSERT_WITH_PATH_AND_JSON_STORE = "SimpleInsertWithPathAndJsonStore";
    public static final String T_SIMPLE_INSERT_WITH_PATH_AND_JSON = "SimpleInsertWithPathAndJson";
    public static final String T___TEMPLATE_ = "$[template]";
    public static final String T_CONTENT_OR_EXAMPLE = "ContentOrExample";
    public static final String T_EMBEDDED_2 = "Embedded2";
    public static final String T_EMBEDDED_0 = "Embedded0";
    public static final String T_CONTENT_EXAMPLE_WITH_KEEP = "ContentExampleWithKeep";
    public static final String T_EMBEDDED_1 = "Embedded1";
//</call>

}
