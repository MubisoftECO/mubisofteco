window.onload = function () {
    document.getElementById("btn-step").addEventListener(
        "click",
        addStep
    );

    document.getElementById("btn-ingredient").addEventListener(
        "click",
        addIngredient
    );
}

function addStep(){
    console.log("step");
    let step = document.getElementById("add_step");
    let steps = document.getElementsByClassName("step");
    let numSteps = steps.length;
    numSteps++;

    step.innerHTML += "<label class='step' htmlFor='step"+numSteps+"'>Step"+numSteps+"</label>";
    step.innerHTML += "<input type='text' id='step"+numSteps+"' name='step' class='form-control' style='height: 90px'>";
    step.innerHTML += "<br>";
}

function addIngredient(){
    let tableBody = document.getElementById("add_ingredient_body");
    let ingredientList = tableBody.querySelectorAll(".new-ingredient");

    // Get last ingredient.
    let ingredientCopy = ingredientList[ingredientList.length - 1];
    let newIngredient = tableBody.insertRow();
    newIngredient.classList.add("new-ingredient");

    let titleCell = newIngredient.insertCell().outerHTML = "<th></th>";
    titleCell.appendChild(document.createTextNode(ingredientCopy.querySelector("ingredient-id").innerHTML + 1))
}



