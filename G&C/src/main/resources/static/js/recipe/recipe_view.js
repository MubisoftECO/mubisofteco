window.onload = function () {
    document.getElementById("btn-share").addEventListener(
        "click",
        getlink
    );
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
