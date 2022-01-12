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

    step.innerHTML += "<label class='step' for='step"+numSteps+"'>Step"+numSteps+"</label>";
    step.innerHTML += "<input type='text' id='step"+numSteps+"' name='step' class='form-control' style='height: 90px'>";
    step.innerHTML += "<br>";
}

function addIngredient(){
    let tableBody = document.getElementById("add_ingredient_body");
    let ingredients = tableBody.querySelectorAll(".new-ingredient");
    let id = parseInt(ingredients[ingredients.length - 1]
            .querySelector(".ingredient-id").innerHTML) + 1;

    // Get last ingredient.
    let newIngredient = tableBody.insertRow();
    newIngredient.classList.add("new-ingredient");
    newIngredient.id = "new-ingredient-" + id;

    // Add table cells.
    let titleCell = newIngredient.insertCell();
    titleCell.outerHTML =
        "<th class='ingredient-id ingredient-id align-middle' scope='row'>" + (id).toString() + "</th>"

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

    let deleteCell = newIngredient.insertCell();
    deleteCell.outerHTML =
        "<td class='delete-ingredient'><input type='button' class='btn btn-danger' onclick='removeIngredient(1)'" +
        " value='Remove'></td>"
}

function removeStep() {

}

function removeIngredient(id) {
    let count = $('tr.new-ingredient').length;
    console.log("Executing delete, element count = " + count);

    // Check if there are more than one ingredients.
    if (count < 1) {
        console.log("Removing: new-ingredient-" + id)
        $("new-ingredient-" + id).delete();
    }
}
