
view.actions = {};


view.actions.view = function() {
  view.nav.html(this.nav.render());
  this.viewBody();
}
view.actions.viewBody = function() {
  view.body.html(this.body.render());
}
//https://stackoverflow.com/questions/1740218/error-handling-in-getjson-calls

/**
 * Create Html part of actions with cache values.
 * @returns {string}
 */


/**
 * Start the body actions part
 * @type {{}}
 */

view.actions.history = [];
view.actions.select = function(actionKey) {
  this.selected = actionKey;
  this.history.unshift(actionKey);
  this.viewBody();
}
view.actions.nav = {};
view.actions.nav.render = function () {
  if (this.rendered != undefined) {
    return this.rendered;
  }
  view.actions.init();
  var result = "<table>";
  for (var actionKey in view.actions.configCache) {
    result += '<tr><td><a href="#" onclick="view.actions.select(\'' + actionKey + '\')">' + actionKey + '</a></td></tr>';
  }
  result += "</table>";
  this.rendered = result;
  return result;
}

view.actions.configCache = {};
view.actions.init = function () {
  var models = eo.configurations["ModelCache"];
  var sortedKeys = Object.keys(models).sort();
  for (var i in sortedKeys) {
    var actionKey = sortedKeys[i];
    if (!(actionKey.match("Action$"))) { // not an action
      continue;
    }
    var actionModel = models[actionKey];
    var eoParams = actionModel["eoParams"];
    if (eoParams === undefined) {
      continue;
    }
    var modelCacheKey = actionModel["eoParams"]["modelCacheKey"];
    if (modelCacheKey === undefined) {  // No caceh defined
      continue;
    }

    for (var modelKey in eo.configurations[modelCacheKey]) {
      if (this.configCache[actionKey] == null) { // init configCache
        this.configCache[actionKey] = [];
      }
      this.configCache[actionKey].push(modelKey);
    }
  }
}


view.actions.body = {};

view.actions.body.send = function(actions) {
  $.post('eo', actions, function(result) {
      $("#" + this.responseId).html(result);
    }
  )
}


view.actions.body.params = ["path","mapPath","modelKeys"];
view.actions.body.actionField = {"actionKey":"",
  "actionCacheKey":"",
  "actionMethod":"",
  "cacheKey":"",
  "attributes":{},
  "requestId":"eoInput",
  "responseId":"eoOutput"
}

view.actions.body.render = function () {
  var selected = view.actions.selected;
  if (selected === undefined) {
    var result = "Select an action to view.";
    return result;
  }
  var action = eo.configurations["ModelCache"][selected];
  if (action === undefined) {
    return "action is not found for " + selected + "!!!";
  }
  var eoParams = action["eoParams"];
  if (eoParams === undefined) {
    return "eoParams is not found for " + selected + "!!!";
  }
  var actionMethods = eoParams["methods"];
  if (actionMethods === undefined) {
    actionMethods = ["execute"];
  }
  var modelCacheKey = eoParams["modelCacheKey"];

  var result = "<form><table>";
  result += "<tr><td>" + selected + "</td>";
  result +=  "<td>";
  result += "<select id=\"selectMethod\" style=\"font-size:11px\" onchange=\"this.transfer.setValue('actionMethod', this.options[this.selectedIndex].value);\">";
  for (var i in actionMethods) {
    result += "<option>" + actionMethods[i] + "</option>";
  }
  this.transfer.actionMethod = actionMethods[0];
  result += "</select></td>";

  // now set select for cache params.
  var cacheList = view.actions.configCache[selected];
  result +=  "<td>";
  result += "<select id=\"selectCache\" style=\"font-size:11px\" onchange=\"view.actions.body.transfer.setValue('cacheKey', this.options[this.selectedIndex].value)\">";
  for (var i in cacheList) {
    result += "<option>" + cacheList[i] + "</option>";
  }
  this.transfer.cacheKey = cacheList[0];
  result += "</select></td>";
  result += "</tr>";
  result += "</table>";

  // now setting global action params like 'path'
  result += "<table>";
  for (var i in this.params) {
    result += "<tr>";
    result += "<td>" + this.params[i] + "</td>";
    result += "<td><input type=\"text\" onchange=\"view.actions.body.transfer.setValue('" + this.params[i]+ "',this.value)\"/></td>";
    result += "</tr>";
  }
  result += "</table>";

  var attributeList = eoParams["attributeList"];
  if (attributeList !== undefined) {
    result += "<table>";
    for (var i in attributeList) {
      result += "<tr>";
      result += "<td>" + attributeList[i] + "</td>";
      result += "<td><input type=\"text\"  onchange=\"view.body.actions.transfer.setAttribute('" + attributeList[i] + "',this.value)\"/></td>";
      result += "</tr>";
    }
    result += "</table>";
  }
  result += 'Request: <textarea id="eoInput">{".actions":[]}</textarea>';
  result += '<input type="button" onclick="" value="send"/><br/>';

  result += 'Response: <textarea id="eoInput"></textarea>';
  result += "</form>";
  return result;
}

view.actions.body.transfer = {};
view.actions.body.transfer.setActionKey = function () {
  this.actionKey = this.actionCacheKey + "." + this.actionMethod + "(" + this.cacheKey + ")";
}

view.actions.body.transfer.setValue = function(key, value) {
  this[key] = value;
  this.setActionKey();
  this.updateRequest();
}

view.actions.body.transfer.setAttribute = function(key, value) {
  this.attributes[key] = value;
  this.updateRequest();
}

view.actions.body.transfer.updateRequest = function() {
  var result = "{\n";
  result += '  "actionKey":"' + this.actionKey + '",' + "\n";
  for (var i in actionParams) {
    if (selectedActions[ actionParams[i]] === undefined) {
      continue;
    }
    result += '  "' + actionParams[i] + '":"' + selectedActions[ actionParams[i]] + '",';
    result += "\n";
  }
  result += '  "attributes":{'  + "\n";
  var counter = 0;
  for ( var i in this.attributes) {
    result += '    "' + i + '":"' + this.attributes[i] + '"';
    if (counter<this.attributes.length) {
      result += ',';
    }
    counter++;
    result += "\n";
  }
  result += "  }" + "\n";
  result += "}" + "\n";
  $("#" + this.requestId).html(result);
}
