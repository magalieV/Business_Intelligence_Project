window.onload = function ()
{
    let add = document.getElementById("add_element");

    add.addEventListener("click", addElement);
    listenerToButton();
    function listenerToButton() {
        let closeBtn = document.getElementsByClassName("same_line");
        for (let i = 0; i < closeBtn.length; i++) {
            let element = closeBtn[i];
            element.addEventListener("click", function() {
                var listElements = document.getElementById("list-interest");
                listElements.removeChild(this.parentNode);
                modifyUser();
            });
        }
    }

    /* Post request to modify save user */

    function saveNewInterest(interestList) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8081/profile/save", true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        var params = {
            "interests": interestList
        };
        xhr.send(JSON.stringify(params));
    }

    function modifyUser() {
        let interestList = document.getElementsByClassName("interest-info");
        let listOfObjects = [];

        for (let i = 0; i < interestList.length; i++) {
            listOfObjects.push(interestList[i].innerHTML);
        }
        saveNewInterest(listOfObjects);
    }

    /* Add Element functions */

    function checkIfElementExist(selectInterest) {
        let interestList = document.getElementsByClassName("interest-info");

        for (let i = 0; i < interestList.length; i++) {
            if (selectInterest.value === interestList[i].innerHTML)
                return true;
        }
        return false;
    }

    function addThis(element) {
        let list = document.getElementById("list-interest");

        let container = document.createElement("p");
        let newNode = document.createElement("span");
        let img = document.createElement("img");
        img.src = "/img/icon/cross.png";
        img.className = "same_line";
        newNode.className = "interest-info";
        newNode.innerHTML = element;
        container.className = "elementInterest";
        container.appendChild(newNode);
        container.appendChild(img);
        list.appendChild(container);
        img.addEventListener("click", function() {
            var list = document.getElementById("list-interest");
            list.removeChild(img.parentNode);
            modifyUser();
        });
    }

    function addElement(e) {
        let selectInterest = document.getElementById("interest_list");

        if (selectInterest.value && selectInterest.value.trim().length > 1 && !checkIfElementExist(selectInterest)) {
            addThis(selectInterest.value);
            modifyUser();
        }
    }
}