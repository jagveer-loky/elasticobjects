<call execute="FileAction.read(Config.java)" />{insert
execute="TemplateAction.execute($[eoParams\shapeType]Create.tpl)" />{insert
execute="FileAction.write(Config.java)" />