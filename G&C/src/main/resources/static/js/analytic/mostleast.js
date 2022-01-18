$(document).ready(function() {
    $('#most').on('click', function () {
        $.ajax({
            method: 'GET',
            url: 'most-sold',
            success: function (result) {
               let details = JSON.parse(result);
               let products = details.values();
               let names = [];
               let numbers = [];
                for (let product of products) {
                    names.push(product.name_en);
                    numbers.push(product.total);
                }

               display(names,numbers);
            }
        });
    });

    $('#least').on('click', function () {
        $.ajax({
            method: 'GET',
            url: 'least-sold',
            success: function (result) {
                let details = JSON.parse(result);
                let products = details.values();
                let names = [];
                let numbers = [];
                for (let product of products) {
                    names.push(product.name_en);
                    numbers.push(product.total);
                }
                display(names,numbers);
            }
        });
    });
});

function display(names, numbers) {
    Highcharts.chart('graphic', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Most Less sold products'
        },
        xAxis: {
            categories: names
        },
        yAxis: {
            min: 0,
            title: {
                text: null
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal'
            }
        },
        series: [{
            name: 'Profit',
            data: numbers
        }]
    });
}