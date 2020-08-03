<{
   "(List,Double)source": {
     "0": 1,
     "1": 2,
     "2": 3
   },
   "(List)_call":{
     "(SinusValueCall)0": {
       "sourcePath": "/source",
       "targetPath": "/target",
       "filterPath": "*"
     },
     "(TemplateCall)1": {
        "sourcePath": "/source",
        "filterPath": "*"
     }
   }
 }>
 sin($[_value]) = $[/target/_parent]