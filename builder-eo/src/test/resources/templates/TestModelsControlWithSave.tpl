{insert
   templateKey="TestDataControl.tpl"/>{insert
      path="/builder/data/*"}{insert
         path="*"}{insert
            path="*"}{insert
              call="TemplateAction.execute(TestModels.tpl)" mapPath="/tmp/templateResult" />{insert
              call="FileAction.write(TestModelsControlWithSaveTpl.string)" mapPath="/tmp/templateResult"  />
</call></call></call>