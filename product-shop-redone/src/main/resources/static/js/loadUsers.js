const currentURL =  window.location.href;
const parsedURL = currentURL.match("page=[0-9]+");
let URL = "/api/allUsers";
if(parsedURL != null) {
    URL+= `?${parsedURL[0]}`;
}

let URLs = {
    getUsers: URL,
}

function userAuthorities(authorities) {
    let result = [];
    for(let i = 0; i < authorities.length; i++) {
        result.push(authorities[i].authority);
    }
    return result;
}


function getRolesOptions(authorities, email) {
    const allAuthorities = ["ADMIN", "USER", "ROOT", "MODERATOR"];
    const currentUserAuthorities = userAuthorities(authorities);
    let element =``;

    allAuthorities.forEach(auth => {
        if(!currentUserAuthorities.includes(auth)) {
            element+=
                `<form action="/api/users/set-role/${auth.toLowerCase()}/${email}" method="post" class="mb-1 mx-1"><input type="hidden" name="_csrf" value="80417983-fe8b-45c7-963e-31f6915a1560">
                     <button class="btn btn-dark">${auth}</button>
                </form>`;
        }
    })
    return element;
}


function getPagingNumbering(data) {
    let totalPages = data.totalPages;
    let currentPage = +data.number;
    let previous = "#";
    let prevPrevious = "#";
    let next = "#";
    let afterNext = "#";
    
    if((currentPage - 1) >= 0) {
        previous = "http://localhost:8080/users/all?page=" + (currentPage - 1);
    }

    if((currentPage - 2) >= 0) {
        prevPrevious = "http://localhost:8080/users/all?page=" + (currentPage - 2);
    }
    
    if((currentPage + 1) <= (totalPages - 1)) {
        next = "http://localhost:8080/users/all?page=" + (currentPage + 1);
    }

    if((currentPage + 2) <= (totalPages - 1)) {
        afterNext = "http://localhost:8080/users/all?page=" + (currentPage + 2);
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
        $("#one").attr("href", prevPrevious).html(currentPage);
        $("#two").attr("href", previous).html(currentPage - 1);
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


fetch(URLs.getUsers)
    .then((response) => {
        return response.json();
    })
    .then((data) => {
        console.log(data);
         getPagingNumbering(data);
        data.content.forEach(user => {
            console.log(user);
          
                    $("#users").append(`<tr class="row mx-auto">
                   <th class="col-md-2 text-center"></th>
                   <td class="col-md-3 text-center">${user.email.split('@')[0]}</td>
                   <td class="col-md-2 text-center">${user.email}</td>
                   <td class="col-md-2 text-center">${userAuthorities(user.authorities).join(" ,")}</td>
                   <td class="col-md-3 text-center">
                        <div class="row">
                            ${getRolesOptions(user.authorities, user.email)}
                        </div>
                          
                   </td>
               </tr>`)
        });
    })




