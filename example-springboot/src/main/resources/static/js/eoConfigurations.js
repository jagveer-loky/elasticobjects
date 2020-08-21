var eo = {};

eo.models = {};
eo.setModel = function(modelCache, cacheKey) {
  this.models.selected = this.configurations[modelCache] [cacheKey];
}
var view = {};
 $(document).ready(function() {
    view.nav = $("#rootNav");
    view.body = $("#entry-content");
    $.getJSON('eoConfigurations.json', function (data) {
        eo.configurations = data;
      }
    )
   view.content.view();
   }
  );

eo.getModel = function (modelKey) {
  return eo.models["ModelCache"][modelKey];
}

eo.getField = function (fieldKey) {
  return eo.models["FieldCache"][fieldKey];
}


// View part .....


view.storedActions = {};
view.storedActions.nav = {};
view.storedActions.body = {};

view.storedActions.view = function() {
  view.body.html(this.nav.render());
}

//https://stackoverflow.com/questions/1740218/error-handling-in-getjson-calls

