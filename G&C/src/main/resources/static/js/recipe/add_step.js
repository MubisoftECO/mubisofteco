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
    let lastIngredient = ingredientList[ingredientList.length - 1];

    // Get last ingredient.
    let newIngredient = tableBody.insertRow();
    newIngredient.classList.add("new-ingredient");

    // Add table cells.
    let titleCell = newIngredient.insertCell();
    titleCell.outerHTML =
        "<th class='ingredient-id ingredient-id align-middle' scope='row'>" +
        (parseInt(lastIngredient.querySelector(".ingredient-id").innerHTML) + 1).toString() + "</th>"

    let descriptionCell = newIngredient.insertCell();
    descriptionCell.outerHTML =
        "<td class='ingredient-description'><input type='text' class='ingredient form-control' " +
        "name='ingredient' placeholder='Ingredient'></td>"

    let quantityCell = newIngredient.insertCell();
    quantityCell.outerHTML =
        "<td class='ingredient-quantity'><input type='number' class='productQuantity form-control' " +
        "name='quantity' min='0'></td>";

    let selectCell = newIngredient.insertCell();
    let optionList = document.querySelectorAll(".ingredient-unit .measurementUnit option");
    selectCell.outerHTML =
        "<td class='ingredient-unit'>" +
        "<select class='measurementUnit form-control' name='unit'>" +
        "<option selected disabled th:value='${null}'>Select</option>" +
        "<option value='" + optionList[1].value + "'> " + optionList[1].innerText + "</option>" +
        "<option value='" + optionList[2].value + "'> " + optionList[2].innerText + "</option>" +
        "<option value='" + optionList[3].value + "'> " + optionList[3].innerText + "</option>" +
        "<option value='" + optionList[4].value + "'> " + optionList[4].innerText + "</option>" +
        "</select>" +
        "</td>";
}



