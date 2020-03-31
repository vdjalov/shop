

$("#categories").append(`
    <div class="custom-control custom-radio mt-3">
    <input type="radio" id="allRadio" name="selection" class="custom-control-input" value="all" checked onchange="setCategory(this)">
    <label class="h4 custom-control-label" for="allRadio">All</label>
</div>`);

let selectedCategory = sessionStorage.getItem("category");
let URL = "/api/category/all";
fetch(URL)
    .then((response) => {
        return response.json();
    })
    .then((data) => {
      
        data.forEach(category => {
            let element = ` 
            <div class="custom-control custom-radio mt-3">
                <input type="radio" id="${category.category.toLowerCase()}Radio" name="selection" class="custom-control-input" value="${category.category.toLowerCase()}" 
                            onchange="setCategory(this)"></input>
                <label class="h4 custom-control-label" for="${category.category.toLowerCase()}Radio" >${category.category}</label>
            </div>`;
       
            if(category.category.toLowerCase() === selectedCategory) {
                element = ` 
            <div class="custom-control custom-radio mt-3">
            <input type="radio" id="${category.category.toLowerCase()}Radio" name="selection" 
                class="custom-control-input" value="${category.category.toLowerCase()}" checked onchange="setCategory(this)"/>
            <label class="h4 custom-control-label" for="${category.category.toLowerCase()}Radio">${category.category}</label>
            </div>`;
            }
            $("#categories").append(element);
        });
       
    });                        
