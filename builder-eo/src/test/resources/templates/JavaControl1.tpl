{insert
   execute="XlsxAction.read(models.xlsx:Model)"
    mapPath="$[naturalId]"
    path="builder/data"
    filter="0 like $[module] && 1 like $[subModule] && 2 match $[filterModels]" />