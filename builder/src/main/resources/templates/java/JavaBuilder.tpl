$[(XlsxReadCall)/ModelConfig/eo->naturalId.
    configKey="eo.xlsx:ModelConfig" /]
$[(XlsxReadCall)/FieldConfig/eo->naturalId.
    configKey="eo.xlsx:FieldConfig" /]
$[(GenerateJavaCall).
    sourcePath="ModelConfig"
    module="eo->module|>*."
    moduleScope="eo->moduleScope|>*."
    naturalId="eo->naturalId|>*."
    buildPath="eo->buildPath|>*."
    fileEnding="javax" /]