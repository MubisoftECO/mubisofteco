function addStep(){
    let steps = $('div.recipe-step');

    if (steps.length < max) {
        let id = parseInt(steps.get(steps.length - 1).id
            .substring('recipe-step-'.length, steps.get(steps.length - 1).id.length)) + 1;

        let newStep = document.createElement('div');
        newStep.id = 'recipe-step-' + id;
        newStep.classList.add('card', 'recipe-step', 'mb-3');

        newStep.innerHTML =
            "<div class='card-header space-between'>" +
            "               <span id='card-header-title-" + id + "' class='badge bg-secondary rounded-pill order'>" + id + "</span>" +
            "               <button type='button' id='button-remove-step-" + id + "' class='btn-close' aria-label='Close'></button>" +
            "            </div>" +
            "            <div class='card-body'>" +
            "               <label class='container-fluid'>" +
            "                   <textarea id='recipe-step-description-" + id + "' class='form-control recipe-step'\n" +
            "                       name='step' maxlength='255'></textarea>" +
            "               </label>" +
            "            </div>"

        // Append the child to the list.
        $('div#add-step').append(newStep);

        // Add event listener
        $('button[type=button]#button-remove-step-' + id).click(function (event) {
            removeStep(event.target.id);
        });

    } else {
        displayAlert('max-step-alert');
        setTimeout(function (e) {
            hideAlert('max-step-alert');
        }, 3000);
    }
}

function addIngredient(){
    if ($('tr.new-ingredient').length < max) {
        let ingredients = $('tbody#add_ingredient_body > tr.new-ingredient');
        let id = parseInt(ingredients[ingredients.length - 1]
            .querySelector(".ingredient-id").innerHTML) + 1;

        // Create clone
        let ingredientClone = ingredients[0].cloneNode(true);
        $('tbody#add_ingredient_body').append(ingredientClone);

        // Change clone IDs
        ingredientClone.id = 'ingredient-' + id;
        ingredientClone.querySelector('th.ingredient-id').innerHTML = id.toString();
        ingredientClone.querySelector('td > input[type="button"]').id = 'btn-remove-ingredient-' + id;

        // Add listener
        ingredientClone.querySelector('td.ingredient-delete > input').addEventListener('click', function (event) {
            removeIngredient(event.target.id);
        })

        // Remove elements
        ingredientClone.querySelector('td.ingredient-description > div').remove();
        ingredientClone.querySelector('td.ingredient-unit > div').remove();

        // Create new select item
        let selectIngredient = $('<select/>', {
            'class': "selectpicker required",
            'name': "ingredient",
            'title': "Ingredient",
            'data-live-search': "true"
        });
        let selectUnit = $('<select/>', {
            'class': "selectpicker",
            'name': "unit",
            'title': "Unit",
            'data-live-search': "true"
        });
        // Add option items
        ingredientList.forEach(item => {
            selectIngredient.append('<option value=' + item.id + '>' + item.nameEn + '</option>');
        });
        measurementList.forEach(item => {
            selectUnit.append('<option value=' + item.toString() + '>' + item.toString().toUpperCase() + '</option>');
        })
        // Append to table
        selectIngredient.appendTo(ingredientClone.querySelector('td.ingredient-description.custom-select')).selectpicker('refresh');
        selectUnit.appendTo(ingredientClone.querySelector('td.ingredient-unit.custom-select')).selectpicker('refresh');
    } else {
        displayAlert('max-ingredient-alert');
        setTimeout(function (e) {
            hideAlert('max-ingredient-alert');
        }, 3000);
    }
}

function removeStep(targetID) {
    let id = parseInt(targetID.substring('button-remove-step-'.length, targetID.length));
    let stepList = $('div.recipe-step');

    if (stepList.length > min) {
        $('#recipe-step-' + id).remove();
        reorderStepID();
    } else {
        displayAlert('min-step-alert');
        setTimeout(function (e) {
            hideAlert('min-step-alert');
        }, 3000);
    }
}

function removeIngredient(targetID) {
    let id = parseInt(targetID.substring('btn-remove-ingredient-'.length, targetID.length));
    let ingredientList = $('tr.new-ingredient');

    if (ingredientList.length > min) {
        $('#ingredient-' + id).remove();
        reorderIngredientID();
    } else {
        displayAlert('min-ingredient-alert');
        setTimeout(function (e) {
            hideAlert('min-ingredient-alert');
        }, 3000);
    }
}

function reorderIngredientID() {
    let list = $("tr.new-ingredient");

    for (let i = 0; i < list.length; i++) {
        let ingredientID = list.get(i).id;
        if (ingredientID !== 'ingredient-' + (i + 1)) {
            $('tr#' + ingredientID)[0].id = 'ingredient-' + (i + 1);
            $('tr#ingredient-' + (i + 1) + ' > th')[0].innerText = (i + 1);
            $('tr#ingredient-' + (i + 1) + ' > td.ingredient-delete > input[type="button"]#btn-remove-ingredient-'
                + ingredientID.substring('ingredient-'.length, ingredientID.length))[0].id = 'btn-remove-ingredient-' + (i + 1);
        }
    }
}

function reorderStepID() {
    let elementList = $('div#add-step > div.recipe-step');

    for (let i = 0; i < elementList.length; i++) {
        let id = elementList.get(i).id.substring('recipe-step-'.length, elementList.get(i).id.length);

        // Check if the id is in the correct location.
        if (id !== (i + 1)) {
            let element = elementList.get(i);
            element.id = 'recipe-step-' + (i + 1);  // Set id.
            element.querySelector('div.card-header > span.badge').id = 'card-header-title-' + (i + 1);
            element.querySelector('div.card-header > span.badge').innerHTML = (i + 1).toString();
            element.querySelector('div.card-header > button.btn-close').id = 'button-remove-step-' + (i + 1);
            element.querySelector('div.card-body > label > textarea.recipe-step').id = 'recipe-step-description-' + (i + 1);
            element.querySelector('div.card-body > label > textarea.recipe-step').name = 'step-' + (i + 1);
        }
    }
}

function displayAlert(alertID) {
    let alert = $('div#' + alertID);
    alert.removeClass('hidden');
}

function hideAlert(alertID) {
    let alert = $('div#' + alertID);
    alert.addClass('hidden');
}