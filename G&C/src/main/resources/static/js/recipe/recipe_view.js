let btn_menu;
window.onload = function () {
    btn_menu = document.querySelectorAll('.btn_menu');

    document.getElementById("btn-share").addEventListener(
        "click",
        getlink
    );
    if(btn_menu != null) {
        for (let element of btn_menu) {
            element.addEventListener('click',function (e) {
                updateCounter(e);
            },false);
        }
    }
}

function getlink() {
    let aux = document.createElement("input");
    aux.setAttribute("value",window.location.href);
    document.body.appendChild(aux);
    aux.select();
    document.execCommand("copy");
    document.body.removeChild(aux);
    alert("Enlace copiado");
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
