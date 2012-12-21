
$('.close').on('click', function(e){
    $(this).closest("#alertBody").hide('fade');
    // hack for a hack
    parent.postMessage("myMessage","https://172.16.12.15");
});
$(".alert").alert()

$("#action").on('click',function(){
   parent.location = $(this).getAttribute('href');
});
