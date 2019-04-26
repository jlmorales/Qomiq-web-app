function addComment(){
    var comments = document.getElementById("comments");
    var comment = document.getElementById("newcomment").value;
    comments.append(
        "            <div class=\"commenterImage\">\n" +
        "              <img src=\"http://placekitten.com/50/50\" />\n" +
        "            </div>\n" +
        "            <div class=\"commentText\">\n" +
        "              <p class=\"\">fun text</p> <span class=\"date sub-text\">on March 5th, 2014</span>\n" +
        "\n" +
        "            </div>\n"
    );

}

$(document).ready(function(){
    $("#like").click(function(){
        $("#like").toggleClass("like");
    });
});;

$(document).ready(function(){
    $("#like").click(function(){
        $("#like").toggleClass("like");
    });
});;



