{insert
   execute="XlsxAction.read(models.xlsx:Model)" mapPath="$[naturalId]"" path="builder/data" filter="0 like $[module] && 1 like $[subModule] && 2 match $[filterModels]" />{insert
   execute="XlsxAction.read(models.xlsx:Model)" path="models" mapPath="$[naturalId]"/>{insert
   execute="XlsxAction.read(models.xlsx:Field)" path="fields"  mapPath="$[naturalId]"/>{insert
      loopPath="/builder/data/*"}$[modelKey] -- $[eoParams/shapeType]<call execute="TemplateAction.execute(Create.tpl)" />
</call>