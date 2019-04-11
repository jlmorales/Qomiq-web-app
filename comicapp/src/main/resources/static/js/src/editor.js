
var canvas = new fabric.Canvas('myCanvas');

canvas.item(0);
canvas.getObjects();

var color = "black";
var brushWidth = 2;

function pencil() {
    fabric.isDrawingMode = true;
    canvas.freeDrawingBrush.width = brushWidth;
    canvas.freeDrawingBrush.color = color;
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
}

function rectangle() {
    var rect = new fabric.Rect({
        width: 10, height: 20,
        left: 100, top: 100,
        fill: color,
        angle: 0
      });
    canvas.add(rect); // add Object
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

function export() {
    holder = canvas.toJSON();
    canvas.clear();
}

function import() {
    canvas.loadFromJSON(holder);
}