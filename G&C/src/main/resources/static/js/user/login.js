let icon, buttons, checkbox;
window.onload = function() {
    icon = document.getElementById('iconPassword');
    buttons = document.querySelector("#button-parent");
    checkbox = document.querySelectorAll('.user-type');
    if(buttons != null) {
        buttons.addEventListener("mouseover",updateCurrent, false);
        buttons.addEventListener("mouseout", resetCurrent,false);
    }
    if(icon != null) {
        icon.addEventListener('click', toggleIconPassword, false);
    }

    if(checkbox != null){

        for (let i = 0; i < checkbox.length; i++) {
            const element = checkbox[i];

            element.addEventListener('click', change, false);
        }
    }
}


function toggleIconPassword(evt) {

    let inputPassword = document.getElementById('inputPassword');
    let id = evt.currentTarget.id;
    let element = document.getElementById(id);
    if(inputPassword.type === "password") {
        element.classList.remove('fa-lock');
        element.classList.add('fa-lock-open')
        inputPassword.type = "text";
    } else {
        element.classList.remove('fa-lock-open');
        element.classList.add('fa-lock')
        inputPassword.type = "password";
    }
}


function updateCurrent(e) {
    if(e.target !== e.currentTarget) {
        e.target.classList.add('current');
        if (e.target.nextElementSibling !== null) {
            e.target.nextElementSibling.classList.remove('current');
        } else {
            e.target.previousElementSibling.classList.remove('current');
        }
    }
}

function resetCurrent(e) {

    let page = window.location.pathname;
    if(page === "/login/sign-in") {

        buttons.children[0].className = "btn custom-btn-signin btn-lg current selected";
        buttons.children[1].className = "btn custom-btn-signup btn-lg";
    } else {
        buttons.children[0].className = "btn custom-btn-signin btn-lg ";
        buttons.children[1].className = "btn custom-btn-signup btn-lg current selected";
    }

}

/*SIGN UP customer & vendor*/
function change(e) {
    let id;
    let customer = document.getElementById("form-customer");
    let vendor = document.getElementById("form-vendor");

    if(e.target.tagName === "INPUT") {
        id = e.target.id;

        switch (e.target.value) {
            case "vendor":
                vendor.classList.remove('hidden');
                customer.classList.add('hidden');
                break;

            case "customer":
                customer.classList.remove('hidden');
                vendor.classList.add('hidden');
                break;
        }

    }
}