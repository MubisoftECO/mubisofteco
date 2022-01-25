const max = 24;
const min = 1;

window.onload = function () {
    /*-------------------------------------------------------------------------*/
    // Add and remove elements
    $('input[type=button]#btn-add-ingredient').click(function (event) {
        addIngredient();
    });
    $('input[type=button]#btn-add-step').click(function (event) {
        addStep();
    });
    $('input[type=button].btn-remove-ingredient').click(function (event) {
        removeIngredient(event.target.id);
    });
    $('button[type=button].btn-close').click(function (event) {
        removeStep(event.target.id);
    });
}