<call execute="FileAction.read(Config.java)" />{insert
execute="TemplateAction.execute(BEANCreate.tpl)" mapPath="/tmp/template" keep="TARGET" />{insert
execute="FileAction.write(Config.java)" />