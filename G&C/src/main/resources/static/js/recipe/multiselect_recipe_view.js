$(document).ready(function(){
    $('#flag-multiselect').on('hidden.bs.select', function(){

        let select = document.getElementById("flag-multiselect");
        let result = [];
        let options = select && select.options;
        let opt;

        for (let i=0, iLen=options.length; i<iLen; i++) {
            opt = options[i];

            if (opt.selected) {
                result.push(opt.value);
                console.log(opt.value);
            }
        }

        let url = "/recipe/view"

        for (let i=0; i<result.length; ++i) {
            if (url.indexOf('?') === -1) {
                url = url + '?flags=' + result[i];
            }else {
                url = url + '&flags=' + result[i];
            }
        }

        window.location = url;
    });
});
