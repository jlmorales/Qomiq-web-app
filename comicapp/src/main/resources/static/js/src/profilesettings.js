var fileInput = document.getElementById('img-inp');
fileInput.addEventListener('change', function(e){
    var fileReader = new FileReader();
    fileReader.onload = function(o) {
        var file = o.target.result;
        var myForm = new FormData();
        myForm.append("image", file);
        $.ajax({
            url : '/profileImage',
            data : myForm,
            type : "POST",
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
    }
    fileReader.readAsDataURL(e.target.files[0]);
});