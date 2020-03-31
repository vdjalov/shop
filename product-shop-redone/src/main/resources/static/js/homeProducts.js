
if(sessionStorage.getItem("category") == null) {
	sessionStorage.setItem("category", "all");
}

loadProductsByCategory();

function loadProductsByCategory() {
	const category = sessionStorage.getItem("category");
	let fetchUrl = `/api/products/byCategory?cat=${category}`;
	fetchProducts(fetchUrl);
}

function fetchProducts(url) {
	console.log(url);
	$("#products").empty();
	fetch(url)
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			let categoryName = sessionStorage.getItem("category");
			data.content.forEach(product => {
				let categories = [];
				product.categories.forEach(cat=> {
					categories.push(cat.category.toLowerCase());
				});
				
				if(categories.includes(categoryName) || categoryName === "all") {
				$("#products").append(`<div class="product">
					<div class="text-center">
						<a href="/products/details/${product.id}">
						<img src="${product.imageUrl}"  width="190" height="190" alt="Image not loaded...">
						</a>
					</div>
						<h5 class="text-center font-weight-bold mt-3">Name: ${product.name}</h5>
						<h5 class="text-center font-weight-bold">Price: ${product.price}</h5>
					</div>`);
				}
			})
		});

}


function setDefaultProductsPerPage() {
	const productsPerPage = $("#productsPerPage").val();
	sessionStorage.setItem("productsPerPage", productsPerPage);
	window.location.href = "/home";
}

function setDefaultSortation(){
	const sortBy = $("#sortBy").val();
	sessionStorage.setItem("sortBy", sortBy);
	window.location.href = "/home";
}

function setCategory(e) {
	console.log("in set category")
	let category = e.value;
	sessionStorage.setItem("category", category);
	window.location.href = "/home";
}