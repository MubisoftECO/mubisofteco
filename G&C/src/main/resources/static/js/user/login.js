let icon, buttons, checkbox;
let login;
let btn_menu;
let product_list, recipe_list,analytics;
let menu_profile_settings, profile_settings;

window.onload = function() {
    icon = document.getElementById('iconPassword');
    buttons = document.querySelector("#button-parent");
    checkbox = document.querySelectorAll('.user-type');
    login = document.getElementById('signin');
    btn_menu = document.querySelectorAll('.btn_menu');
    product_list = document.getElementById('product_list');
    recipe_list = document.getElementById('recipe_list');
    analytics = document.getElementById('analytics');
    menu_profile_settings = document.getElementById('menu_profile_settings');
    profile_settings =  document.getElementById('profile_settings');

    if(buttons != null) {
        buttons.addEventListener("mouseover",updateCurrent, false);
        buttons.addEventListener("mouseout", resetCurrent,false);
    }
    if(icon != null) {
        icon.addEventListener('click', toggleIconPassword, false);
    }

    if(checkbox != null){
        for (let element of checkbox) {
            element.addEventListener('click', change, false);
        }
    }
    if(login != null) {
        buttons.addEventListener("click",updateCounter, false);
    }


    if(btn_menu != null) {
        for (let element of btn_menu) {
            element.addEventListener('click',function (e) {
                updateCounter(e);
            },false);
        }
    }

    if(product_list != null) {
        product_list.addEventListener("click",updateCounter, false);
    }
    if(recipe_list != null) {
        recipe_list.addEventListener("click",updateCounter, false);
    }
    if(analytics != null) {
        analytics.addEventListener("click",updateCounter, false);
    }
    if(menu_profile_settings!= null) {
        menu_profile_settings.addEventListener('click', updateCounter,false);
    }
    if (profile_settings != null) {
        profile_settings.addEventListener('click', updateCounter,false);
    }
}


function toggleIconPassword(evt) {
    let inputPassword = document.getElementById('inputPassword');
    let id = evt.currentTarget.id;
    let element = document.getElementById(id);

    if (inputPassword.type === "password") {
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
    let vendor = document.getElementById("form-vendor");

    if(e.target.tagName === "INPUT") {
        switch (e.target.value) {
            case "vendor":
                vendor.classList.remove('hidden');
                break;

            case "customer":
                vendor.classList.add('hidden');
                break;
        }
    }
}

function updateCounter(e) {
    if (e.target.tagName === "A") {
        let id = e.target.id;
        if(id === "signin")  id = "login";
        let parameters = {id : id};

        $.post("/metric/counter",parameters);
    }
}

