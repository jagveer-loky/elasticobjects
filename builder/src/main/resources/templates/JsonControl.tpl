{insert
   execute="XlsxAction.read($[\tmp\sheet])"
   path="builder/data"
   filter="0 like $[module] && 1 like $[subModule] && 2 match $[filterModels]" />
{insert
   loopPath="builder/data/">0 - $[_parentKey]{insert
      loopPath="*"}1 - $[_parentKey]{insert
          loopPath="*"}<call execute="JsonAction.write($[\tmp\sheet])"/></call></call></call>