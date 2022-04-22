$(function(){

  var txt = $(".section-2 p span"); 
  var btn = $("#GooroomStudy");
  var gotoqna = $("button#gotoqna")
  var modal1 = $(".modal.fade");
  var modalqna = $(".modalqna");
  var modalqnaclose = $(".modalqna button");
  modal1.hide();



  var clock = setInterval(function(){
    var now = new Date();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();

    if (hour<10) {
      hour = "0"+hour;
    }
    if (minute<10) {
      minute = "0"+minute;
    }
    if (second<10) {
      second = "0"+second;
    }

    txt.eq(0).text(hour);
    txt.eq(1).text(minute);
    txt.eq(2).text(second);


    if (hour>18 && hour<24) {
      btn.off().on('click',function(){
        // alert("환영합니다");
        var url = "home.html";
        $(location).attr('href',url);
      })
    } else {
      btn.off().on('click',function(){
        modal1.show();
      })
    }

    gotoqna.off().on('click',function(){
      modal1.hide();
      modalqna.show();
      var modalqnaclose = $(".modalqna button");
    });
    modalqnaclose.off().on('click',function(){
      modalqna.hide();
      modal1.show();
    });
  },100)

})