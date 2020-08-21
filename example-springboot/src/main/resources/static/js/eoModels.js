view.models = {};

view.models.view = function() {
  view.nav.html(this.nav.render());
  this.viewBody();
}
view.models.viewBody = function() {
  view.body.html(this.body.render());
}

//https://stackoverflow.com/questions/1740218/error-handling-in-getjson-calls

view.models.history = [];
view.models.select = function(configurationKey) {
  this.selected = configurationKey;
  this.history.unshift(configurationKey);
  //alert(configurationKey);cv
  this.viewBody();
}

view.models.nav = {};
view.models.nav.renderSelect = function (model) {
  var selectedCache = eo.configurations[model];
  var sortedKeys = Object.keys(selectedCache).sort();
  var result = "<tr><th style=\"font-size:12px\">";
  result +=  model + '</th><td>';
  result += "<form style=\"font-size:11px\">";
  result += "<select id=\"select" + model + "\" style=\"font-size:11px\" ";
  result += "onchange=\"view.models.select(\'" + model + ".' + this.options[this.selectedIndex].value + '\')\">";
  result += "<option />";
  for (var i in sortedKeys) {
    var key = sortedKeys[i];
    result += '<option>' + key + '</option>';
  }
  result += "</select></form></td></tr>";
  return result;
}

view.models.nav.render = function() {
  if (this.rendered === undefined) {
    var result = "<table>";
    var nav = ["ModelCache","FieldCache","HibernateModelCache"];
    for (var i in nav) {
      result += this.renderSelect(nav[i]);
    }
    result += "</table>";
    this.rendered = result;
  }
  return this.rendered;
}


view.models.body = {};
view.models.body.renderFields = function() {
  var keys = view.models.selected.split(".");
  var selectedConfig = eo.configurations[keys[0]][keys[1]];
  var result = '<h4>' + view.models.selected + '</h4>';
  result += "<table>";
  var sortedKeys = Object.keys(selectedConfig).sort();
  for (var i in sortedKeys) {
    var fieldKey = sortedKeys[i];
    var field = selectedConfig[fieldKey];
    result += '<tr  style="font-size:11px">';
    result += '<th style="font-size:11px" valign="top">' + fieldKey + '</th>';
    result += '<td style="font-size:11px">';
    if (fieldKey == "superKey") {
      result += '<a href="#" onclick="view.models.select(\'ModelCache.' + field + '\')">' + selectedConfig[fieldKey] + '</a>';
    }
    else if (field == keys[1]) {
      result += '<b>' + field + '</b>';
    }
    else if (fieldKey == "naturalId") {
      result += '<b>' + field + '</b>';
    }
    else if (fieldKey.match("Key$") == "Key") {
      var cache = fieldKey.replace("Key", "Cache");
      cache = cache.substr(0, 1).toUpperCase() + cache.substr(1, cache.length);
      result += '<a href="#" onclick="view.models.select(\'' + cache + '.' + field + '\')">' + selectedConfig[fieldKey] + '</a>';
    }
    else if (fieldKey == "fieldKeys") {
      result += "<ul>";
      for (var i in field) {
        var fieldRow = field[i];
        result += "<li><a href=\"#\" onclick=\"view.models.select('FieldCache." + fieldRow + "');\">" + fieldRow + "</a></li>";
      }
      result += "</ul>";
    }
    else if (fieldKey == "dbParams") {
      result += "<table>";
      for (var i in field) {
        result += "<tr><td>" + i + "</td><td> " + field[i] + "</td></tr>";
      }
      result += "</table>";
    }
    else if (fieldKey.match("Keys$") == "Keys") {
      var cache = fieldKey.replace("Keys", "Cache");

      cache = cache.substr(0, 1).toUpperCase() + cache.substr(1, cache.length);
      var entries = field.split(",");
      for (var entry in entries) {
        var myModel = entries[entry];
        result += '<a href="#" onclick="view.models.select(\'' + cache + '.' + myModel + '\')">' + myModel + '</a>';
        result += ",";
      }
    }
    else if (fieldKey == "eoParams") {
      result += "<table>";
      for (var i in field) {
        result += "<tr><td>" + i + "</td><td> " + field[i] + "</td></tr>";
      }
      result += "</table>";
    }
    else {
      result += '' + field + '';
    }
    result += '</td></tr>';
  }
    result += "</table>";
    return result;

  /*if (eo.actions[model] !== undefined) {
    result += '<tr><th style="font-size:11px">Actions</th><td style="font-size:11px">';
    for (var i in eo.actions.Caches[model]) {
      var action = eo.actions.Caches[model][i];
      result += '<a href="#" onclick="view.models.select(\'' + action + '\',\'' + key + '\')">' + action + '</a>';
    }
    result += '</td></tr>';
  }*/
}

view.models.body.render = function () {
  if (view.models.selected == undefined) {
    var result = "Select a configuration!";
    return result;
  }
  var result = "<form style=\"font-size:11px\">";
  result += "<select id=\"selectConfigHistory\" style=\"font-size:11px\" onchange=\"view.models.select(this.options[this.selectedIndex].value)\">";
  for (var i in view.models.history) {
    result += '<option>' + view.models.history[i] + '</option>';
  }
  result += "</select></form>";
  result += this.renderFields();
  return result;
}

