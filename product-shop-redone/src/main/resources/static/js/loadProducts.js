
function updateProductsIpp() {
    let itemsPerPage = $("#perPage").val();
    sessionStorage.setItem("ipp", itemsPerPage);
    window.location.href = "/products/all";
}

function updateProductsSort() {
    let sort = $("#sort").val();
    sessionStorage.setItem("sort", sort);
    window.location.href = "/products/all";
}

function loadProducts() {
    addIppOptions();
    addSortOptions();
    let sort = sessionStorage.getItem("sort");
    let itemsPerPage = sessionStorage.getItem("ipp");
    if(itemsPerPage == null) {
        itemsPerPage = 5;
    }
  
    const currentURL =  window.location.href;
    let parsedPageNumber = currentURL.match("page=[0-9]+");
    let parsedSortValue = currentURL.match("sortBy=[a-z]+");
    
    let initUrl = "/api/products/all?ipp=" + itemsPerPage; 
    if(parsedPageNumber != null) {
        initUrl+=`&${parsedPageNumber}`;
    }

    if(parsedSortValue!= null) {
        initUrl+=`&${parsedSortValue}`;
    } else if(sort != null) {
        initUrl+=`&sortBy=${sort}`;
    }

    fetchProducts(initUrl, currentURL, sort, itemsPerPage);
}

function fetchProducts(initUrl, currentURL, sort, itemsPerPage) {
    fetch(initUrl)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            $("#products").empty();  
            const multiplier = $("#perPage").val();
            getPagingNumbering(data, currentURL, sort, itemsPerPage);
            let counter = 1 + (+data.number * +multiplier); 
           data.content.forEach(product => {
             
            $("#products").append(`<tr class="row mx-auto">
            <th class="col-md-1 text-center">${counter}</th>
            <td class="col-md-3"><div class="text-center"><img src="${product.imageUrl}" width="40" height="40" alt="Product"></div></td>
            <td class="col-md-3 text-center">${product.name}</td>
            <td class="col-md-2 text-center">${product.price}</td>
            <td class="col-md-3 text-center">
                <a href="/products/details/${product.id}" class="btn btn-secondary font-weight-bold text-white">Details</a>
                <a href="/products/edit/${product.id}" class="btn btn-success font-weight-bold text-white">Edit</a>
                <a href="/products/delete/${product.id}" class="btn btn-danger font-weight-bold text-white">Delete</a>
            </td>
            </tr>`);
            counter++;
           });
        });
}

function getPagingNumbering(data, currentURL, sort, itemsPerPage) {
    
    let totalPages = data.totalPages;
    let currentPage = +data.number;
    let previous = "#";
    let prevPrevious = "#";
    let next = "#";
    let afterNext = "#";

    if((currentPage - 1) >= 0) {
        previous = "http://localhost:8080/products/all?page=" + (currentPage - 1) + "&ipp=" + itemsPerPage;
    }

    if((currentPage - 2) >= 0) {
        prevPrevious = "http://localhost:8080/products/all?page=" + (currentPage - 2) + "&ipp=" + itemsPerPage;
    }
    
    if((currentPage + 1) <= (totalPages - 1)) {
        next = "http://localhost:8080/products/all?page=" + (currentPage + 1) + "&ipp=" + itemsPerPage;
    }

    if((currentPage + 2) <= (totalPages - 1)) {
        afterNext = "http://localhost:8080/products/all?page=" + (currentPage + 2) + "&ipp=" + itemsPerPage;
    }

    $("#previous").attr("href", previous);
    $("#next").attr("href", next);
    if(previous != "#" && next != "#") {
        $("#one").attr("href", previous).html(currentPage);
        $("#two").attr("href", currentURL).html(currentPage + 1);
        $("#three").attr("href", next).html(currentPage + 2);
    } else if(prevPrevious == "#" && previous != "#" && next == "#") {
        $("#one").attr("href", previous).html(currentPage);
        $("#two").attr("href", currentURL).html(currentPage + 1);
        $("#three").hide();
    } else if(prevPrevious != "#" && previous != "#" && next == "#") {
        $("#one").attr("href", prevPrevious).html(currentPage - 1);
        $("#two").attr("href", previous).html(currentPage);
        $("#three").attr("href", currentURL).html(currentPage + 1);
    } else if(previous == "#" && next == "#") {
        $("#one").hide();
        $("#two").attr("href", currentURL).html(currentPage + 1);
        $("#three").hide();
    } else if (previous == "#" && next != "#" && afterNext != "#") {
        $("#one").attr("href", currentURL).html(currentPage + 1);
        $("#two").attr("href", next).html(currentPage + 2);
        $("#three").attr("href", afterNext).html(currentPage + 3);
    } else if(previous == "#" && next != "#") {
        $("#one").hide();
        $("#two").attr("href", currentURL).html(currentPage + 1);
        $("#three").attr("href", next).html(currentPage + 2);
    } 
}

//  Items per page
function addIppOptions() {          
    let ipp = sessionStorage.getItem("ipp");
    if(ipp == null) {
        $("#perPage").append(`<option value="5" selected="selected">5</option>`);
        $("#perPage").append(`<option value="10">10</option>`);
        $("#perPage").append(`<option value="20">20</option>`);
    } else if(ipp == 5) {
        $("#perPage").append(`<option value="5" selected="selected">5</option>`)
        $("#perPage").append(`<option value="10">10</option>`);
        $("#perPage").append(`<option value="20">20</option>`);
    } else if(ipp == 10) {
        $("#perPage").append(`<option value="5">5</option>`);
        $("#perPage").append(`<option value="10" selected="selected">10</option>`);
        $("#perPage").append(`<option value="20">20</option>`);
    } else if(ipp == 20) {
        $("#perPage").append(`<option value="5">5</option>`);
        $("#perPage").append(`<option value="10">10</option>`);
        $("#perPage").append(`<option value="20" selected="selected">20</option>`);
    }
}

function addSortOptions() {

    let sort = sessionStorage.getItem("sort");
    if(sort == null) {
        $("#sort").append(`<option "name" selected="selected">name</option>`);
        $("#sort").append(`<option value="price-asc">price-asc</option>`);
        $("#sort").append(`<option value="price-desc">price-desc</option>`);
    } else if(sort == "name") {
        $("#sort").append(`<option value="name" selected="selected">name</option>`);
        $("#sort").append(`<option value="price-asc">price-asc</option>`);
        $("#sort").append(`<option value="price-desc">price-desc</option>`);
    } else if(sort == "price-asc") {
        $("#sort").append(`<option "name">name</option>`);
        $("#sort").append(`<option value="price-asc" selected="selected">price-asc</option>`);
        $("#sort").append(`<option value="price-desc">price-desc</option>`);
    } else if(sort == "price-desc") {
        $("#sort").append(`<option "name">name</option>`);
        $("#sort").append(`<option value="price-asc">price-asc</option>`);
        $("#sort").append(`<option value="price-desc" selected="selected">price-desc</option>`);
    }
}