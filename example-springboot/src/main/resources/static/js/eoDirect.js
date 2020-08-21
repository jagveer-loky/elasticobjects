view.direct = {};

view.direct.view = function() {
  view.nav.html(this.nav.render());
  this.viewBody();
}
view.direct.viewBody = function() {
  view.body.html(this.body.render());
  this.select();
}

//https://stackoverflow.com/questions/1740218/error-handling-in-getjson-calls

view.direct.buffer ={};
view.direct.select = function(contentKey, content) {
  if (contentKey == undefined) {
    contentKey = "empty.json";
  }
  this.selected = contentKey;
  if (this.buffer[contentKey] === undefined) {
    if (content === undefined) {
      $.get('adapter/' + contentKey, function (data, status) {
          //alert("Data: " + data + "\nStatus: " + status);
          view.direct.buffer[contentKey] = JSON.stringify(data) + " ";
          view.direct.viewInput();
        }
      )
    }
    else {
      view.direct.buffer[contentKey] = content;
      this.viewInput();
    }
  }
  else {
    if (content !== undefined) {
      view.direct.buffer[contentKey] = " " + content + " ";
    }
    this.viewInput();
  }
}

view.direct.viewInput = function() {
  $("#eoDirectHeader").html(this.selected);
  $("#eoInput").text(this.buffer[this.selected]);
}

view.direct.viewOutput = function() {
  $("#eoOutput").html();
}

view.direct.nav = {};

view.direct.nav.render = function() {
  if (this.rendered === undefined) {
    var result = "<table>";
    var nav = ["empty.json","calc.json"];
    for (var i in nav) {
      var contentKey = nav[i];
      result += '<tr><td><a href="#" onclick="view.direct.select(\'' + contentKey + '\')">' + contentKey + '</a></td></tr>';
    }
    result += "</table>";
    this.rendered = result;
  }
  return this.rendered;
}

view.direct.send = function () {
  this.buffer[this.selected] = $("#eoInput").text;
  $.post('eo', this.buffer[this.selected])
    .done( function(data,status) {
      alert (data, status);
      $("#eoOutput").text = JSON.stringify(data);
    }
    )
      .fail (function() {
        alert("error");
      }
      )
    .always (function() {
        alert ("always");
      }
    )

}

view.direct.body = {};
view.direct.body.render = function () {
  var result = '<h4 id="eoDirectHeader">' + view.direct.selected + '</h4>';
  result += "<form>";
  result += 'Request: <textarea id="eoInput"></textarea>';
  result += '<input type="button" onclick="view.direct.send();" value="send"/><br/>';

  result += 'Response: <textarea id="eoOutput"></textarea>';
  result += "</form>";
  return result;
}

