let publish_product;
let btn_menu;

window.onload = function() {
   publish_product = document.getElementById('publish_product');
   btn_menu = document.querySelectorAll('.btn_menu');

   if(publish_product != null) {
       publish_product.addEventListener('click', function (e) {
           updateCounter(e);
       }, false);
   }
    if (btn_menu != null) {
        for (let element of btn_menu) {
            element.addEventListener('click',function (e) {
                updateCounter(e);
            },false);
        }
    }
}

function updateCounter(e) {
    $.post("/metric/counter",{id : e.target.id});
}

