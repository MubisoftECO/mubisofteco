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
    if(btn_menu != null) {
        for (let i = 0; i < btn_menu.length; i++) {
            const element = btn_menu[i];

            element.addEventListener('click',function (e) {
                updateCounter(e);
            },false);
        }
    }
}

function updateCounter(e) {
    let id = e.target.id;
    let parameters = {id : id};

    if (e.target.tagName === "A") {
        $.post("/metric/counter",parameters);
    } else if(e.target.tagName === "BUTTON") {
        $.post("/metric/counter",parameters);
    }
}

