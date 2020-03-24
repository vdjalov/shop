
function loadProducts() {
  
    const currentURL =  window.location.href;
    const parsedPage = currentURL.match("page=[0-9]+");
    const parsedSort = currentURL.match("sortBy=[a-z]+");
    console.log($("#sort").val());
    console.log($("#perPage").val());
    let initUrl = "/api/products/all"
if(parsedPage != null) {
    initUrl+=`?${parsedPage}`
}

if(parsedSort!= null && parsedPage!= null) {
    initUrl+=`&${parsedSort}`;
} else if(parsedSort != null) {
    initUrl+=`?${parsedSort}`;
}

    console.log(initUrl);
    fetch(initUrl)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
           data.content.forEach(product => {
               
            $("#products").append(`<tr class="row mx-auto">
            <th class="col-md-1 text-center">1</th>
            <td class="col-md-3"><div class="text-center"><img src="${product.imageUrl}" width="40" height="40" alt="Product"></div></td>
            <td class="col-md-3 text-center">${product.name}</td>
            <td class="col-md-2 text-center">${product.price}</td>
            <td class="col-md-3 text-center">
                <a href="/products/details/${product.id}" class="btn btn-secondary font-weight-bold text-white">Details</a>
                <a href="/products/edit/${product.id}" class="btn btn-success font-weight-bold text-white">Edit</a>
                <a href="/products/delete/${product.id}" class="btn btn-danger font-weight-bold text-white">Delete</a>
            </td>
            </tr>`)
           });
        })


    
}