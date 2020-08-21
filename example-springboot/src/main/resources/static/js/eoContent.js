view.content = {};
view.content.view = function() {
  view.nav.html(this.nav.render());
  this.viewBody();
}
view.content.viewBody = function() {
  view.body.html(this.body.render());
}

//https://stackoverflow.com/questions/1740218/error-handling-in-getjson-calls

view.content.history = [];
view.content.select = function(contentKey) {
  this.selected = contentKey;
  this.history.unshift(contentKey);
  //alert("contentKey");
  this.viewBody();
}

view.content.nav = {};

view.content.nav.render = function() {
  if (this.rendered === undefined) {
    var result = "<table>";
    var nav = ["Adapter","Model","Action","Cache","Templates"];
    for (var i in nav) {
      var contentKey = nav[i];
      result += '<tr><td><a href="#" onclick="view.content.select(\'' + contentKey + '\')">' + contentKey + '</a></td></tr>';
    }
    result += "</table>";
    this.rendered = result;
  }
  return this.rendered;
}


view.content.body = {};
view.content.body.buffer = {};
view.content.body.buffer.get = function (contentKey) {
  if (this[contentKey] === undefined) {
    $.get('content/' + view.content.selected + '.html', function (data, status) {
        //alert("Data: " + data + "\nStatus: " + status);
      view.content.body.buffer[contentKey] = data;
       view.content.viewBody();
      }
    )
  }
  else {
    return this[contentKey]
  }
}
view.content.body.render = function () {
  if (view.content.selected == undefined) {
    view.content.selected = "Home";
  }
  var selected = view.content.selected;

  var bodyContent = this.buffer.get(selected);
  if (bodyContent === undefined) {
    return "";
  }

  var result = "<form style=\"font-size:11px\">";
  result += "<select id=\"selectConfigHistory\" style=\"font-size:11px\" onchange=\"view.content.select(this.options[this.selectedIndex].value)\">";
  for (var i in view.content.history)   {
    result += '<option>' + view.content.history[i] + '</option>';
  }
  result += "</select></form>";
  result += '<h4>' + selected + '</h4>';
  result += "<div>";
  result += bodyContent;
  result += "</div>";
  return result;
}




