
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
    var step = document.getElementById("add_step");
    var steps = document.getElementsByClassName("step");
    var numSteps = steps.length;
    numSteps++;

    //step.innerHTML += "<tr><td><label htmlFor='step'>Step</label></td><td><input type='text' id='step' name='step'></td></tr>";
    step.innerHTML += "<br>";
    step.innerHTML += "<label class='step' htmlFor='step"+numSteps+"'>Step"+numSteps+"</label>";
    step.innerHTML += "<input type='text' id='step"+numSteps+"' name='step' style='width:250px; height: 90px'>";
    step.innerHTML += "<br>";
}

function addIngredient(){
    console.log("ingredient");

    var ingredient = document.getElementById("add_ingredient");
    var ingredients = document.getElementsByClassName("ingredient");
    var numIngredients = ingredients.length;

    numIngredients++;

    ingredient.innerHTML += "<br><br>";
    ingredient.innerHTML += "<label class='ingredient' htmlFor='ingredient"+numIngredients+"'>Ingredient"+numIngredients+"</label>";
    ingredient.innerHTML += "<input type='text' id='ingredient' name='ingredient'>";
    ingredient.innerHTML += "<br>";
    ingredient.innerHTML += "<label class='form-label'>Quantity</label>";
    ingredient.innerHTML += "<select name='productNumber'>" +
        "<option value='1'>1</option>" +
        "<option value='2'>2</option>" +
        "<option value='3'>3</option>" +
        "<option value='4'>4</option>" +
        "<option value='5'>5</option>" +
        "<option value='6'>6</option>" +
        "<option value='7'>7</option>" +
        "<option value='8'>8</option>" +
        "<option value='9'>9</option>";
    ingredient.innerHTML += "</select>";

    ingredient.innerHTML += "<label class='form-label'>Unit</label>";
    ingredient.innerHTML += "<select name='productNumber'>" +
        "<option value='gr'>GR</option>" +
        "<option value='kg'>KG</option>" +
        "<option value='cl'>CL</option>" +
        "<option value='l'>L</option>"
    ingredient.innerHTML += "</select>";
}



