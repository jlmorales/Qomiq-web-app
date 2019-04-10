//creates the canvas
var canvas = new fabric.Canvas('c');



// create a rectangle object
var rect = new fabric.Rect(
    {
        left:100,
        top: 100,
        width: 100,
        height: 100
    }
);


var rect2 = new fabric.Rect(
    {
        left:100,
        top: 100,
        width: 100,
        height: 100
    }
);



function mouseclick(e) {
    var mouse = canvas.getPointer(e.memo.e);
    //started = true;
    //x = mouse.x;
    //y = mouse.y;

    var square = new fabric.Rect({
        width: 100,
        height: 100,
        left: mouse.x,
        top: mouse.y
    });

    canvas.add(square);
    canvas.renderAll();
    canvas.setActiveObject(square);

}




function pencilmode() {
    canvas.isDrawingMode = true;
    canvas.freeDrawingBrush.width = 2;
    canvas.freeDrawingBrush.color = "green";
    
}

var holder;

function exportEdit(){
    holder = canvas.toJSON();
    //console.log(canvas.toJSON());
    canvas.clear();
}

function importEdit(){
    canvas.loadFromJSON(holder);
}
