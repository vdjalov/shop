

let sort = sessionStorage.getItem("sortBy");
    if(sort == null) {
        $("#sortBy").append(`<option "name" selected="selected">name</option>`);
        $("#sortBy").append(`<option value="price-asc">price-asc</option>`);
        $("#sortBy").append(`<option value="price-desc">price-desc</option>`);
    } else if(sort == "name") {
        $("#sortBy").append(`<option value="name" selected="selected">name</option>`);
        $("#sortBy").append(`<option value="price-asc">price-asc</option>`);
        $("#sortBy").append(`<option value="price-desc">price-desc</option>`);
    } else if(sort == "price-asc") {
        $("#sortBy").append(`<option "name">name</option>`);
        $("#sortBy").append(`<option value="price-asc" selected="selected">price-asc</option>`);
        $("#sortBy").append(`<option value="price-desc">price-desc</option>`);
    } else if(sort == "price-desc") {
        $("#sortBy").append(`<option "name">name</option>`);
        $("#sortBy").append(`<option value="price-asc">price-asc</option>`);
        $("#sortBy").append(`<option value="price-desc" selected="selected">price-desc</option>`);
    }
    
let ipp = sessionStorage.getItem("productsPerPage");
    if(ipp == null) {
        $("#productsPerPage").append(`<option value="5" selected="selected">5</option>`);
        $("#productsPerPage").append(`<option value="10">10</option>`);
        $("#productsPerPage").append(`<option value="20">20</option>`);
    } else if(ipp == 5) {
        $("#productsPerPage").append(`<option value="5" selected="selected">5</option>`)
        $("#productsPerPage").append(`<option value="10">10</option>`);
        $("#productsPerPage").append(`<option value="20">20</option>`);
    } else if(ipp == 10) {
        $("#productsPerPage").append(`<option value="5">5</option>`);
        $("#productsPerPage").append(`<option value="10" selected="selected">10</option>`);
        $("#productsPerPage").append(`<option value="20">20</option>`);
    } else if(ipp == 20) {
        $("#productsPerPage").append(`<option value="5">5</option>`);
        $("#productsPerPage").append(`<option value="10">10</option>`);
        $("#productsPerPage").append(`<option value="20" selected="selected">20</option>`);
    }    

