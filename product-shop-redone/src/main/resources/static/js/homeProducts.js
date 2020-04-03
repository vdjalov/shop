const refererURL = document.referrer;
cleanSessionStorage(refererURL);
if(sessionStorage.getItem("category") == null) {
	sessionStorage.setItem("category", "all");
}
const href = window.location.href;
loadProductsByCategory();

function loadProductsByCategory() {
	const category = sessionStorage.getItem("category");
	const productsPerPage = sessionStorage.getItem("productsPerPage");
	const sortBy = sessionStorage.getItem("sortBy");
	let page = href.match("page=[0-9]+");
	let pageNumber = null;
	if(page != null) {
		pageNumber = page[0].split('=')[1] - 1;
	}

	let fetchUrl = `/api/products/byCategory?cat=${category}`;
		if(productsPerPage != null) {
			fetchUrl+=`&size=${productsPerPage}`;
		}

		if(sortBy != null) {
			fetchUrl+=`&sortBy=${sortBy}`;
		}

		if(pageNumber != null && pageNumber >= 0) {
			fetchUrl+=`&page=${pageNumber}`;
		}
	
	fetchProducts(fetchUrl);
}

function fetchProducts(url) {
	$("#products").empty();
	fetch(url)
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			getNextPreviousCurrentPageNumbers(data);
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
						<h5 class="text-center font-weight-bold">Price: ${product.price.toFixed(2)}</h5>
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
	let category = e.value;
	sessionStorage.setItem("category", category);
	window.location.href = "/home";
}

function cleanSessionStorage(refererURL) {
	let prevUrlMatch = refererURL.match("home");
	if(prevUrlMatch == null) {
		sessionStorage.clear();
	}
}

function getNextPreviousCurrentPageNumbers(data) {
	
	let currentPageNumber = data.number;
	let totalPages = data.totalPages;
	let previous = "#";
	let prevPrevious = "#";
	let currentURL = "#";
	let next = "#";
	let afterNext = "#";
	let currentHref = "http://localhost:8080/home";
	if((currentPageNumber - 1) >= 0) {
		previous = currentHref + `?page=${currentPageNumber - 1}`;
	}

	if((currentPageNumber - 2) >= 0) {
		prevPrevious = currentHref + `?page=${currentPageNumber - 2}`;
	}
	currentURL = currentHref + `?page=${currentPageNumber + 1}`;
	
	if((currentPageNumber + 1) <= (totalPages -1)) {
		next =  currentHref + `?page=${currentPageNumber + 2}`;
	}

	if((currentPageNumber + 2) <= (totalPages - 1)) {
		afterNext =  currentHref + `?page=${currentPageNumber + 3}`;
	}

	if(previous != "#" && next != "#") {
        $("#one").attr("href", previous).html(currentPageNumber);
        $("#two").attr("href", currentURL).html(currentPageNumber + 1);
        $("#three").attr("href", next).html(currentPageNumber + 2);
    } else if(prevPrevious == "#" && previous != "#" && next == "#") {
        $("#one").attr("href", previous).html(currentPageNumber);
        $("#two").attr("href", currentURL).html(currentPageNumber + 1);
        $("#three").hide();
    } else if(prevPrevious != "#" && previous != "#" && next == "#") {
        $("#one").attr("href", prevPrevious).html(currentPageNumber - 1);
        $("#two").attr("href", previous).html(currentPageNumber);
        $("#three").attr("href", currentURL).html(currentPageNumber + 1);
    } else if(previous == "#" && next == "#") {
        $("#one").hide();
        $("#two").attr("href", currentURL).html(currentPageNumber + 1);
        $("#three").hide();
    } else if (previous == "#" && next != "#" && afterNext != "#") {
        $("#one").attr("href", currentURL).html(currentPageNumber + 1);
        $("#two").attr("href", next).html(currentPageNumber + 2);
        $("#three").attr("href", afterNext).html(currentPageNumber + 3);
    } else if(previous == "#" && next != "#") {
        $("#one").hide();
        $("#two").attr("href", currentURL).html(currentPageNumber + 1);
        $("#three").attr("href", next).html(currentPageNumber + 2);
    } 

}