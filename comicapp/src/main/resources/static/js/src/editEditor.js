var canvas = new fabric.Canvas('myCanvas');

//var json_url  = "https://s3.us-east-2.amazonaws.com/comic-bucket/series" + seriesId1 + "comic" + comicId1 + "page"+ pageId1 + ".json";
//var jsongetter=

$.getJSON("https://s3.us-east-2.amazonaws.com/comic-bucket/series" + seriesId1 + "comic" + comicId1 + "page"+ pageId1 + ".json", function(data) {
    canvas.loadFromJSON(data, window.setTimeout(canvas.renderAll(), 1000));
});
//
//

//canvas.loadFromJSON();

//for the initial canvas setup
var redoOn = false;
var actionStack = [];
var holder;
var clipboard = null;

canvas.renderTop();
canvas.renderAll();

canvas.defaultCursor = 'circle';

canvas.on('object:added',function(){
    if(!redoOn){
        actionStack = [];
    }
    redoOn = false;
});
var color = "black";
var brushWidth = 2;

//check that message exists from backend


// ajaxGet();
//
// function ajaxGet(){
//     $.ajax({
//         type:"GET",
//         dataType: "text",
//         url : "/api/file/edit1.json",
//         success: function (data) {
//             console.log(data);
//
//         },
//         error: function(err){
//             console.log(err);
//         }
//     });
// }

// $.ajax({
//     url: "/download",
//     type: 'GET',
//     success: function(response) {
//         console.log("success")
//         console.log(response);
//         alert(response);
//     },
//     error: function(err){
//         console.log("wrong")
//         console.log(err);
//     }
//
// });

console.log("hello");


var fileInput = document.getElementById('img-inp');
fileInput.addEventListener('change', function(e){
    console.log("file input is real!");
    console.log(e);
    console.log(e.target.files[0].name);
    var fileReader = new FileReader();
    fileReader.onload = function(o) {
        console.log("yo");
        var imgObject = new Image();
        imgObject.src = o.target.result;
        imgObject.onload = function() {
            var img = new fabric.Image(imgObject);
            img.set({
                angle: 0,
                padding: 15,
                cornersize: 15,
                height: 200,
                width: 200
            });
            canvas.centerObject(img);
            canvas.add(img);
            canvas.renderAll();
        }
    }
    fileReader.readAsDataURL(e.target.files[0]);



});


// var bucket = document.getElementById("bkt");
// bucket.addEventListener('change',function (ev) {
//     // if(canvas.getActiveObject() === null){
//     //     canvas.setColor(ev.target.value);
//     // }
//     console.log(ev.target.value);
// });





function pencil() {
    if (!canvas.isDrawingMode) {
        canvas.isDrawingMode = true;
    }
    else {
        canvas.isDrawingMode = false;
    }
    canvas.defaultCursor = "/images/a.jpg";
    canvas.freeDrawingBrush.width = brushSize();
    canvas.freeDrawingBrush.color = color;
}



function addText() {
    var input = prompt("Enter your text: ");
    var text_obj = new fabric.Text(
        input,{
            fontFamily: 'Comic Sans',
            fontSize : 16,
            left: 100,
            top : 100
        }
    );

    // text_obj.on('selected', function(e){
    //     var x = prompt("change text: ");
    //     text_obj.setText(x);
    // });


    canvas.add(text_obj);

}




function eraser() {

}

function bucket() {
    var bucket = document.getElementById("col-pk").value;
    console.log(bucket == null);
    var selection = canvas.getActiveObject();
    if(selection == null){
        canvas.backgroundColor = bucket;
    }else{
        selection.set({fill:bucket});
        console.log("slection color has been changed to " + bucket);
    }
    canvas.renderAll();


}



function select() {

}

function circle() {
    var circle = new fabric.Circle({
        radius: 20, fill: color, left: 100, top: 100
    });
    canvas.add(circle);
    canvas.renderAll();
}

function rectangle() {
    var rect = new fabric.Rect({
        width: 10, height: 20,
        left: 100, top: 100,
        fill: color,
        angle: 0
    });
    canvas.add(rect); // add Object
    canvas.renderAll();
}

function triangle() {
    var triangle = new fabric.Triangle({
        width: 20, height: 30, fill: color, left: 50, top: 50
    });
    canvas.add(triangle);
}

function setWidth(newWidth) {
    width = newWidth;
}

function setColor(newColor) {
    color = newColor;
}

function exportEdit() {
    holder = JSON.stringify(canvas.toJSON());
    console.log(holder);
    var fileName = window.prompt("Please enter filename:");
    var file = new Blob([holder], {type: "application/json"});
    //stuff
    var myForm = new FormData();
    myForm.append("file", file);

    $.ajax({
        dataType : 'json',
        url : '/upload',
        data : myForm,
        type : "POST",
        enctype: 'multipart/form-data',
        processData: false,
        contentType:false,
        success : function (result) {
            console.log(result);
        },
        error : function (result) {
            console.log(result)

        }

    });

    //stuff
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename)
    else {
        var a = document.createElement("a"),
            url = URL.createObjectURL(file);
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 0);
    }
}


var fileInput = document.getElementById('import-inp');
fileInput.addEventListener('change', function(e){
    var file = e.target.files[0];
    if(!file) return;
    var reader = new FileReader();
    reader.onload = function(f) {
        var data = f.target.result;
        canvas.loadFromJSON(
            JSON.parse(data),
            canvas.renderAll.bind(canvas));
    };
    reader.readAsText(file);
});

function copy() {
    canvas.getActiveObject().clone(function(clonedObj) {
        clipboard = clonedObj;
    });
}

function cut() {
    canvas.getActiveObject().clone(function(clonedObj) {
        clipboard = clonedObj;
    });
    canvas.remove(canvas.getActiveObject());
}

function paste() {
    clipboard.clone(function(clonedObj) {
        canvas.discardActiveObject();
        clonedObj.set({
            left: clonedObj.left + 10,
            top: clonedObj.top + 10,
            event: true,
        });
        if (clonedObj.type === 'activeSelection') {
            clonedObj.canvas = canvas;
            clonedObj.forEachObject(function(o) {
                canvas.add(o);
            });
            clonedObj.setCoords();
        }
        else {
            canvas.add(clonedObj);
        }
        clipboard.top += 10;
        clipboard.left += 10;
        canvas.setActiveObject(clonedObj);
        canvas.requestRenderAll();
    });
}



function undo() {
    if (canvas._objects.length > 0) {
        actionStack.push(canvas._objects.pop());
        canvas.renderAll();
    }
}

function redo() {
    if (actionStack.length > 0) {
        redoOn = true;
        canvas.add(actionStack.pop());
    }
}

function text() {
    var text = new fabric.Text('Type here...', {fontFamily: 'times new roman', left: 100, top:1000});
    canvas.add(text);
    canvas.renderAll();
}



function brushSize(){
    if (canvas.isDrawingMode) {
        var size = prompt("Enter the brush size: ");
    }
    return size;
}



function boldToggle(){

    var selected = canvas.getActiveObject();
    console.log(selected);
    if(selected.get('type')==='text' && selected.get('fontWeight')!== 'bold'){
        selected.set({fontWeight:'bold'});
    }else{
        selected.set({fontWeight:'normal'});
    }
    canvas.renderAll();
}


function italicToggle(){

    var selected = canvas.getActiveObject();
    console.log(selected);
    if(selected.get('type')==='text' && selected.get('fontStyle')!== 'italic'){
        selected.set({fontStyle:'italic'});
    }else{
        selected.set({fontStyle:'normal'});
    }

    canvas.renderAll();


}








function loadWorkspace(){

}


function initWorkspace(){

}


function registerHandler(){

}

function updateWorkspace(){

}




/**
 * need to add setup workspace component
 * need to register event listeners
 *
 */

$(document).ready(function() {
    $(".seriesSelect").select2({
        tags: true
    });
});


//for the initial canvas setup
var canvas = new fabric.Canvas('myCanvas');
canvas.backgroundColor = "white";
var redoOn = false;
var actionStack = [];
var holder;
var clipboard = null;

canvas.renderTop();
canvas.renderAll();

canvas.defaultCursor = 'circle';

canvas.on('object:added',function(){
  if(!redoOn){
    actionStack = [];
  }
  redoOn = false;
});
var color = "black";
var brushWidth = 2;


console.log("hello");


var fileInput = document.getElementById('img-inp');
fileInput.addEventListener('change', function(e){
    console.log("file input is real!");
    console.log(e);
    console.log(e.target.files[0].name);
    var fileReader = new FileReader();
    fileReader.onload = function(o) {
    console.log("yo");
        var imgObject = new Image();
        imgObject.src = o.target.result;
        imgObject.onload = function() {
            var img = new fabric.Image(imgObject);
            img.set({
                angle: 0,
                padding: 15,
                cornersize: 15,
                height: 200,
                width: 200
            });
            canvas.centerObject(img);
            canvas.add(img);
            canvas.renderAll();
        }
    }
    fileReader.readAsDataURL(e.target.files[0]);



});



function pencil() {
    if (!canvas.isDrawingMode) {
        canvas.isDrawingMode = true;
    }
    else {
        canvas.isDrawingMode = false;
    }
    canvas.defaultCursor = "/images/a.jpg";
    canvas.freeDrawingBrush.width = brushSize();
    canvas.freeDrawingBrush.color = color;
}



function addText() {
    var input = prompt("Enter your text: ");
    var text_obj = new fabric.Text(
        input,{
            fontFamily: 'Comic Sans',
            fontSize : 16,
            left: 100,
            top : 100
        }
    );

   // text_obj.on('selected', function(e){
   //     var x = prompt("change text: ");
   //     text_obj.setText(x);
   // });


    canvas.add(text_obj);
    update_layers();

}




function eraser() {

}

function bucket() {
  var bucket = document.getElementById("col-pk").value;
  console.log(bucket == null);
  var selection = canvas.getActiveObject();
  if(selection == null){
      canvas.backgroundColor = bucket;
  }else{
      selection.set({fill:bucket});
      console.log("slection color has been changed to " + bucket);
  }
canvas.renderAll();
  update_layers();


}



function select() {

}

function circle() {
    var circle = new fabric.Circle({
        radius: 20, fill: color, left: 100, top: 100
    });
    canvas.add(circle);
    canvas.renderAll();
    update_layers();

}

function rectangle() {
    var rect = new fabric.Rect({
        width: 10, height: 20,
        left: 100, top: 100,
        fill: color,
        angle: 0
    });
    canvas.add(rect); // add Object
    canvas.renderAll();
    update_layers();
}

function triangle() {
    var triangle = new fabric.Triangle({
        width: 20, height: 30, fill: color, left: 50, top: 50
    });
    canvas.add(triangle);
    canvas.renderAll();
    update_layers();
}

function setWidth(newWidth) {
    width = newWidth;
}

function setColor(newColor) {
    color = newColor;
}

function publish() {
    holder = JSON.stringify(canvas.toJSON());
    var file = new Blob([holder], {type: "application/json"});
    var myForm = new FormData();
    var pngholder=null;

    var data = canvas.toDataURL()
    myForm.append("file", file);
    myForm.append("pngFile",data);
    myForm.append("comicId", comicId1);
    myForm.append("pageId", pageId1);
    $.ajax({
        url : '/uploadOldPage',
        data : myForm,
        type : "POST",
        async : true,
        processData: false,
        contentType:false,
        success : function (result) {
            console.log("success");
            console.log(result);
            window.location.href = result.redirect
        },
        error : function (result) {
            console.log("error");
            console.log(result);
            window.location.replace(result.form);
        }

    });





}

function sendBackwards() {
    var selected = canvas.getActiveObject();
    canvas.sendBackwards(selected);
}

function sendToBack() {
    var selected = canvas.getActiveObject();
    canvas.sendToBack(selected);
}

function bringForwards() {
    var selected = canvas.getActiveObject();
    canvas.bringForward(selected);
}

function bringToFront() {
    var selected = canvas.getActiveObject();
    canvas.bringToFront(selected);
}

function trash() {
    var selected = canvas.getActiveObject();
    canvas.remove(selected);
    update_layers();
}

function exportEdit() {
    holder = JSON.stringify(canvas.toJSON());
    console.log(holder);
    var fileName = window.prompt("Please enter filename:");
    var file = new Blob([holder], {type: "application/json"});
    //stuff
    var seriesName = "mySeries";
    var comicName = "myComic";
    var myForm = new FormData();
    myForm.append("file", file);
    myForm.append("seriesName", seriesName);
    myForm.append("comicName", comicName);
    console.log(myForm);

    $.ajax({
        dataType : 'json',
        url : '/upload',
        data : myForm,
        type : "POST",
        enctype: 'multipart/form-data',
        processData: false,
        contentType:false,
        success : function (result) {
            console.log("success");
            console.log(result);
        },
        error : function (result) {
            console.log("error");
            console.log(result)

        }

    });

    //stuff
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename)
    else {
        var a = document.createElement("a"),
        url = URL.createObjectURL(file);
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 0);
    }
}


var fileInput = document.getElementById('import-inp');
fileInput.addEventListener('change', function(e){
    var file = e.target.files[0];
    if(!file) return;
    var reader = new FileReader();
    reader.onload = function(f) {
      var data = f.target.result;
      canvas.loadFromJSON(
      JSON.parse(data),
      canvas.renderAll.bind(canvas));
    };
    reader.readAsText(file);
});

function copy() {
    canvas.getActiveObject().clone(function(clonedObj) {
        clipboard = clonedObj;
    });
}

function cut() {
    canvas.getActiveObject().clone(function(clonedObj) {
        clipboard = clonedObj;
    });
    canvas.remove(canvas.getActiveObject());
}

function paste() {
    clipboard.clone(function(clonedObj) {
    	canvas.discardActiveObject();
    	clonedObj.set({
    	    left: clonedObj.left + 10,
    		top: clonedObj.top + 10,
    		event: true,
    	});
    	if (clonedObj.type === 'activeSelection') {
    		clonedObj.canvas = canvas;
    		clonedObj.forEachObject(function(o) {
    			canvas.add(o);
    		});
    		clonedObj.setCoords();
    	}
    	else {
    		canvas.add(clonedObj);
    	}
    	clipboard.top += 10;
    	clipboard.left += 10;
    	canvas.setActiveObject(clonedObj);
    	canvas.requestRenderAll();
    });
}



function undo() {
    if (canvas._objects.length > 0) {
        actionStack.push(canvas._objects.pop());
        canvas.renderAll();
    }
}

function redo() {
    if (actionStack.length > 0) {
        redoOn = true;
        canvas.add(actionStack.pop());
    }
}

function text() {
    var text = new fabric.Text('Type here...', {fontFamily: 'times new roman', left: 100, top:1000});
    canvas.add(text);
    canvas.renderAll();
    update_layers();
}



function brushSize(){
    if (canvas.isDrawingMode) {
        var size = prompt("Enter the brush size: ");
    }
    return size;
}



function boldToggle(){

    var selected = canvas.getActiveObject();
    console.log(selected);
    if(selected.get('type')==='text' && selected.get('fontWeight')!== 'bold'){
        selected.set({fontWeight:'bold'});
    }else{
        selected.set({fontWeight:'normal'});
    }
    canvas.renderAll();
}


function italicToggle(){

    var selected = canvas.getActiveObject();
    console.log(selected);
    if(selected.get('type')==='text' && selected.get('fontStyle')!== 'italic'){
        selected.set({fontStyle:'italic'});
    }else{
        selected.set({fontStyle:'normal'});
    }

    canvas.renderAll();


}




function update_layers() {

    obj = canvas.getObjects();

    for( var i=0; i<obj.length; i++){
        var layer = "<li>" + obj[i]+"</li>";
        document.getElementById("layers").append(layer);
    }

}






//
// $.getJSON(json_url, function(data) {
//
//     canvas.loadFromJSON(data);
//     canvas.renderAll();
//
//
// });
//








function loadWorkspace(){

}


function initWorkspace(){

}


function registerHandler(){

}

function updateWorkspace(){

}