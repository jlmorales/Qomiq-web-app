
var canvas = new fabric.Canvas('myCanvas');
canvas.backgroundColor = "white";
canvas.renderTop();
canvas.renderAll();

var color = "black";
var brushWidth = 2;

function pencil() {

    canvas.isDrawingMode= true;
    canvas.freeDrawingBrush.width = 2;
    canvas.freeDrawingBrush.color = "white";

}

function eraser() {

}

function bucket() {

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

var holder;

function exportEdit() {
    holder = canvas.toJSON();
    canvas.clear();
}

function importEdit() {
    canvas.loadFromJSON(holder);
}