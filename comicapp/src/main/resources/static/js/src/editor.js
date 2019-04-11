
var canvas = new fabric.Canvas('myCanvas');
canvas.backgroundColor = "white";
canvas.renderTop();
canvas.renderAll();

var color = "black";
var brushWidth = 2;



var fileInput = document.getElementById('img-inp');

// fileInput.addEventListener('change', (e) => {console.log(e.target.filename)});
// fileInput.onchange((e)=>{
//     console.log(e.target.filename);
// });
fileInput.addEventListener('change', function(e){
    console.log("file input is real!");
    console.log(e);
    console.log(e.target.files[0].name);
    var fileReader = new FileReader();
    fileReader.onload = function(o) {
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
    canvas.isDrawingMode = true;
    console.log('yo');
    canvas.freeDrawingBrush.width = 2;
    canvas.freeDrawingBrush.color = "black";
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
//
// function image(e) {
//     var fileReader = new FileReader();
//     fileReader.onload = function(o) {
//         var imgObject = new Image();
//         imgObject.src = o.target.result;
//         imgObject.onload = function() {
//             var img = new fabric.Image(imgObject);
//             img.set({
//                 angle: 0,
//                 padding: 15,
//                 cornersize: 15,
//                 height: 200,
//                 width: 200
//             });
//             canvas.centerObject(img);
//             canvas.add(img);
//             canvas.renderAll();
//         }
//     }
//     reader.readAsDataURL(e.target.files[0]);
// }
