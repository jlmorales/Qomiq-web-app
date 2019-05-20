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
            //imgObject.scaleToWidth(200,false);
            canvas.centerObject(img);
            canvas.add(img);
            canvas.renderAll();
        }
    }
    fileReader.readAsDataURL(e.target.files[0]);



});



function labeling(){

    var tagging  = document.createElement("lable");
    tagging.for = "img-inp";
}








// thought bubble code starts here



function addBubble(){


    var img = new Image();
    img.src = "https://static.thenounproject.com/png/1545185-200.png";
    img.onload = function(){
        // img.width = 200;
        //img.height= 200;
        var tb = new fabric.Image(img);
        tb.set({
            originX: 'center',
            originY: 'center',
            angle: 0,
            padding: 15,
            cornersize: 15,

        });
        tb.scaleToWidth(200, false);




        // addding the text for grouping

        var dial = prompt("Enter your text");
        if (dial === null || dial === ""){
            return;
        }

        var text = new fabric.Text(dial, {
            fontSize: 20,
            originX: 'center',
            originY: 'center'
        });




        // text grouping ends

        var group = new fabric.Group([ tb, text ], {
            left: 150,
            top: 100,
            angle: 0
        });

        /// creates ad new group






        ///ends the group creation







        canvas.centerObject(group);
        canvas.add(group);
        canvas.renderAll();
    }




}









// thought bubble code ends here












function displayNewSeriesInput(){
    document.getElementById('newSeriesTextBox').hidden = false;
    document.getElementById('currentSeries').hidden = true;
}

function hideNewSeriesInput(){
    document.getElementById('newSeriesTextBox').hidden = true;
    document.getElementById('currentSeries').hidden = false;
}

function pencil() {
    if (!canvas.isDrawingMode) {

        canvas.isDrawingMode = true;
    }
    else {
        canvas.isDrawingMode = false;
    }

    if (canvas.isDrawingMode){

        canvas.freeDrawingBrush.width = brushSize();
        canvas.freeDrawingBrush.color = color;
    }
    //canvas.defaultCursor = "/images/a.jpg";

}



function addText() {
    if (canvas.isDrawingMode) canvas.isDrawingMode = false;
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
if (!canvas.isDrawingMode) canvas.isDrawingMode = true;
else { canvas.isDrawingMode = false; }
if (canvas.isDrawingMode) {
    canvas.freeDrawingBrush.width = brushSize();
        canvas.freeDrawingBrush.color = "white";
}
}

function bucket() {
if (canvas.isDrawingMode) canvas.isDrawingMode = false;
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
    canvas.isDrawingMode = false;

}

function circle() {
    if (canvas.isDrawingMode) canvas.isDrawingMode = false;
    var circle = new fabric.Circle({
        radius: 20, fill: color, left: 100, top: 100
    });
    canvas.add(circle);
    canvas.renderAll();
    update_layers();

}

function rectangle() {
    if (canvas.isDrawingMode) canvas.isDrawingMode = false;
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
    if (canvas.isDrawingMode) canvas.isDrawingMode = false;
    var triangle = new fabric.Triangle({
        width: 20, height: 30, fill: color, left: 50, top: 50
    });
    canvas.add(triangle);
    canvas.renderAll();
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
    // var seriesName = $("#comicSeries option:selected").text();
    var seriesName;
    if (willCreateNewSeries() == "No"){
        seriesName = $("#comicSeries option:selected").attr('value');
        seriesName = $("#currentSeries").val();
    }
    else{
        seriesName = document.getElementById("newSeriesTitle").value;
    }

    // var seriesName = document.getElementById("currentSeries").innerText = document.getElementById("comic_series").value;
    console.log(seriesName);
    var comicName= $("#comicTitle").val();
    var makePublic = $("#makePublic").val();
    var enableComments = $("#commentsBoolean").val();
    var category = $("#categories").val();
    var myForm = new FormData();
    var pngholder=null;
    // $("#myCanvas").get(0).toBlob(function(blob){
    //     pngholder=blob;
    // });
    var data = canvas.toDataURL()
    // var blob = new Blob([data], {type:"octet/stream"});

    console.log(data);
    // var pngfile = new Blob([pngholder], {type:'image/png'});

    myForm.append("file", file);
    myForm.append("pngFile",data);
    myForm.append("seriesName", seriesName);
    myForm.append("comicName", comicName);
    myForm.append("makePublic", makePublic);
    myForm.append("enableComments", enableComments);
    myForm.append("category", category)
    $.ajax({
        url : '/upload',
        data : myForm,
        type : "POST",
        processData: false,
        contentType:false,
        success : function (result) {
            console.log("success");
            console.log(result);
            location.href = "/create"
        },
        error : function (result) {
            console.log("error");
            console.log(result);
            window.location.replace('/create');

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
}



function brushSize(){
    if (canvas.isDrawingMode) {
        var size = prompt("Enter the brush size: ");
    }
    return size;
}


function willCreateNewSeries(){
    var radios = document.getElementsByName('isNewSeries');
    for (var i = 0, length = radios.length; i < length; i++)
    {
        if (radios[i].checked)
        {
            // do whatever you want with the checked radio
            return radios[i].value;
        }
    }

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

